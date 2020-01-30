package com.dy.service;

import com.dy.domain.FavoriteDTO;

public interface FavoriteService {

	public boolean changeFavoriteStatus(FavoriteDTO params);

	public int getFavoriteTotalCount(String code);

	public int getFavoriteStatus(String username, String code);

}
