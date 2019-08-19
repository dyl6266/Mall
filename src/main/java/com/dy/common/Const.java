package com.dy.common;

public class Const {

	/**
	 * 상품 & 결제 상태
	 */
	public enum Status {
		  Y("정상", 1)
		, N("중지", 0)
		, D("할인", null)
		, S("품절", null)
		, R("환불", null);

		private String strValue;
		private Integer intValue;

		private Status(String strValue, Integer intValue) {
			this.strValue = strValue;
			this.intValue = intValue;
		}

		public String getStrValue() {
			return strValue;
		}

		public Integer getIntValue() {
			return intValue;
		}
	}

	/**
	 * 결제 방법
	 */
	public enum PayMethod {
		  CARD("신용카드")
		, TRANSFER("계좌이체");

		private String payMethod;

		private PayMethod(String payMethod) {
			this.payMethod = payMethod;
		}

		public String getPayMethod() {
			return payMethod;
		}
	}

	/**
	 * 사용자 권한
	 */
	public enum Authority {
		  MEMBER("회원")
		, MANAGER("매니저")
		, ADMIN("관리자");

		private String authority;

		private Authority(String authority) {
			this.authority = authority;
		}

		public String getAuthority() {
			return authority;
		}
	}

	public enum YesNo {
		Y, N
	}

	/**
	 * 테이블명
	 */
	public enum TableName {
		  USER("회원")
		, AUTHORITY("권한")
		, GOODS("상품")
		, STOCK("재고")
		, CART("장바구니")
		, REVIEW("리뷰")
		, PURCHASE("구매")
		, ATTACH("첨부 파일");

		private String tableName;

		private TableName(String tableName) {
			this.tableName = tableName;
		}

		public String getTableName() {
			return tableName;
		}
	}

	/**
	 * 이메일 전송 타입
	 */
	public enum MailType {
		TEXT, HTML, FILE
	}

	/**
	 * HTTP 요청 메소드
	 */
	public enum Method {
		GET, POST, PUT, DELETE, PATCH
	}
}
