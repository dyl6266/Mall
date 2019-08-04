package com.dy.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.dy.domain.GoodsDTO;

public interface GoodsService {

	public boolean registerGoods(GoodsDTO params);

	public boolean registerGoods(GoodsDTO params, MultipartFile[] files) throws IOException;

	public GoodsDTO getGoodsDetails(String code);

	public Map<String, Object> getGoodsDetailsWithImages(String code);

	public boolean deleteGoods(List<String> codes);

	public List<GoodsDTO> getGoodsList();

}
