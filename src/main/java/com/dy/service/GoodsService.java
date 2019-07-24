package com.dy.service;

import java.util.List;

import com.dy.domain.GoodsDTO;

public interface GoodsService {

	public boolean registerGoods(GoodsDTO params);

	public GoodsDTO getGoodsDetails(String code);

	public boolean deleteGoods(List<String> codeList);

	public List<GoodsDTO> getGoodsList();

}
