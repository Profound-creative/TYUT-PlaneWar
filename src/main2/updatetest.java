package main2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class updatetest {
	try{              //仅仅是连上数据库，至于SQL查询和释放部分由点击登录或注册后再实现
		  //1.注册JDBC驱动
			String driverName="com.mysql.cj.jdbc.Driver";  //采用java反射技术采用别人的接口来注册
			Class.forName(driverName);
			System.out.println("加载驱动成功！");
		  //2.获取连接对象
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL连接成功！");
			} catch(Exception e3){
				  e3.printStackTrace();
			  }
}	
	String sql = "select maxscore from playerinfo where id = '" + LoginFrameFinal.username  + "'"; 
	Connection dbConn = null;
	Statement stmt = ((java.sql.Connection) dbConn).createStatement();
	ResultSet res = stmt.executeQuery(sql);  //将查询的结果放在一个集合中
	int maxscore = res.getInt(sql);
	if(score > maxscore){
		String sql1 = "update playerinfo set maxscore = '" + score + "' where id = '" + LoginFrameFinal.username  + "'";	
		stmt = ((java.sql.Connection) dbConn).createStatement();
		stmt.executeUpdate(sql1);
}
