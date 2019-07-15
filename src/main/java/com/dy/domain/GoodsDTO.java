package com.dy.domain;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.dy.common.Const.Status;
import com.dy.common.Const.YesNo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoodsDTO {

	/** 상품 번호 (PK) */
	private Integer idx;

	/** 상품 코드 */
	@NotBlank(message = "상품 코드를 입력해 주세요.")
	private String code;

	/** 상품명 */
	@NotBlank(message = "상품명을 입력해 주세요.")
	private String name;

	/** 상품 정보 */
	@NotBlank(message = "상품 정보를 입력해 주세요.")
	private String info;

	/** 상품 가격 */
	@Min(value = 1000, message = "상품 가격은 최소 1,000원 이상이어야 합니다.")
	private int price;

	/** 상품 수량 */
	@Min(value = 0, message = "상품 수량은 최소 0 이상이어야 합니다.")
	private int quantity;

	/** 상품 상태 */
	private Status status;

	/** 상품 삭제 여부 */
	private YesNo deleteYn;

	/** 상품 등록일 */
	private Date insertTime;

	/** 상품 수정일 */
	private Date updateTime;

}
