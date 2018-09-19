package com.tmp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmp.util.SnowFlake;

public class BaseService {

	@Autowired
	private SnowFlake snowFlake;

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public long generateId() {
		return snowFlake.nextId();
	}

}
