package com.dy.domain;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

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

	/** 사이즈 */
	@NotBlank(message = "사이즈를 선택해 주세요.")
	private String size;

	/** 수량 */
	@Range(min = 1, message = "수량은 최소 1개까지 선택할 수 있습니다.")
	private int quantity;

	/** 상품 정보 */
	private GoodsDTO goods;

}
