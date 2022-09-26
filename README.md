# FakeORM
## Pleaze add Connection file that name is "RDBConnection.java" in package sheng.orm.service
## EX:
```java
public class RDBConnection {
	private static final String driver = System.getProperty("driver", "com.mysql.cj.jdbc.Driver");
	private static final String url = System.getProperty("url", "jdbc:mysql://localhost:3306/<your_database_name>");
	private static final String userid = System.getProperty("userid", "<your_user_id>");
	private static final String pwd = System.getProperty("pwd", "<your_password>");
	
	static Connection getConnection(){
		// 不能寫 try(...)裡面，不然還沒傳給商業邏輯前就被殺掉了
		try {
			Class.forName(driver); //載入Driver類別
			
			try {//建立連線
				Connection con = DriverManager.getConnection(url,userid,pwd);
				return con;
			} catch (SQLException e) {
				throw new RuntimeException("建立連線失敗",e);
			} 
			
			
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("載入Driver類別失敗",e);
		}
	}
}
```
