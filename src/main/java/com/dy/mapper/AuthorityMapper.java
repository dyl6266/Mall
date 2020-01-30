package com.dy.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dy.domain.AuthrotiyDto;

@Mapper
public interface AuthorityMapper {

	public int insertUserAuthority(AuthrotiyDto params);

	public int updateUserAuthority(AuthrotiyDto params);

	public int deleteUserAuthority(AuthrotiyDto params);

	public Collection<? extends SimpleGrantedAuthority> selectUserGrantedAuthorities(String username);

	public int selectUserAuthorityTotalCount(AuthrotiyDto params);

}
