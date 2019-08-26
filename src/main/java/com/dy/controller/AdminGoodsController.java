package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;
import com.dy.util.UiUtils;

@Controller
@RequestMapping(value = "/admin")
public class AdminGoodsController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GoodsService goodsService;

	@GetMapping(value = "/goods")
	public String openAdminGoodsList(Model model) {

		List<GoodsDTO> goods = goodsService.getGoodsList();
		model.addAttribute("goods", goods);

		return "admin/goods/list";
	}

}
