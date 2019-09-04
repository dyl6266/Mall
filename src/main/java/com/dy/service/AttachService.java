package com.dy.service;

import java.util.List;

import com.dy.domain.AttachDTO;

public interface AttachService {

	public boolean deleteAttach(String code, Integer idx);

	public List<AttachDTO> getAttachList(String code);

}
