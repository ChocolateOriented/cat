package com.cat.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.cat.exception.ApiException;
import com.cat.exception.ServiceException;
import com.cat.manager.RaptorManager;
import com.cat.mapper.TaskMapper;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.Code;
import com.cat.module.entity.Relief;
import com.cat.module.enums.ReliefReason;
import com.cat.repository.ReliefRepository;
import com.cat.util.Md5Encrypt;

@Service
public class ReliefService extends BaseService {

	@Autowired
	private RaptorManager raptorManager;

	@Autowired
	private TaskMapper taskMapper;

	@Autowired
	private ReliefRepository reliefRepository;

	@Value("${feignClient.raptor.secret2}")
    private String secret;

	/**
	 * 减免操作
	 * @return
	 * @throws ApiException 
	 */
	@Transactional(rollbackFor = Exception.class)
	public void reliefAmount(Relief relief) throws ServiceException {
		try {
			requestRelief(relief);
		} catch (ApiException e) {
			logger.error("订单" + relief.getOrderId() + "调用raptor减免失败", e);
			throw new ServiceException(e.getMessage());
		}
		relief.setId(generateId());
		reliefRepository.save(relief);
		taskMapper.updateReliefAmount(relief);
		logger.info("减免成功订单号={},减免金额={}", relief.getOrderId(), relief.getReliefAmount());
	}

	/**
	 * 请求减免接口
	 * @param relief
	 * @throws ApiException
	 */
	private void requestRelief(Relief relief) throws ApiException {
		HashMap<String, String> params = new HashMap<String, String>();
		if("0".equals( relief.getReliefAmount().toString())){
			params.put("bundleId", relief.getOrderId());
			params.put("operator", relief.getCollectorName());
		}else{
			
			params.put("number", relief.getReliefAmount().toString());
			params.put("bundleId", relief.getOrderId());
			params.put("creator", relief.getCollectorName());
			params.put("reason", relief.getReliefReason() == null ? "无" : relief.getReliefReason().getDesc());
		}
        
        String sign = Md5Encrypt.sign(params, secret);
        params.put("sign", sign);
        
        String jsonString = JSON.toJSONString(params);
		BaseResponse response = null;
        if("0".equals( relief.getReliefAmount().toString())){ 
        	response = raptorManager.cancel(jsonString);
        }else{
        	response = raptorManager.send(jsonString);
        }
		if (response == null || !response.isSuccess()) {
			throw new ApiException(response == null ? "请求减免失败" : response.toString());
		}
	}

	/**
	 * 根据订单号查询减免记录
	 * @param orderId
	 * @return
	 */
	public List<Relief> findByOrderIdOrderByCreateTimeDesc(String orderId) {
		return reliefRepository.findByOrderIdOrderByCreateTimeDesc(orderId);
	}

	/**
	 * 获取所有减免原因列表
	 * @return
	 */
	public List<Code> listReliefReason() {
		List<Code> codes = new ArrayList<>();
		for (ReliefReason reliefReason : ReliefReason.values()) {
			Code code = new Code();
			code.setCode(reliefReason.name());
			code.setDesc(reliefReason.getDesc());
			codes.add(code);
		}
		return codes;
	}
}
