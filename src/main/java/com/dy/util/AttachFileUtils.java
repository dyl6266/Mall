package com.dy.util;

import java.awt.Color;
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
	
	
//	private static boolean makeThumbnail5(String storedName, String extension) {
//
//		File target = new File(uploadPath, storedName);
//		try {
//			/* 원본 이미지에 대한 메모리상의 이미지를 의미하는 인스턴스 */
//			BufferedImage sourceImage = ImageIO.read(target);
//
//			/* 썸네일의 너비와 높이 */
//			int tWidth = 450, tHeight = 270;
//
//			/* 원본 이미지의 너비와 높이 */
//			int oWidth = sourceImage.getWidth();
//			int oHeight = sourceImage.getHeight();
//
//			/* 늘어날 길이를 계산하여 패딩 */
//			int padding = 0;
//			if (oWidth > oHeight) {
//				padding = (int) (Math.abs((tHeight * oWidth / (double) tWidth) - oHeight) / 2d);
//			} else {
//				padding = (int) (Math.abs((tWidth * oHeight / (double) tHeight) - oWidth) / 2d);
//			}
//
//			/* 주어진 색상으로 이미지 가장자리 주위에 패딩을 적용하여 추가 패딩 공간을 채워주는 메소드 (상/하/좌/우 동일하게 적용됨) */
//			sourceImage = Scalr.pad(sourceImage, padding, Color.BLACK, Scalr.OP_ANTIALIAS);
//
//			/* pad() 메소드로 변경된 이미지 사이즈 가져오기 */
//			oWidth = sourceImage.getWidth();
//			oHeight = sourceImage.getHeight();
//
//			/* 썸네일 비율로 크롭할 크기 지정 (crop될 너비와 높이) */
//			int cWidth = oWidth;
//			int cHeight = (oWidth * tHeight) / tWidth;
//			if (cHeight > oHeight) {
//				cWidth = (oHeight * tWidth) / tHeight;
//				cHeight = oHeight;
//			}
//
//			/*
//			 * 늘어난 이미지의 중앙을 썸네일 비율로 크롭
//			 * 
//			 * 두 번째 인자 => crop할 좌상단의 X 좌표
//			 * 세 번째 인자 => crop할 좌상단의 Y 좌표
//			 */
//			BufferedImage cropImage = Scalr.crop(sourceImage, (oWidth - cWidth) / 2, (oHeight - cHeight) / 2, cWidth,
//					cHeight);
//			/* 이미지 크기 조정 (썸네일 생성) */
//			BufferedImage destImage = Scalr.resize(cropImage, tWidth, tHeight);
//
//			/* 썸네일 이미지 경로 + 파일명 */
//			String thumbnail = uploadPath + File.separator + "t_" + storedName;
//			/* 썸네일 이미지 인스턴스 */
//			target = new File(thumbnail);
//			/* 디스크에 이미지 파일 생성 */
//			ImageIO.write(destImage, extension.toUpperCase(), target);
//
//			/* 테스트용 크롭 이미지 */
//			String crop = uploadPath + File.separator + "crop_" + storedName;
//			target = new File(crop);
//			ImageIO.write(cropImage, extension.toUpperCase(), target);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//	
//	
//
//	private static boolean makeThumbnail3(String storedName, String extension) {
//
//		File target = new File(uploadPath, storedName);
//		try {
//			/* 원본 이미지에 대한 메모리상의 이미지를 의미하는 인스턴스 */
//			BufferedImage srcImg = ImageIO.read(target);
//
//			/* 썸네일의 너비와 높이 */
//			int dw = 450, dh = 270;
//
//			/* 원본 이미지의 너비와 높이 */
//			int ow = srcImg.getWidth();
//			int oh = srcImg.getHeight();
//
//			// 늘어날 길이를 계산하여 패딩합니다.
//			int pd = 0;
//			if (ow > oh) {
//				pd = (int) (Math.abs((dh * ow / (double) dw) - oh) / 2d);
//			} else {
//				pd = (int) (Math.abs((dw * oh / (double) dh) - ow) / 2d);
//			}
//			srcImg = Scalr.pad(srcImg, pd, Color.WHITE, Scalr.OP_ANTIALIAS);
//
//			// 이미지 크기가 변경되었으므로 다시 구합니다.
//			ow = srcImg.getWidth();
//			oh = srcImg.getHeight();
//
//			// 썸네일 비율로 크롭할 크기를 구합니다.
//			int nw = ow;
//			int nh = (ow * dh) / dw;
//			if (nh > oh) {
//				nw = (oh * dw) / dh;
//				nh = oh;
//			}
//
//			// 늘려진 이미지의 중앙을 썸네일 비율로 크롭 합니다.
//			BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);
//			// 리사이즈(썸네일 생성)
//			BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
//
//			/* 썸네일 이미지 경로 + 파일명 */
//			String thumbnail = uploadPath + File.separator + "testtest_" + storedName;
//			/* 썸네일 이미지 인스턴스 */
//			target = new File(thumbnail);
//
//			ImageIO.write(destImg, extension.toUpperCase(), target);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//	
//	private static boolean makeThumbnail4(String storedName, String extension) {
//
//		File target = new File(uploadPath, storedName);
//		try {
//			/* 원본 이미지에 대한 메모리상의 이미지를 의미하는 인스턴스 */
//			BufferedImage srcImg = ImageIO.read(target);
//
//			// 썸네일의 너비와 높이 입니다.
//			int dw = 250, dh = 150;
//			// 원본 이미지의 너비와 높이 입니다.
//			int ow = srcImg.getWidth();
//			int oh = srcImg.getHeight();
//
//			// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
//			int nw = ow;
//			int nh = (ow * dh) / dw;
//			// 계산된 높이가 원본보다 높다면 crop이 안되므로
//			// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
//			if (nh > oh) {
//				nw = (oh * dw) / dh;
//				nh = oh;
//			}
//			// 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
//			BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);
//			// crop된 이미지로 썸네일을 생성합니다.
////			BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
//			BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH, 250);
//
//			/* 썸네일 이미지 경로 + 파일명 */
//			String thumbnail = uploadPath + File.separator + "width_" + storedName;
//			/* 썸네일 이미지 인스턴스 */
//			target = new File(thumbnail);
//
//			ImageIO.write(destImg, extension.toUpperCase(), target);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}
//
//	private static boolean makeThumbnail2(String storedName, String extension) {
//
//		File target = new File(uploadPath, storedName);
//		try {
//			/* 원본 이미지에 대한 메모리상의 이미지를 의미하는 인스턴스 */
//			BufferedImage srcImg = ImageIO.read(target);
//
//			// 썸네일의 너비와 높이 입니다.
//			int dw = 250, dh = 150;
//			// 원본 이미지의 너비와 높이 입니다.
//			int ow = srcImg.getWidth();
//			int oh = srcImg.getHeight();
//
//			// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
//			int nw = ow;
//			int nh = (ow * dh) / dw;
//			// 계산된 높이가 원본보다 높다면 crop이 안되므로
//			// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
//			if (nh > oh) {
//				nw = (oh * dw) / dh;
//				nh = oh;
//			}
//			// 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
//			BufferedImage cropImg = Scalr.crop(srcImg, (ow - nw) / 2, (oh - nh) / 2, nw, nh);
//			// crop된 이미지로 썸네일을 생성합니다.
////			BufferedImage destImg = Scalr.resize(cropImg, dw, dh);
//			BufferedImage destImg = Scalr.resize(srcImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 150);
//
//			/* 썸네일 이미지 경로 + 파일명 */
//			String thumbnail = uploadPath + File.separator + "height_" + storedName;
//			/* 썸네일 이미지 인스턴스 */
//			target = new File(thumbnail);
//
//			ImageIO.write(destImg, extension.toUpperCase(), target);
//
//		} catch (IOException e) {
//			e.printStackTrace();
//			return false;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return false;
//		}
//
//		return true;
//	}

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

	/**
	 * 첨부 이미지 삭제
	 * 
	 * @param storedFilename - 저장 파일명
	 * @param uploadedDate - 업로드 날짜
	 * @return
	 */
	public static boolean deleteFile(String storedFilename, String uploadedDate) {

		/* 삭제할 파일 경로 */
		String path = "C:" + File.separator + "upload" + File.separator + uploadedDate + File.separator;
		/* 원본 이미지 */
		File original = new File(path + storedFilename);
		/* 썸네일 이미지 */
		File thumbnail = new File(path + "t_" + storedFilename);

		if (original.exists() == false || thumbnail.exists() == false) {
			return false;
		}

		original.delete();
		thumbnail.delete();

		return true;
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
