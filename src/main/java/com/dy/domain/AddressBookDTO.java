package com.dy.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressBookDTO extends CommonDTO {

	/** 번호 (PK) */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 배송지 이름 */
	private String name;

	/** 주소 */
	private String address;

}
