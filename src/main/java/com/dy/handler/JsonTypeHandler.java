package com.dy.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonTypeHandler extends BaseTypeHandler<Object> {

	Gson gson = new GsonBuilder().create();

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
			throws SQLException {

		String str = gson.toJson(parameter);
		ps.setObject(i, str);
	}

	@Override
	public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {

		Object obj = rs.getObject(columnName);
		if (obj == null) {
			return obj;
		}

		return gson.fromJson(obj.toString(), Object.class);
	}

	@Override
	public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {

		Object obj = rs.getObject(columnIndex);
		if (obj == null) {
			return obj;
		}

		return gson.fromJson(obj.toString(), Object.class);
	}

	@Override
	public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {

		Object obj = cs.getObject(columnIndex);
		if (obj == null) {
			return obj;
		}

		return gson.fromJson(obj.toString(), Object.class);
	}

}
