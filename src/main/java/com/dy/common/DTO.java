package com.dy.common;

import java.util.Date;

import com.dy.common.Const.YesNo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTO {

	/** 삭제 여부 */
	private YesNo deleteYn;

	/** 등록일 */
	private Date insertTime;

	/** 수정일 */
	private Date updateTime;

}
