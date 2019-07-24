//package com.dy;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.dao.DataAccessException;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.dy.domain.CartDTO;
//import com.dy.domain.GoodsDTO;
//import com.dy.mapper.CartMapper;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class MapperTests {
//
////	@Autowired
////	private GoodsMapper goodsMapper;
//
//	@Autowired
//	private CartMapper cartMapper;
//
//	@Test
//	public void 장바구니_등록() {
//		GoodsDTO goods = new GoodsDTO();
//		goods.setCode("GOODS-00002");
//		goods.setQuantity(12);
//
//		CartDTO cart = new CartDTO();
//		cart.setEmail("1song2devil3@naver.com");
//		cart.setGoods(goods);
//		try {
//			cartMapper.insertGoodsToCart(cart);
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void 장바구니_조회() {
//		List<CartDTO> cartList1 = cartMapper.selectGoodsListInCart("1song2devil3@naver.com");
//		System.out.println("=========================================");
//		System.out.println("=========================================");
//		System.out.println("=========================================");
//		List<CartDTO> cartList2 = cartMapper.selectGoodsListInCart("dyl6266@naver.com");
//	}
//
//	@Test
//	public void 장바구니_수정() {
//		GoodsDTO goods = new GoodsDTO();
//		goods.setCode("GOODS-00007");
//		goods.setQuantity(30);
//
//		CartDTO cart = new CartDTO();
//		cart.setEmail("dyl6266@naver.com");
//		cart.setGoods(goods);
//		cartMapper.updateGoodsInCart(cart);
//	}
//
//	@Test
//	public void 장바구니_삭제() {
//		List<String> codes = new ArrayList<>();
//		codes.add("GOODS-00002");
//		codes.add("GOODS-00001");
//		codes.add("GOODS-00010");
//
//		HashMap<String, Object> params = new HashMap<>();
//		params.put("email", "1song2devil3@naver.com");
//		params.put("codes", codes);
//		try {
//			cartMapper.deleteGoodsInCart(params);
//		} catch (DataAccessException e) {
//			e.printStackTrace();
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
////	@Test
////	public void testInsert() {
////		try {
////			for (int i = 0; i < 20; i++) {
////				GoodsDTO goods = new GoodsDTO();
////				goods.setCode(goodsMapper.generateGoodsCode(TableName.GOODS));
////				goods.setName("도영이의" + i + "번째 반바지");
////				goods.setName("도영이의" + i + "번째 옷입니당");
////				goods.setPrice(10000 + i);
////				goods.setQuantity(i);
////				goods.setStatus(Status.Y);
////				goods.setDeleteYn(YesNo.N);
////				goodsMapper.insertGoods(goods);
////			}
////		} catch (NullPointerException e) {
////			e.printStackTrace();
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
////
////	@Test
////	public void testSelect() {
////		GoodsDTO goods = goodsMapper.selectGoodsDetails("GOODS-00002");
////		System.out.println(goods.toString());
////	}
////
////	@Test
////	public void testUpdate() {
////		GoodsDTO goods = new GoodsDTO();
////		goods.setName("도영이의 반바지를 바꿨당");
////		goods.setDescription("도영이가 입던 옷을 바꿨땅.");
////		goods.setPrice(115000);
////		goods.setQuantity(33);
////		goods.setStatus(Status.N);
////		goods.setDeleteYn(YesNo.Y);
////		goods.setCode("GOODS-00004");
////
////		goodsMapper.updateGoods(goods);
////	}
////
////	@Test
////	public void testDelete() {
////		goodsMapper.deleteGoods("GOODS-00003");
////	}
////
////	@Test
////	public void testSelectList() {
////		List<GoodsDTO> goodsList = goodsMapper.selectGoodsList();
////		if (CollectionUtils.isEmpty(goodsList) == false) {
////			for (GoodsDTO goods : goodsList) {
////				System.out.println(goods.getName());
////				System.out.println("==============");
////			}
////		}
////	}
//
//}
