package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.ReviewDTO;

@Mapper
public interface ReviewMapper {

	public int insertReview(ReviewDTO params);

	public int updateReview(ReviewDTO params);

	public int deleteReview(HashMap<String, Object> params);

	public List<ReviewDTO> selectReviewList(ReviewDTO params);

	public int selectReviewTotalCount(ReviewDTO params);

}
