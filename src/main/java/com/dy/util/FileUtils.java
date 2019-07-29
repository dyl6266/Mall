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

public class FileUtils {

	private final static String uploadPath = "C:" + File.separator + "upload" + File.separator
			+ CommonUtils.getCurrentTime().substring(2, 10);

	private static String makeThumbnail(String uploadPath, String filename) throws IOException {

		/* 파일 인스턴스 생성 */
		File file = new File(uploadPath, filename);

		/* 실제 이미지가 아닌 메모리상의 이미지를 의미하는 인스턴스 */
		BufferedImage sourceImage = ImageIO.read(file);
		BufferedImage destImage = Scalr.resize(sourceImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);

		/* 썸네일 이미지 경로 */
		String thumbnailPath = uploadPath + File.separator + "s_" + filename;
		file = new File(thumbnailPath);

		/* 파일 확장자 */
		String extension = FilenameUtils.getExtension(filename);
		/* 디렉터리에 파일 추가 */
		ImageIO.write(destImage, extension.toUpperCase(), file);

		/* 썸네일 이미지 이름 */
		String thumbnailName = thumbnailPath.substring(uploadPath.length()).replace(File.separatorChar, '/');
		return thumbnailName;
	}

	public static String uploadFile(String originalFilename, byte[] fileData) throws IOException {

		String saveFilename = CommonUtils.getRandomString() + "_" + originalFilename;

		File file = new File(uploadPath);
		if (file.exists() == false) {
			file.mkdirs();
		}

		file = new File(uploadPath, saveFilename);
		FileCopyUtils.copy(fileData, file);

		/* 파일 확장자 */
		String extension = FilenameUtils.getExtension(originalFilename);
		String uploadedFilename = null;

		MediaType mediaType = MediaUtils.getMediaType(extension);
		if (ObjectUtils.isEmpty(mediaType) == false) {
			uploadedFilename = makeThumbnail(uploadPath, saveFilename);
		} else {
			String iconName = uploadPath + File.separator + saveFilename;
			System.out.println(iconName);
			iconName = iconName.substring(uploadPath.length()).replace(File.separatorChar, '/');
			System.out.println("=====================");
			System.out.println(iconName);
			return iconName;
		}

		return uploadedFilename;
	}

}
