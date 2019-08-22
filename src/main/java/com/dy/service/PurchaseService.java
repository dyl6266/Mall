package com.dy.service;

import java.util.List;

import com.dy.domain.PurchaseDTO;

public interface PurchaseService {

	public boolean purchaseGoods(PurchaseDTO params);

	public PurchaseDTO getPurchaseDetails(Integer idx, String username);

	// TODO => 삭제도 필요한지 검토해보기 (Mapper와 같이)
	public boolean deletePurchaseInfo(Integer idx, String username);

	public List<PurchaseDTO> getPurchaseList(String username);

}
