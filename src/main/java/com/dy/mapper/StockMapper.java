package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.StockDTO;

@Mapper
public interface StockMapper {

	public int insertStock(StockDTO params);

	public StockDTO selectStockDetails(String code);

	public int updateStock(StockDTO params);

	public int deleteStock(String code);

	public List<StockDTO> selectStockList();

	public int selectStockTotalCount();

	public String test(List<String> colors);

}
