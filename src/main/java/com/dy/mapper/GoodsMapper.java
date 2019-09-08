package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.common.Const.TableName;
import com.dy.domain.GoodsDTO;

@Mapper
public interface GoodsMapper {

	public int insertGoods(GoodsDTO params);

	public GoodsDTO selectGoodsDetails(String code);

	public int updateGoods(GoodsDTO params);

	public int deleteGoods(List<String> codes);

	public List<GoodsDTO> selectGoodsList();

	public List<GoodsDTO> selectGoodsListWithMainImage();

	public int selectGoodsTotalCount();

	public String generateGoodsCode(TableName tableName);

	public int checkForDuplicateGoodsCode(String code);

	public String generateMaxGoodsCode(TableName tableName);

}
