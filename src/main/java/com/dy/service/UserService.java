package com.dy.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.dy.domain.UserDTO;

/**
 * UserDetailsService => DB에서 사용자의 정보를 직접 가지고 올 때 사용하는 인터페이스
 */
public interface UserService extends UserDetailsService {

	public boolean registerUser(UserDTO params);

	public boolean deleteUser(String username);

	public List<UserDTO> getUserList();

}