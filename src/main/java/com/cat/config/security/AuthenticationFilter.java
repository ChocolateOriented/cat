package com.cat.config.security;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jxli on 2018/10/31.
 */
public class AuthenticationFilter extends AccessControlFilter {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void destroy() {
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse,
      Object o) throws Exception {
    HttpServletRequest httpReq = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    RequestInfoFetcher requestInfoFetcher = new RequestInfoFetcher();

    StatelessToken token = new StatelessToken(requestInfoFetcher.getAccountCode(httpReq),
        requestInfoFetcher.getAccessToken(httpReq));
    Subject subject = getSubject(servletRequest,servletResponse);
    try {
      subject.login(token);
    }catch (Exception e){
      this.write(httpServletResponse,new Results(ResultConstant.NEED_LOGIN, e.getMessage()));
      return false;
    }
    return true;
  }

  private void write(HttpServletResponse response, Results result) {
    response.setStatus(403);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    PrintWriter writer = null;
    try {
      writer = response.getWriter();
      writer.print(result);
    } catch (IOException e) {
      logger.info("响应异常",e);
    }finally {
      writer.close();
    }
  }

  @Override
  protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse)
      throws Exception {
    return false;
  }
}
