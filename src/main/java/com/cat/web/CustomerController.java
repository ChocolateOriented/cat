package com.cat.web;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.User;
import com.cat.module.enums.Role;
import com.cat.module.dto.BlackListDto;
import com.cat.module.vo.OrderInfo;
import com.cat.repository.UserRepository;
import com.cat.service.CustomerService;
import com.cat.service.DisposeOrderAndCustomerInfoService;

import javax.servlet.http.HttpServletRequest;

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
	@Autowired 
	private	UserRepository userRepository;
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

    @PostMapping("blacklist_customer")
    public Results blackList(@Validated @RequestBody BlackListDto blackListDto,BindingResult bindingResult, HttpServletRequest request){
      if (bindingResult.hasErrors()) {
        return new Results(ResultConstant.EMPTY_PARAM, getFieldErrorsMessages(bindingResult));
      }
        String userId = request.getHeader("User-Id");
		User user = userRepository.findOne(userId);
		if(user == null){
			logger.warn("该用户不存在,userID={}",userId);
			return new Results(-1L,"您没有权限");
		}
		Role role = user.getRole() ;
		if(role == null || role == Role.COLLECTOR ){
			logger.warn("该用户没分配角色,userID={}",userId);
			return new Results(-1L,"您没有权限");
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
