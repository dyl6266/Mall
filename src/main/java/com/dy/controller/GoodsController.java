package com.dy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dy.common.Const.Method;
import com.dy.domain.AddressBookDTO;
import com.dy.domain.GoodsDTO;
import com.dy.domain.PurchaseDTO;
import com.dy.domain.UserDTO;
import com.dy.service.AddressBookService;
import com.dy.service.GoodsService;
import com.dy.service.PurchaseService;
import com.dy.service.UserService;
import com.dy.util.MediaUtils;
import com.dy.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Controller
public class GoodsController extends UiUtils {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private UserService userService;

	@Autowired
	private AddressBookService addressbookService;

	@Autowired
	private PurchaseService purchaseService;

	@GetMapping(value = "/goods/register")
	public String openGoodsRegister(Model model) {

		model.addAttribute("goods", new GoodsDTO());
		return "goods/register";
	}

	@GetMapping("/goods/list")
	public String openGoodsList(@ModelAttribute("params") GoodsDTO params,
			@RequestParam(value = "isAjax", required = false) boolean isAjax, Model model) {

		List<GoodsDTO> goodsList = goodsService.getGoodsList(params);
		model.addAttribute("goodsList", goodsList);

		String page = isAjax == false ? "goods/list" : "goods/ajax/list";
		return page;
	}

	@GetMapping("/goods/details")
	public String openGoodsDetails(@RequestParam(value = "code", required = false) String code, Model model) {

		if (StringUtils.isEmpty(code)) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", "/goods/list", Method.GET, null, model);
		}

		Map<String, Object> map = goodsService.getGoodsDetailsWithImages(code);
		for (String key : map.keySet()) {
			model.addAttribute(key, map.get(key));
		}

		if (map.get("goods") != null) {
			GoodsDTO goods = (GoodsDTO) map.get("goods");
			String optionsStr = goods.getStock().getOptions();

			JsonObject options = new Gson().fromJson(optionsStr, JsonObject.class);
			model.addAttribute("options", options);
		}

		return "goods/details";
	}

	@GetMapping(value = "/goods/images/{time}/{filename}")
	@ResponseBody
	public ResponseEntity<byte[]> printGoodsImages(@PathVariable("time") String time,
			@PathVariable("filename") String filename) {

		ResponseEntity<byte[]> entity = null;

		String ext = FilenameUtils.getExtension(filename);
		MediaType mediaType = MediaUtils.getMediaType(ext);

		if (ObjectUtils.isEmpty(mediaType)) {
			return null;
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(mediaType);

		InputStream input = null;
		try {
			input = new FileInputStream(File.separator + "upload" + File.separator + time + File.separator + filename);
			byte[] fileBytes = IOUtils.toByteArray(input);

			entity = new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
			input.close();

		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@GetMapping(value = "/goods/checkout")
	public String openCheckout(@RequestParam(value = "code", required = false) String code,
			@RequestParam(value = "size", required = false) Integer size,
			@RequestParam(value = "quantity", required = false) Integer quantity, Model model) {

		if (StringUtils.isEmpty(code)) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", request.getContextPath() + "/goods/list", Method.GET, null,
					model);

		} else if (StringUtils.isEmpty(size) || StringUtils.isEmpty(quantity)) {
			return showMessageWithRedirect("사이즈 또는 수량을 확인해 주세요.", request.getHeader("referer"), Method.GET, null,
					model);
		}

		try {
			Map<String, Object> map = goodsService.getGoodsDetailsWithImages(code);
			for (String key : map.keySet()) {
				model.addAttribute(key, map.get(key));
			}

			if (map.get("goods") != null) {
				GoodsDTO goods = (GoodsDTO) map.get("goods");

				/* 상품의 사이즈와 수량을 key, value 형태로 가지는 Json 문자열 */
				String optionsStr = goods.getStock().getOptions();
				/* Json 문자열을 JsonObject로 변환 */
				JsonObject options = new Gson().fromJson(optionsStr, JsonObject.class);
				/* DB에 저장된 실제 수량 */
				int actualQuantity = Integer.parseInt(String.valueOf(options.get(String.valueOf(size))));

				if (quantity > actualQuantity) {
					return showMessageWithRedirect("선택된 수량이 재고 수량보다 많습니다.", request.getHeader("referer"), Method.GET, null, model);
				}

				/* 해당 사이즈의 수량을 업데이트한 JSON 프로퍼티 추가 (결제 로직에서 사용하기 위해 뷰로 전달) */
				options.addProperty(String.valueOf(size), (actualQuantity - quantity));
				model.addAttribute("options", new Gson().toJson(options));

				model.addAttribute("size", size);
				model.addAttribute("quantity", quantity);
			}

			/* 로그인 회원 정보 */
			String username = userService.getAuthentication().getName();
			UserDTO user = (UserDTO) userService.loadUserByUsername(username);
			model.addAttribute("user", user);

			/* th:object로 사용할 비어있는 인스턴스 */
			model.addAttribute("purchase", new PurchaseDTO());

		} catch (NullPointerException e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", request.getHeader("referer"), Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", request.getHeader("referer"), Method.GET, null, model);
		}

		return "goods/checkout";
	}

	/**
	 * 구매자 배송지 리스트 HTML (Ajax Success Response)
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/goods/checkout/address-popup")
	public String returAddressListOfBuyerHTML(Model model) {

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", "/login", Method.GET, null, model);
		}

		List<AddressBookDTO> addressBook = addressbookService.getAddressBook(username);
		model.addAttribute("addressBook", addressBook);

		return "goods/address-popup";
	}

	@PostMapping(value = "/goods/purchase")
	@ResponseBody
	public JsonObject insertPurchase(@RequestBody @Validated PurchaseDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);

		} else {
			try {
				String username = userService.getAuthentication().getName();
				params.setUsername(username);
				params.getAddressBook().setUsername(username);

				boolean isPurchased = purchaseService.purchaseGoods(params);
				if (isPurchased == false) {
					jsonObj.addProperty("message", "결제에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isPurchased);

			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "DB 처리 중에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후에 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}

		return jsonObj;
	}

}
