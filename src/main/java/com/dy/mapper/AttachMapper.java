package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.AttachDTO;

@Mapper
public interface AttachMapper {

	public int insertAttach(AttachDTO params);

	public AttachDTO selectAttachDetails(Integer idx);

	public int deleteAttach(HashMap<String, Object> params);

	public int deleteAllAttachByCode(String code);

	public List<AttachDTO> selectAttachList(String code);

	public int selectAttachTotalCount(String code);

}
