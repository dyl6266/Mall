package com.dy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.dy.common.Const.Authority;
import com.dy.common.Const.Status;
import com.dy.common.Const.TableName;
import com.dy.domain.GoodsDTO;
import com.dy.domain.StockDTO;
import com.dy.mapper.GoodsMapper;
import com.dy.mapper.StockMapper;
import com.dy.service.CartService;
import com.dy.service.GoodsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTests {

	@Autowired
	private GoodsService service;

	@Autowired
	private CartService cartService;

	@Autowired
	private StockMapper stockMapper;

	@Autowired
	private GoodsMapper goodsMapper;

	@Autowired
	private PasswordEncoder encoder;

	@Test
	public void 회원가입() {
		String test = encoder.encode("1234");
		System.out.println(test);
//		MemberDTO member = new MemberDTO();
//		member.setEmail("dyl6266@naver.com");
//		member.setPassword(encoder.encode("flehdud##@*2436"));
//		member.setName("이도영");
//		member.setPhone("01033282436");
//		memberMapper.insertMember(member);
	}

	@Test
	public void 재고_조회() {
		StockDTO stock = stockMapper.selectStockDetails("GOODS-00001");
		System.out.println(stock.toString());
	}

	@Test
	public void 상품_등록() {
		GoodsDTO goods = new GoodsDTO();
		goods.setCode(goodsMapper.generateGoodsCode(TableName.GOODS));
		goods.setName("복숭아");
		goods.setDescription("아부지가 깎아 주신 복숭아");
		goods.setPrice(3500);

		StockDTO stock = new StockDTO();
		stock.setCode(goods.getCode());

		List<String> colors = new ArrayList<>();
		colors.add("화이트");
		colors.add("블랙");
		colors.add("옐로우");
		colors.add("블루");
		colors.add("그레이");
//		stock.setColor(colors);

		List<String> sizes = new ArrayList<>();
		sizes.add("210");
		sizes.add("220");
		sizes.add("230");
		sizes.add("270");
		sizes.add("280");
//		stock.setSize(sizes);

		Gson gson = new Gson();
		HashMap<String, Object> map = new HashMap<>();
		map.put("colors", colors);
		map.put("sizes", sizes);

		JsonObject json = new JsonObject();
		String jsonStr = gson.toJson(map);
		System.out.println(jsonStr);

		sizes = new ArrayList<>();
		HashMap<String, Object> result = gson.fromJson(jsonStr, HashMap.class);

		System.out.println(result.get("colors"));
	}

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

//	@Test
//	public void testRegister() {
//		try {
//			for (int i = 1; i <= 20; i++) {
//				GoodsDTO goods = new GoodsDTO();
//				goods.setCode(mapper.generateGoodsCode(TableName.GOODS));
//				goods.setName("도영이의" + i + "번째 반바지");
//				goods.setDescription("도영이의" + i + "번째 반바지입니다.");
//				goods.setPrice(10000 + i);
//				goods.setQuantity(i);
//				goods.setStatus(Status.Y);
//				goods.setDeleteYn(YesNo.N);
//				mapper.insertGoods(goods);
//			}
//		} catch (NullPointerException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test
//	public void testSelect() {
//		GoodsDTO goods = service.getGoodsDetails("GOODS-00007");
//		System.out.println(goods.toString());
//	}
//
//	@Test
//	public void testDelete() {
//		service.deleteGoods("GOODS-00007");
//	}
//
//	@Test
//	public void testList() {
//		List<GoodsDTO> goodsList = service.getGoodsList();
//		for (GoodsDTO goods : goodsList) {
//			System.out.println("===============");
//			System.out.println(goods.getName());
//		}
//	}

}
