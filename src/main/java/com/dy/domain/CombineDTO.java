package com.dy.domain;

import javax.validation.Valid;

import lombok.Getter;
import lombok.Setter;

/** 상품과 관련된 클래스들을 인스턴스(멤버)로 가지는 클래스 */
@Getter
@Setter
public class CombineDTO {

	@Valid
	private GoodsDTO goods;

	@Valid
	private StockDTO stock;

}
