package com.dy.common;

public class Const {

	public enum Status {
		Y("판매"), N("판매 중지"), D("할인"), S("품절");

		private String status;

		private Status(String status) {
			this.status = status;
		}

		public String getStatus() {
			return status;
		}
	}

	public enum YesNo {
		Y, N
	}

	public enum TableName {
		GOODS("상품");

		private String tableName;

		private TableName(String tableName) {
			this.tableName = tableName;
		}

		public String getTableName() {
			return tableName;
		}
	}

}
