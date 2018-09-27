package com.cat.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cat.module.dto.CustomerAllInfo;
import com.cat.module.dto.RepaymentMessage;
import com.cat.module.entity.Bank;
import com.cat.module.entity.Contact;
import com.cat.module.entity.CustomerBaseInfo;
import com.cat.module.entity.Task;
import com.cat.service.DisposeOrderAndCustomerInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
@Component
public class CustomerAndOrderListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${spring.rabbitmq.loanMq.receivedAllRoutingKey}")
    private String receivedAllRoutingKey;

    @Value("${spring.rabbitmq.repayMq.orderRepaymentRoutingKey}")
    private String orderRepaymentRoutingKey;


    @Autowired
    private DisposeOrderAndCustomerInfoService disposeOrderAndCustomerInfoService;

    @RabbitListener(queues = {"${spring.rabbitmq.loanMq.queue}","${spring.rabbitmq.repayMq.queue}"})
    public void recieveLoanInfo(Message message) {
        String messageId = null;
        try {
            messageId = message.getMessageProperties().getMessageId();
            logger.info("接收消息,messageId: {}", messageId);
            String receivedRoutingKey = message.getMessageProperties().getReceivedRoutingKey();
            if(receivedAllRoutingKey.equals(receivedRoutingKey)) {
                logger.info("接收到订单消息和用户消息, messageId: {}, message: {}", messageId, new String(message.getBody() , "UTF-8"));
                disposeCustomerAllInfoMessage(message);
            } else if (orderRepaymentRoutingKey.equals(receivedRoutingKey)) {
                logger.info("接收到已还款或延期消息, messageId: {}, message: {}", messageId, new String(message.getBody() , "UTF-8"));
                disposeRepaymentMessage(message);
            }
        } catch (Exception e) {
            logger.info("接收消息异常,messageId:"+messageId+",message:"+new String(message.getBody()), e);
        }
    }


    /**
     * 处理接收到的订单
     * @param message
     * @throws Exception
     */
    private void disposeCustomerAllInfoMessage(Message message) throws Exception{
        JSONObject jsonObject = JSON.parseObject(new String(message.getBody(), "utf-8"));
        JSONObject lendInfo = jsonObject.getJSONObject("lendInfo");
        JSONObject userInfo = jsonObject.getJSONObject("userInfo");
        lendInfo.putAll(userInfo);
        CustomerAllInfo customerAllInfo = parseToCustomerAllInfo(lendInfo);
        disposeOrderAndCustomerInfoService.disposeOrderAndCustomer(customerAllInfo);
    }

    /**
     * 处理还款信息或者延期信息
     * @param message
     */
    private void disposeRepaymentMessage(Message message) throws Exception {
        RepaymentMessage repaymentMessage = parseToRepaymentMessage(message);
        disposeOrderAndCustomerInfoService.disposeRepayment(repaymentMessage);
    }

    /**
     * 解析还款信息
     * @param message
     * @return
     * @throws Exception
     */
    private RepaymentMessage parseToRepaymentMessage(Message message) throws Exception {
        System.out.println(new String(message.getBody(), "utf-8"));
        JSONObject repayInfo = JSON.parseObject(new String(message.getBody(), "utf-8")).getJSONObject("repayInfo");
        return JSON.parseObject(repayInfo.toJSONString(), RepaymentMessage.class);
    }

    /**
     * 解析接收到的订单信息和用户信息
     * @param message
     * @return
     * @throws Exception
     */
    private CustomerAllInfo parseToCustomerAllInfo(JSONObject message) throws Exception {
        CustomerBaseInfo customerBaseInfo = parseToCustomerInfo(message);
        Task task = parseToTask(message);
        Bank bank = parseToBank(message);
//        List<Contact> contactList = parseToContactInfo(message);

        CustomerAllInfo customerAllInfo = new CustomerAllInfo();
        customerAllInfo.setBank(bank);
        customerAllInfo.setTask(task);
//        customerAllInfo.setContactList(contactList);
        customerAllInfo.setCustomerBaseInfo(customerBaseInfo);
        return customerAllInfo;
    }

    /**
     * 解析成联系人对象
     * @param message
     * @return
     */
    private List<Contact> parseToContactInfo(JSONObject message) {
        String contactsListString = message.getString("contactsList");
        Boolean flag = contactListIsArray(contactsListString);
//        JSONObject contactsListJSON = JSON.parseObject(contactsListString);
        List<Contact> contactList = new ArrayList<>();
        if (!flag) {
            contactList = JSON.parseObject(contactsListString).getJSONArray("contact").toJavaList(Contact.class);
        } else {
            contactList = JSON.parseArray(contactsListString, Contact.class);
        }
        for (Contact contact : contactList) {
            contact.setMobile(message.getString("mobile"));
            contact.setCustomerId(message.getString("ownerId"));
        }
        return contactList;
    }

    private Boolean contactListIsArray(String contactsListString) {
        String c = contactsListString.trim().charAt(0)+"";
        if ("[".equals(c)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 解析成银行信息
     * @param message
     * @return
     */
    private Bank parseToBank(JSONObject message) {
        return JSON.parseObject(message.toJSONString(), Bank.class);
    }

    /**
     * 解析成任务
     * @param message
     * @return
     */
    private Task parseToTask(JSONObject message) {
        return JSON.parseObject(message.toJSONString(), Task.class);
    }

    /**
     * 解析人用户信息
     * @param message
     * @return
     * @throws Exception
     */
    private CustomerBaseInfo parseToCustomerInfo(JSONObject message) throws Exception {
        CustomerBaseInfo customerBaseInfo = JSON.parseObject(message.toJSONString(), CustomerBaseInfo.class);
        return customerBaseInfo;
    }

}
