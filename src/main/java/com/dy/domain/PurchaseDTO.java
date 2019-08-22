package com.dy.domain;

import java.util.List;

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
	private String recipient;

	/** 수령인 연락처 */
	private String phone;

	/** 우편 번호 */
	private String postcode;

	/** 주소 */
	private String address;

	/** 상세 주소 */
	private String detailedAddress;

	/** 요청 메시지 */
	private String requestMessage;

	/** 결제 방법 */
	private PayMethod payMethod;

	/** 상태 (Y : 정상 | N : 중지 | R : 환불)  */
	private Status status;

	/** 사용자 정보 */
	private UserDTO user;

	/** 상품 리스트 */
	private List<GoodsDTO> goodsList;

}
