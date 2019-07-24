package com.dy.service;

import java.util.List;

import com.dy.domain.CartDTO;

public interface CartService {

	public boolean addGoodsToCart(CartDTO params);

	public boolean changeQuantityOfGoodsInCart(CartDTO params);

	public boolean removeGoodsInCart(String email, List<String> codeList);

	public List<CartDTO> getListOfGoodsInCart(String email);

	public boolean checkForDuplicateGoodsInCart(String email, String code);

}
