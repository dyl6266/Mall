package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.domain.UserDTO;
import com.dy.service.UserService;
import com.dy.util.UiUtils;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/admin")
public class AdminUserController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/users")
	public String openAdminUserList(Model model) {

		List<UserDTO> users = userService.getUserList();
		model.addAttribute("users", users);

		return "admin/user/list";
	}

	@PatchMapping(value = "/users/{idx}")
	@ResponseBody
	public JsonObject changeAccountStatus(@PathVariable("idx") Integer idx,
			@RequestParam("accountNonLocked") boolean accountNonLocked, @RequestParam("enabled") boolean enabled) {

		JsonObject jsonObj = new JsonObject();
		System.out.println(accountNonLocked + " / " + enabled);

		return jsonObj;
	}

}
