package com.dy.service;

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
	public boolean registerAttach(AttachDTO params) {
		
		
		return false;
	}

	@Override
	public boolean removeAttach(List<Integer> idxs) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AttachDTO> getAttachList(String code) {
		// TODO Auto-generated method stub
		return null;
	}

}
