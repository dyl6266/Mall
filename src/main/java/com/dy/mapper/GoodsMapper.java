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

	public int deleteGoods(String code);

	public List<GoodsDTO> selectGoodsList();

	public int selectGoodsTotalCount();

	// TODO => 시퀀스 테이블로 변경해야 할지 고민해보기
	public String generateGoodsCode(TableName tableName);

	public int checkForDuplicateGoodsCode(String code);

}
