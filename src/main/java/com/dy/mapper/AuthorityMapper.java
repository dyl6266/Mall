package com.dy.mapper;

import java.util.Collection;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.dy.domain.AuthorityDTO;

@Mapper
public interface AuthorityMapper {

	public int insertUserAuthority(AuthorityDTO params);

	public int updateUserAuthority(AuthorityDTO params);

	public int deleteUserAuthority(AuthorityDTO params);

	public Collection<? extends SimpleGrantedAuthority> selectUserAuthorities(String username);

	public int selectUserAuthorityTotalCount(AuthorityDTO params);

}
