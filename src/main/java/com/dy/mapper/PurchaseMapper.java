package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.PurchaseDTO;

@Mapper
public interface PurchaseMapper {

	public int insertPurchase(PurchaseDTO params);

	public PurchaseDTO selectPurchaseDetails(HashMap<String, Object> params);

	public int updatePurchase(PurchaseDTO params);

	public int deletePurchase(HashMap<String, Object> params);

	public List<PurchaseDTO> selectPurchaseList(String username);

	public int selectPurchaseTotalCount(String username);

	public List<Integer> selectPurchaseSequenceList(String username);

	public List<PurchaseDTO> selectPurchaseGoodsList(Integer sequence);

}
