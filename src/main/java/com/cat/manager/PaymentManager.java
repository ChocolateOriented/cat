package com.cat.manager;

import com.cat.module.dto.result.Results;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 *
 * @author cyuan
 * @date 2018/10/10
 */
@FeignClient(url = "${feignClient.manualPayment.url}", name = "raptor")
public interface PaymentManager {
    @PostMapping("/offline/repay")
    Results repay(@RequestBody Map<String, String> repayInfo);
}
