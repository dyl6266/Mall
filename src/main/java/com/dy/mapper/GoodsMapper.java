package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.common.Const.TableName;
import com.dy.domain.GoodsDTO;

// TODO => 메소드명 고민해보기 => by, to, a 이런 거 파파고랑 구글이랑 같이 돌려보자.. 아니면 혜미형한테 물어보기?
@Mapper
public interface GoodsMapper {

	public int insertGoods(GoodsDTO params);

	public GoodsDTO selectGoodsDetails(String code);

	public int updateGoods(GoodsDTO params);

	public int deleteGoods(List<String> codeList);

	public List<GoodsDTO> selectGoodsList();

	public int selectGoodsTotalCount();

	public String generateGoodsCode(TableName tableName);

	public int checkForDuplicateGoodsCode(String code);

}
