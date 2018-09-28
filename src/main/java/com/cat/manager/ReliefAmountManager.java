package com.cat.manager;

import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//@FeignClient(url = "${feignClient.raptor.url}", name = "reliefAmount")
public interface ReliefAmountManager {

//  @GetMapping("/ss/")
  Results send(SuonaMessageDto message);
}
