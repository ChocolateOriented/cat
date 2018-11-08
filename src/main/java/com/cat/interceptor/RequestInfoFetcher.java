package com.cat.interceptor;

import com.cat.util.StringUtils;
import com.mo9.nest.client.info.DefaultRequestInfoFetcher;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by jxli on 2018/9/26.
 */
public class RequestInfoFetcher extends DefaultRequestInfoFetcher {

  @Override
  public String getAccountCode(HttpServletRequest request) {
    String uid = request.getHeader("User-Id");
    if (request.getMethod().equals(RequestMethod.GET.name()) && StringUtils.isEmpty(uid)) {
      uid = request.getParameter("User-Id");
    }
    return uid;
  }

  @Override
  public String getAccessToken(HttpServletRequest request) {
    String token = request.getHeader("Access-Token");
    if (request.getMethod().equals(RequestMethod.GET.name()) && StringUtils.isEmpty(token)) {
      token = request.getParameter("Access-Token");
    }
    return token;
  }

  @Override
  public String getClientId(HttpServletRequest request) {
    return "503";
  }
}
