package com.dy.domain;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.dy.common.Const.PayMethod;
import com.dy.common.Const.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseDTO extends CommonDTO {

	/** 구매 번호 (PK) */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 상품 코드 (FK) */
	private String code;

	/** 결제 금액 */
	private int amount;

	/** 수령인 */
	@Pattern(message = "수령인을 올바른 형식으로 입력해 주세요.", regexp = "^[가-힣]{2,4}$")
	private String recipient;

	/** 수령인 연락처 */
	@Pattern(message = "연락처를 올바른 형식으로 입력해 주세요.", regexp = "(^02.{0}|^01.{1}|[0-9]{3})([0-9]+)([0-9]{4})")
	private String phone;

	/** 우편 번호 */
	@NotBlank(message = "우편 번호를 입려해 주세요.")
	private String postcode;

	/** 주소 */
	@NotBlank(message = "주소를 입력해 주세요.")
	private String address;

	/** 상세 주소 */
	@NotBlank(message = "상세 주소를 입력해 주세요.")
	private String detailAddress;

	/** 결제 방법 */
	private PayMethod payMethod;

	/** 요청 메시지 */
	private String requestMessage;

	/** 상태 (Y : 정상 | N : 중지 | R : 환불)  */
	private Status status;

	/** 상품 리스트 */
	private List<GoodsDTO> goodsList;

	/** 상품 정보 */
	private GoodsDTO goods;

	/** 배송지 정보 */
	private AddressBookDTO addressBook;

}
