package com.cat.manager;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

import com.cat.module.dto.BaseResponse;

@FeignClient(url = "${feignClient.cti.url}", name = "cti")
public interface CtiBaseManager {

	@PostMapping(path = "/cti/cpi", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	BaseResponse commonComand(String formEntity);

	@PostMapping(path = "/cti/cpi", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	String queryComand(String formEntity);
}
