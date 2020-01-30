package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dy.common.Const.SearchOrder;
import com.dy.common.Const.Status;
import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;

@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GoodsService goodsService;

	@GetMapping(value = "/index")
	public String openIndexPage(Model model) {

		/* 출력할 상품 수 */
		GoodsDTO params = new GoodsDTO();
		params.setRecordCountPerPage(4);

		/* 신상품 */
		List<GoodsDTO> newGoods = goodsService.getGoodsList(params);
		model.addAttribute("newGoods", newGoods);

		/* 베스트 셀러 */
		params.setSearchOrder(SearchOrder.BEST);
		List<GoodsDTO> bestGoods = goodsService.getGoodsList(params);
		model.addAttribute("bestGoods", bestGoods);

		/* 할인 상품 */
		params.setSearchOrder(null);
		params.setStatus(Status.D);
		List<GoodsDTO> saleGoods = goodsService.getGoodsList(params);
		model.addAttribute("saleGoods", saleGoods);

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
	public String openLoginPage(Model model) {

		/* 이전 페이지 URI */
		String referer = request.getHeader("referer");
		if (StringUtils.isEmpty(referer) == false) {
			request.getSession().setAttribute("referer", referer);
		}

		return "login";
	}

}
