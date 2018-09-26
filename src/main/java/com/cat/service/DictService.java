package com.cat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.cat.mapper.DictMapper;
import com.cat.module.bean.Dict;
import com.google.common.collect.Lists;

@Service
@CacheConfig(cacheNames = "DictCache")
public class DictService extends BaseService {
	@Autowired
	DictMapper dictMapper;

	@Cacheable(key = "'findAllList'")
	public Map<String, List<Dict>> findAllList() {
		logger.info("把数据字典表放入缓存");
		Map<String, List<Dict>> dictMap = new HashMap<>();
		List<Dict> list = dictMapper.findAllList();
		for (Dict dict : list) {
			List<Dict> dictList = dictMap.get(dict.getType());
			if (dictList != null) {
				dictList.add(dict);
			} else {
				dictMap.put(dict.getType(), Lists.newArrayList(dict));
			}
		}
		return dictMap;
	}

}
