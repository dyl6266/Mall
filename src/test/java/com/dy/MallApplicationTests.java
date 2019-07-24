package com.dy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MallApplicationTests {

	@Test
	public void contextLoads() {
	}

	private static final String DRIVER = "com.mysql.jdbc.Driver";
	private static final String URL = "jdbc:mysql://106.10.40.91:3306/project?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
	private static final String USER = "doyoung";
	private static final String PW = "Ehdud##@*2436";

	@Test
	public void testDB() throws ClassNotFoundException {
		try {
			Class.forName(DRIVER);

			Connection con = DriverManager.getConnection(URL, USER, PW);
			System.out.println(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
