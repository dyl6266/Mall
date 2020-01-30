package com.dy.domain;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

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

	/** 할인율 */
	@Min(value = 0, message = "할인율은 0보다 작을 수 없습니다.")
	@Max(value = 99, message = "할인율은 99보다 클 수 없습니다.")
	private float rate;

	/** 상태 (Y : 정상 | N : 중지 | D : 할인 | S : 품절) */
	private Status status;

	/** 재고 정보 */
	@Valid
	private StockDTO stock;
	
	/*
	 * TODO => 컨트롤러가 아닌 DTO에서 멤버로 가지고 있어도 파라미터로 받을 수 있음
	 */
	private MultipartFile[] files;

	/** 이미지 리스트 */
	private List<AttachDTO> attachList;

}
