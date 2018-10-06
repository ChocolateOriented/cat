package com.cat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.mapper.ContactMapper;
import com.cat.module.bean.CallLogBean;
@Service
public class OtherDateBaseService {

	@Autowired
	private ContactMapper contactMapper;
	/**
	 * 切库拿通话记录
	 * @param mobile
	 * @return
	 */
	@DataSource(DynamicDataSource.RISK_DATASOURCE)
	public List<CallLogBean> findCallLogList(String mobile) {
		return contactMapper.findCallLogList(mobile);
	}

}
