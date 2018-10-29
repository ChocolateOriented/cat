package com.cat.manager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cat.exception.ApiException;
import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.cti.CallinInfo;
import com.cat.module.dto.cti.CalloutInfo;
import com.cat.module.dto.cti.cmd.CallInfoQueryCommand;
import com.cat.module.dto.cti.cmd.RequestCommand;
import com.cat.module.enums.AgentStatus;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CtiManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private CtiBaseManager ctiBaseManager;

	private String buildFormEntity(Map<String, Object> params) {
		StringBuilder builder = new StringBuilder();
		for (Entry<String, Object> entry : params.entrySet()) {
			builder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		
		if (builder.length() > 0) {
			builder.deleteCharAt(builder.length() - 1);
		}
		
		return builder.toString();
	}

	private void checkResponse(BaseResponse response, String formEntity) throws ApiException {
		if (response == null) {
			logger.info("CTI接口失败, param:{}", formEntity);
			throw new ApiException("CTI接口失败");
		}
		
		if (!response.isSuccess()) {
			logger.info("CTI接口失败, param:{}, error_code:{}, error_msg:{}", formEntity, response.getCode(), response.getMessage());
			throw new ApiException("CTI接口失败:" + response.getMessage());
		}
	}

	public BaseResponse commonComand(Map<String, Object> params) throws ApiException {
		String formEntity = buildFormEntity(params);
		BaseResponse response = ctiBaseManager.commonComand(formEntity);
		checkResponse(response, formEntity);
		return response;
	}

	public <T extends BaseResponse> T queryComand(Map<String, Object> params, TypeReference<T> type) throws IOException, ApiException {
		String formEntity = buildFormEntity(params);
		String strResp = ctiBaseManager.queryComand(formEntity);
		T response = mapper.readValue(strResp, type);
		checkResponse(response, formEntity);
		return response; 
	}

	public BaseResponse originate(String agent, String targetTel, String customerNo) throws ApiException {
		Map<String, Object> params = new HashMap<>();
		params.put("cmd", RequestCommand.ORIGINATE);
		params.put("agent", agent);
		params.put("target", targetTel);
		params.put("auto_answer", true);
		
		if (customerNo != null) {
			params.put("customerno", customerNo);
		}
		
		return commonComand(params);
	}

	public BaseResponse changeAgentStatus(String agent, AgentStatus agentStatus) throws ApiException {
		Map<String, Object> params = new HashMap<>();
		params.put("cmd", RequestCommand.AGENT_STATUS_CHANGE);
		params.put("agent", agent);
		params.put("status", agentStatus.getValue());
		return commonComand(params);
	}

	public PageResponse<CallinInfo> queryCallinInfo(CallInfoQueryCommand queryCommand) throws IOException, ApiException {
		queryCommand.setCmd(RequestCommand.CALLIN_INFO);
		return queryComand(queryCommand.toParamMap(), new TypeReference<PageResponse<CallinInfo>>() {});
	}

	public PageResponse<CalloutInfo> queryCalloutInfo(CallInfoQueryCommand queryCommand) throws IOException, ApiException {
		queryCommand.setCmd(RequestCommand.CALLOUT_INFO);
		return queryComand(queryCommand.toParamMap(), new TypeReference<PageResponse<CalloutInfo>>() {});
	}
}
