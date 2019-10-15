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
import org.springframework.util.CollectionUtils;
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
import com.dy.domain.CartDTO;
import com.dy.domain.GoodsDTO;
import com.dy.domain.PurchaseDTO;
import com.dy.service.AddressBookService;
import com.dy.service.CartService;
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

	@Autowired
	private CartService cartService;

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
	public String openCheckout(@RequestParam(value = "codes", required = false) List<String> codes, Model model) {

		if (CollectionUtils.isEmpty(codes)) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", request.getHeader("referer"), Method.GET, null, model);
		}

		String username = userService.getAuthentication().getName();
		try {
			/* 각각 상품, 이미지 정보를 가지는 리스트 */
			List<Map<String, Object>> datas = goodsService.getListOfGoodsDetailsWithImages(codes);
			if (CollectionUtils.isEmpty(datas)) {
				return showMessageWithRedirect("올바르지 않은 접근입니다.", request.getHeader("referer"), Method.GET, null, model);
			}

			for (Map<String, Object> data : datas) {
				/* 상품 상세 정보 */
				GoodsDTO goods = (GoodsDTO) data.get("goods");
				/* 장바구니 상세 정보 */
				CartDTO cart = cartService.getGoodsDetailsInCart(username, goods.getCode());

				/* 상품의 사이즈와 수량을 key, value 형태로 가지는 Json 문자열 */
				String optionsStr = goods.getStock().getOptions();
				/* Json 문자열을 JsonObject로 변환 */
				JsonObject options = new Gson().fromJson(optionsStr, JsonObject.class);
				/* DB에 저장된 실제 수량 */
				int actualQuantity = Integer.parseInt(String.valueOf(options.get(String.valueOf(cart.getSize()))));

				if (cart.getQuantity() > actualQuantity) {
					return showMessageWithRedirect("선택된 수량이 재고보다 많습니다.", request.getHeader("referer"), Method.GET, null,
							model);
				}

				/* 해당 사이즈의 수량을 업데이트한 값으로 변경 (결제 로직에서 사용) */
				options.addProperty(String.valueOf(cart.getSize()), (actualQuantity - cart.getQuantity()));
				goods.getStock().setOptions(new Gson().toJson(options));

				data.put("goods", goods);
				data.put("cart", cart);
			}

			model.addAttribute("datas", datas);

		} catch (NullPointerException e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", request.getHeader("referer"), Method.GET, null, model);

		} catch (Exception e) {
			return showMessageWithRedirect("시스템에 문제가 발생하였습니다.", request.getHeader("referer"), Method.GET, null, model);
		}

		/* th:object로 사용할 비어있는 인스턴스 */
		model.addAttribute("purchase", new PurchaseDTO());

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

	/**
	 * 상품 구매 (다중 상품)
	 * @param params - PurchaseDTO
	 * @param bindingResult - 유효성 검사 에러 매핑 오브젝트
	 * @return JsonObject (message, result)
	 */
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
