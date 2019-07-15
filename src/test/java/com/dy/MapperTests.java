package com.dy;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import com.dy.common.Const.Status;
import com.dy.common.Const.TableName;
import com.dy.common.Const.YesNo;
import com.dy.domain.GoodsDTO;
import com.dy.mapper.GoodsMapper;
import com.dy.service.GoodsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTests {

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private GoodsService service;

	@Test
	public void testInsert() {
		try {
			GoodsDTO goods = new GoodsDTO();
			goods.setCode(goodsMapper.generateGoodsCode(TableName.GOODS));
			goods.setName("도영이의 반바지");
			goods.setInfo("도영이가 입던 옷입니당.");
			goods.setPrice(138000);
			goods.setQuantity(5);
			goods.setStatus(Status.Y);
			goods.setDeleteYn(YesNo.N);
			service.registerGoods(goods);
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSelect() {
		GoodsDTO goods = goodsMapper.selectGoodsDetails("GOODS-00002");
		System.out.println(goods.toString());
	}
	
	@Test
	public void testUpdate() {
		GoodsDTO goods = new GoodsDTO();
		goods.setName("도영이의 반바지를 바꿨당");
		goods.setInfo("도영이가 입던 옷을 바꿨땅.");
		goods.setPrice(115000);
		goods.setQuantity(33);
		goods.setStatus(Status.N);
		goods.setDeleteYn(YesNo.Y);
		goods.setCode("GOODS-00004");
		
		goodsMapper.updateGoods(goods);
	}
	
	@Test
	public void testDelete() {
		goodsMapper.deleteGoods("GOODS-00003");
	}
	
	@Test
	public void testSelectList() {
		List<GoodsDTO> goodsList = goodsMapper.selectGoodsList();
		if ( CollectionUtils.isEmpty(goodsList) == false ) {
			for ( GoodsDTO goods : goodsList ) {
				System.out.println(goods.getName());
				System.out.println("==============");
			}
		}
	}

}
