import java.sql.*;   //导入sql jar包

public class mysql{
	 public static void main(String[] args) throws Exception{
		 //1.注册jdbc驱动
		  String driverName = "com.mysql.cj.jdbc.Driver";
		  try{
			  Class.forName(driverName);
			  System.out.println("加载驱动成功！");
		  } catch(Exception e){
			  e.printStackTrace();
			  System.out.println("加载驱动失败！");
		  }
		//2.获取连接对象
		  String dbURL = "jdbc:mysql://127.0.0.1:3306?useSSL=false&serverTimezone=UTC";
		  String userName = "root";		
		  String userPwd = "123456";//数据库密码（自己设置的）
		  try{
			   dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			  System.out.println("MySQL连接成功！");
		  } catch(Exception e){
			  e.printStackTrace();
			  System.out.print("MySQL连接失败！");
		  }	
		  Connection Conn=DriverManager.getConnection(dbURL,userName,userPwd);
		  Statement stat=Conn.createStatement();
	 }
}
