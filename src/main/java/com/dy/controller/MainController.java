package com.dy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dy.common.Const.Status;
import com.dy.common.paging.Criteria;
import com.dy.domain.GoodsDTO;
import com.dy.domain.PurchaseDTO;
import com.dy.service.GoodsService;
import com.dy.service.PurchaseService;

@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private PurchaseService purchaseService;

	@GetMapping(value = "/index")
	public String openIndexPage(Model model) {

		/* 가장 많이 팔린 상품 (베스트 셀러) */
//		List<PurchaseDTO> bestSeller = goodsService.getGoodsList(new GoodsDTO());

		/* 출력할 상품 수 */
		GoodsDTO params = new GoodsDTO();
		params.setRecordCountPerPage(4);

		/* 신상품 */
		List<GoodsDTO> newGoods = goodsService.getGoodsList(params);
		model.addAttribute("newGoods", newGoods);

		/* 할인 상품 */
		params.setStatus(Status.D);
		List<GoodsDTO> discountGoods = goodsService.getGoodsList(params);
		model.addAttribute("discountGoods", discountGoods);

		return "index";
	}

	@GetMapping(value = "/upload")
	public String openUpload() {
		return "upload";
	}

	/**
	 * TODO => 로그인 페이지를 GetMapping으로 처리하면 LoginFailureHandler에서 forward 처리에 문제가 생김
	 * 
	 * @return 페이지
	 */
	@RequestMapping(value = "/login")
	public String openLoginPage() {

		return "login";
	}

}
