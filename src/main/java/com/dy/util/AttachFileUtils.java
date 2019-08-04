package com.dy.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dy.domain.AttachDTO;

public class AttachFileUtils {

	/* 업로드 경로 */
	private final static String uploadPath = "C:" + File.separator + "upload" + File.separator
			+ CommonUtils.getCurrentTime().substring(2, 10);

	private static boolean makeThumbnail(String storedName, String extension) {

		/* 원본 이미지 인스턴스 */
		File target = new File(uploadPath, storedName);

		/* 실제 이미지가 아닌 메모리상의 이미지를 의미하는 인스턴스 (원본 이미지) */
		try {
			BufferedImage sourceImage = ImageIO.read(target);
			BufferedImage destImage = Scalr.resize(sourceImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

			/* 썸네일 이미지 경로 + 파일명 */
			String thumbnail = uploadPath + File.separator + "t_" + storedName;
			/* 썸네일 이미지 인스턴스 */
			target = new File(thumbnail);

			/* 디렉터리에 썸네일 이미지 추가 */
			ImageIO.write(destImage, extension.toUpperCase(), target);

		} catch (IOException e) {
			// TODO => 예외 핸들링 처리하기
			return false;

		} catch (Exception e) {
			// TODO => 예외 핸들링 처리하기
			return false;
		}

		return true;
	}

	public static List<AttachDTO> uploadFiles(MultipartFile[] files, String code) {

		/* 파일의 정보를 담는 List */
		List<AttachDTO> attachList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File target = new File(uploadPath);
		if (target.exists() == false) {
			target.mkdirs();
		}

		for (MultipartFile file : files) {
			/* 파일 확장자 */
			String extension = FilenameUtils.getExtension(file.getOriginalFilename());
			/* 저장 파일명 */
			String storedName = CommonUtils.getRandomString() + "." + extension;
			/* 확장자 체크 */
			MediaType mediaType = MediaUtils.getMediaType(extension);
			if (ObjectUtils.isEmpty(mediaType)) {
				return null;
			}

			try {
				/* uploadPath에 storedName의 이름을 가진 파일을 생성 */
				target = new File(uploadPath, storedName);
				FileCopyUtils.copy(file.getBytes(), target);

				/* 썸네일 이미지 생성 결과 */
				boolean isCreated = makeThumbnail(storedName, extension);
				if (isCreated == false) {
					return null;
				}

				/* 파일 정보 저장 */
				AttachDTO attach = new AttachDTO();
				attach.setCode(code);
				attach.setOriginalName(file.getOriginalFilename());
				attach.setStoredName(storedName);
				attach.setSize(file.getSize());

				/* 파일 정보 추가 */
				attachList.add(attach);
			} catch (IOException e) {
				// TODO => 예외 핸들링 처리하기
				return null;

			} catch (Exception e) {
				// TODO => 예외 핸들링 처리하기
				return null;
			}
		}
		// end of for

		return attachList;
	}
	// end of method

	/*
	 * 나중에 쓸 일이 있을지도 모르는 원본보다 큰 썸네일 이미지 (px만 적고, 사이즈는 큼)
	 */
//	private static void makeThumbnail1(String uploadPath, String filename, String extension) throws IOException {
//
//		File file = new File(uploadPath, filename);
//
//		BufferedImage srcImg = ImageIO.read(file);
//
//		// 썸네일의 너비, 높이
//		int tWidth = 450, tHeight = 270;
//
//		// 원본 이미지의 너비, 높이
//		int oWidth = srcImg.getWidth();
//		int oHeight = srcImg.getHeight();
//
//		// 늘어날 길이를 계산하여 패딩
//		int pd = 0;
//		if (oWidth > oHeight) {
//			pd = (int) (Math.abs((tHeight * oWidth / (double) tWidth) - oHeight) / 2d);
//		} else {
//			pd = (int) (Math.abs((tWidth * tHeight / (double) tHeight) - oWidth) / 2d);
//		}
//		srcImg = Scalr.pad(srcImg, pd, Color.WHITE, Scalr.OP_ANTIALIAS);
//
//		oWidth = srcImg.getWidth();
//		oHeight = srcImg.getHeight();
//
//		// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산(?)
//		int nWidth = oWidth;
//		int nHeight = (oWidth * tHeight) / tWidth;
//
//		// 계산된 높이가 원본보다 높다면 crop이 안되므로
//		// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산(?)
//		if (nHeight > oHeight) {
//			nWidth = (oHeight * tWidth) / tHeight;
//			nHeight = oHeight;
//		}
//
//		// 계산된 크기로 원본 이미지를 가운데에서 crop
//		BufferedImage cropImg = Scalr.crop(srcImg, (oWidth - nWidth) / 2, (oHeight - nHeight) / 2, nWidth, nHeight);
//
//		// 썸네일 생성
//		BufferedImage destImg = Scalr.resize(cropImg, tWidth, tHeight);
//
//		/* 썸네일 이미지 경로 */
//		String thumbnailPath = uploadPath + File.separator + "t2_" + filename;
//		file = new File(thumbnailPath);
//
//		/* 디렉터리에 썸네일 추가 */
//		ImageIO.write(destImg, extension.toUpperCase(), file);
//	}

}
