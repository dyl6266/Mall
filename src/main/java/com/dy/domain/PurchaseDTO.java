package com.dy.domain;

import com.dy.common.Const.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDTO extends CommonDTO {

	/** 구매 번호 (PK) */
	private Integer idx;

	/** 상품 코드 (FK) */
	private String code;

	/** 금액 */
	private int amount;

	/** 상태 (Y : 정상 | N : 중지 | R : 환불)  */
	private Status status;

}
