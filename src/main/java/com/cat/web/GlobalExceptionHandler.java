package com.cat.web;

import com.cat.module.dto.result.ResultConstant;
import com.cat.module.dto.result.Results;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by jxli on 2018/11/1.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
  protected final Logger logger = LoggerFactory.getLogger(getClass());

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public Results handleUnauthorizedError(HttpServletRequest req, HttpServletResponse rsp, Exception e){
    logger.info("权限异常",e);
    return new Results(ResultConstant.INSUFFICIENT_PERMISSIONS);
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public Results handleInternalError(HttpServletRequest req, HttpServletResponse rsp, Exception e) {
    logger.info(req.getRequestURI()+"失败",e);
    return new Results(ResultConstant.INNER_ERROR);
  }
}
