package com.cat.service;

import com.cat.exception.ServiceException;
import com.cat.manager.PaymentManager;
import com.cat.mapper.ManualPaymentsMapper;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.ManualPayments;
import com.cat.module.entity.Task;
import com.cat.util.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author cyuan
 * @date 2018/10/10
 */
@Service
public class ManualPaymentService extends BaseService {

    @Value("${feignClient.raptor.primaryKey}")
    private String privateKey;

    @Autowired
    private ManualPaymentsMapper manualPaymentsMapper;

    @Autowired
    private PaymentManager paymentManager;

    @Autowired
    private TaskService taskService;

    /**
     * 处理手动还款
     * @param manualPayments
     * @throws ServiceException
     */
    @Transactional(rollbackFor = Exception.class)
    public void disposeManualPayment(ManualPayments manualPayments) throws ServiceException{
        //检查获取这笔订单
        Task task = taskService.findByOrderId(manualPayments.getOrderId());
        if (task == null) {
            logger.info("没有此订单,订单号:{}", manualPayments.getOrderId());
            throw new ServiceException("未找到此订单");
        }
        Map<String, String> repayInfo = covertToRepayInfo(manualPayments, task);

        //调用接口
        Results repay =  paymentManager.repay(repayInfo);
        if (!repay.isSuccess()) {
            logger.info("调用repay接口异常获取结果:{}",repay);
            throw new ServiceException(repay.getMessage());
        }
        logger.info("调用repay接口获取结果成功:{}",repay);

        //对参数进行处理
        manualPayments.setCustomerId(task.getCustomerId());
        manualPayments.setUserId(task.getCollectorId());
        manualPayments.setUserName(task.getCollectorName());
        manualPayments.setProductType(task.getProductType());
        manualPayments.setId(generateId());
        //如果返回成功保存到数据库
        manualPaymentsMapper.insert(manualPayments);
        logger.info("保存手动还款数据成功:{}", manualPayments);
    }

    /**
     * 转换成调用手动还款接口参数类型
     * @param manualPayments
     * @param task
     * @return
     */
    private Map<String, String> covertToRepayInfo(ManualPayments manualPayments, Task task) {
        Map<String, String> map = new HashMap<>();
        map.put("userCode", task.getCustomerId());
        map.put("orderId", manualPayments.getOrderId());
        map.put("type", "PAYOFF".equals(manualPayments.getPaymentStatus()) ? "REPAY" : manualPayments.getPaymentStatus());
        map.put("amount", manualPayments.getActualPaymentAmount().toString());
        map.put("creator", manualPayments.getCreateBy());
        map.put("reliefReason", null);
        String sign = Md5Encrypt.sign(map, privateKey);
        map.put("sign",sign);
        return map;
    }
}
