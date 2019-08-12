package com.dy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.CartDTO;
import com.dy.service.CartService;
import com.dy.service.UserService;
import com.google.gson.JsonObject;

@Controller
public class CartController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/cart")
	@ResponseBody
	public JsonObject addGoodsToCart(@Validated @RequestBody CartDTO params, BindingResult bindingResult) {

		String username = userService.getAuthentication().getName();

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);

		} else if ("anonymousUser".equals(username)) {
			jsonObj.addProperty("message", "로그인이 필요한 서비스입니다. 로그인 페이지로 이동하시겠어요?");
			jsonObj.addProperty("type", 1);
			jsonObj.addProperty("result", false);

		} else {
			params.setUsername(username);
			try {
				/* 장바구니에 같은 상품이 존재하는지 확인 */
				boolean isExist = cartService.checkForDuplicateGoodsInCart(params);
				if (isExist == false) {
					jsonObj.addProperty("message", "이미 장바구니에 존재하는 상품입니다. 장바구니로 이동하시겠어요?");
					jsonObj.addProperty("type", 2);
					jsonObj.addProperty("result", false);

				} else {
					/* 장바구니에 상품을 추가한 결과 */
					boolean isAdded = cartService.addGoodsToCart(params);
					if (isAdded == false) {
						jsonObj.addProperty("message", "장바구니 추가에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
					} else {
						jsonObj.addProperty("message", "장바구니에 상품이 추가되었습니다. 장바구니로 이동하시겠어요?");
					}
					jsonObj.addProperty("result", isAdded);
				}

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "DB 처리 중에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}
	// end of method

//	@DeleteMapping(value = "/cart/goods/{codes}")
//	@ResponseBody
//	public JsonObject removeGoodsFromCart(@PathVariable("codes") List<String> codes) {
//
//		JsonObject jsonObj = new JsonObject();
//
//		/*
//		 * TODO
//		 * 1. 이메일 로그인 아이디로 처리하기
//		 * 2. 로그인이 필요한 서비스입니다.
//		 * 어차피 로그인한 아이디는 파라미터로 변경돼서 들어올 수 없으니 내부에서 처리하는 게 맞지
//		 */
//
//		if (CollectionUtils.isEmpty(codes)) {
//			jsonObj.addProperty("message", "장바구니에서 삭제할 상품을 선택해 주세요.");
//		} else {
//			boolean isRemoved = cartService.removeGoodsInCart(email, codes);
//			if (isRemoved == false) {
//				jsonObj.addProperty("message", "잘못된 접근입니다.");
//			} else {
//				jsonObj.addProperty("result", isRemoved);
//			}
//			jsonObj.addProperty("result", isRemoved);
//		}
//
//		return jsonObj;
//	}
//	// end of method
//
//	@GetMapping(value = "/cart/goods")
//	@ResponseBody
//	public JsonObject getListOfGoodsFromCart() {
//
//		JsonObject jsonObj = new JsonObject();
//
//		/*
//		 * TODO
//		 * 1. 이메일 로그인 아이디로 처리하기
//		 * 2. 로그인이 필요한 서비스입니다.
//		 * 어차피 로그인한 아이디는 파라미터로 변경돼서 들어올 수 없으니 내부에서 처리하는 게 맞지
//		 */
//		String email = getLoggedInID();
//
//		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(email);
//		if (CollectionUtils.isEmpty(goodsList)) {
//			jsonObj.add("goodsList", null);
//		} else {
//			Gson gson = new GsonBuilder().setPrettyPrinting().create();
//			JsonArray jsonArr = gson.toJsonTree(goodsList).getAsJsonArray();
//			jsonObj.add("goodsList", jsonArr);
//		}
//
//		return jsonObj;
//	}
	// end of method

	/**
	 * 여기부터 시작하기
	 * 여기부터 시작하기
	 * 여기부터 시작하기
	 * @param model
	 * @return
	 */
	@GetMapping("/cart")
	public String openGoodsListInCart(Model model) {

		String username = userService.getAuthentication().getName();
		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(username);
		model.addAttribute("goodsList", goodsList);
		return "goods/cart";
	}

}
