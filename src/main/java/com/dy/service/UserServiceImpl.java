package com.dy.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.Const.Authority;
import com.dy.domain.AuthorityDTO;
import com.dy.domain.UserDTO;
import com.dy.mapper.AuthorityMapper;
import com.dy.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private AuthorityMapper authorityMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * DB에서 사용자 정보를 가지고 온 뒤에 UserDetails 타입으로 반환
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDTO user = userMapper.selectUserDetails(username);

		Collection<? extends GrantedAuthority> authorities = null;
		int authorityCount = authorityMapper.selectUserAuthorityTotalCount(new AuthorityDTO(username, null));
		if (authorityCount > 0) {
			authorities = authorityMapper.selectUserAuthorities(username);
			user.setAuthorities(authorities);
		}

		return user;
	}

	@Override
	public boolean registerUser(UserDTO params) {

		if (StringUtils.isEmpty(params.getPassword()) == false) {
			/* 비밀번호 인코딩 */
			params.setPassword(passwordEncoder.encode(params.getPassword()));
		}

		int queryResult;
		if (params.getIdx() == null) {
			/* 상품 등록 쿼리 */
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
				Collection<? extends GrantedAuthority> authorities = authorityMapper.selectUserAuthorities(user.getUsername());
				user.setAuthorities(authorities);
			}
		}

		return users;
	}

}
