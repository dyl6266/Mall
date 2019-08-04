package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.PurchaseDTO;

@Mapper
public interface PurchaseMapper {

	public int insertPurchase(PurchaseDTO params);

	public PurchaseDTO selectPurchaseDetails(PurchaseDTO params);

	public int updatePurchase(PurchaseDTO params);

	public int deletePurchase(PurchaseDTO params);

	public List<PurchaseDTO> selectPurchaseList(PurchaseDTO params);

}
