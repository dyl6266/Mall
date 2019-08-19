package com.dy.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dy.common.Const.TableName;
import com.dy.domain.AttachDTO;
import com.dy.domain.GoodsDTO;
import com.dy.domain.StockDTO;
import com.dy.mapper.AttachMapper;
import com.dy.mapper.GoodsMapper;
import com.dy.mapper.StockMapper;
import com.dy.util.AttachFileUtils;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private AttachMapper attachMapper;

	@Override
	public boolean registerGoods(GoodsDTO params) {

		int queryResult;

		if (StringUtils.isEmpty(params.getCode())) {
			/* 상품 코드 생성 */
			String code = goodsMapper.generateGoodsCode(TableName.GOODS);

			/* 상품 코드 중복 검사 (재고 테이블에는 FK 제약조건을 걸어두었기 때문에 존재하지 않는 상품 코드가 레코드에 들어갈 수 없음) */
			int codeCount = goodsMapper.checkForDuplicateGoodsCode(code);
			if (codeCount != 0) {
				return false;
			}

			/* 상품 등록 쿼리 */
			params.setCode(code);
			queryResult = goodsMapper.insertGoods(params);
			if (queryResult != 1) {
				return false;
			}

			/* 재고 등록 */
			params.getStock().setCode(code);
			queryResult = stockMapper.insertStock(params.getStock());
			if (queryResult != 1) {
				return false;
			}

		} else {
			/* 상품 수정 쿼리 */
			queryResult = goodsMapper.updateGoods(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean registerGoods(GoodsDTO params, MultipartFile[] files) throws IOException {

		int queryResult;

		if (StringUtils.isEmpty(params.getCode())) {
			/* 상품 코드 생성 */
			String code = goodsMapper.generateGoodsCode(TableName.GOODS);

			/* 상품 코드 중복 검사 (재고 테이블에는 FK 제약조건을 걸어두었기 때문에 존재하지 않는 상품 코드가 레코드에 들어갈 수 없음) */
			int codeCount = goodsMapper.checkForDuplicateGoodsCode(code);
			if (codeCount != 0) {
				return false;
			}

			/* 상품 등록 쿼리 */
			params.setCode(code);
			queryResult = goodsMapper.insertGoods(params);
			if (queryResult != 1) {
				return false;
			}

			/* 재고 등록 */
			params.getStock().setCode(code);
			queryResult = stockMapper.insertStock(params.getStock());
			if (queryResult != 1) {
				return false;
			}

			/* 이미지 리스트 등록 */
			List<AttachDTO> attachList = AttachFileUtils.uploadFiles(files, params.getCode());
			for (AttachDTO attach : attachList) {
				/* 이미지 등록 쿼리 */
				queryResult += attachMapper.insertAttach(attach);
			}
			if (queryResult < 1) {
				return false;
			}

		} else {
			/* 상품 수정 쿼리 */
			queryResult = goodsMapper.updateGoods(params);
			if (queryResult != 1) {
				return false;
			}
		}

		return true;
	}

	@Override
	public GoodsDTO getGoodsDetails(String code) {

		GoodsDTO goods = goodsMapper.selectGoodsDetails(code);
		if (goods != null) {
			StockDTO stock = stockMapper.selectStockDetails(code);
			goods.setStock(stock);
		}

		return goods;
	}

	// TODO => 컨ㅌ롤러에서 goodsService, attachService를 호출하는 방식으로 바꿀지 생각해보기
	@Override
	public Map<String, Object> getGoodsDetailsWithImages(String code) {

		Map<String, Object> map = new HashMap<>();

		GoodsDTO goods = goodsMapper.selectGoodsDetails(code);
		if (goods != null) {
			StockDTO stock = stockMapper.selectStockDetails(code);
			goods.setStock(stock);
		}
		map.put("goods", goods);

		List<AttachDTO> attachList = attachMapper.selectAttachList(code);
		if (CollectionUtils.isEmpty(attachList) == false) {
			map.put("images", attachList);
		}

		return map;
	}

	@Override
	public boolean deleteGoods(List<String> codes) {

		int queryResult = goodsMapper.deleteGoods(codes);
		if (queryResult < 1) {
			return false;
		}

		return true;
	}

	@Override
	public List<GoodsDTO> getGoodsList() {

		List<GoodsDTO> goodsList = null;

		int goodsTotalCount = goodsMapper.selectGoodsTotalCount();
		if (goodsTotalCount > 0) {
			goodsList = goodsMapper.selectGoodsListWithMainImage();
		}

		return goodsList;
	}

}
