package com.dy.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.Const.Authority;
import com.dy.common.Const.MailType;
import com.dy.domain.AuthorityDTO;
import com.dy.domain.UserDTO;
import com.dy.mapper.AuthorityMapper;
import com.dy.mapper.UserMapper;
import com.dy.util.CommonUtils;
import com.dy.util.MailUtils;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthorityMapper authorityMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MailUtils mailUtils;

	/**
	 * DB에서 사용자 정보를 가지고 온 뒤에 UserDetails 타입으로 반환
	 */
	@Override
	public UserDetails loadUserByUsername(String username) {

		UserDTO user = userMapper.selectUserDetailsByUsername(username);
		if (user != null) {
			Collection<? extends GrantedAuthority> authorities = null;
			int authorityCount = authorityMapper.selectUserAuthorityTotalCount(new AuthorityDTO(username, null));
			if (authorityCount > 0) {
				authorities = authorityMapper.selectUserGrantedAuthorities(username);
				user.setAuthorities(authorities);
			}
		}

		return user;
	}

	/**
	 * 인증 사용자 정보를 반환
	 */
	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 사용자가 익명의 사용자인지 확인
	 */
	@Override
	public boolean isAnonymousUser(String username) {
		return "anonymousUser".equals(username);
	}

	@Override
	public boolean registerUser(UserDTO params) {

		if (StringUtils.isEmpty(params.getPassword()) == false) {
			/* 비밀번호 인코딩 */
			params.setPassword(passwordEncoder.encode(params.getPassword()));
		}

		int queryResult;
		if (params.getIdx() == null) {
			/* 회원 등록 쿼리 */
			queryResult = userMapper.insertUser(params);
			if (queryResult != 1) {
				return false;
			}

			/* TODO => 관리자에서 등록하는 경우 authorities로 처리하기 */
			AuthorityDTO authority = new AuthorityDTO(params.getUsername(), Authority.MEMBER);
			/* 권한 등록 쿼리 */
			queryResult = authorityMapper.insertUserAuthority(authority);
			if (queryResult != 1) {
				return false;
			}

		} else {
			/* 회원 수정 쿼리 */
			queryResult = userMapper.updateUser(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public UserDTO getUserDetails(UserDTO params) {
		return userMapper.selectUserDetails(params);
	}

	@Override
	public boolean deleteUser(String username) {

		int queryResult = userMapper.deleteUser(username);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<UserDTO> getUserList() {

		List<UserDTO> users = null;
		int userTotalCount = userMapper.selectUserTotalCount();
		if (userTotalCount > 0) {
			users = userMapper.selectUserList();

			for (UserDTO user : users) {
				Collection<? extends GrantedAuthority> authorities = authorityMapper.selectUserGrantedAuthorities(user.getUsername());
				user.setAuthorities(authorities);

				List<String> authorityNames = new ArrayList<>();
				for (GrantedAuthority authority : authorities) {
					for (Authority name : Authority.values()) {
						if (String.valueOf(authority).contains(String.valueOf(name))) {
							authorityNames.add(name.getAuthority());
						}
					}
				}
				// end of GrantedAuthority foreach

				user.setAuthorityNames(authorityNames);
			}
			// end of UserDTO foreach
		}

		return users;
	}

	@Override
	public UserDTO findUserAccountInfo(UserDTO params) {

		UserDTO user = userMapper.selectUserDetails(params);
		if (user != null) {
			/* 아이디 찾기의 경우 */
			if (StringUtils.isEmpty(params.getUsername())) {
				/* 이메일 앞자리 길이 - 2 */
				int length = user.getUsername().split("@")[0].length() / 2;

				String maskingUsername = user.getUsername().replaceAll(".(?=.{0," + length + "}@)", "*");
				user.setUsername(maskingUsername);

			} else {
				/* 비밀번호 찾기의 경우 */
				String tempPassword = CommonUtils.getRandomString(10);
				params.setPassword(passwordEncoder.encode(tempPassword));

				/* 비밀번호 업데이트 */
				int queryResult = userMapper.updateUser(params);
				if (queryResult != 1) {
					return null;
				}

				/* 임시 비밀번호 발송 */
				String subject = "do-young's mall 임시 비밀번호입니다.";
				String text = user.getNickname() + "님의 임시 비밀번호는 " + tempPassword + "입니다. 로그인 이후에 비밀번호를 꼭! 변경해 주세요.";

				boolean wasSent = mailUtils.sendMailByUsername(user.getUsername(), subject, text, MailType.TEXT);
				if (wasSent == false) {
					return null;
				}
			}
			// end of else
		}
		// end of if

		return user;
	}

	@Override
	public boolean checkPasswordMatches(String username, String password) {

		UserDTO user = userMapper.selectUserDetailsByUsername(username);
		if (user == null) {
			return false;
		}

		boolean isMatch = passwordEncoder.matches(password, user.getPassword());
		return isMatch;
	}

	@Override
	public boolean changeUserPassword(String username, String newPassword) {

		UserDTO params = new UserDTO(username, passwordEncoder.encode(newPassword), null, null);
		int queryResult = userMapper.updateUser(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public boolean checkLoginFailureCount(String username) {

		/* 로그인 실패 횟수 */
		int failureCount = userMapper.selectUserDetailsByUsername(username).getFailureCount();
		if (failureCount >= 5) {
			/* 계정 잠금 */
			int queryResult = userMapper.lockUserAccount(username);
			if (queryResult != 1) {
				return false;
			}

		} else {
			/* 로그인 실패 횟수 증가 */
			int queryResult = userMapper.increaseLoginFailureCount(username);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean initializeLoginFailureCount(String username) {

		int queryResult = userMapper.initializeLoginFailureCount(username);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

}
