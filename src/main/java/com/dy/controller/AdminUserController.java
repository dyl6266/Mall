package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dy.domain.UserDTO;
import com.dy.service.UserService;
import com.dy.util.UiUtils;

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

}
