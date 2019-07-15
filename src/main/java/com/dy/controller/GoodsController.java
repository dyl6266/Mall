package com.dy.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class GoodsController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GoodsService goodsService;

	@PostMapping(value = "/goods")
	@ResponseBody
	public JsonObject registerGoods(@Validated @RequestBody GoodsDTO params, BindingResult bindingResult) {

		JsonObject json = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			json.addProperty("message", fieldError.getDefaultMessage());
		} else {
			try {
				boolean result = goodsService.registerGoods(params);
				if (result == false) {
					json.addProperty("message", "상품 등록에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
				} else {
					json.addProperty("message", "상품 등록이 완료되었습니다.");
				}
				json.addProperty("result", result);

			} catch (DataAccessException e) {
				json.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				json.addProperty("result", false);

			} catch (Exception e) {
				json.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				json.addProperty("result", false);
			}
		}

		return json;
	}
	// end of method

	@GetMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject getGoodsDetails(@PathVariable("code") String code) {

		if (StringUtils.isEmpty(code)) {
			System.out.println("1111111111111");
		}

		JsonObject json = new JsonObject();

		GoodsDTO goods = goodsService.getGoodsDetails(code);
		if (goods == null) {
			json.addProperty("message", "존재하지 않는 상품입니다.");
			json.addProperty("result", false);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			/*
			 * 오브젝트를 파싱해서 JsonObject에 담기 위해서는 JsonElement를 사용
			 * 이러한 방식을 사용하지 않으면 웹에서 이상한 결과가 나오게 됨
			 * CommentController의 getCommentList()도 참고할 것
			 */
			JsonElement element = gson.toJsonTree(goods);

			json.add("goods", element);
//			json.addProperty("result", true);
		}

		return json;
	}
	
	@GetMapping(value = "/goods/test")
	public String test() {
		return "test";
	}

}
