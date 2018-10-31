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
import com.cat.module.dto.cti.CallInfo;
import com.cat.module.dto.cti.CallinInfo;
import com.cat.module.dto.cti.CalloutInfo;
import com.cat.module.dto.cti.cmd.CallInfoQueryCommand;
import com.cat.module.dto.cti.cmd.RequestCommand;
import com.cat.module.enums.AgentStatus;
import com.cat.module.enums.CallType;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CtiManager {

	private Logger logger = LoggerFactory.getLogger(getClass());

	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private CtiBaseManager ctiBaseManager;

	/**
	 * 根据参数拼接为 key1=value1&key2=value2...
	 * @param params
	 * @return
	 */
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

	/**
	 * 检查cti响应是否正常
	 * @param response
	 * @param formEntity
	 * @throws ApiException
	 */
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

	/**
	 * CTI通用请求
	 * @param params
	 * @return
	 * @throws ApiException
	 */
	public BaseResponse commonComand(Map<String, Object> params) throws ApiException {
		String formEntity = buildFormEntity(params);
		BaseResponse response = ctiBaseManager.commonComand(formEntity);
		checkResponse(response, formEntity);
		return response;
	}

	/**
	 * CTI查询请求
	 * @param params
	 * @param type
	 * @return
	 * @throws IOException
	 * @throws ApiException
	 */
	public <T extends BaseResponse> T queryComand(Map<String, Object> params, TypeReference<T> type) throws IOException, ApiException {
		String formEntity = buildFormEntity(params);
		String strResp = ctiBaseManager.queryComand(formEntity);
		T response = mapper.readValue(strResp, type);
		checkResponse(response, formEntity);
		return response; 
	}

	/**
	 * 发起呼叫
	 * @param agent
	 * @param targetTel
	 * @param customerNo
	 * @return
	 * @throws ApiException
	 */
	public BaseResponse originate(String agent, String targetTel, String customerNo) throws ApiException {
		Map<String, Object> params = new HashMap<>();
		params.put("cmd", RequestCommand.ORIGINATE);
		params.put("agent", agent);
		params.put("target", targetTel);
		params.put("auto_answer", true);
		
		if (customerNo != null) {
			params.put("customerno", customerNo);
		}
		
		logger.debug("呼叫请求参数：{}", params);
		return commonComand(params);
	}

	/**
	 * 变更坐席状态
	 * @param agent
	 * @param agentStatus
	 * @return
	 * @throws ApiException
	 */
	public BaseResponse changeAgentStatus(String agent, AgentStatus agentStatus) throws ApiException {
		Map<String, Object> params = new HashMap<>();
		params.put("cmd", RequestCommand.AGENT_STATUS_CHANGE);
		params.put("agent", agent);
		params.put("status", agentStatus.getValue());
		return commonComand(params);
	}

	/**
	 * 查询CTI呼入信息
	 * @param queryCommand
	 * @return
	 * @throws IOException
	 * @throws ApiException
	 */
	public PageResponse<CallinInfo> queryCallinInfo(CallInfoQueryCommand queryCommand) throws IOException, ApiException {
		queryCommand.setCmd(RequestCommand.CALLIN_INFO);
		return queryComand(queryCommand.toParamMap(), new TypeReference<PageResponse<CallinInfo>>() {});
	}

	/**
	 * 查询CTI呼出信息
	 * @param queryCommand
	 * @return
	 * @throws IOException
	 * @throws ApiException
	 */
	public PageResponse<CalloutInfo> queryCalloutInfo(CallInfoQueryCommand queryCommand) throws IOException, ApiException {
		queryCommand.setCmd(RequestCommand.CALLOUT_INFO);
		return queryComand(queryCommand.toParamMap(), new TypeReference<PageResponse<CalloutInfo>>() {});
	}

	/**
	 * 查询CTI呼叫信息
	 * @param queryCommand
	 * @param callType
	 * @return
	 * @throws IOException
	 * @throws ApiException
	 */
	@SuppressWarnings("unchecked")
	public PageResponse<CallInfo> queryCallInfo(CallInfoQueryCommand queryCommand, CallType callType) throws IOException, ApiException {
		PageResponse<?> resp;
		if (callType == CallType.IN) {
			resp = queryCallinInfo(queryCommand);
		} else {
			resp = queryCalloutInfo(queryCommand);
		}
		return (PageResponse<CallInfo>) resp;
	}

	/**
	 * 过滤CTI呼叫信息加拨号码
	 * @param targetTel
	 * @return
	 */
	public static String trimCtiCallInfoTel(String targetTel) {
		if (targetTel == null) {
			return targetTel;
		}

		if (targetTel.startsWith("179690")) {
			return targetTel.substring(6);
		}

		if (targetTel.startsWith("17969")) {
			return targetTel.substring(5);
		}

		if (targetTel.startsWith("01") && !targetTel.startsWith("010") || targetTel.startsWith("00")) {
			return targetTel.substring(1);
		}
		return targetTel;
	}
}
