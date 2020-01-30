package com.dy.service;

import java.util.List;

import com.dy.domain.ReviewDTO;

public interface ReviewService {

	public boolean registerReview(ReviewDTO params);

	public boolean deleteReview(String username, Integer idx);

	public List<ReviewDTO> getReviewList(ReviewDTO params);

	public int getReviewAverage(String code);

}
