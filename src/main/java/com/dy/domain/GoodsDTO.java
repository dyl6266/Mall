package com.dy.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.dy.common.Const.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDTO extends CommonDTO {

	/** 상품 번호 (PK) */
	private Integer idx;

	/** 상품 코드 */
	private String code;

	/** 상품명 */
	@NotBlank(message = "상품명을 입력해 주세요.")
	private String name;

	/** 상품 설명 */
	@NotBlank(message = "상품 설명을 입력해 주세요.")
	private String description;

	/** 상품 가격 */
	@Min(value = 1000, message = "상품 가격은 최소 1,000원 이상이어야 합니다.")
	private int price;

	/** 상태 (Y : 정상 | N : 중지 | D : 할인 | S : 품절) */
	private Status status;

	/** 재고 정보 */
	@Valid
	private StockDTO stock;

	/** 이미지 리스트 */
	private List<AttachDTO> attachList;

}
