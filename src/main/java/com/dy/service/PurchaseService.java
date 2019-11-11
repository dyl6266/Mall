package com.dy.service;

import java.util.List;
import java.util.Map;

import com.dy.domain.PurchaseDTO;

public interface PurchaseService {

	public boolean purchaseGoods(PurchaseDTO params);

	public PurchaseDTO getPurchaseDetails(Integer idx, String username);

	public PurchaseDTO getPurchaseDetails(String username, String code);

	// TODO => 삭제도 필요한지 검토해보기 (Mapper와 같이)
	public boolean deletePurchaseInfo(Integer idx, String username);

	public List<List<Map<String, Object>>> getPurchaseList(String username);

}
