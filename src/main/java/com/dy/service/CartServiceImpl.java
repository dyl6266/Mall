package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.common.Const.Status;
import com.dy.domain.CartDTO;
import com.dy.domain.GoodsDTO;
import com.dy.mapper.CartMapper;
import com.dy.mapper.GoodsMapper;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartMapper cartMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public boolean addGoodsToCart(CartDTO params) {

		/*
		 *  TODO => 사용자 이메일이 존재하는지 체크하는 메소드 만들기 (UserDTO 오브젝트)
		 *  		아니면 컨트롤러에서 Json으로 처리해야 하나...? 잘 생각해보자
		 */
		GoodsDTO goods = goodsMapper.selectGoodsDetails(params.getCode());
		if (goods == null || (goods.getStatus() == Status.N || Status.S == goods.getStatus())) {
			return false;
		}

		int queryResult = cartMapper.insertGoodsToCart(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteGoodsInCart(String username, List<String> codes) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("codes", codes);

		int queryResult = cartMapper.deleteGoodsInCart(params);
		if (queryResult < 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<CartDTO> getListOfGoodsInCart(String username) {

		List<CartDTO> goodsList = null;

		int goodsTotalCount = cartMapper.selectGoodsCountInCartByUsername(username);
		if (goodsTotalCount > 0) {
			goodsList = cartMapper.selectGoodsListInCart(username);
		}

		return goodsList;
	}

	@Override
	public boolean checkForDuplicateGoodsInCart(CartDTO params) {

		int queryResult = cartMapper.selectGoodsCountInCart(params);
		if (queryResult != 0) {
			return false;
		}

		return true;
	}

}
