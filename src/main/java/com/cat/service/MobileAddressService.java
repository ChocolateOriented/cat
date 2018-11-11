package com.cat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cat.module.entity.MobileAddress;
import com.cat.repository.MobileAddressRepository;

@Service
public class MobileAddressService {

	@Autowired
	private MobileAddressRepository mobileAddressRepository;

	/**
	 * 查询手机号地址信息
	 * @param mobile
	 * @return
	 */
	public MobileAddress findByMobile(String mobile) {
		if (mobile == null || mobile.length() != 11) {
			return null;
		}
		
		return mobileAddressRepository.findTopByPre7(mobile.substring(0, 7));
	}

	/**
	 * 查询手机号所属省市
	 * <pre>例:
	 * 
	 * 直辖市 -> 上海
	 * 非直辖市 -> 江苏 南京</pre>
	 * @param mobile
	 * @return
	 */
	public String getFullAddressByMobile(String mobile) {
		MobileAddress mobileAddress = findByMobile(mobile);
		if (mobileAddress == null) {
			return null;
		}
		return mobileAddress.getProvince().equals(mobileAddress.getCity()) ? mobileAddress.getProvince()
				: mobileAddress.getProvince() + " " + mobileAddress.getCity();
	}

	/**
	 * 查询手机号所属省
	 * @param mobile
	 * @return
	 */
	public String getProvinceByMobile(String mobile) {
		MobileAddress mobileAddress = findByMobile(mobile);
		return mobileAddress == null ? null : mobileAddress.getProvince();
	}

	/**
	 * 查询手机号所属市
	 * @param mobile
	 * @return
	 */
	public String getCityByMobile(String mobile) {
		MobileAddress mobileAddress = findByMobile(mobile);
		return mobileAddress == null ? null : mobileAddress.getCity();
	}

	/**
	 * 判断是否为手机号
	 * @param number
	 * @return
	 */
	public boolean isMobile(String number) {
		return number.length() == 11 && (number.startsWith("13") || number.startsWith("14") || number.startsWith("15")
				|| number.startsWith("17") || number.startsWith("18"));
	}

	/**
	 * 判断是否为本地(上海)手机
	 * @param mobile
	 * @return
	 */
	public boolean isLocalMobile(String mobile) {
		String location = getCityByMobile(mobile);
		return "上海".equals(location);
	}
}
