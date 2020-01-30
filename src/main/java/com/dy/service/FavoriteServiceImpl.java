package com.dy.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.common.Const.Status;
import com.dy.domain.FavoriteDTO;
import com.dy.mapper.FavoriteMapper;

@Service
public class FavoriteServiceImpl implements FavoriteService {

	@Autowired
	private FavoriteMapper favoriteMapper;

	@Override
	public boolean changeFavoriteStatus(FavoriteDTO params) {

		int queryResult;

		/* 좋아요를 적용했던 내역(레코드)이 존재하지 않으면 insert, 존재하면 update 실행 */
		FavoriteDTO favoriteHistory = favoriteMapper.selectFavoriteDetails(params);
		if (favoriteHistory == null) {
			queryResult = favoriteMapper.insertFavorite(params);

		} else {
			/* 현재 좋아요 상태의 반대되는 값으로 status 설정 */
			int favoriteStatus = favoriteMapper.selectFavoriteStatus(params);
			Status status = (favoriteStatus == 1) ? Status.N : Status.Y;
			params.setStatus(status);

			queryResult = favoriteMapper.updateFavorite(params);
		}

		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public int getFavoriteTotalCount(String code) {

		return favoriteMapper.selectFavoriteTotalCount(code);
	}

	@Override
	public int getFavoriteStatus(String username, String code) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("code", code);

		return favoriteMapper.selectFavoriteStatus(params);
	}

}
