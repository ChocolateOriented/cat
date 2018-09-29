package com.cat.web;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.dto.BlackListDto;
import com.cat.module.vo.OrderInfo;
import com.cat.service.CustomerService;
import com.cat.service.DisposeOrderAndCustomerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author cyuan
 * @date 2018/9/20
 */
@RestController
@RequestMapping("/cat/v1/customer")
public class CustomerController extends BaseController{
    @Autowired
    private DisposeOrderAndCustomerInfoService disposeCustomerInfoService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("get_customer_and_order_info")
    public Results getCustomerInfo(@RequestParam("orderId") String orderId) {
        try {
            OrderInfo orderInfo = disposeCustomerInfoService.getCustomerAllInfo(orderId);
            if (orderInfo == null) {
                return new Results(ResultConstant.EMPTY_ENTITY, "没有此订单");
            }
            return Results.ok().putData(orderInfo);

        } catch (Exception e) {
            logger.info("获取客户信息异常", e);
            return new Results(ResultConstant.INNER_ERROR);
        }
    }

    @PostMapping("blackList_customer")
    public Results blackList(@Validated @RequestBody BlackListDto blackListDto,BindingResult bindingResult){
      if (bindingResult.hasErrors()) {
        return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
      }
      try {
        customerService.blackList(blackListDto);
      }catch (Exception e){
        logger.info("拉黑失败"+blackListDto,e);
        return new Results(ResultConstant.INNER_ERROR,"拉黑失败:"+e.getMessage());
      }
      return Results.ok();
    }
}
