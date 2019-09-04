package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.domain.AttachDTO;
import com.dy.mapper.AttachMapper;

@Service
public class AttachServiceImpl implements AttachService {

	@Autowired
	private AttachMapper attachMapper;

	@Override
	public boolean deleteAttach(String code, Integer idx) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("code", code);
		params.put("idx", idx);

		int queryResult = attachMapper.deleteAttach(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<AttachDTO> getAttachList(String code) {

		List<AttachDTO> attachList = null;

		int totalCount = attachMapper.selectAttachTotalCount(code);
		if (totalCount > 0) {
			attachList = attachMapper.selectAttachList(code);
		}

		return attachList;
	}

}
