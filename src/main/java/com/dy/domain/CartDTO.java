package com.dy.domain;

import javax.validation.constraints.NotBlank;

import com.dy.common.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends DTO {

	/** 상품(카트) 번호 (PK) */
	private Integer idx;

	/** 회원 아이디 (FK) */
	private String email;

	/** 상품 코드 (FK) */
	@NotBlank(message = "상품 코드를 입력해 주세요.")
	private String code;

}
