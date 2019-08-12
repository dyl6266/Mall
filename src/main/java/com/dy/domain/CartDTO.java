package com.dy.domain;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends CommonDTO {

	/** 번호 (PK) */
	private Integer idx;

	/** 이메일 (FK) */
	private String username;

	/** 상품 코드 (FK) */
	@NotBlank(message = "상품 코드를 입력해 주세요.")
	private String code;

}
