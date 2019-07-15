package com.dy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dy.common.Const.TableName;
import com.dy.domain.GoodsDTO;
import com.dy.mapper.GoodsMapper;

@Service
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private GoodsMapper goodsMapper;

	@Override
	public boolean registerGoods(GoodsDTO params) {

		int queryResult;

		if (StringUtils.isEmpty(params.getCode())) {
			String code = goodsMapper.generateGoodsCode(TableName.GOODS);

			int codeCount = goodsMapper.checkForDuplicateGoodsCode(code);
			if (codeCount != 0) {
				return false;
			}
			queryResult = goodsMapper.insertGoods(params);

		} else {
			queryResult = goodsMapper.updateGoods(params);
		}

		if (queryResult != 1) {
			return false;
		}

		return true;
	}

	@Override
	public GoodsDTO getGoodsDetails(String code) {
		return goodsMapper.selectGoodsDetails(code);
	}

	@Override
	public int deleteGoods(String code) {
		return goodsMapper.deleteGoods(code);
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
