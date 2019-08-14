package com.dy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.common.Const.Method;
import com.dy.domain.CartDTO;
import com.dy.domain.GoodsDTO;
import com.dy.service.CartService;
import com.dy.service.GoodsService;
import com.dy.service.UserService;
import com.dy.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class CartController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private GoodsService goodsService;

	/**
	 * 장바구니 상품 추가
	 * 
	 * @param params - CartDTO 오브젝트
	 * @param bindingResult - Bean 유효성 검사 에러 매핑 오브젝트
	 * @return
	 */
	@PostMapping(value = "/cart")
	@ResponseBody
	public JsonObject addGoodsToCart(@Validated @RequestBody CartDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		String username = userService.getAuthentication().getName();

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

	/**
	 * 옵션 변경 팝업 HTML (Ajax Success Response)
	 * 
	 * @param code - 상품 코드
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/cart/option-popup")
	public String returnChangeOptionPopupHTML(@RequestParam(value = "code", required = false) String code,
			Model model) {

		if (StringUtils.isEmpty(code)) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/goods/list", Method.GET, null, model);
		}

		Map<String, Object> map = goodsService.getGoodsDetailsWithImages(code);
		for (String key : map.keySet()) {
			model.addAttribute(key, map.get(key));
		}

		if (map.get("goods") != null) {
			GoodsDTO goods = (GoodsDTO) map.get("goods");
			String optionsStr = goods.getStock().getOptions();

			JsonObject options = new Gson().fromJson(optionsStr, JsonObject.class);
			model.addAttribute("options", options);
		}

		return "user/cart/option-popup";
	}

	/**
	 * 장바구니 상품 삭제 
	 * 
	 * @param codes - 상품 코드 리스트
	 * @return 메시지, 결과
	 */
	@PatchMapping(value = "/cart/{codes}")
	@ResponseBody
	public JsonObject removeGoodsFromCart(@PathVariable("codes") List<String> codes) {

		JsonObject jsonObj = new JsonObject();

		String username = userService.getAuthentication().getName();

		if ("anonymousUser".equals(username)) {
			jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			jsonObj.addProperty("result", false);

		} else if (CollectionUtils.isEmpty(codes)) {
			jsonObj.addProperty("message", "삭제할 상품을 선택해 주세요.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				boolean isRemoved = cartService.deleteGoodsInCart(username, codes);
				if (isRemoved == false) {
					jsonObj.addProperty("message", "상품 삭제에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isRemoved);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터 베이스 처리 중에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}
	// end of method

	/**
	 * 장바구니 상품 목록 HTML (Ajax Success Response)
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/cart/goods-list")
	public String returnGoodsListInCartHTML(Model model) {

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", request.getContextPath() + "/login", Method.GET, null, model);
		}

		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(username);
		model.addAttribute("goodsList", goodsList);

		return "user/cart/goods-list";
	}
	// end of method

}
