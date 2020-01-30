package com.dy.domain;

import com.dy.common.Const.Status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FavoriteDTO {

	/** PK */
	private Integer idx;

	/** 아이디 (FK) */
	private String username;

	/** 상품 코드 (FK) */
	private String code;

	/** 좋아요 상태 */
	private Status status;

}
