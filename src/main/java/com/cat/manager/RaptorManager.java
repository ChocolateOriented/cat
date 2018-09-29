package com.cat.manager;

import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${feignClient.raptor.url}", name = "raptor")
public interface RaptorManager {

  @PostMapping("/coupon/create ")
  Results send(SuonaMessageDto message);

  @GetMapping("/outside/to_black_user ")
  Results blacklistCustomer(@RequestParam("userCode") String userCode
      ,@RequestParam("desc") String desc,
      @RequestParam("sign") String sign);
}
