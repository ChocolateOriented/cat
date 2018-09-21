package com.cat.mapper;

import java.util.List;

import com.cat.module.bean.Dict;


public interface DictMapper {
	
	public List<String> findTypeList(Dict dict);
	
	public List<Dict> findAllList(Dict dict);
	
}
