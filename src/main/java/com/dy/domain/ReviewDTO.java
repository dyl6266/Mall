package com.dy.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO extends CommonDTO {

	/** 댓글 번호 (PK) */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 상품 코드 (FK) */
	private String code;

	/** 별명 */
	private String nickname;

	/** 내용 */
	private String content;

	/** 평점 */
	private int grade;

}
