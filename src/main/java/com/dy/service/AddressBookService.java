package com.dy.service;

import java.util.List;

import com.dy.domain.AddressBookDTO;

public interface AddressBookService {

	public boolean registerAddress(AddressBookDTO params);

	public AddressBookDTO getDefaultAddressDetails(String username);

	public boolean deleteAddress(Integer idx, String username);

	public List<AddressBookDTO> getAddressBook(String username);

}
