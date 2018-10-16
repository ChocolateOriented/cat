package com.cat.service;

import com.alibaba.fastjson.JSON;
import com.cat.manager.MessageSender;
import com.cat.module.dto.RegisterDto;
import com.cat.module.dto.ResetPasswordDto;
import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import com.cat.module.entity.Organization;
import com.cat.module.entity.User;
import com.cat.module.enums.UserStatus;
import com.cat.module.vo.LoginVo;
import com.cat.repository.OrganizationRepository;
import com.cat.repository.UserRepository;
import com.cat.util.EncryptionUtils;
import com.cat.util.RedisUtil;
import com.mo9.nest.client.AuthClient;
import com.mo9.nest.client.result.LoginResults;
import com.mo9.nest.client.result.RegisterResults;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by jxli on 2018/9/21.
 */
@Service
public class AccountService extends BaseService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private AuthClient authClient;
  @Autowired
  private MessageSender messageSender;
  @Autowired
  private OrganizationRepository organizationRepository;
  @Value("${feignClient.suona.systemCode}")
  private String systemCode;
  @Value("${feignClient.suona.captchaTemplateCode}")
  private String captchaTemplateCode;
  @Value("${feignClient.suona.resetPasswordTemplateCode}")
  private String resetPasswordTemplateCode;
  @Value("${feignClient.cat-front-end.url}")
  private String frontEndUrl;

  private static final String PASSWORD_SALT = "356a192b7913b04c54574d1";
  private static final String CACHE_VALIDATE_CODE_PREFIX = "validateCode";
  private static final String CACHE_RESET_PASSWORD_TOKEN_PREFIX = "resetPasswordToken";

  @Transactional
  public void registerByEmail(RegisterDto registerDto, HttpServletRequest request) {
    String email = registerDto.getEmail();
    String validateCode = registerDto.getValidateCode();
    String name = registerDto.getName();
    String password = registerDto.getPassword();

    if (!isValidValidateCode(email, validateCode)) {
      throw new RuntimeException("无效验证码");
    }

    String accountCode = this.registToUserCenter(request, email,password);
    User sameUser = userRepository.findTopByEmail(email);
    if (sameUser!=null){
      throw new RuntimeException("邮箱已被注册");
    }
    sameUser = userRepository.findTopByName(name);
    if (sameUser!=null){
      throw new RuntimeException("昵称已被占用");
    }


    User user = new User();
    user.setName(name);
    user.setEmail(registerDto.getEmail());
    user.setStatus(UserStatus.NORMAL);
    user.setId(accountCode);
    user.setPassword(EncryptionUtils.password(PASSWORD_SALT,password));
    userRepository.save(user);
  }

  private String registToUserCenter(HttpServletRequest request, String email, String password) {
    RegisterResults registerResults;
    try {
      registerResults = authClient.register(request, email, password);
      logger.debug(JSON.toJSONString(registerResults));

    } catch (IOException e) {
      throw new RuntimeException("调用用户中心失败", e);
    }
    //用户已存在
    if (RegisterResults.CodeEnum.REGISTER_MOBILE_IS_USED.equals(registerResults.getCode())){
      try {
        return authClient.findCode(request,email);
      } catch (IOException e) {
        throw new RuntimeException("调用用户中心失败", e);
      }
    }
    if (!RegisterResults.CodeEnum.SUCCESS.equals(registerResults.getCode())){
      throw new RuntimeException(registerResults.getResults().getMessage());
    }
    return registerResults.getAccount().getCode();
  }

  public LoginVo login(String email, String password, HttpServletRequest request) {
    User user = userRepository.findTopByEmail(email);
    if (user == null) {
      throw new RuntimeException("未注册邮箱");
    }
    String receivePassword = EncryptionUtils.password(PASSWORD_SALT,password);
    if (!Objects.equals(receivePassword,user.getPassword())){
      throw new RuntimeException("密码错误");
    }
    String userId = user.getId();
    LoginResults loginResults;
    try {
      loginResults = authClient.loginWithoutPassword(request, userId);
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
    RedisUtil.set(CACHE_VALIDATE_CODE_PREFIX + email, validateCode, 5*60);

    SuonaMessageDto messageDto = new SuonaMessageDto();
    messageDto.setMessageId(super.generateId()+"");
    messageDto.setSystemCode(systemCode);
    messageDto.setTemplateCode(captchaTemplateCode);
    messageDto.getReceivers().add(email);
    Map<String, String> values = messageDto.getVariableValues();
    values.put("pinCode", validateCode);
    Results results = messageSender.send(messageDto);
    logger.debug(results.toString());
    if (!results.isSuccess()) {
      throw new RuntimeException("发送验证码失败");
    }
  }

  private boolean isValidValidateCode(String email, String validateCode) {
    String cachedCode = RedisUtil.get(CACHE_VALIDATE_CODE_PREFIX + email);
    if (Objects.equals(cachedCode, validateCode)) {
      return true;
    }
    return false;
  }

  public void sendResetPasswordEmail(String email) {
    User user = userRepository.findTopByEmail(email);
    if (user==null){
      throw new RuntimeException("该邮箱未注册");
    }

    String token = RandomStringUtils.randomAlphanumeric(24);
    RedisUtil.set(CACHE_RESET_PASSWORD_TOKEN_PREFIX + email, token, 10*60);

    SuonaMessageDto messageDto = new SuonaMessageDto();
    messageDto.setMessageId(super.generateId()+"");
    messageDto.setSystemCode(systemCode);
    messageDto.setTemplateCode(resetPasswordTemplateCode);
    messageDto.getReceivers().add(email);
    Map<String, String> values = messageDto.getVariableValues();
    String url= frontEndUrl+"reset_password?token="+token+"&email="+email;
    values.put("url", url);
    values.put("name", user.getName());

    Results results = messageSender.send(messageDto);
    logger.debug(results.toString());
    if (!results.isSuccess()) {
      throw new RuntimeException("发重置密码邮件失败");
    }
  }

  public void resetPassword(ResetPasswordDto resetPasswordDto) {
    String email = resetPasswordDto.getEmail();
    String receiveToken = resetPasswordDto.getToken();
    String password = resetPasswordDto.getPassword();

    User user = userRepository.findTopByEmail(email);
    if (user==null){
      throw new RuntimeException("该邮箱未注册");
    }

    String cachedToken = RedisUtil.get(CACHE_RESET_PASSWORD_TOKEN_PREFIX + email);
    if (!Objects.equals(cachedToken, receiveToken)) {
      throw new RuntimeException("无效令牌");
    }

    user.setPassword(EncryptionUtils.password(PASSWORD_SALT,password));
  }
}
