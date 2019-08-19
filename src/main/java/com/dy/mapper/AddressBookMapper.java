package com.dy.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.dy.domain.AddressBookDTO;

@Mapper
public interface AddressBookMapper {

	public int insertAddress(AddressBookDTO params);

	public int deleteAddress(AddressBookDTO params);

	public List<AddressBookDTO> selectAddressBook(String username);

}
