package com.dy;

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

import com.dy.domain.AuthorityDTO;
import com.dy.domain.UserDTO;
import com.dy.mapper.AuthorityMapper;
import com.dy.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthorityMapper authMapper;

	@Autowired
	private PasswordEncoder encoder;

//	@Test
//	public void 가입() {
//		UserDTO user = new UserDTO("dyl3328", encoder.encode("dkdrlahfl1234"), "도링도링", "01033282436");
//		try {
//			int result = userMapper.insertUser(user);
//			if (result > 0) {
//				AuthorityDTO auth = new AuthorityDTO(user.getUsername(), null);
//				authMapper.insertUserAuthority(auth);
//			}
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void 조회() {
//		UserDTO user = userMapper.selectUserDetails("dyl62661");
//	}
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
