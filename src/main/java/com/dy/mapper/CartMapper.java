package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.CartDTO;

@Mapper
public interface CartMapper {

	public int insertGoodsToCart(CartDTO params);

	public int insertGoodsToCart(HashMap<String, Object> params);

	public int deleteGoodsInCart(HashMap<String, Object> params);

	public List<CartDTO> selectGoodsListInCart(String email);

	public int selectGoodsCountInCart(HashMap<String, Object> params);

}
