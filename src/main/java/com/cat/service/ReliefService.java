package com.cat.service;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.fastjson.JSON;
import com.cat.manager.RaptorManager;
import com.cat.mapper.TaskMapper;
import com.cat.module.dto.BaseResponse;
import com.cat.util.Md5Encrypt;

public class ReliefService extends BaseService {

	@Autowired
	private RaptorManager raptorManager;

	@Autowired
	private TaskMapper taskMapper;

	@Value("${feignClient.raptor.secret2}")
    private String secret;

	/**
	 * 
	 * @param orderId
	 * @param reliefAmount
	 * @param userId 
	 * @return
	 */
	public BaseResponse reliefAmount(String orderId, BigDecimal reliefAmount, String userId) {
		if(reliefAmount == null ){
			return  new BaseResponse(-1,"金额不能为空");
		}

	    HashMap<String, String> params = new HashMap<String, String>();
        params.put("number", reliefAmount.toString());
        params.put("bundleId", orderId);
        params.put("creator", "贷后系统");
        params.put("reason", "无");
        String sign = Md5Encrypt.sign(params, secret);
        params.put("sign", sign);
        String jsonString = JSON.toJSONString(params);
		BaseResponse baseResponse = null;
		try {
			 baseResponse = raptorManager.send(jsonString);
		} catch (Exception e) {
			logger.error("减免出现异常,orderId={}", orderId, e);
			 return new BaseResponse(-1,"服务出现异常,稍后再试");
		}
		taskMapper.updateReliefAmount(orderId,reliefAmount,userId);
		logger.info("减免成功订单号={},减免金额={}",orderId,reliefAmount);
		return baseResponse;
	}
}
