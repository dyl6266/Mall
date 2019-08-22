package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.UserDTO;

@Mapper
public interface UserMapper {

	public int insertUser(UserDTO params);

	public UserDTO selectUserDetails(UserDTO params);

	public UserDTO selectUserDetailsByUsername(String username);

	public int updateUser(UserDTO params);

	public int deleteUser(String username);

	public List<UserDTO> selectUserList();

	public int selectUserTotalCount();

	public int increaseLoginFailureCount(String username);

	public int initializeLoginFailureCount(String username);

	public int lockUserAccount(String username);

	public int unlockUserAccount(String username);
}
