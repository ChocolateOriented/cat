package com.cat.config.security;

import com.cat.repository.UserRepository;
import com.mo9.nest.client.redis.RedisHolder;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.servlet.Filter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSubjectFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jxli on 2018/10/30.
 */
@Configuration
public class ShiroConfig {

  @Bean
  public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager,
      @Value("${nest.exclude.urls}") String[] excludeUrls) {
    ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
    shiroFilterFactoryBean.setSecurityManager(securityManager);

    Map<String,Filter> filters = new LinkedHashMap<>();
    filters.put("statelessAuthc", new AuthenticationFilter());
    shiroFilterFactoryBean.setFilters(filters);

    //拦截器.顺序判断
    Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
    if (excludeUrls != null) {
      for (String anonymousUrl : excludeUrls) {
        filterChainDefinitionMap.put(anonymousUrl, "anon");
      }
    }
    filterChainDefinitionMap.put("/**", "statelessAuthc");
    shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
    return shiroFilterFactoryBean;
  }

  @Bean
  public DefaultWebSecurityManager securityManager(Realm realm,DefaultWebSubjectFactory subjectFactory){
    DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
    securityManager.setRealm(realm);
    DefaultSessionManager sessionManager = new DefaultSessionManager();
    sessionManager.setSessionValidationSchedulerEnabled(false);
    securityManager.setSessionManager(sessionManager);
    securityManager.setSubjectFactory(subjectFactory);

    //禁用sessionStorage
    DefaultSubjectDAO de = (DefaultSubjectDAO)securityManager.getSubjectDAO();
    DefaultSessionStorageEvaluator defaultSessionStorageEvaluator =(DefaultSessionStorageEvaluator)de.getSessionStorageEvaluator();
    defaultSessionStorageEvaluator.setSessionStorageEnabled(false);

    return securityManager;
  }

  @Bean
  public Realm realm(UserRepository userRepository,RedisHolder nestRedisHolder){
    return new ShiroRealm(userRepository,nestRedisHolder);
  }

  @Bean
  public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
    defaultAAP.setProxyTargetClass(true);
    return defaultAAP;
  }

  /**
   * 会话管理类 禁用session
   * @return
   */
  @Bean
  public DefaultSessionManager sessionManager(){
    DefaultSessionManager manager = new DefaultSessionManager();
    manager.setSessionValidationSchedulerEnabled(false);
    return manager;
  }

  @Bean
  public DefaultWebSubjectFactory  subjectFactory(){
    DefaultWebSubjectFactory  subjectFactory = new DefaultWebSubjectFactory (){
      @Override
      public Subject createSubject(SubjectContext context) {
        context.setSessionCreationEnabled(false);
        return super.createSubject(context);
      }
    };
    return subjectFactory;
  }

}
