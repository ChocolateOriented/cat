package com.cat.service;

import com.alibaba.fastjson.JSON;
import com.cat.manager.MessageSender;
import com.cat.module.entity.Organization;
import com.cat.module.vo.LoginVo;
import com.cat.module.dto.RegisterDto;
import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.UserRepository;
import com.cat.util.RedisUtil;
import com.cat.util.StringUtils;
import com.mo9.nest.client.AuthClient;
import com.mo9.nest.client.result.LoginResults;
import com.mo9.nest.client.result.RegisterResults;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jxli on 2018/9/21.
 */
@Service
public class AccountService extends BaseService {

  @Autowired
  UserRepository userRepository;
  @Autowired
  AuthClient authClient;
  @Autowired
  MessageSender messageSender;
  @Autowired
  OrganizationRepository organizationRepository;

  private static final String CACHE_VALIDATE_CODE_PREFIX = "validateCode";

  public LoginVo registerByEmail(RegisterDto registerDto, HttpServletRequest request) {
    String email = registerDto.getEmail();
    String validateCode = registerDto.getValidateCode();
    String name = registerDto.getName();

    if (!isValidValidateCode(email, validateCode)) {
      throw new RuntimeException("无效验证码");
    }

    RegisterResults registerResults;
    try {
      registerResults = authClient.register(request, registerDto.getEmail(),
          registerDto.getPassword());
      logger.debug(JSON.toJSONString(registerResults));
      if (!RegisterResults.CodeEnum.SUCCESS.equals(registerResults.getCode())) {
        throw new RuntimeException(registerResults.getResults().getMessage());
      }
    } catch (IOException e) {
      throw new RuntimeException("调用用户中心失败", e);
    }

    String accountCode = registerResults.getAccount().getCode();
    User user = new User();
    user.setName(name);
    user.setEmail(registerDto.getEmail());
    user.setStatus(UserStatus.NORMAL);
    user.setId(accountCode);
    userRepository.save(user);

    return new LoginVo(accountCode, name, registerResults.getToken());
  }

  public LoginVo login(String email, String password, HttpServletRequest request) {
    User user = userRepository.findTopByEmail(email);
    if (user == null) {
      throw new RuntimeException("未注册邮箱");
    }
    String userId = user.getId();
    LoginResults loginResults;
    try {
      loginResults = authClient.loginWithPassword(request, userId, password);
      logger.debug(JSON.toJSONString(loginResults));
    } catch (IOException e) {
      throw new RuntimeException("调用用户中心失败", e);
    }
    if (!LoginResults.CodeEnum.SUCCESS.equals(loginResults.getCode())) {
      throw new RuntimeException(loginResults.getResults().getMessage());
    }

    String accessToken = loginResults.getToken();
    LoginVo loginVo = new LoginVo();
    loginVo.setUserId(userId);
    loginVo.setAccessToken(accessToken);
    loginVo.setName(user.getName());
    loginVo.setRole(user.getRole());
    Long organizationId = user.getOrganizationId();
    if (organizationId != null){
      Organization organization = organizationRepository.findOne(organizationId);
      loginVo.setOrganizationName(organization.getName());
    }
    return loginVo;
  }

  public void sendValidateCode(String email) {
    String validateCode = RandomStringUtils.randomNumeric(6);
    RedisUtil.set(CACHE_VALIDATE_CODE_PREFIX + email, validateCode, 90);

    SuonaMessageDto messageDto = new SuonaMessageDto();
    messageDto.setMessageId(System.currentTimeMillis() + "");
    messageDto.setSystemCode("BOURSE");
    messageDto.setTemplateCode("GENERAL_CAPTCHA");
    messageDto.getReceivers().add(email);
    Map<String, String> values = messageDto.getVariableValues();
    values.put("pinCode", validateCode);
    values.put("sign", "验证码");
    Results results = messageSender.send(messageDto);
    logger.debug(results.toString());
    if (!results.isSuccess()) {
      throw new RuntimeException("发送验证码失败");
    }
  }

  public boolean isValidValidateCode(String email, String validateCode) {
    String cachedCode = RedisUtil.get(CACHE_VALIDATE_CODE_PREFIX + email);
    if (Objects.equals(cachedCode, validateCode)) {
      return true;
    }
    return false;
  }

}
