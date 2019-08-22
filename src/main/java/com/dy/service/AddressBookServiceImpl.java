package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dy.domain.AddressBookDTO;
import com.dy.mapper.AddressBookMapper;

@Service
public class AddressBookServiceImpl implements AddressBookService {

	@Autowired
	private AddressBookMapper addressBookMapper;

	@Override
	public boolean registerAddress(AddressBookDTO params) {

		int queryResult = addressBookMapper.insertAddress(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public AddressBookDTO getDefaultAddressDetails(String username) {

		return addressBookMapper.selectDefaultAddressDetails(username);
	}

	@Override
	public boolean deleteAddress(Integer idx, String username) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("idx", idx);
		params.put("username", username);

		int queryResult = addressBookMapper.deleteAddress(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<AddressBookDTO> getAddressBook(String username) {

		List<AddressBookDTO> addressBook = null;

		int totalCount = addressBookMapper.selectAddressBookTotalCount(username);
		if (totalCount > 0) {
			addressBook = addressBookMapper.selectAddressBook(username);
		}

		return addressBook;
	}

}
