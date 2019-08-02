package com.dy.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dy.mapper.AttachMapper;
import com.dy.util.AttachFileUtils;
import com.google.gson.JsonObject;

@RestController
public class AttachController {

	@Autowired
	private AttachMapper attachMapper;

	@PostMapping(value = "/attach", produces = "text/plain;charset=UTF-8")
	public JsonObject insertAttach(List<MultipartFile> files,
			@RequestParam(value = "code", required = false) String code) {

		JsonObject jsonObj = new JsonObject();

		if (files.isEmpty() || StringUtils.isEmpty(code)) {
			jsonObj.addProperty("message", "잘못된 요청입니다. 다시 시도해 주세요.");
			jsonObj.addProperty("result", false);
		} else {

			for (MultipartFile file : files) {
				try {
					AttachFileUtils.uploadImageFile(file.getOriginalFilename(), file.getBytes());

				} catch (IOException e) {
					e.printStackTrace();

				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println("original filename : " + file.getOriginalFilename());
				System.out.println("size : " + file.getSize());
				System.out.println("content type : " + file.getContentType());
				System.out.println("name : " + file.getName());
			}
		}

		return null;
	}

}
