package com.dy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dy.common.Const.TableName;
import com.dy.common.paging.PaginationInfo;
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
	public boolean registerGoods(GoodsDTO params, MultipartFile[] files) {

		int queryResult;

		if (StringUtils.isEmpty(params.getCode())) {
			/* 상품 코드 생성 */
			String code = goodsMapper.generateGoodsCode(TableName.GOODS);

			/* 상품 코드 중복 검사 (재고 테이블에는 FK 제약조건을 걸어두었기 때문에 존재하지 않는 상품 코드가 레코드에 들어갈 수 없음) */
			int codeCount = goodsMapper.checkForDuplicateGoodsCode(code);
			if (codeCount != 0) {
				code = goodsMapper.generateMaxGoodsCode(TableName.GOODS);
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

			/* 재고 수정 */
			params.getStock().setCode(params.getCode());
			queryResult = stockMapper.updateStock(params.getStock());
			if (queryResult != 1) {
				return false;
			}
		}

		/* 이미지 리스트 등록 */
		if (StringUtils.isEmpty(files[0].getName()) == false && files[0].getSize() > 0) {
			List<AttachDTO> attachList = AttachFileUtils.uploadFiles(files, params.getCode());
			if (CollectionUtils.isEmpty(attachList)) {
				return false;
			}
			for (AttachDTO attach : attachList) {
				/* 이미지 등록 쿼리 */
				queryResult += attachMapper.insertAttach(attach);
			}
			if (queryResult < 1) {
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

			map.put("goods", goods);
		}

		List<AttachDTO> attachList = attachMapper.selectAttachList(code);
		if (CollectionUtils.isEmpty(attachList) == false) {
			map.put("images", attachList);
		}

		return map;
	}

	@Override
	public List<Map<String, Object>> getListOfGoodsDetailsWithImages(List<String> codes) {

		List<Map<String, Object>> datas = new ArrayList<>();

		for (String code : codes) {
			Map<String, Object> map = new HashMap<>();

			/* 상품 상세 정보 */
			GoodsDTO goods = goodsMapper.selectGoodsDetails(code);
			/* 상품 이미지 리스트 */
			List<AttachDTO> attachList = attachMapper.selectAttachList(code);

			if (goods == null || CollectionUtils.isEmpty(attachList)) {
				return null;
			}

			StockDTO stock = stockMapper.selectStockDetails(code);
			goods.setStock(stock);

			map.put("goods", goods);
			map.put("images", attachList);

			datas.add(map);
		}

		return datas;
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
	public List<GoodsDTO> getGoodsList(GoodsDTO params) {

		List<GoodsDTO> goodsList = null;

		/* 전체 상품 수 */
		int goodsTotalCount = goodsMapper.selectGoodsTotalCount(params);
		if (goodsTotalCount > 0) {
			/* 페이징 계산에 필요한 전체 상품 수 저장 */
			params.setTotalRecordCount(goodsTotalCount);

			/* 페이징 정보 저장 */
			PaginationInfo paginationInfo = new PaginationInfo(params);
			params.setPaginationInfo(paginationInfo);

			/* 상품 리스트 */
			goodsList = goodsMapper.selectGoodsListWithMainImage(params);
		}

		return goodsList;
	}

}
