package com.dy.domain;

import javax.validation.constraints.NotEmpty;

import com.dy.common.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO extends DTO {

	/** 카트(아이템) 번호 (PK) */
	private Integer idx;

	/*
	 * TODO
	 * 이메일은 어차피 UserDTO에 들어가야 하는 인스턴스인데, CartDTO에 따로 String 변수로 존재해야 하는건가..? 생각해보자
	 * 만약 그렇다면 아래의 GoodsDTO의 인스턴스는 어떻게 해야 하는가... ****************************
	 */
	/** 회원 아이디 (FK) */
	private String email;

	/** 상품 정보 */
	private GoodsDTO goods;

}
