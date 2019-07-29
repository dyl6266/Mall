package com.dy.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.dy.util.FileUtils;

@Controller
public class MainController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping(value = "/index")
	public String openIndexPage() {

		return "index";
	}

	@GetMapping(value = "/upload")
	public String openUpload() {
		return "upload";
	}

	@PostMapping(value = "/uploadTest")
	public void testByUpload(MultipartFile file, Model model, MultipartRequest multipartRequest) {

		if (file.isEmpty() == false) {
			System.out.println("original filename : " + file.getOriginalFilename());
			System.out.println("size : " + file.getSize());
			System.out.println("content type : " + file.getContentType());

			try {
				FileUtils.uploadFile(file.getOriginalFilename(), file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
