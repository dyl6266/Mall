package com.dy.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import com.dy.common.Const.Method;
import com.dy.domain.CartDTO;
import com.dy.domain.UserDTO;
import com.dy.service.CartService;
import com.dy.service.UserService;
import com.dy.util.UiUtils;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Controller
public class UserController extends UiUtils {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private UserService userService;

	@Autowired
	private CartService cartService;

	@GetMapping(value = "/access-denied")
	public String openAccessDenied(Model model) {

		String previousPage = request.getHeader("referer");
		return showMessageWithRedirect("접근이 허용되지 않은 계정입니다.", previousPage, Method.GET, null, model);
	}

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
					jsonObj.addProperty("message", "회원 가입에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				} else {
					jsonObj.addProperty("message", "회원이 되신 것을 진심으로 환영합니다!");
				}
				jsonObj.addProperty("result", isInserted);

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

	// TODO => 패스워드 변경 처리하기 / mypage.html과 같이
	@PatchMapping(value = "/users/{username}")
	@ResponseBody
	public JsonObject updateUser(@PathVariable("username") String username, @RequestBody @Valid UserDTO params,
			BindingResult bindingResult) {

		JsonObject jsonObj = new JsonObject();

		if (bindingResult.hasErrors()) {
			FieldError fieldError = bindingResult.getFieldError();
			jsonObj.addProperty("message", fieldError.getDefaultMessage());
			jsonObj.addProperty("result", false);

		} else {
			try {
				boolean isUpdated = userService.registerUser(params);
				if (isUpdated == false) {
					jsonObj.addProperty("message", "정보 수정에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				} else {
					jsonObj.addProperty("message", "정보 수정이 완료되었습니다.");
				}
				jsonObj.addProperty("result", isUpdated);

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

	@GetMapping(value = "/user/find-{type}")
	public String openFindAccountInfo(@PathVariable(value = "type", required = false) String type, Model model) {

		String previousUri = request.getHeader("referer");
		if (StringUtils.isEmpty(type) || "username".equals(type) == false && "password".equals(type) == false) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", previousUri, Method.GET, null, model);
		}

		return "user/find/" + type;
	}

	@PostMapping(value = "/user/find-result")
	public String openFindResult(HttpServletRequest request, UserDTO params,
			@RequestParam(value = "type", required = false) String type, Model model) {

		String previousUri = request.getHeader("referer");

		if (StringUtils.isEmpty(type) || "username".equals(type) == false && "password".equals(type) == false) {
			return showMessageWithRedirect("올바르지 않은 접근입니다.", previousUri, Method.GET, null, model);
		}

		UserDTO user = userService.findUserAccountInfo(params);
		if (user == null) {
			return showMessageWithRedirect("존재하지 않는 회원이거나, 시스템에 문제가 발생하였습니다.", previousUri, Method.GET, null, model);
		}

		model.addAttribute("type", type);
		model.addAttribute("user", user);

		return "user/find/result";
	}

	@GetMapping(value = "/user/mypage")
	public String openMypage(Model model) {

		String username = userService.getAuthentication().getName();
		if (userService.isAnonymousUser(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", "/login", Method.GET, null, model);
		}

		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		model.addAttribute("user", user);

		return "user/mypage";
	}

	/**
	 * 회원 비밀번호 변경 페이지
	 * 
	 * @param model
	 * @return 페이지
	 */
	@GetMapping(value = "/user/change-password")
	public String openChangePassword(Model model) {

		String username = userService.getAuthentication().getName();
		if (userService.isAnonymousUser(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", "/login", Method.GET, null, model);
		}

		model.addAttribute("username", username);

		return "user/change-password";
	}

	/**
	 * 회원 비밀번호 변경
	 * 
	 * @param password - 기존 비밀번호
	 * @param newPassword - 신규 비밀번호
	 * @return message, result
	 */
	@PatchMapping(value = "/users/account/{username}")
	@ResponseBody
	public JsonObject updateUserPassword(@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "newPassword", required = false) String newPassword) {

		JsonObject jsonObj = new JsonObject();

		/* 로그인한 회원의 아이디 */
		String username = userService.getAuthentication().getName();
		/* 로그인한 회원의 기존 비밀번호 확인 */
		boolean isMatch = userService.checkPasswordMatches(username, password);

		/* 비밀번호 정규식 (영문, 숫자, 특수문자 조합) */
		String regexp = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[~`!@#$%\\^&*()-]).{8,20}$";
		/* 신규 비밀번호 유효성 검사 */
		boolean isValid = Pattern.matches(regexp, newPassword);

		if (isMatch == false) {
			jsonObj.addProperty("message", "현재 비밀번호가 일치하지 않습니다. 다시 입력해 주세요.");
			jsonObj.addProperty("result", false);

		} else if (isValid == false) {
			jsonObj.addProperty("message", "새로운 비밀번호를 올바른 형식으로 입력해 주세요.");
			jsonObj.addProperty("result", false);

		} else {
			try {
				boolean isUpdated = userService.changeUserPassword(username, newPassword);
				if (isUpdated == false) {
					jsonObj.addProperty("message", "비밀번호 변경에 실패하였습니다. 새로고침 후에 다시 시도해 주세요.");
				}
				jsonObj.addProperty("result", isUpdated);

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

	// TODO => user/cart ? 아니면 CartController를 따로 만들어서 처리할지 생각해보기
	@GetMapping(value = "/cart")
	public String openCart(Model model) {

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", "/login", Method.GET, null, model);
		}

		/* 전체 상품 목록 */
		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(username);
		if (CollectionUtils.isEmpty(goodsList)) {
			return showMessageWithRedirect("장바구니가 비어있습니다.", request.getContextPath() + "/goods/list", Method.GET, null,
					model);
		}
		/* 전체 상품 금액 */
		int totalAmount = cartService.getTotalAmount(username);

		model.addAttribute("goodsList", goodsList);
		model.addAttribute("totalAmount", totalAmount);

		return "user/cart";
	}

}
