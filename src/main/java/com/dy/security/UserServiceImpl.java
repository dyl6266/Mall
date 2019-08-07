package com.dy.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

	/**
	 * DB에서 사용자 정보를 가지고 온 뒤에 UserDetails 타입으로 반환
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserDTO user = userMapper.selectUserDetails(username);
		if (user == null) {
			throw new UsernameNotFoundException("member does not exist...");
		}

		Collection<? extends GrantedAuthority> authorities = null;
		int authorityCount = authorityMapper.selectUserAuthorityTotalCount(new AuthorityDTO(username, null));
		if (authorityCount > 0) {
			authorities = authorityMapper.selectUserAuthorities(username);
			user.setAuthorities(authorities);
		}

		return user;
	}

}
