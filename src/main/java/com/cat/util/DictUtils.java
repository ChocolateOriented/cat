/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cat.util;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.cat.mapper.DictMapper;
import com.cat.module.bean.Dict;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 字典工具类
 * @author ThinkGem
 * @version 2013-5-29
 */
public class DictUtils {
	
	private static DictMapper dictDao = ApplicationContextHolder.getBean(DictMapper.class);

	public static final String CACHE_DICT_MAP = "dictMap";
	
	/**
	 * 根据键值和类型获取标签
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabel(String value, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(value)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && value.equals(dict.getValue())){
					return dict.getLabel();
				}
			}
		}
		return defaultValue;
	}
	
	
	/**
	 * 根据标签和类型获取描述
	 * @param label
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictDescription(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getDescription();
				}
			}
		}
		return defaultLabel;
	}
	
	
	/**
	 * 根据键值和类型获取标签列表
	 * @param value
	 * @param type
	 * @param defaultValue
	 * @return
	 */
	public static String getDictLabels(String values, String type, String defaultValue){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(values)){
			List<String> valueList = Lists.newArrayList();
			for (String value : StringUtils.split(values, ",")){
				valueList.add(getDictLabel(value, type, defaultValue));
			}
			return StringUtils.join(valueList, ",");
		}
		return defaultValue;
	}
	

	/**
	 * 根据标签和类型获取键值
	 * @param label
	 * @param type
	 * @param defaultLabel
	 * @return
	 */
	public static String getDictValue(String label, String type, String defaultLabel){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict.getValue();
				}
			}
		}
		return defaultLabel;
	}
	
	/**
	 * 根据标签和类型获取键值
	 * @param label
	 * @param type
	 * @param defaultDict
	 * @return
	 */
	public static Dict getDict(String label, String type, Dict defaultDict){
		if (StringUtils.isNotBlank(type) && StringUtils.isNotBlank(label)){
			for (Dict dict : getDictList(type)){
				if (type.equals(dict.getType()) && label.equals(dict.getLabel())){
					return dict;
				}
			}
		}
		return defaultDict;
	}
	
	/**
	 * 获取字典集合
	 * @param type
	 * @return
	 */
	public static List<Dict> getDictList(String type){
		@SuppressWarnings("unchecked")
			Map<String, List<Dict>>	dictMap = Maps.newHashMap();
			for (Dict dict : dictDao.findAllList(new Dict())){
				List<Dict> dictList = dictMap.get(dict.getType());
				if (dictList != null){
					dictList.add(dict);
				}else{
					dictMap.put(dict.getType(), Lists.newArrayList(dict));
				}
			}
		List<Dict> dictList = dictMap.get(type);
		if (dictList == null){
			dictList = Lists.newArrayList();
		}
		return dictList;
	}
	
}
