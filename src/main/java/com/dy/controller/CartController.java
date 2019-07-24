package com.dy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.CartDTO;
import com.dy.service.CartService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@Controller
public class CartController {

	public static String getLoggedInID() {
		return "dyl6266@nate.com";
	}

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CartService cartService;

	@PostMapping(value = "/cart/goods")
	@ResponseBody
	public JsonObject addGoodsToCart(@Validated @RequestBody CartDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);
		} else {
			/*
			 * TODO
			 * 1.이메일을 params에 담아서 오지 않고, 내부에서 처리하기 (**********)
			 * params.setEmail(getMemberId()) 등
			 */
			try {
				String email = getLoggedInID();

				/* 장바구니에 같은 상품이 존재하는지 확인 */
				boolean isExist = cartService.checkForDuplicateGoodsInCart(email, params.getCode());
				if (isExist == false) {
					jsonObj.addProperty("message", "이미 장바구니에 존재하는 상품입니다.");
					jsonObj.addProperty("result", false);
				} else {
					/* 장바구니에 상품을 추가한 결과 */
					params.setEmail(email);

					boolean isAdded = cartService.addGoodsToCart(params);
					if (isAdded == false) {
						jsonObj.addProperty("message", "장바구니에 추가할 수 없는 상품입니다.");
					} else {
						jsonObj.addProperty("message", "장바구니에 상품이 추가되었습니다.");
					}
					jsonObj.addProperty("result", isAdded);
				}

			} catch (DataAccessException e) {
				jsonObj.addProperty("result", false);
				e.printStackTrace();

			} catch (Exception e) {
				jsonObj.addProperty("result", false);
				e.printStackTrace();
			}
		}

		return jsonObj;
	}
	// end of method

	@DeleteMapping(value = "/cart/goods/{codes}")
	@ResponseBody
	public JsonObject removeGoodsFromCart(@PathVariable("codes") List<String> codes) {

		JsonObject jsonObj = new JsonObject();

		/*
		 * TODO
		 * 1. 이메일 로그인 아이디로 처리하기
		 * 2. 로그인이 필요한 서비스입니다.
		 * 어차피 로그인한 아이디는 파라미터로 변경돼서 들어올 수 없으니 내부에서 처리하는 게 맞지
		 */
		String email = getLoggedInID();

		if (CollectionUtils.isEmpty(codes)) {
			jsonObj.addProperty("message", "장바구니에서 삭제할 상품을 선택해 주세요.");
		} else {
			boolean isRemoved = cartService.removeGoodsInCart(email, codes);
			if (isRemoved == false) {
				jsonObj.addProperty("message", "잘못된 접근입니다.");
			} else {
				jsonObj.addProperty("result", isRemoved);
			}
			jsonObj.addProperty("result", isRemoved);
		}

		return jsonObj;
	}
	// end of method

	@GetMapping(value = "/cart/goods")
	@ResponseBody
	public JsonObject getListOfGoodsFromCart() {

		JsonObject jsonObj = new JsonObject();

		/*
		 * TODO
		 * 1. 이메일 로그인 아이디로 처리하기
		 * 2. 로그인이 필요한 서비스입니다.
		 * 어차피 로그인한 아이디는 파라미터로 변경돼서 들어올 수 없으니 내부에서 처리하는 게 맞지
		 */
		String email = getLoggedInID();

		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(email);
		if (CollectionUtils.isEmpty(goodsList)) {
			jsonObj.add("goodsList", null);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray jsonArr = gson.toJsonTree(goodsList).getAsJsonArray();
			jsonObj.add("goodsList", jsonArr);
		}

		return jsonObj;
	}
	// end of method

}
