package com.dy;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dy.common.Const.Status;
import com.dy.common.Const.YesNo;
import com.dy.domain.GoodsDTO;
import com.dy.service.GoodsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	private GoodsService service;

	@Test
	public void testRegister() {
		try {
			GoodsDTO goods = new GoodsDTO();
			goods.setCode("GOODS-00007");
			goods.setName("베이직 코튼 반바지 수정");
			goods.setInfo("도영이가 입던 베코 반바지 수정");
			goods.setPrice(9800);
			goods.setQuantity(333);
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
		GoodsDTO goods = service.getGoodsDetails("GOODS-00007");
		System.out.println(goods.toString());
	}

	@Test
	public void testDelete() {
		service.deleteGoods("GOODS-00007");
	}

	@Test
	public void testList() {
		List<GoodsDTO> goodsList = service.getGoodsList();
		for (GoodsDTO goods : goodsList) {
			System.out.println("===============");
			System.out.println(goods.getName());
		}
	}

}
