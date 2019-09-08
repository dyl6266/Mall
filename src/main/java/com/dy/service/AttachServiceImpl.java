package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.domain.AttachDTO;
import com.dy.mapper.AttachMapper;
import com.dy.util.AttachFileUtils;
import com.dy.util.CommonUtils;

@Service
public class AttachServiceImpl implements AttachService {

	@Autowired
	private AttachMapper attachMapper;

	@Override
	public boolean deleteAttach(String code, Integer idx) {

		/* 파일 조회 */
		AttachDTO attach = attachMapper.selectAttachDetails(idx);
		if (attach == null) {
			return false;
		}

		/* 업로드된 날짜 */
		String uploadedDate = CommonUtils.formatDate(attach.getInsertTime(), "yy-MM-dd");
		/* 디스크에서 파일 삭제 */
		boolean isDeleted = AttachFileUtils.deleteFile(attach.getStoredName(), uploadedDate);
		if (isDeleted == false) {
			return false;
		}

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
	public boolean deleteAllAttachByCode(String code) {

		int queryResult = attachMapper.deleteAllAttachByCode(code);
		if (queryResult != 1) {
			return false;
		}

		return false;
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

	@Override
	public int getAttachTotalCount(String code) {

		int totalCount = attachMapper.selectAttachTotalCount(code);
		return totalCount;
	}

}
