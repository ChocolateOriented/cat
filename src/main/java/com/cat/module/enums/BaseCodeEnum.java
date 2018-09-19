package com.cat.module.enums;

import java.util.EnumSet;
import java.util.Iterator;

public interface BaseCodeEnum {

	int getValue();

	//通过value获取对应的Enum
	static <E extends Enum<E> & BaseCodeEnum> E getCodeEnumByValue(Class<E> enumClz,int value){
		Iterator<E> iterator = EnumSet.allOf(enumClz).iterator();
		while (iterator.hasNext()){
			E e = iterator.next();
			if (e.getValue() == value){
				return e;
			}
		}
		return null;
	}
}