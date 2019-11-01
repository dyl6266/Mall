package com.dy.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dy.service.PurchaseService;
import com.dy.service.UserService;

@Controller
public class PurchaseController {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private PurchaseService purchaseService;

	@Autowired
	private UserService userService;

	@GetMapping(value = "/purchase/list")
	public String openPurchaseGoodsList(Model model) {

		String username = userService.getAuthentication().getName();

		List<List<Map<String, Object>>> purchaseList = purchaseService.getPurchaseList(username);
		model.addAttribute("purchaseList", purchaseList);

		return "purchase/list";
	}

}
