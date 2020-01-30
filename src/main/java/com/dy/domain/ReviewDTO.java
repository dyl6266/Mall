package com.dy.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO extends CommonDTO {

	public ReviewDTO() {
	}

	public ReviewDTO(String code) {
		this.code = code;
	}

	/** 댓글 번호 (PK) */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 상품 코드 (FK) */
	@NotBlank(message = "상품 코드를 입력해 주세요.")
	private String code;

	/** 별명 */
	@NotBlank(message = "닉네임을 입력해 주세요.")
	private String nickname;

	/** 내용 */
	@NotBlank(message = "내용을 입력해 주세요.")
	private String content;

	/** 평점 */
	@Min(value = 1, message = "평점은 1보다 작을 수 없습니다.")
	@Max(value = 5, message = "평점은 5보다 클 수 없습니다.")
	private int grade;

}
