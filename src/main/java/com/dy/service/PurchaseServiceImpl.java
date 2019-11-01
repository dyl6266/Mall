package com.dy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.dy.common.Const.TableName;
import com.dy.common.Const.YesNo;
import com.dy.domain.GoodsDTO;
import com.dy.domain.PurchaseDTO;
import com.dy.mapper.AddressBookMapper;
import com.dy.mapper.PurchaseMapper;
import com.dy.mapper.SequenceMapper;
import com.dy.mapper.StockMapper;

@Service
public class PurchaseServiceImpl implements PurchaseService {

	@Autowired
	private PurchaseMapper purchaseMapper;

	@Autowired
	private AddressBookMapper addressBookMapper;

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private SequenceMapper sequenceMapper;

	@Autowired
	private GoodsService goodsService;

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

		if (CollectionUtils.isEmpty(params.getGoodsList())) {
			return false;
		}

		/* 다음 구매 시퀀스 */
		int nextSequence = sequenceMapper.selectNextSequence(TableName.PURCHASE);

		/* 구매하는 상품 개수만큼 forEach */
		for (GoodsDTO goods : params.getGoodsList()) {
			params.setCode(goods.getCode());
			params.setSequence(nextSequence); /* 묶음 구매 처리를 위한 구매 시퀀스 처리 */
			params.setAmount(goods.getPrice()); /* 상품 금액 * 수량을 처리한 값 */

			queryResult = purchaseMapper.insertPurchase(params);
			if (queryResult != 1) {
				return false;
			}

			/* 재고 업데이트 */
			queryResult = stockMapper.updateStock(goods.getStock());
			if (queryResult != 1) {
				return false;
			}
		}

		/* 다음 구매 시퀀스 증가 처리 */
		HashMap<String, Object> sequenceParams = new HashMap<>();
		sequenceParams.put("tableName", TableName.PURCHASE);
		sequenceParams.put("nextSequence", nextSequence);

		queryResult = sequenceMapper.updateSequence(sequenceParams);
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
	public List<List<Map<String, Object>>> getPurchaseList(String username) {

		/* 구매한 상품과 상품의 이미지를 담는 리스트 */
		List<List<Map<String, Object>>> purchaseList = new ArrayList<>();

		/* 구매 시퀀스 리스트 */
		List<Integer> sequenceList = purchaseMapper.selectPurchaseSequenceList(username);

		if (CollectionUtils.isEmpty(sequenceList) == false) {
			for (Integer sequence : sequenceList) {

				/* 상품 코드 리스트 */
				List<String> codes = new ArrayList<>();

				/* 시퀀스별 구매 상품 리스트 */
				List<PurchaseDTO> purchaseGoodsList = purchaseMapper.selectPurchaseGoodsList(sequence);

				for (PurchaseDTO purchaseGoods : purchaseGoodsList) {
					codes.add(purchaseGoods.getCode());
				}

				/* 구매한 상품의 상세 정보 & 이미지 */
				List<Map<String, Object>> goodsList = goodsService.getListOfGoodsDetailsWithImages(codes);

				/* 리스트 안에 시퀀스별 구매 상품 리스트 추가 */
				purchaseList.add(goodsList);
			}

		}

		return purchaseList;
	}

}
