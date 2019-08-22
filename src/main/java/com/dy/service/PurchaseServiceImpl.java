package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.domain.PurchaseDTO;
import com.dy.mapper.PurchaseMapper;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseMapper purchaseMapper;

	@Override
	public boolean purchaseGoods(PurchaseDTO params) {

		// TODO => 상품이 리스트 형태로 넘어오는 경우에는 foreach로 처리를 해야 할까? 어떻게 해야 할지 생각해보기
		int queryResult = purchaseMapper.insertPurchase(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public PurchaseDTO getPurchaseDetails(Integer idx, String username) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("idx", idx);
		params.put("username", username);

		return purchaseMapper.selectPurchaseDetails(params);
	}

	// TODO => 삭제가 필요한지 생각해보기
	@Override
	public boolean deletePurchaseInfo(Integer idx, String username) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("idx", idx);
		params.put("username", username);

		int queryResult = purchaseMapper.deletePurchase(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<PurchaseDTO> getPurchaseList(String username) {

		List<PurchaseDTO> purchaseList = null;

		int totalCount = purchaseMapper.selectPurchaseTotalCount(username);
		if (totalCount > 0) {
			purchaseList = purchaseMapper.selectPurchaseList(username);
		}

		return purchaseList;
	}

}
