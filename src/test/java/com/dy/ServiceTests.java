//package com.dy;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import com.dy.domain.CartDTO;
//import com.dy.domain.GoodsDTO;
//import com.dy.mapper.CartMapper;
//import com.dy.service.CartService;
//import com.dy.service.GoodsService;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class ServiceTests {
//
//	@Autowired
//	private GoodsService service;
//
//	@Autowired
//	private CartService cartService;
//	
//	@Test
//	public void 중복추가_확인() {
//		String email = "dyl6266@nate.com";
//		String code = "GOODS-00017";
//		boolean result = cartService.checkForDuplicateGoodsInCart(email, code);
//		System.out.println( result );
//	}
//
//	@Test
//	public void 장바구니_추가() {
//		CartDTO cart = new CartDTO();
//		cart.setEmail("lhy097@naver.com");
//
//		GoodsDTO goods = new GoodsDTO();
//		goods.setCode("GOODS-00015");
//		goods.setQuantity(7);
////		cart.setGoods(goods);
//		cartService.addGoodsToCart(cart);
//	}
//
//	@Test
//	public void 수량_변경() {
//		CartDTO cart = new CartDTO();
//		cart.setEmail("lhy097@naver.com");
//
//		GoodsDTO goods = new GoodsDTO();
//		goods.setQuantity(13);
//		goods.setCode("GOODS-00015");
////		cart.setGoods(goods);
//
//		cartService.changeQuantityOfGoodsInCart(cart);
//	}
//
//	@Test
//	public void 장바구니_삭제() {
//		List<String> codes = new ArrayList<>();
//		codes.add("GOODS-00020");
//		codes.add("GOODS-00019");
//
//		String email = "dyl6266@nate.com";
//		cartService.removeGoodsInCart(email, codes);
//	}
//
//	@Test
//	public void 장바구니_목록() {
//		List<CartDTO> goodsList = cartService.getListOfGoodsInCart("dyl6266@nate.com");
//		System.out.println(goodsList);
//	}
//
////	@Test
////	public void testRegister() {
////		try {
////			for (int i = 1; i <= 20; i++) {
////				GoodsDTO goods = new GoodsDTO();
////				goods.setCode(mapper.generateGoodsCode(TableName.GOODS));
////				goods.setName("도영이의" + i + "번째 반바지");
////				goods.setDescription("도영이의" + i + "번째 반바지입니다.");
////				goods.setPrice(10000 + i);
////				goods.setQuantity(i);
////				goods.setStatus(Status.Y);
////				goods.setDeleteYn(YesNo.N);
////				mapper.insertGoods(goods);
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
////		GoodsDTO goods = service.getGoodsDetails("GOODS-00007");
////		System.out.println(goods.toString());
////	}
////
////	@Test
////	public void testDelete() {
////		service.deleteGoods("GOODS-00007");
////	}
////
////	@Test
////	public void testList() {
////		List<GoodsDTO> goodsList = service.getGoodsList();
////		for (GoodsDTO goods : goodsList) {
////			System.out.println("===============");
////			System.out.println(goods.getName());
////		}
////	}
//
//}
