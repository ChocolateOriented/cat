package com.tmp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.tmp.mapper")
public class TmpApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmpApplication.class, args);
	}
}
