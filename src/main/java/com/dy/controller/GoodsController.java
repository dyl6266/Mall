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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class GoodsController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GoodsService goodsService;

	@PostMapping(value = "/goods")
	@ResponseBody
	public JsonObject insertGoods(@Validated @RequestBody GoodsDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
		} else {
			try {
				boolean result = goodsService.registerGoods(params);
				if (result == false) {
					jsonObj.addProperty("message", "상품 등록에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
				} else {
					jsonObj.addProperty("message", "상품 등록이 완료되었습니다.");
				}
				jsonObj.addProperty("result", result);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}
	// end of method

	@PatchMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject updateGoods(@PathVariable("code") String code, @Validated @RequestBody GoodsDTO params,
			BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
		} else {
			try {
				boolean result = goodsService.registerGoods(params);
				if (result == false) {
					jsonObj.addProperty("message", "상품 수정에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
				} else {
					jsonObj.addProperty("message", "상품 수정이 완료되었습니다.");
				}
				jsonObj.addProperty("result", result);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}
	// end of method

	@GetMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject getGoodsDetails(@PathVariable("code") String code) {

		JsonObject jsonObj = new JsonObject();

		GoodsDTO goods = goodsService.getGoodsDetails(code);
		if (goods == null) {
			jsonObj.addProperty("message", "존재하지 않는 상품입니다.");
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			/*
			 * 오브젝트를 파싱해서 JsonObject에 담기 위해서는 JsonElement를 사용
			 * 이러한 방식을 사용하지 않으면 웹에서 이상한 결과가 나오게 됨
			 * CommentController의 getCommentList()도 참고할 것
			 */
			JsonElement jsonElem = gson.toJsonTree(goods);

			jsonObj.add("goods", jsonElem);
		}

		return jsonObj;
	}
	// end of method

	@DeleteMapping(value = "/goods/{codeList}")
	@ResponseBody
	public JsonObject deleteGoods(@PathVariable("codeList") List<String> codeList) {

		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(codeList)) {
			jsonObj.addProperty("message", "삭제할 상품을 선택해 주세요.");
		} else {
			try {
				boolean result = goodsService.deleteGoods(codeList);
				if (result == false) {
					jsonObj.addProperty("message", "상품 삭제에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
					jsonObj.addProperty("result", false);
				}
			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}
		jsonObj.addProperty("result", true);

		return jsonObj;
	}
	// end of method

	@GetMapping(value = "/goods")
	@ResponseBody
	public JsonObject getGoodsList() {

		JsonObject jsonObj = new JsonObject();

		List<GoodsDTO> goodsList = goodsService.getGoodsList();
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

	@GetMapping("/test")
	public String test() {
		return "test";
	}

}
