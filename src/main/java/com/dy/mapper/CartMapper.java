package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.CartDTO;

@Mapper
public interface CartMapper {

	public int insertGoodsToCart(CartDTO params);

	public CartDTO selectGoodsDetailsInCart(HashMap<String, Object> params);

	public int updateGoodsInCart(CartDTO params);

	public int deleteGoodsInCart(HashMap<String, Object> params);

	public List<CartDTO> selectGoodsListInCart(String username);

	public int selectGoodsCountInCart(CartDTO params);

	public int selectGoodsCountInCartByUsername(String username);

	public int selectTotalAmount(String username);

}
