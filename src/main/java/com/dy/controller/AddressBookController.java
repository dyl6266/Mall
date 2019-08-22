package com.dy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dy.service.AddressBookService;
import com.dy.service.UserService;
import com.google.gson.JsonObject;

@RestController
public class AddressBookController {

	@Autowired
	private AddressBookService addressBookService;

	@Autowired
	private UserService userService;

	@DeleteMapping(value = "/address-book/{idx}")
	@ResponseBody
	public JsonObject deleteAddressFromAddressBook(@PathVariable("idx") Integer idx) {

		JsonObject jsonObj = new JsonObject();

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			jsonObj.addProperty("message", "올바르지 않은 접근입니다.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				boolean isDeleted = addressBookService.deleteAddress(idx, username);
				if (isDeleted == false) {
					jsonObj.addProperty("message", "배송지 삭제에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isDeleted);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "DB 처리 중에 오류가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 오류가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}
	// end of function

}
