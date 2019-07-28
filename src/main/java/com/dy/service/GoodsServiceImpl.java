package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.Const.TableName;
import com.dy.domain.GoodsDTO;
import com.dy.mapper.GoodsMapper;
import com.dy.mapper.StockMapper;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private StockMapper stockMapper;

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
	public GoodsDTO getGoodsDetails(String code) {
		return goodsMapper.selectGoodsDetails(code);
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
			goodsList = goodsMapper.selectGoodsList();
		}

		return goodsList;
	}

}
