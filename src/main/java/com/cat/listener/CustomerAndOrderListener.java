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
import java.util.List;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
@Component
public class CustomerAndOrderListener {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${rabbit.customerAndOrder.receivedAllRoutingKey}")
    private String receivedAllRoutingKey;

    @Value("${rabbit.customerAndOrder.orderRepaymentRoutingKey}")
    private String orderRepaymentRoutingKey;


    @Autowired
    private DisposeOrderAndCustomerInfoService disposeOrderAndCustomerInfoService;

    @RabbitListener(queues = "${rabbit.customerAndOrder.queue}")
    public void recieveRegisterInfo(Message message) {
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

    private RepaymentMessage parseToRepaymentMessage(Message message) throws Exception {
        JSONObject repayInfo = JSON.parseObject(new String(message.getBody(), "utf-8")).getJSONObject("repayInfo");
        return JSON.parseObject(repayInfo.toJSONString(), RepaymentMessage.class);
    }

    private CustomerAllInfo parseToCustomerAllInfo(JSONObject message) throws Exception {
        CustomerBaseInfo customerBaseInfo = parseToCustomerInfo(message);
        Task task = parseToTask(message);
        Bank bank = parseToBank(message);
        List<Contact> contactList = parseToContactInfo(message);

        CustomerAllInfo customerAllInfo = new CustomerAllInfo();
        customerAllInfo.setBank(bank);
        customerAllInfo.setTask(task);
        customerAllInfo.setContactList(contactList);
        customerAllInfo.setCustomerBaseInfo(customerBaseInfo);
        return customerAllInfo;
    }

    private List<Contact> parseToContactInfo(JSONObject message) {
        String string = message.getJSONArray("contactsList").toString();
        List<Contact> contactList = JSON.parseArray(string, Contact.class);
        for (Contact contact : contactList) {
            contact.setMobile(message.getString("mobile"));
            contact.setCustomerId(message.getString("ownerId"));
        }
        return contactList;
    }

    private Bank parseToBank(JSONObject message) {
        return JSON.parseObject(message.toJSONString(), Bank.class);
    }

    private Task parseToTask(JSONObject message) {
        return JSON.parseObject(message.toJSONString(), Task.class);
    }

    private CustomerBaseInfo parseToCustomerInfo(JSONObject message) throws Exception {
        CustomerBaseInfo customerBaseInfo = JSON.parseObject(message.toJSONString(), CustomerBaseInfo.class);
        return customerBaseInfo;
    }

}
