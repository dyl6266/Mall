package com.dy.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.dy.common.Const.TableName;

@Mapper
public interface SequenceMapper {

	public Integer selectNextSequence(TableName tableName);

	public int updateSequence(HashMap<String, Object> params);

}
