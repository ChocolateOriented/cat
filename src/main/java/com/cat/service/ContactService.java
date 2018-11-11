package com.cat.service;

import java.util.ArrayList;
import java.util.List;

import com.cat.mapper.ContactMapper;
import com.cat.mapper.CustomerMapper;
import com.cat.module.entity.Contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.annotation.DataSource;
import com.cat.config.DynamicDataSource;
import com.cat.module.bean.CallLogBean;
import com.cat.module.dto.AddressBook;
import com.cat.module.dto.Code;
import com.cat.module.dto.PageResponse;
import com.cat.module.dto.PageResponse.Page;
import com.cat.module.enums.ContactType;
import com.cat.module.vo.CallLogVo;
import com.cat.module.vo.ContactVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContactService extends BaseService {

	@Autowired
	private ContactMapper contactMapper;
	@Autowired
	private CallLogService callLogService;
	@Autowired
	private CustomerMapper customerMapper;
	
	/**
	 * 查询风控通话记录
	 * @param mobile
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */

	/**
	 * 查询风控通话记录
	 * @param mobile
	 * @param mobile2 
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Page<CallLogVo> findCalllog(String customerId, String mobile, int pageNum, int pageSize) {
		List<CallLogBean> callLogBeans = callLogService.findCallLogList(mobile);
		int from = (pageNum - 1) * pageSize;
		int to = Math.min(pageNum * pageSize, callLogBeans.size());
		List<CallLogBean> list = callLogBeans.subList(from, to);
		List<CallLogVo> callLogVos = new ArrayList<>();
		for (CallLogBean callLogBean : list) {
			String callTel = callLogBean.getCallTel();
			String contactName = contactMapper.findContactName(callTel);
			Integer actionRecordNum = contactMapper.findActionRecordNum(callTel,customerId);
			CallLogVo callLogVo = new CallLogVo();
			callLogVo.setName(contactName);
			callLogVo.setActionRecordNum(actionRecordNum);
			callLogVo.setCallDuration(callLogBean.getCallDuration());
			callLogVo.setCallNum(callLogBean.getCallNum());
			callLogVo.setTel(callTel);
			callLogVos.add(callLogVo);
		}
		Page<CallLogVo> page = new Page<>();
		page.setEntities(callLogVos);
		page.setPageNum(pageNum);
		page.setPageSize(pageSize);
		page.setTotal(Long.valueOf(callLogBeans.size()));

		return page;
	}

	/**
	 * 获取所有联系人类型
	 * @return
	 */
	public List<Code> listTargetType() {
		List<Code> codes = new ArrayList<>();
		for (ContactType targetType : ContactType.values()) {
			Code code = new Code();
			code.setCode(String.valueOf(targetType.getValue()));
			code.setDesc(targetType.getDesc());
			codes.add(code);
		}
		return codes;
	}

	/**
	 * 通讯录
	 * @param customerId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public PageResponse<ContactVo> findListAddressbook(String customerId, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<ContactVo> list = contactMapper.findListByCustomerId(customerId);
		if(list != null && list.size() >0){
			for (ContactVo contactVo : list) {
				Integer actionRecordNum = contactMapper.findActionRecordNum(contactVo.getTel(),customerId);
				contactVo.setActionRecordNum(actionRecordNum);
			}
		}
		PageInfo<ContactVo> pageInfo = new PageInfo<>(list);
		return new PageResponse<ContactVo>(list, pageNum, pageSize, pageInfo.getTotal());
	}

	/**
	 * 根据借款人Id查询通讯录
	 * @param customerId
	 * @return
	 */
	public List<Contact> fetchContactsByCustomerId(String customerId) {
		return contactMapper.fetchContactsByCustomerId(customerId);
	}

	public void insert(Contact contact) {
		contactMapper.insert(contact);
	}

	/**
	 * 批量插入联系人
	 * @param contactList
	 */
	public void insertAll(List<Contact> contactList) {
		contactMapper.insertList(contactList);
	}
	/**
	 * 获取通讯录
	 * @param listCustomeId 
	 * @param maxCareateTime
	 * @return
	 */
	@DataSource(DynamicDataSource.RAPTOR_DATASOURCE)
	public  List<AddressBook>  getAddressBook(List<String> listCustomeId, Long maxCareateTime){
		logger.info("获取通讯录");
		return  contactMapper.findListContact(listCustomeId,maxCareateTime);
		
	}

	public Long maxCareateTime() {
		return contactMapper.maxCareateTime();
	}

	public void deleteBycustomerId(String customerId) {
		contactMapper.deleteContact(customerId);
	}
	@DataSource(DynamicDataSource.RAPTOR_DATASOURCE)
	public List<AddressBook> reloadAddressBook(List<String> customerIds) {
		return contactMapper.reloadAddressBook( customerIds);
	}
}
