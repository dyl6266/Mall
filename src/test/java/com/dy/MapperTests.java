package com.dy;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.dy.common.Const.Authority;
import com.dy.domain.AttachDTO;
import com.dy.domain.AuthorityDTO;
import com.dy.domain.GoodsDTO;
import com.dy.domain.UserDTO;
import com.dy.mapper.AddressBookMapper;
import com.dy.mapper.AttachMapper;
import com.dy.mapper.AuthorityMapper;
import com.dy.mapper.GoodsMapper;
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
		List<GoodsDTO> goodsList = goodsMapper.selectGoodsListWithMainImage();
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
				AuthorityDTO auth = new AuthorityDTO(user.getUsername(), Authority.MEMBER);
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
