package com.dy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.common.paging.PaginationInfo;
import com.dy.domain.ReviewDTO;
import com.dy.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
	private ReviewMapper reviewMapper;

	@Override
	public boolean registerReview(ReviewDTO params) {

		int queryResult;

		if (params.getIdx() == null) {
			queryResult = reviewMapper.insertReview(params);
		} else {
			queryResult = reviewMapper.updateReview(params);
		}

		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean deleteReview(String username, Integer idx) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("username", username);
		params.put("idx", idx);

		int queryResult = reviewMapper.deleteReview(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<ReviewDTO> getReviewList(ReviewDTO params) {

		List<ReviewDTO> reviewList = new ArrayList<>();

		/* 전체 리뷰 수 */
		int totalCount = reviewMapper.selectReviewTotalCount(params.getCode());
		if (totalCount > 0) {
			/* 페이징 계산에 필요한 전체 리뷰 수 저장 */
			params.setTotalRecordCount(totalCount);

			/* 페이징 정보 저장 */
			params.setRecordCountPerPage(10);
			params.setPageSize(5);

			PaginationInfo paginationInfo = new PaginationInfo(params);
			params.setPaginationInfo(paginationInfo);

			/* 리뷰 리스트 */
			reviewList = reviewMapper.selectReviewList(params);
		}

		return reviewList;
	}

	@Override
	public int getReviewAverage(String code) {

		return reviewMapper.selectReviewAverage(code);
	}

}
