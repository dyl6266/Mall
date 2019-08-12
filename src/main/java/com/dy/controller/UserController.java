package com.dy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.UserDTO;
import com.dy.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/user/join")
	public String openJoinPage(Model model) {

		model.addAttribute("user", new UserDTO(null, null, null, null));
		return "user/join";
	}

	@PostMapping(value = "/users")
	@ResponseBody
	public JsonObject insertUser(@RequestBody @Validated UserDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);
		} else {
			try {
				boolean isInserted = userService.registerUser(params);
				if (isInserted == false) {
					jsonObj.addProperty("message", "회원 가입에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
				} else {
					jsonObj.addProperty("message", "회원이 되신 것을 진심으로 환영합니다!");
				}
				jsonObj.addProperty("result", isInserted);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "DB 작업 중에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", "false");

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", "false");
			}
		}

		return jsonObj;
	}

	@GetMapping(value = "/users/{username}")
	@ResponseBody
	public JsonObject getUserDetails(@PathVariable("username") String username) {

		JsonObject jsonObj = new JsonObject();

		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		if (user != null) {
			JsonElement jsonElem = new Gson().toJsonTree(user);
			jsonObj.add("user", jsonElem);
		}

		return jsonObj;
	}

}
