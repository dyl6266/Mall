package com.dy.mapper;

import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.FavoriteDTO;

@Mapper
public interface FavoriteMapper {

	public int insertFavorite(FavoriteDTO params);

	public FavoriteDTO selectFavoriteDetails(FavoriteDTO params);

	public int updateFavorite(FavoriteDTO params);

	public int selectFavoriteTotalCount(String code);

	public int selectFavoriteStatus(HashMap<String, Object> params);

	public int selectFavoriteStatus(FavoriteDTO params);

}
