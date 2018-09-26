package com.cat.module.dto;

import com.alibaba.fastjson.JSON;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jxli on 2018/9/25.
 */
public class SuonaMessageDto {

  private String systemCode;
  private String messageId;
  private String templateCode;
  private Integer retry = 0;
  private String transMode = "MAIL";
  private String businessCode = "NOTICE";
  private String notifyUrl ="";
  private String mediaType ="WORD";
  private String receiverArea = "CN";
  private String gatewayCode ="DEFAULT_MAIL";
  private List<String> receivers = new ArrayList<>();
  private Map<String,String> variableValues = new HashMap<>();

  public void setSystemCode(String systemCode) {
    this.systemCode = systemCode;
  }

  public void setMessageId(String messageId) {
    this.messageId = messageId;
  }

  public void setTemplateCode(String templateCode) {
    this.templateCode = templateCode;
  }

  public String getSystemCode() {
    return systemCode;
  }

  public String getMessageId() {
    return messageId;
  }

  public String getTemplateCode() {
    return templateCode;
  }

  public Integer getRetry() {
    return retry;
  }

  public String getBusinessCode() {
    return businessCode;
  }

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public String getMediaType() {
    return mediaType;
  }

  public String getReceiverArea() {
    return receiverArea;
  }

  public String getGatewayCode() {
    return gatewayCode;
  }

  public List<String> getReceivers() {
    return receivers;
  }

  public Map<String, String> getVariableValues() {
    return variableValues;
  }

  public String getTransMode() {
    return transMode;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }
}
