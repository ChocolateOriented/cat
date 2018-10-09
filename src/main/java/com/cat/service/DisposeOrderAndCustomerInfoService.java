package com.cat.service;

import com.cat.module.dto.CustomerAllInfo;
import com.cat.module.dto.RepaymentMessage;
import com.cat.module.entity.*;
import com.cat.module.enums.BankType;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.module.enums.OrderStatus;
import com.cat.module.vo.OrderInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cyuan
 * @date 2018/9/19
 */
@Service
public class DisposeOrderAndCustomerInfoService extends BaseService {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private BankService bankService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TaskLogService taskLogService;

    private static final String REPAY_POSTPONE = "REPAY_POSTPONE";
    /**
     * 获取用户信息
     * @param orderId
     * @return
     */
    public OrderInfo getCustomerAllInfo(String orderId) {
        Task task = taskService.findByOrderId(orderId);
        if (task == null) {
            return null;
        }
        Bank bank = bankService.findBankByBankNoAndType(task.getBankNo(),BankType.LEND);
        CustomerBaseInfo customerBaseInfo = customerService.fetchCustomerByCustomerId(bank.getCustomerId());
        OrderInfo orderInfo = convertToOrderInfo(task, bank, customerBaseInfo);
        return orderInfo;
    }

    /**
     * 处理订单信息
     * @param customerAllInfo
     */
    @Transactional(rollbackFor = Exception.class)
    public void disposeOrderAndCustomer(CustomerAllInfo customerAllInfo) {
        //保存用户基本信息
        CustomerBaseInfo customerBaseInfo = customerAllInfo.getCustomerBaseInfo();
        CustomerBaseInfo dbCustomerInfo = customerService.fetchCustomerByCustomerId(customerBaseInfo.getCustomerId());
        if (dbCustomerInfo == null) {
            customerBaseInfo.setId(this.generateId());
            customerService.insertCustomer(customerBaseInfo);
        } else {
            customerBaseInfo.setId(dbCustomerInfo.getId());
            customerService.updateCustomer(customerBaseInfo);
        }

        //保存银行卡信息
        Bank bank = customerAllInfo.getBank();
        bank.setType(BankType.LEND.name());
        Bank dbBank = bankService.findBankByBankNoAndType(bank.getBankCard(), BankType.LEND);
        if (dbBank == null) {
            bank.setId(this.generateId());
            bankService.insertBank(bank);
        }

        //保存用户联系人信息
//        List<Contact> contactList = customerAllInfo.getContactList();
//        List<Contact> dbContactList = contactService.fetchContactsByCustomerId(customerBaseInfo.getCustomerId());
//        if (dbContactList == null || dbContactList.isEmpty()) {
//            contactList.forEach(x->x.setId(this.generateId()));
//            contactService.insertAll(contactList);
//        } else {
//            List<Contact> diffContacts = new ArrayList<>();
//            for (Contact contact : contactList) {
//                if (!dbContactList.contains(contact)) {
//                    contact.setId(this.generateId());
//                    diffContacts.add(contact);
//                }
//            }
//            if (!diffContacts.isEmpty()) {
//                contactService.insertAll(diffContacts);
//            }
//        }

        //保存任务信息
        Task task = customerAllInfo.getTask();
        Task dbTask = taskService.findByOrderId(task.getOrderId());
        if (dbTask != null) {
            throw new RuntimeException("此订单已存在,task:"+task);
        }
        if (task.getPayoffTime() != null && OrderStatus.PAYOFF.equals(task.getOrderStatus())) {
            task.setCollectTaskStatus(CollectTaskStatus.TASK_FINISHED);
            task.setIspayoff(true);
        } else {
            task.setCollectTaskStatus(CollectTaskStatus.UNOPEND_TASK);
        }
        task.setId(this.generateId());
        taskService.insert(task);
        logger.info("插入订单任务成功,orderID:{}",task.getOrderId());
    }

    /**
     * 处理还款信息
     * @param repaymentMessage
     */
    @Transactional(rollbackFor = Exception.class)
    public void disposeRepayment(RepaymentMessage repaymentMessage) {
        Task dbTask = taskService.findByOrderId(repaymentMessage.getOrderId());
        if (dbTask == null) {
            throw new RuntimeException("任务不存在,延期或还款失败");
        }
        if (dbTask.isIspayoff()) {
            throw new RuntimeException("订单已还清");
        }
        TaskLog taskLog = null;
        //还款类型是延期还款,并且没有还清时间
        if (REPAY_POSTPONE.equals(repaymentMessage.getPayType()) && repaymentMessage.getPayoffTime() == null) {
            //如果是延期还款
            dbTask = coverToTask(dbTask, repaymentMessage, REPAY_POSTPONE);
            //转换成日志表,对象
            taskLog = covertToTaskLog(dbTask, repaymentMessage, REPAY_POSTPONE);
            //清空催收人信息
            dbTask = emptyCollectionInfo(dbTask);
        } else if (repaymentMessage.getPayoffTime() != null){ //还清时间不为null说明已还清
            //还清
            dbTask = coverToTask(dbTask, repaymentMessage, repaymentMessage.getPayType());
            //日志表:
            taskLog = covertToTaskLog(dbTask, repaymentMessage, repaymentMessage.getPayType());
        }
        taskService.updateTaskStatus(dbTask);
        taskLogService.insert(taskLog);
        logger.info("延期或还款成功,orderId:{}", dbTask.getOrderId());
    }

    /**
     * 转换成任务对象
     * @param dbTask
     * @param repaymentMessage
     * @param repayPostpone
     * @return
     */
    private Task coverToTask(Task dbTask, RepaymentMessage repaymentMessage, String repayPostpone) {
        if (REPAY_POSTPONE.equals(repayPostpone) && repaymentMessage.getPayoffTime() == null) {
            //延期次数
            dbTask.setPostponeCount(repaymentMessage.getPostponeCount());
            //到期还款日
            dbTask.setRepaymentTime(repaymentMessage.getRepaymentDate());
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_POSTPONE);
            //添加延期金额
            BigDecimal oldAmount = dbTask.getPostponeTotalAmount();
            dbTask.setPostponeTotalAmount((oldAmount == null ? BigDecimal.ZERO : oldAmount).add(repaymentMessage.getRepayAmount()));
        } else if (repaymentMessage.getPayoffTime() != null){
            //payoffTime还清时间
            dbTask.setPayoffTime(repaymentMessage.getPayoffTime());
            //任务结束时间
            dbTask.setTaskEndTime(new Date());
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_FINISHED);
            //修改订单状态
            dbTask.setOrderStatus(OrderStatus.PAYOFF.name());
            dbTask.setIspayoff(true);
        }
        return dbTask;
    }

    /**
     * 清空催收人信息
     * @param dbTask
     * @return
     */
    private Task emptyCollectionInfo(Task dbTask) {
        dbTask.setCollectorId(null);
        dbTask.setCollectorName(null);
        dbTask.setTaskStartTime(null);
        dbTask.setTaskEndTime(null);
        dbTask.setCollectPeriodBegin(null);
        dbTask.setCollectPeriodEnd(null);
        dbTask.setCollectTelRemark(null);
        dbTask.setCollectTime(null);
        dbTask.setCollectCycle(null);
        return dbTask;
    }

    /**
     * 转换成任务日志对象
     * @param dbTask
     * @param repaymentMessage
     * @param type
     * @return
     */
    private TaskLog covertToTaskLog(Task dbTask, RepaymentMessage repaymentMessage, String type) {
        TaskLog taskLog = new TaskLog();
        BeanUtils.copyProperties(dbTask, taskLog, "id");
        if (REPAY_POSTPONE.equals(type) && repaymentMessage.getPayoffTime() == null) {
            //行为状态
            taskLog.setBehaviorStatus(BehaviorStatus.POSTPONE);
            //延期后,应催金额:本金+利息
            taskLog.setCreditamount(dbTask.getLoanAmount().add(dbTask.getInterestValue()));
            //逾期天数
            taskLog.setOverdueDays(taskLog.calculateOverdueDays());
        } else if (repaymentMessage.getPayoffTime() != null) {
            //催收员行为状态
            taskLog.setBehaviorStatus(BehaviorStatus.FINISHED);
            //还清后应催金额:0
            taskLog.setCreditamount(BigDecimal.ZERO);
        }
        //taskid
        taskLog.setTaskId(dbTask.getId());
        //渠道
        taskLog.setPlatformext(repaymentMessage.getChannel());
        //本次还款金额
        taskLog.setRepaymentAmount(repaymentMessage.getRepayAmount());
        taskLog.setId(this.generateId());

        return taskLog;
    }


    /**
     * 转换成OrderInfo
     * @param task
     * @param bank
     * @param customerBaseInfo
     * @return0
     */
    private OrderInfo convertToOrderInfo(Task task, Bank bank, CustomerBaseInfo customerBaseInfo) {
        OrderInfo orderInfo = new OrderInfo();
        if (bank != null) {
            orderInfo.setIdCard(bank.getIdCard());
            orderInfo.setBankName(bank.getBankName());
            orderInfo.setBankNo(bank.getBankCard());
            orderInfo.setCustomerId(bank.getCustomerId());
        }

        if (customerBaseInfo != null) {
            orderInfo.setMobile(customerBaseInfo.getMobile());
            orderInfo.setGender(customerBaseInfo.getGender());
            orderInfo.setIdCardAddress(customerBaseInfo.getIdCardAddress());
            orderInfo.setBlacklist(customerBaseInfo.getBlacklist());
        }

        if (task != null) {
            orderInfo.setOrderId(task.getOrderId());
            orderInfo.setName(task.getCustomerName());
            orderInfo.setCustomerTotalAmount(task.getRepayAmount());
            orderInfo.setPrincipal(task.getLoanAmount());
            orderInfo.setRepaymentTime(task.getRepaymentTime());
            orderInfo.setOverdueFee(task.getOverDueAmount());
            orderInfo.setPrincipalAndInterest(task.getOrderAmount());
            orderInfo.setLentAmount(task.getLentAmount());
            orderInfo.setInterest(task.getInterestValue());
            orderInfo.setLoanTerm(task.getLoanTerm());
            orderInfo.setPostponeCount(task.getPostponeCount() );
            orderInfo.setPostponeAmount(task.getPostponeTotalAmount() == null ? BigDecimal.ZERO : task.getPostponeTotalAmount());
            if (task.getLendTime() != null) {
                orderInfo.setCollectionTime(task.getLendTime().getTime());
            }
            orderInfo.setReliefAmount(task.getReliefAmount() == null ? BigDecimal.ZERO : task.getReliefAmount());
        }

//        orderInfo.setMobileLocation();todo 手机号归属地
        return orderInfo;
    }
}
