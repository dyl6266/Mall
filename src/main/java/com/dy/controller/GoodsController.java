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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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

//	@PatchMapping(value = "/goods/{code}")
//	@ResponseBody
//	public JsonObject updateGoods(@PathVariable("code") String code, @Validated @RequestBody GoodsDTO params,
//			BindingResult bindingResult) {
//
//		JsonObject jsonObj = new JsonObject();
//
//		if (bindingResult.hasErrors()) {
//			FieldError fieldError = bindingResult.getFieldError();
//			jsonObj.addProperty("message", fieldError.getDefaultMessage());
//		} else {
//			try {
//				boolean result = goodsService.registerGoods(params);
//				if (result == false) {
//					jsonObj.addProperty("message", "상품 수정에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
//				} else {
//					jsonObj.addProperty("message", "상품 수정이 완료되었습니다.");
//				}
//				jsonObj.addProperty("result", result);
//
//			} catch (DataAccessException e) {
//				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
//				jsonObj.addProperty("result", false);
//
//			} catch (Exception e) {
//				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
//				jsonObj.addProperty("result", false);
//			}
//		}
//
//		return jsonObj;
//	}
	// end of method

	@GetMapping(value = "/goods/{code}")
	@ResponseBody
	public JsonObject getGoodsDetails(@PathVariable("code") String code) {

		JsonObject jsonObj = new JsonObject();

		GoodsDTO goods = goodsService.getGoodsDetails(code);
		if (goods == null) {
			jsonObj.addProperty("message", "존재하지 않는 상품입니다.");
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			/*
			 * 오브젝트를 파싱해서 JsonObject에 담기 위해서는 JsonElement를 사용 이러한 방식을 사용하지 않으면 웹에서 이상한 결과가
			 * 나오게 됨 CommentController의 getCommentList()도 참고할 것
			 */
			JsonElement jsonElem = gson.toJsonTree(goods);
			jsonObj.add("goods", jsonElem);
		}

		return jsonObj;
	}
	// end of method

	@DeleteMapping(value = "/goods/{codes}")
	@ResponseBody
	public JsonObject deleteGoods(@PathVariable("codes") List<String> codes) {

		JsonObject jsonObj = new JsonObject();
		if (CollectionUtils.isEmpty(codes)) {
			jsonObj.addProperty("message", "삭제할 상품을 선택해 주세요.");
		} else {
			try {
				boolean result = goodsService.deleteGoods(codes);
				if (result == false) {
					jsonObj.addProperty("message", "상품 삭제에 실패하였습니다. 새로고침 후 다시 시도해 주세요.");
					jsonObj.addProperty("result", false);
				}
			} catch (DataAccessException e) {
				jsonObj.addProperty("message", "데이터베이스에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);

			} catch (Exception e) {
				jsonObj.addProperty("message", "시스템에 문제가 발생하였습니다. 새로고침 후 다시 시도해 주세요.");
				jsonObj.addProperty("result", false);
			}
		}
		jsonObj.addProperty("result", true);

		return jsonObj;
	}
	// end of method

	@GetMapping(value = "/goods")
	@ResponseBody
	public JsonObject getGoodsList() {

		JsonObject jsonObj = new JsonObject();

		List<GoodsDTO> goodsList = goodsService.getGoodsList();
		if (CollectionUtils.isEmpty(goodsList)) {
			jsonObj.add("goodsList", null);
		} else {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonArray jsonArr = gson.toJsonTree(goodsList).getAsJsonArray();
			jsonObj.add("goodsList", jsonArr);
		}

		return jsonObj;
	}
	// end of method

	@GetMapping(value = "/goods/register")
	public String openGoodsRegister(Model model) {

		model.addAttribute("goods", new GoodsDTO());
		return "goods/register";
	}

	@GetMapping("/goods/list")
	public String openGoodsList(Model model) {

		List<GoodsDTO> goodsList = goodsService.getGoodsList();
		model.addAttribute("goodsList", goodsList);
		return "goods/list";
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
	public String openCheckout(@RequestParam(value = "code", required = false) String code, Model model) {

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", "/login", Method.GET, null, model);

		} else if (StringUtils.isEmpty(code)) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", request.getContextPath() + "/goods/list", Method.GET, null,
					model);
		}

		/* 로그인 회원 정보 */
		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		model.addAttribute("user", user);

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

	@PostMapping(value = "/goods/purchase")
	public JsonObject insertPurchase(@RequestBody @Validated PurchaseDTO params, BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			jsonObj.addProperty("message", "로그인이 필요한 서비스입니다.");
			jsonObj.addProperty("result", false);

		} else if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);

		} else {
			// TODO => 배송지 목록에 추가하기 / 새로운 기본 배송지로 설정하기 여기서 처리하기(?)
			try {
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
