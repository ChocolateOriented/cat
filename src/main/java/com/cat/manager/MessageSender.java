package com.cat.manager;

import com.cat.module.dto.SuonaMessageDto;
import com.cat.module.dto.result.Results;
import java.util.Map;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created by jxli on 2018/9/25.
 */
@FeignClient(url = "${feignClient.suona.url}", name = "suona")
public interface MessageSender {

  @PostMapping("/message/send")
  Results send(SuonaMessageDto message);

  @PostMapping("/template/create")
  Results createTemplate(Map<String,String> template);
}
