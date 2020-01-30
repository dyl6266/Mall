package com.dy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.dy.common.Const.Authority;
import com.dy.common.Const.Status;
import com.dy.common.Const.YesNo;
import com.dy.domain.AttachDTO;
import com.dy.domain.AuthrotiyDto;
import com.dy.domain.FavoriteDTO;
import com.dy.domain.GoodsDTO;
import com.dy.domain.PurchaseDTO;
import com.dy.domain.UserDTO;
import com.dy.mapper.AddressBookMapper;
import com.dy.mapper.AttachMapper;
import com.dy.mapper.AuthorityMapper;
import com.dy.mapper.FavoriteMapper;
import com.dy.mapper.GoodsMapper;
import com.dy.mapper.PurchaseMapper;
import com.dy.mapper.UserMapper;
import com.dy.util.AttachFileUtils;
import com.dy.util.CommonUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthorityMapper authMapper;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private AddressBookMapper bookMapper;

	@Autowired
	private AttachMapper attachMapper;
	
	@Autowired
	private PurchaseMapper purchaseMapper;
	
	@Autowired
	private FavoriteMapper favoriteMapper;
	
	@Test
	public void 테스트2() {
		Collection<?extends SimpleGrantedAuthority> authorities = authMapper.selectUserGrantedAuthorities("admin");
		for (SimpleGrantedAuthority auth : authorities) {
			System.out.println(auth);
		}
	}
	
	@Test
	public void 테스트() {
		String ran1 = CommonUtils.getRandomNumber(6, YesNo.Y);
		String ran2 = CommonUtils.getRandomNumber(6, YesNo.N);
		System.out.println(ran1);
		System.out.println(ran2);
	}
	
	@Test
	public void 좋아요() {
		FavoriteDTO params = new FavoriteDTO();
		params.setUsername("dyl6266@nate.com");
		params.setCode("GOODS-00020");

		favoriteMapper.insertFavorite(params);
	}
	
	@Test
	public void 좋아요_취소() {
		FavoriteDTO params = new FavoriteDTO();
		params.setStatus(Status.N);
		params.setUsername("dyl6266@naver.com");
		params.setCode("GOODS-00020");
		
		favoriteMapper.updateFavorite(params);
	}
	
	@Test
	public void 좋아요_카운트() {
		favoriteMapper.selectFavoriteTotalCount("GOODS-00020");
	}
	
	@Test
	public void 구매상품_목록() {
		
		List<List<PurchaseDTO>> purchaseList = new ArrayList<>();
//		List<PurchaseDTO> purchaseList = new ArrayList<>();
		try {
			List<Integer> seqList = purchaseMapper.selectPurchaseSequenceList("dyl6266@naver.com");
			if (CollectionUtils.isEmpty(seqList) == false) {
				for (Integer seq : seqList) {
					List<PurchaseDTO> purList = purchaseMapper.selectPurchaseGoodsList(seq);
					purchaseList.add(purList);
				}
			}
			System.out.println(purchaseList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 파일_삭제() {
		AttachDTO file = attachMapper.selectAttachDetails(25);

		String uploadedDate = CommonUtils.formatDate(file.getInsertTime(), "yy-MM-dd");
		boolean isDeleted = AttachFileUtils.deleteFile(file.getStoredName(), uploadedDate);
	}

	@Test
	public void 주소_등록() {
//		AddressBookDTO book = new AddressBookDTO("dyl6266@nate.com", "회사", "인천시 남동구 펜타코드");
//		bookMapper.insertAddress(book);
	}

	@Test
	public void 주소_삭제() {
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("username", "dyl6266@nate.com");
//		params.put("idx", 3);
//		bookMapper.deleteAddress(params);
	}

	@Test
	public void 주소_리스트() {
		bookMapper.selectAddressBook("dyl6266@nate.com");
	}

	@Test
	public void 상품_이미지() {
		List<GoodsDTO> goodsList = goodsMapper.selectGoodsListWithMainImage(new GoodsDTO());
		for (GoodsDTO goods : goodsList) {
			System.out.println(goods);
		}
	}

	@Test
	public void 가입() {
		UserDTO user = new UserDTO("dyl6266@nate.com", encoder.encode("ehdud123!@#"), "도영도영", "01033282400");
		try {
			int result = userMapper.insertUser(user);
			if (result > 0) {
				AuthrotiyDto auth = new AuthrotiyDto(user.getUsername(), Authority.MEMBER);
				authMapper.insertUserAuthority(auth);
			}
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void 조회() {
		UserDTO user = userMapper.selectUserDetailsByUsername("dyl2436");
	}
//
//	@Test
//	public void 수정() {
//		UserDTO user = new UserDTO("dyl6266", encoder.encode("wlsahfl!@#"), "앙~도영띠", "01012345678");
//		userMapper.updateUser(user);
//	}
//
//	@Test
//	public void 탈퇴() {
//		userMapper.deleteUser("dyl6266");
//	}
//
//	@Test
//	public void 목록() {
//		int count = userMapper.selectUserTotalCount();
//		if (count > 0) {
//			List<UserDTO> userList = userMapper.selectUserList();
//		}
//	}
//
//	@Test
//	public void 권한_수정() {
//		AuthorityDTO auth = new AuthorityDTO("dyl3328", "MANAGER");
//		authMapper.updateUserAuthority(auth);
//	}
//
//	@Test
//	public void 권한_삭제() {
//		authMapper.deleteUserAuthority(new AuthorityDTO("dyl3328", "MEMBER"));
//	}
//
//	@Test
//	public void 권한_목록() {
//		int total = authMapper.selectUserAuthorityTotalCount(new AuthorityDTO("dyl3328", null));
//		if (total > 0) {
//			try {
//				Collection<? extends SimpleGrantedAuthority> list = authMapper.selectUserAuthorities(null);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}

}
