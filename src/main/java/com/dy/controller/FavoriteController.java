package com.dy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dy.domain.FavoriteDTO;
import com.dy.service.FavoriteService;
import com.dy.service.UserService;
import com.google.gson.JsonObject;

@RestController
public class FavoriteController {

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private UserService userService;

	@PostMapping(value = "/favorite")
	public JsonObject applyFavorite(@RequestBody FavoriteDTO params) {

		JsonObject jsonObj = new JsonObject();

		if (userService.isAnonymousUser()) {
			jsonObj.addProperty("message", "로그인이 필요한 서비스입니다.");
			jsonObj.addProperty("result", false);

		} else if (StringUtils.isEmpty(params.getCode())) {
			jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				String username = userService.getAuthentication().getName();
				params.setUsername(username);

				boolean isApplied = favoriteService.changeFavoriteStatus(params);
				if (isApplied == false) {
					jsonObj.addProperty("message", "좋아요 처리 중에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				}

				/* 좋아요 개수 (뷰에서 바인딩 용도로 사용) */
				int totalCount = favoriteService.getFavoriteTotalCount(params.getCode());
				jsonObj.addProperty("totalCount", totalCount);
				jsonObj.addProperty("result", isApplied);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}

	@PatchMapping(value = "/favorite/{code}")
	public JsonObject changeFavoriteStatus(@PathVariable("code") String code, FavoriteDTO params) {

		JsonObject jsonObj = new JsonObject();

		if (StringUtils.isEmpty(code)) {
			jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				String username = userService.getAuthentication().getName();
				params.setUsername(username);

				boolean isChanged = favoriteService.changeFavoriteStatus(params);
				if (isChanged == false) {
					jsonObj.addProperty("message", "좋아요 처리 중에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				}

				/* 좋아요 개수 (뷰에서 바인딩 용도로 사용) */
				int totalCount = favoriteService.getFavoriteTotalCount(params.getCode());
				jsonObj.addProperty("totalCount", totalCount);
				jsonObj.addProperty("result", isChanged);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스 처리 중에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}

}
