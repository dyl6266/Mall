package com.dy.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.dy.domain.AttachDTO;
import com.dy.domain.GoodsDTO;
import com.dy.service.AttachService;
import com.dy.service.GoodsService;
import com.dy.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/admin")
public class AdminGoodsController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private AttachService attachService;

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

		/* Bean 유효성 검사에 실패한 경우 */
		if (bindingResult.hasErrors()) {
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "모든 필드에 값을 입력해 주세요.");
			} else {
				FieldError fieldError = bindingResult.getFieldError();
				jsonObj.addProperty("message", fieldError.getDefaultMessage());
			}
			jsonObj.addProperty("result", false);

			/* 이미지가 넘어오지 않은 경우 (ObjectUtils의 isEmpty() 메소드로는 체크 불가능) */
		} else if (files[0].getSize() < 1) {
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
	 * 상품 수정
	 * 
	 * @param code - 상품 코드
	 * @param params - GoodsDTO
	 * @param bindingResult - 에러 매핑 오브젝트
	 * @param files - 상품 이미지 파일
	 * @return JsonObject - (message, result)
	 */
	@PostMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject updateGoods(@PathVariable("code") String code, @Validated GoodsDTO params,
			BindingResult bindingResult, MultipartFile[] files) {

		JsonObject jsonObj = new JsonObject();

		/* Bean 유효성 검사에 실패한 경우 */
		if (bindingResult.hasErrors()) {
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "모든 필드에 값을 입력해 주세요.");
			} else {
				FieldError fieldError = bindingResult.getFieldError();
				jsonObj.addProperty("message", fieldError.getDefaultMessage());
			}
			jsonObj.addProperty("result", false);

			/* 해당 상품에 이미지가 하나도 존재하지 않는 경우 */
		} else if (files[0].getSize() < 1) {
			List<AttachDTO> attachList = attachService.getAttachList(code);
			if (CollectionUtils.isEmpty(attachList)) {
				jsonObj.addProperty("message", "상품 이미지를 하나 이상 등록해 주세요.");
				jsonObj.addProperty("result", false);
			}

		} else {
			try {
				/* 상품 정보 수정 */
				boolean isUpdated = goodsService.registerGoods(params, files);
				if (isUpdated == false) {
					jsonObj.addProperty("message", "상품 등록에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isUpdated);

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

	/**
	 * 관리자 상품 목록 페이지
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/goods")
	public String openAdminGoodsList(@ModelAttribute("params") GoodsDTO params, Model model) {

		List<GoodsDTO> goods = goodsService.getGoodsList(params);
		model.addAttribute("goods", goods);

		return "admin/goods/list";
	}

	/**
	 * 관리자 상품 등록 페이지
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/goods/register")
	public String openAdminGoodsRegister(@RequestParam(value = "code", required = false) String code, Model model) {

		if (StringUtils.isEmpty(code)) {
			model.addAttribute("goods", new GoodsDTO());

		} else {
			model.addAttribute("code", code);
			GoodsDTO goods = goodsService.getGoodsDetails(code);
			if (goods != null) {
				/* 사이즈, 수량을 key : value 형태로 담은 문자열 */
				String optionsStr = goods.getStock().getOptions();

				/* optionsStr을 JsonObject로 변환 */
				JsonObject options = new Gson().fromJson(optionsStr, JsonObject.class);
				model.addAttribute("options", options);
			}
			model.addAttribute("goods", goods);

			List<AttachDTO> files = attachService.getAttachList(code);
			model.addAttribute("files", files);
		}

		return "admin/goods/register";
	}

	@DeleteMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject deleteGoods(@PathVariable("code") String code,
			@RequestParam(value = "idx", required = false) Integer idx) {

		JsonObject jsonObj = new JsonObject();
		if (idx == null) {
			jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				boolean isDeleted = attachService.deleteAttach(code, idx);
				if (isDeleted == false) {
					jsonObj.addProperty("message", "파일 삭제에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				} else {
					int totalCount = attachService.getAttachTotalCount(code);
					jsonObj.addProperty("totalCount", totalCount);
				}
				jsonObj.addProperty("result", isDeleted);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}

}
