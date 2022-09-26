package sheng.orm.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

class RdbcTesting {

	@Test
	void test() {
		final String driver = System.getProperty("driver", "com.mysql.cj.jdbc.Driver");
		final String url = System.getProperty("url", "jdbc:mysql://localhost:3306/fake_orm");
		final String userid = System.getProperty("userid", "root");
		final String pwd = System.getProperty("pwd", "EighthGate9527");
		
		try {
			Class.forName(driver); //載入Driver類別
			
			try {//建立連線
				Connection con = DriverManager.getConnection(url,userid,pwd);
				System.out.println("RDBC連線成功");
				System.out.println(con);
				return;
			} catch (SQLException e) {
				throw new RuntimeException("建立連線失敗",e);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("載入Driver類別失敗",e);
		}
	}
}
