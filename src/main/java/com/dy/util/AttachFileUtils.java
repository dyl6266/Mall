package com.dy.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.imgscalr.Scalr;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;

public class AttachFileUtils {

	private AttachFileUtils() {
	}

	/* 업로드 경로 */
	private final static String uploadPath = "C:" + File.separator + "upload" + File.separator
			+ CommonUtils.getCurrentTime().substring(2, 10);

	private static String makeThumbnail(String uploadPath, String storedName, String extension) throws IOException {

		/* 원본 이미지 인스턴스 */
		File file = new File(uploadPath, storedName);

		/* 실제 이미지가 아닌 메모리상의 이미지를 의미하는 인스턴스 (원본 이미지) */
		BufferedImage sourceImage = ImageIO.read(file);
		BufferedImage destImage = Scalr.resize(sourceImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

		/* 썸네일 이미지 업로드 경로 */
		uploadPath = uploadPath + File.separator;

		String thumbnail = uploadPath + "t_" + storedName;

		/* 썸네일 이미지 인스턴스 */
		file = new File(thumbnail);

		/* 디렉터리에 썸네일 이미지 추가 */
		ImageIO.write(destImage, extension.toUpperCase(), file);
		return thumbnail;
	}

	public static String uploadImageFile(String originalName, byte[] bytes) throws IOException {

		/* 저장 파일명 */
		String storedName = CommonUtils.getRandomString() + "_" + originalName;

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File file = new File(uploadPath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		/* 파일 확장자 */
		String extension = FilenameUtils.getExtension(originalName);

		/* 확장자가 이미지 타입인지 체크 */
		MediaType mediaType = MediaUtils.getMediaType(extension);
		if (ObjectUtils.isEmpty(mediaType)) {
//			return false;
			return null;
		}

		/* uploadPath에 storedName의 이름을 가진 파일을 생성 */
		file = new File(uploadPath, storedName);
		FileCopyUtils.copy(bytes, file);

		/* 썸네일 이미지 생성 */
		String thumbnail = makeThumbnail(uploadPath, storedName, extension);
		return thumbnail;
	}

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
