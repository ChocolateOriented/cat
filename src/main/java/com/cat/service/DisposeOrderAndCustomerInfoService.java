package com.cat.service;

import com.cat.module.dto.CustomerAllInfo;
import com.cat.module.dto.RepaymentMessage;
import com.cat.module.entity.*;
import com.cat.module.enums.BankType;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.module.vo.OrderInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
    private TaskService taskBaseService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private TaskLogService taskLogService;

    /**
     * 获取用户信息
     * @param orderId
     * @return
     */
    public OrderInfo getCustomerAllInfo(String orderId) {
        Task task = taskBaseService.findTaskByOrderId(orderId);
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
        List<Contact> contactList = customerAllInfo.getContactList();
        List<Contact> dbContactList = contactService.fetchContactsByCustomerId(customerBaseInfo.getCustomerId());
        if (dbContactList == null || dbContactList.isEmpty()) {
            for (Contact contact : contactList) {
                contact.setId(this.generateId());
                contactService.insert(contact);
            }
        } else {
            for (Contact contact : contactList) {
                if (!dbContactList.contains(contact)) {
                    contact.setId(this.generateId());
                    contactService.insert(contact);
                }
            }
        }

        //保存任务信息
        Task task = customerAllInfo.getTask();
        task.setId(this.generateId());
        task.setCollectTaskStatus(CollectTaskStatus.UNOPEND_TASK);
        taskBaseService.insert(task);

    }

    /**
     * 处理还款信息
     * @param repaymentMessage
     */
    @Transactional(rollbackFor = Exception.class)
    public void disposeRepayment(RepaymentMessage repaymentMessage) {
        Task dbTask = taskBaseService.findByOrderId(repaymentMessage.getOrderId());
        TaskLog taskLog = new TaskLog();
        if ("REPAY_POSTPONE".equals(repaymentMessage.getPayType())) {
            //todo
            //如果是延期还款
            //延期次数
            dbTask.setPostponeCount(repaymentMessage.getPostponeCount());
            //到期还款日
//            dbTask.setRepaymentTime(repaymentMessage.getRepaymentTime());//todo mq没有还款日期
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_POSTPONE);
            //添加延期金额
            BigDecimal oldAmount = dbTask.getPostponeTotalAmount();
            dbTask.setPostponeTotalAmount((oldAmount == null ? BigDecimal.ZERO : oldAmount).add(repaymentMessage.getRepayAmount()));
            //借贷期限增加
//            dbTask.setLoanTerm(dbTask.getLoanTerm() ==null? + repaymentMessage.getPostponeCount());
            //任务结束时间
            dbTask.setTaskEndTime(new Date());//todo 是否需要记录任务结束时间
            //转换成日志表,对象
            taskLog = covertToTaskLog(dbTask, repaymentMessage);
            //清空联系人信息
            dbTask = emptyCollectionInfo(dbTask);

        } else {
            //还清
            //payoffTime还清时间
//            dbTask.setPayoffTime(repaymentMessage.getRepaymentTime());//todo
            //任务结束时间
            dbTask.setTaskEndTime(new Date());//todo 是否需要记录任务结束时间
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_FINISHED);
            dbTask.setIspayoff(true);

            //日志表:
            taskLog = new TaskLog();
            BeanUtils.copyProperties(dbTask, taskLog, "id");
            //催收员行为状态
            taskLog.setBehaviorStatus(BehaviorStatus.FINISHED);
            //taskid
            taskLog.setTaskId(dbTask.getId());
            //到期时间
            taskLog.setOverdueDays(calculateOverdueDays(dbTask.getRepaymentTime()));
            //渠道
            taskLog.setPlatformext(repaymentMessage.getChannel());
            //还清后应催金额:0
            taskLog.setCreditamount(BigDecimal.ZERO);
            //本次还款金额
            taskLog.setRepaymentAmount(repaymentMessage.getRepayAmount());

        }
        taskBaseService.updateTaskStatus(dbTask);
        taskLogService.insert(taskLog);
    }

    private Task emptyCollectionInfo(Task dbTask) {
        //清空催收人信息
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

    private TaskLog covertToTaskLog(Task dbTask, RepaymentMessage repaymentMessage) {
        TaskLog taskLog = new TaskLog();
        BeanUtils.copyProperties(dbTask, taskLog, "id");
        taskLog.setId(this.generateId());
        taskLog.setTaskId(dbTask.getId());
        taskLog.setOverdueDays(calculateOverdueDays(dbTask.getRepaymentTime()));
        taskLog.setBehaviorStatus(BehaviorStatus.POSTPONE);
        taskLog.setPlatformext(repaymentMessage.getChannel());
        //延期后,应催金额:本金+利息
        taskLog.setCreditamount(dbTask.getLoanAmount().add(dbTask.getInterestValue()));
        return taskLog;
    }

    private Integer calculateOverdueDays(Date date) {
        long betweenDays = (System.currentTimeMillis() - date.getTime())/(1000*60*60*24);
        if (betweenDays <= 0) {
            betweenDays = 0;
        }
        return Integer.parseInt(betweenDays+"");
    }
    /**
     * 转换成OrderInfo
     * @param task
     * @param bank
     * @param customerBaseInfo
     * @return
     */
    private OrderInfo convertToOrderInfo(Task task, Bank bank, CustomerBaseInfo customerBaseInfo) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(task.getOrderId());
        orderInfo.setName(task.getCustomerName());
        orderInfo.setMobile(customerBaseInfo.getMobile());
        orderInfo.setGender(customerBaseInfo.getGender());
        orderInfo.setIdCard(bank.getIdCard());
        orderInfo.setIdCardAddress(customerBaseInfo.getIdCardAddress());
        orderInfo.setCustomerTotalAmount(task.getRepayAmount());
        orderInfo.setPrincipal(task.getLoanAmount());
        orderInfo.setRepaymentTime(task.getRepaymentTime());
        orderInfo.setOverdueFee(task.getOverDueAmount());
        orderInfo.setPrincipalAndInterest(task.getOrderAmount());
        orderInfo.setLentAmount(task.getLentAmount());
        orderInfo.setInterest(task.getInterestValue());
        orderInfo.setLoanTerm(task.getLoanTerm());
        orderInfo.setPostponeCount(task.getPostponeCount());
        orderInfo.setPostponeAmount(task.getPostponeTotalAmount());
        orderInfo.setBankName(bank.getBankName());
        orderInfo.setCollectionTime(task.getCollectTime().getTime());
        orderInfo.setBankNo(bank.getBankCard());
        return orderInfo;
    }
}
