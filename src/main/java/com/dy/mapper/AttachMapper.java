package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.AttachDTO;

@Mapper
public interface AttachMapper {

	public int insertAttach(AttachDTO params);

	public AttachDTO selectAttachDetails(Integer idx);

	public int updateAttach(AttachDTO params);

	public int deleteAttach(List<Integer> idxs);

	public List<AttachDTO> selectAttachList(String code);

	public int selectAttachTotalCount(String code);

}
