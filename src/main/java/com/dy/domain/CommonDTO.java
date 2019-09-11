package com.dy.domain;

import java.time.LocalDateTime;

import com.dy.common.Const.YesNo;
import com.dy.common.paging.Criteria;
import com.dy.common.paging.PaginationInfo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonDTO extends Criteria {

	/** 삭제 여부 */
	private YesNo deleteYn;

	/** 등록한 사용자 */
	private String insertUser;

	/** 등록일 */
	private LocalDateTime insertTime;

	/** 수정한 사용자 */
	private String updateUser;

	/** 수정일 */
	private LocalDateTime updateTime;

	/** 페이징 정보 */
	private PaginationInfo paginationInfo;

}
