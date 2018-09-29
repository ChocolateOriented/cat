package com.cat.manager;

import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${feignClient.raptor.url}", name = "reliefAmount")
public interface ReliefAmountManager {

  @PostMapping("/coupon/create ")
  Results send(SuonaMessageDto message);
}
