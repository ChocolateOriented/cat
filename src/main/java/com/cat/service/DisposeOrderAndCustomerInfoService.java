package com.cat.service;

import com.cat.module.dto.CustomerAllInfo;
import com.cat.module.dto.RepaymentMessage;
import com.cat.module.entity.*;
import com.cat.module.enums.BehaviorStatus;
import com.cat.module.enums.CollectTaskStatus;
import com.cat.module.vo.OrderInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TaskBaseService taskBaseService;

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
        OrderInfo orderInfo = new OrderInfo();
        Task task = taskBaseService.findTaskByOrderId(orderId);
        Bank bank = bankService.findBankByBankNo(task.getBankNo());
        CustomerBaseInfo customerBaseInfo = customerService.fetchCustomerByCustomerId(bank.getCustomerId());
        BeanUtils.copyProperties(task, orderInfo);
        BeanUtils.copyProperties(bank, orderInfo);
        BeanUtils.copyProperties(customerBaseInfo, orderInfo);
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
        bankService.insertBank(bank);

        //保存用户联系人信息
        List<Contact> contactList = customerAllInfo.getContactList();
        Integer contactInfoCount = contactService.countByCustomerId(customerBaseInfo.getCustomerId());
        if (contactInfoCount != null || contactInfoCount != 0) {
            contactService.deleteContact(customerBaseInfo.getCustomerId());
        }
        contactList.forEach(x->contactService.insert(x));
        ;

        //保存任务信息
        Task task = customerAllInfo.getTask();
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
        if ("REPAY_POSTPONE" == repaymentMessage.getPayType()) {
            //todo
            //如果是延期还款
            //延期次数
            dbTask.setPostponeCount(repaymentMessage.getPostponeCount());
            //到期还款日
//            dbTask.setRepaymentTime(repaymentMessage.getRepaymentTime());//todo
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_POSTPONE);
            //接待期限增加
            //日志表:
            BeanUtils.copyProperties(dbTask, taskLog);
            taskLog.setBehaviorStatus(BehaviorStatus.POSTPONE);

        } else {
            //还清
            //payoffTime还清时间
//            dbTask.setPayoffTime(repaymentMessage.getRepaymentTime());//todo
            //催收任务状态
            dbTask.setCollectTaskStatus(CollectTaskStatus.TASK_POSTPONE);
            dbTask.setIspayoff(true);
            //日志表:
            taskLog = new TaskLog();
            BeanUtils.copyProperties(dbTask, taskLog);
            //催收员行为状态
            taskLog.setBehaviorStatus(BehaviorStatus.POSTPONE);
        }
        taskBaseService.updateTaskStatus(dbTask);
        taskLogService.insert(taskLog);
    }
}
