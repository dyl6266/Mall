package com.dy.service;

import java.util.List;

import com.dy.domain.CombineDTO;
import com.dy.domain.GoodsDTO;

public interface GoodsService {

	public boolean registerGoods(CombineDTO params);

	public GoodsDTO getGoodsDetails(String code);

	public boolean deleteGoods(List<String> codes);

	public List<GoodsDTO> getGoodsList();

}
