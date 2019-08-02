package com.dy.domain;

import javax.validation.constraints.NotBlank;

import com.dy.common.DTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class StockDTO extends DTO {

	/** 상품(재고) 번호 (PK) */
	private Integer idx;

	/** 상품 코드 */
	private String code;

	/** 상품 옵션 (색상, 사이즈, 수량 정보를 담는 JSON) */
	@NotBlank(message = "상품의 옵션(색상, 사이즈, 수량)을 입력해 주세요.")
	private String options;

}
