package com.dy.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorityDTO extends CommonDTO {

	public AuthorityDTO(String username, String name) {
		this.username = username;
		this.name = name;
	}

	/** 번호 (PK) */
	private Integer idx;

	/** 이메일 (FK) */
	private String username;

	/** 이름 */
	private String name;

}
