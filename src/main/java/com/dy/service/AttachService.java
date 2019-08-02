package com.dy.service;

import java.util.List;

import com.dy.domain.AttachDTO;

public interface AttachService {

	public boolean registerAttach(AttachDTO params);

	public boolean removeAttach(List<Integer> idxs);

	public List<AttachDTO> getAttachList(String code);

}
