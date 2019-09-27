package com.dy.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.AddressBookDTO;

@Mapper
public interface AddressBookMapper {

	public int insertAddress(AddressBookDTO params);

	public AddressBookDTO selectAddressDetails(String username);

	public int updateAddress(HashMap<String, Object> params);

	public int deleteAddress(HashMap<String, Object> params);

	public List<AddressBookDTO> selectAddressBook(String username);

	public int selectAddressBookTotalCount(String username);

}
