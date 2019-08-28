package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;
import com.dy.util.UiUtils;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/admin")
public class AdminGoodsController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GoodsService goodsService;

	/**
	 * 상품 등록
	 * 
	 * @param params - GoodsDTO
	 * @param bindingResult - 에러 매핑 오브젝트
	 * @param files - 상품 이미지 파일
	 * @return JsonObject - (message, result)
	 */
	@PostMapping(value = "/goods")
	@ResponseBody
	public JsonObject registerGoods(@Validated GoodsDTO params, BindingResult bindingResult, MultipartFile[] files) {

//		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
//		String violations = validator.validate(params, MyGroupSequence.class).toString();

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "모든 필드에 값을 입력해 주세요.");
			} else {
				FieldError fieldError = bindingResult.getFieldError();
				jsonObj.addProperty("message", fieldError.getDefaultMessage());
			}
			jsonObj.addProperty("result", false);

		} else if (ObjectUtils.isEmpty(files)) {
			jsonObj.addProperty("message", "상품 이미지를 하나 이상 등록해 주세요.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				/* 상품 등록 */
				boolean isInserted = goodsService.registerGoods(params, files);
				if (isInserted == false) {
					jsonObj.addProperty("message", "상품 등록에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isInserted);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
				e.printStackTrace();
			}
		}

		return jsonObj;
	}
	// end of method

	/**
	 * 관리자 상품 목록
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/goods")
	public String openAdminGoodsList(Model model) {

		List<GoodsDTO> goods = goodsService.getGoodsList();
		model.addAttribute("goods", goods);

		return "admin/goods/list";
	}

	/**
	 * 관리자 상품 등록
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/goods/register")
	public String openAdminGoodsRegister(Model model) {

		model.addAttribute("goods", new GoodsDTO());

		return "admin/goods/register";
	}

}
