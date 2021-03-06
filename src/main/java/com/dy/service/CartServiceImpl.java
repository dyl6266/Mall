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
		String code = params.getGoods().getCode();

		GoodsDTO goods = goodsMapper.selectGoodsDetails(code);
		if (goods == null || goods.getStatus() == Status.N) {
			return false;
		} else {
			int queryResult = cartMapper.insertGoodsToCart(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean changeQuantityOfGoodsInCart(CartDTO params) {
		/*
		 *  TODO => 사용자 이메일이 존재하는지 체크하는 메소드 만들기 (UserDTO 오브젝트)
		 *  		아니면 컨트롤러에서 Json으로 처리해야 하나...? 잘 생각해보자
		 */
		String code = params.getGoods().getCode();
		int quantity = params.getGoods().getQuantity();

		/*
		 *  TODO => 상품의 수량보다 장바구니의 수량이 더 많은 경우는 화면에서 제약을 줄까?
		 *  		이것도 생각해보자
		 */
		GoodsDTO goods = goodsMapper.selectGoodsDetails(code);
		if (goods == null || quantity > goods.getQuantity()) {
			return false;
		} else {
			int queryResult = cartMapper.updateGoodsInCart(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean removeGoodsInCart(String email, List<String> codeList) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("codeList", codeList);

		int queryResult = cartMapper.deleteGoodsInCart(params);
		if (queryResult < 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<CartDTO> getListOfGoodsInCart(String email) {
		/*
		 *  TODO => 사용자 이메일이 존재하는지 체크하는 메소드 만들기 (UserDTO 오브젝트)
		 *  		아니면 컨트롤러에서 Json으로 처리해야 하나...? 잘 생각해보자
		 */
		List<CartDTO> goodsList = null;

		/*
		 * TODO
		 * 1. 등록 시 중복 처리 때문에 이렇게 한건데 굳이 이렇게 할 필요가 있을지 검토해보자..
		 * 2. 따로 메소드를 만드는 게 나을까..?
		 */
		HashMap<String, Object> params = new HashMap<>();
		params.put("email", email);

		int goodsTotalCount = cartMapper.selectGoodsCountInCart(params);
		if (goodsTotalCount > 0) {
			goodsList = cartMapper.selectGoodsListInCart(email);
		}

		return goodsList;
	}

	@Override
	public boolean checkForDuplicateGoodsInCart(String email, String code) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("email", email);
		params.put("code", code);

		int queryResult = cartMapper.selectGoodsCountInCart(params);
		if (queryResult != 0) {
			return false;
		}

		return true;
	}

}
