package com.dy.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.Const.YesNo;
import com.dy.domain.PurchaseDTO;
import com.dy.mapper.AddressBookMapper;
import com.dy.mapper.PurchaseMapper;
import com.dy.mapper.StockMapper;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseMapper purchaseMapper;

	@Autowired
	private AddressBookMapper addressBookMapper;

	@Autowired
	private StockMapper stockMapper;

	@Override
	public boolean purchaseGoods(PurchaseDTO params) {

		int queryResult;
		if (StringUtils.isEmpty(params.getAddressBook().getName()) == false) {

			/* 해당 사용자의 모든 배송지에서 기본 배송지 여부를 'N'으로 업데이트 */
			if (params.getAddressBook().getDefaultYn() == YesNo.Y) {
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("username", params.getUsername());
				hashMap.put("defaultYn", YesNo.N);

				queryResult = addressBookMapper.updateAddress(hashMap);
			}

			/* 배송지 추가 */
			params.getAddressBook().setPostcode(params.getPostcode());
			params.getAddressBook().setAddress(params.getAddress());
			params.getAddressBook().setDetailAddress(params.getDetailAddress());
			queryResult = addressBookMapper.insertAddress(params.getAddressBook());
		}
		// TODO => 상품이 리스트 형태로 넘어오는 경우에는 foreach로 처리를 해야 할까? 어떻게 해야 할지 생각해보기
		queryResult = purchaseMapper.insertPurchase(params);
		if (queryResult != 1) {
			return false;
		}

		/* 재고 업데이트 */
		params.getGoods().getStock().setCode(params.getCode());
		queryResult = stockMapper.updateStock(params.getGoods().getStock());
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public PurchaseDTO getPurchaseDetails(Integer idx, String username) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("idx", idx);
		params.put("username", username);

		return purchaseMapper.selectPurchaseDetails(params);
	}

	// TODO => 삭제가 필요한지 생각해보기
	@Override
	public boolean deletePurchaseInfo(Integer idx, String username) {

		HashMap<String, Object> params = new HashMap<>();
		params.put("idx", idx);
		params.put("username", username);

		int queryResult = purchaseMapper.deletePurchase(params);
		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<PurchaseDTO> getPurchaseList(String username) {

		List<PurchaseDTO> purchaseList = null;

		int totalCount = purchaseMapper.selectPurchaseTotalCount(username);
		if (totalCount > 0) {
			purchaseList = purchaseMapper.selectPurchaseList(username);
		}

		return purchaseList;
	}

}
