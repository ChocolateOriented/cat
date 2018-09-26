package com.cat.web;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.vo.OrderInfo;
import com.cat.service.DisposeOrderAndCustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
@RestController
@RequestMapping("customer")
public class CustomerController extends BaseController{
    @Autowired
    private DisposeOrderAndCustomerInfoService disposeCustomerInfoService;
    @GetMapping("customer_info")
    public Results getCustomerInfo(@RequestParam("orderId") String orderId) {
        try {
            OrderInfo orderInfo = disposeCustomerInfoService.getCustomerAllInfo(orderId);
            return Results.ok().putData(orderInfo);
        } catch (Exception e) {
            logger.info("获取客户信息异常", e);
            return new Results(ResultConstant.INNER_ERROR);
        }
    }
}
