package com.dy.service;

import java.util.List;

import com.dy.domain.CartDTO;

public interface CartService {

	public boolean registerGoodsToCart(CartDTO params);

	public CartDTO getGoodsDetailsInCart(String username, String code);

	public boolean deleteGoodsInCart(String username, List<String> codes);

	public List<CartDTO> getListOfGoodsInCart(String username);

	public int getTotalAmount(String username);

	public boolean checkForDuplicateGoodsInCart(CartDTO params);

}
