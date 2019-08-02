package com.dy.domain;

import com.dy.common.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachDTO extends DTO {

	/** 첨부 번호 (PK) */
	private Integer idx;

	/** 상품 코드 */
	private String code;

	/** 원본 파일명 */
	private String originalName;

	/** 저장 파일명 */
	private String storedName;

	/** 파일 크기 */
	private Long size;

}
