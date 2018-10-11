package com.cat.manager;

import com.cat.module.dto.BaseResponse;
import com.cat.module.dto.result.Results;


import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feignClient.raptor.url}", name = "raptor")
public interface RaptorManager {

  @PostMapping(consumes="application/json", value="/coupon/create")
  BaseResponse send( String jsonString);

  @GetMapping("/outside/to_black_user")
  Results blacklistCustomer(@RequestParam("userCode") String userCode
      ,@RequestParam("desc") String desc,
      @RequestParam("sign") String sign);
}
