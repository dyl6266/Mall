package com.dy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.dy.util.FileUtils;
import com.dy.util.MediaUtils;

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
	public void testByUpload(MultipartFile file, Model model, MultipartRequest multipartRequest) throws IOException {

		if (file.isEmpty() == false) {
			System.out.println("original filename : " + file.getOriginalFilename());
			System.out.println("size : " + file.getSize());
			System.out.println("content type : " + file.getContentType());
			System.out.println("resource : " + file.getResource());
			System.out.println("name : " + file.getName());
			System.out.println("inputStream : " + file.getInputStream());

			try {
				boolean isUploaded = FileUtils.uploadImageFile(file.getOriginalFilename(), file.getBytes());
				if (isUploaded == false) {
					System.out.println("오이오이 실패라구웃");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@GetMapping(value = "/print")
	@ResponseBody
	public ResponseEntity<byte[]> testByImagePrint(@RequestParam(value = "filename", required = false) String filename,
			@RequestParam(value = "uploadPath", required = false) String uploadPath) {

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
			input = new FileInputStream(uploadPath + filename);
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

}
