package com.cat.interceptor;

import com.mo9.nest.client.info.DefaultRequestInfoFetcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by jxli on 2018/9/26.
 */
public class RequestInfoFetcher extends DefaultRequestInfoFetcher {

  @Override
  public String getAccountCode(HttpServletRequest request) {
    return request.getHeader("User-Id");
  }

  @Override
  public String getClientId(HttpServletRequest request) {
    return "503";
  }
}
