package com.cat.util;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import com.cat.module.enums.BaseCodeEnum;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jxli on 2018/3/14.
 */
@MappedTypes(value = {})
public class CodeEnumTypeHandler<E extends Enum<E> & BaseCodeEnum> extends BaseTypeHandler<E> {
	private Class<E> type;

	public CodeEnumTypeHandler(Class<E> type) {
		if(type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		} else {
			this.type = type;
		}
	}

	public CodeEnumTypeHandler() {

	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if(jdbcType == null) {
			ps.setInt(i, parameter.getValue());
		} else {
			ps.setObject(i, parameter.getValue(), jdbcType.TYPE_CODE);
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		int value = rs.getInt(columnName);
		return BaseCodeEnum.getCodeEnumByValue(type,value);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		int value = rs.getInt(columnIndex);
		return BaseCodeEnum.getCodeEnumByValue(type,value);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		int value = cs.getInt(columnIndex);
		return BaseCodeEnum.getCodeEnumByValue(type,value);
	}
}
