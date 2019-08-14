package com.dy.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", request.getContextPath() + "/login", Method.GET, null, model);
		}

		UserDTO user = (UserDTO) userService.loadUserByUsername(username);
		model.addAttribute("user", user);

		return "user/mypage";
	}

	@GetMapping(value = "/user/cart")
	public String openCartPage(Model model) {

		String username = userService.getAuthentication().getName();
		if ("anonymousUser".equals(username)) {
			return showMessageWithRedirect("로그인이 필요한 서비스입니다.", request.getContextPath() + "/login", Method.GET, null, model);
		}

		List<CartDTO> goodsList = cartService.getListOfGoodsInCart(username);
		model.addAttribute("goodsList", goodsList);

		return "user/cart";
	}

}
