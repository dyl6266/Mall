package com.dy.common.paging;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo {

	/**
	 * 유효하지 않은 값이 들어온 경우, 기본 값 설정
	 * 
	 * @param criteria
	 */
	public PaginationInfo(Criteria criteria) {
		if (criteria.getCurrentPageNo() < 1) {
			criteria.setCurrentPageNo(1);
		}
		if (criteria.getRecordCountPerPage() < 1 || criteria.getRecordCountPerPage() > 50) {
			criteria.setRecordCountPerPage(8);
		}
		if (criteria.getPageSize() < 5 || criteria.getPageSize() > 20) {
			criteria.setPageSize(10);
		}
		this.criteria = criteria;

		calculation();
	}

	/**
	 * 페이징과 관련된 파라미터들을 쿼리 스트링 형태로 반환
	 * 
	 * @param pageNo - 페이지 번호
	 * @return 쿼리 스트링 (Parameters)
	 */
	public String makeQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
		.queryParam("currentPageNo", pageNo)
		.queryParam("recordCountPerPage", criteria.getRecordCountPerPage())
		.queryParam("pageSize", criteria.getPageSize())
		.build()
		.encode();

		return uriComponents.toUriString();
	}

	/**
	 * 검색 + 페이징과 관련된 파라미터들을 쿼리 스트링 형태로 반환
	 * 
	 * @param pageNo - 페이지 번호
	 * @param isAjax - Ajax 요청 여부
	 * @return 쿼리 스트링 (Parameters)
	 */
	public String makeSearchQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
		.queryParam("currentPageNo", pageNo)
		.queryParam("recordCountPerPage", criteria.getRecordCountPerPage())
		.queryParam("pageSize", criteria.getPageSize())
		.queryParam("searchType", criteria.getSearchType())
		.queryParam("searchOrder", criteria.getSearchOrder())
		.queryParam("searchKeyword", criteria.getSearchKeyword())
		.build()
		.encode();

		return uriComponents.toUriString();
	}

	/** 페이징 계산에 필요한 파라미터들이 담긴 클래스 (transient 한정자 => 순환 참조 에러 처리 / Jackson은 JsonBackReference 애너테이션으로 처리 가능) */
	private transient Criteria criteria;

	/** 전체 페이지 개수 */
	private int totalPageCount;

	/** 페이지 리스트의 첫 페이지 번호 */
	private int firstPage;

	/** 페이지 리스트의 마지막 페이지 번호 */
	private int lastPage;

	/** SQL의 조건절에 사용되는 첫 RNUM */
	private int firstRecordIndex;

	/** SQL의 조건절에 사용되는 마지막 RNUM */
	private int lastRecordIndex;

	/** 이전 페이지 존재 여부 */
	private boolean previousPage;

	/** 다음 페이지 존재 여부 */
	private boolean nextPage;

	/**
	 * 페이징 정보를 계산한다.
	 */
	private void calculation() {

		/* 전체 페이지 개수 */
		totalPageCount = ((criteria.getTotalRecordCount() - 1) / criteria.getRecordCountPerPage()) + 1;

		/* 페이지 리스트의 첫 페이지 번호 */
		firstPage = ((criteria.getCurrentPageNo() - 1) / criteria.getPageSize()) * criteria.getPageSize() + 1;

		/* 페이지 리스트의 마지막 페이지 번호 */
		lastPage = firstPage + criteria.getPageSize() - 1;
		if (lastPage > totalPageCount) {
			lastPage = totalPageCount;
		}

		/* SQL의 조건절에 사용되는 첫 RNUM */
		firstRecordIndex = (criteria.getCurrentPageNo() - 1) * criteria.getRecordCountPerPage();

		/* SQL의 조건절에 사용되는 마지막 RNUM */
		lastRecordIndex = criteria.getCurrentPageNo() * criteria.getRecordCountPerPage();

		/* 이전 페이지 존재 여부 */
		previousPage = firstPage == 1 ? false : true;

		/* 다음 페이지 존재 여부 */
		nextPage = (lastPage * criteria.getRecordCountPerPage()) >= criteria.getTotalRecordCount() ? false : true;
	}

}
