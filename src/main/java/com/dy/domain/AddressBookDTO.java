package com.dy.domain;

import com.dy.common.Const.YesNo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressBookDTO extends CommonDTO {

	public AddressBookDTO(String username, String name, String postcode, String address, String detailedAddress) {
		this.username = username;
		this.name = name;
		this.postcode = postcode;
		this.address = address;
		this.detailedAddress = detailedAddress;
	}

	/** 번호 (PK) */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 배송지 이름 */
	private String name;

	/** 우편 번호 */
	private String postcode;

	/** 주소 */
	private String address;

	/** 상세 주소 */
	private String detailedAddress;

	/** 기본 배송지 여부 */
	private YesNo defaultYn;

}
