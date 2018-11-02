package com.cat.config.security;

import com.cat.module.entity.Role;
import com.cat.module.entity.RolePermission;
import com.cat.module.entity.User;
import com.cat.repository.UserRepository;
import com.cat.util.StringUtils;
import com.mo9.nest.client.info.ClientInfo;
import com.mo9.nest.client.redis.RedisHolder;
import com.mo9.nest.client.redis.key.RedisKeyGenerator;
import com.mo9.nest.client.redis.key.RedisKeys;
import com.mo9.nest.util.enums.LoginMode;
import java.util.List;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jxli on 2018/10/30.
 */
public class ShiroRealm extends AuthorizingRealm {
  private Logger logger = LoggerFactory.getLogger(getClass());
  private UserRepository userRepository;
  private RequestInfoFetcher requestInfoFetcher = new RequestInfoFetcher();
  private RedisHolder redisHolder;

  public ShiroRealm(UserRepository userRepository, RedisHolder redisHolder) {
    this.userRepository = userRepository;
    this.redisHolder = redisHolder;
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    User user = (User) principalCollection.getPrimaryPrincipal();
    SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    Role role = user.getRole();
    if (role != null && role.getEnabled()){
      authorizationInfo.addRole(role.getName());
    }
    List<RolePermission> permissions = role.getPermissions();
    if (permissions != null && permissions.size() > 0){
      for (RolePermission permission : permissions) {
        authorizationInfo.addStringPermission(permission.getPermission());
      }
    }
    return authorizationInfo;
  }

  /**
   * @Description 由用户中心实现认证
   * @author jxli
   * @version 2018/10/31
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
      throws AuthenticationException {
    SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo();
    StatelessToken statelessToken = (StatelessToken) authenticationToken;

    String userId = (String) statelessToken.getPrincipal();
    String token =  this.findTokenFromCache(userId);
    authenticationInfo.setCredentials(token);
    User user = userRepository.findOne(userId);
    return new SimpleAuthenticationInfo(user,token,user.getName());
  }

  private String findTokenFromCache(String userId) throws AuthenticationException{
    long timestamp = System.currentTimeMillis();
    //获取缓存中的token
    ClientInfo clientInfo = ClientInfo.getInstance();
    RedisKeys redisKeys = this.buildRedisKeys(userId, clientInfo, requestInfoFetcher.getClientId());
    Object redisOperator = this.redisHolder.getRedisOperator();

    String token ;
    try {
      token = this.redisHolder.hget(redisOperator, redisKeys.getRedisKey(), redisKeys.getHashKey());
      String limit = this.redisHolder.hget(redisOperator, redisKeys.getExpireKey(), redisKeys.getHashKey());
      if(StringUtils.isEmpty(token) || StringUtils.isEmpty(limit)) {
        this.cleanRedis(redisOperator, redisKeys);
        throw new AuthenticationException("请先进行登录");
      }
      if(timestamp > Long.valueOf(limit).longValue()) {
        this.cleanRedis(redisOperator, redisKeys);
        throw new AuthenticationException("登录已过期");
      }

      long newTimestamp = timestamp + this.requestInfoFetcher.getExpireSeconds() * 1000L;
      this.redisHolder.hset(redisOperator, redisKeys.getExpireKey(), redisKeys.getHashKey(), String.valueOf(newTimestamp));

    } finally {
      this.redisHolder.close(redisOperator);
    }
    return token;
  }

  @Override
  public boolean supports(AuthenticationToken token) {
    return token instanceof StatelessToken;
  }

  private RedisKeys buildRedisKeys(String accountCode, ClientInfo clientInfo, String clientId) {
    RedisKeys redisKeys = null;
    if(clientInfo.getLoginMode() == LoginMode.COMMON) {
      redisKeys = RedisKeyGenerator.generate(accountCode);
    } else if(clientInfo.getLoginMode() == LoginMode.ALONE) {
      redisKeys = RedisKeyGenerator.generate(accountCode, clientInfo.getSystemCode(), clientId);
    } else if(clientInfo.getLoginMode() == LoginMode.GROUP) {
      redisKeys = RedisKeyGenerator.generate(accountCode, clientInfo.getSystemCode(), clientInfo.getSystemGroup(), clientId);
    }

    return redisKeys;
  }

  private void cleanRedis(Object redisOperator, RedisKeys redisKeys) {
    this.redisHolder.hdel(redisOperator, redisKeys.getRedisKey(), redisKeys.getHashKey());
    this.redisHolder.hdel(redisOperator, redisKeys.getExpireKey(), redisKeys.getHashKey());
  }
}
