package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.PurchaseDTO;
import com.dy.domain.ReviewDTO;
import com.dy.service.PurchaseService;
import com.dy.service.ReviewService;
import com.dy.service.UserService;
import com.dy.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class ReviewController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ReviewService reviewService;

	@Autowired
	private UserService userService;

	@Autowired
	private PurchaseService purchaseService;

	@PostMapping(value = "/reviews")
	@ResponseBody
	public JsonObject registerReview(@RequestBody @Validated ReviewDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		String username = userService.getAuthentication().getName();

		/* 구매 내역 확인 */
		PurchaseDTO history = purchaseService.getPurchaseDetails(username, params.getCode());
		if (history == null) {
			jsonObj.addProperty("message", "리뷰는 상품 구매 후에 작성할 수 있습니다.");
			jsonObj.addProperty("result", false);
			return jsonObj;
		}

		/* Bean 유효성 검사에 실패한 경우 */
		if (bindingResult.hasErrors()) {
			if (bindingResult.getErrorCount() > 1) {
				jsonObj.addProperty("message", "모든 필드에 값을 입력해 주세요.");
			} else {
				FieldError fieldError = bindingResult.getFieldError();
				jsonObj.addProperty("message", fieldError.getDefaultMessage());
			}
			jsonObj.addProperty("result", false);

		} else {
			try {
				params.setUsername(username);
				/* 리뷰 등록 */
				boolean isInserted = reviewService.registerReview(params);
				if (isInserted == false) {
					jsonObj.addProperty("message", "리뷰 등록에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isInserted);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}

	@GetMapping(value = "/reviews")
	@ResponseBody
	public JsonObject openReviewList(ReviewDTO params) {

		JsonObject jsonObj = new JsonObject();

		List<ReviewDTO> reviewList = reviewService.getReviewList(params);
		if (CollectionUtils.isEmpty(reviewList)) {
			jsonObj.add("reviews", null);

		} else {
			/* 결과 리스트 */
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonElement jsonElem = gson.toJsonTree(reviewList);
			JsonArray jsonArr = jsonElem.getAsJsonArray();
			jsonObj.add("reviews", jsonArr);

			/* 페이징 정보 */
			jsonElem = new Gson().toJsonTree(params);
			jsonObj.add("params", jsonElem);

			/* 로그인 여부 */
			boolean isAnonymousUser = userService.isAnonymousUser();
			jsonObj.addProperty("isAnonymousUser", isAnonymousUser);
		}

		return jsonObj;
	}

}
