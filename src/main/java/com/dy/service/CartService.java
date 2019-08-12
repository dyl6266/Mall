package com.dy.service;

import java.util.List;

import com.dy.domain.CartDTO;

public interface CartService {

	public boolean addGoodsToCart(CartDTO params);

	public boolean removeGoodsInCart(String username, List<String> codes);

	public List<CartDTO> getListOfGoodsInCart(String username);

	public boolean checkForDuplicateGoodsInCart(CartDTO params);

}
