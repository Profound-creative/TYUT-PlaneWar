package mains;

import java.sql.*;

public class Connection {
	public static void main(String[] args) {
		java.sql.Connection dbConn = null;
		Statement stmt = null;
		try{
		  //1.注册JDBC驱动
			String driverName="com.mysql.cj.jdbc.Driver";  //采用java反射技术采用别人的接口来注册
			Class.forName(driverName);
			System.out.println("加载驱动成功！");
			//2.获取连接对象
			String dbURL = "jdbc:mysql://127.0.0.1:3306/test1";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL连接成功！");
		  //3.获取数据库操作对象
			stmt = dbConn.createStatement();
		  //4.执行SQL语句
			String sql = "insert into playerinfo values('2020001321', 'haha',200)";
			//专门执行DML语句  返回值是影响数据库中的记录条数
			int cnt = stmt.executeUpdate(sql); 
			System.out.println(cnt == 1 ? "保存成功" : "保存失败");
		  	} catch(Exception e){
			  e.printStackTrace();
		  	} finally { 
			  //6.释放资源
			  //为了保证资源一定释放，在finally中关闭资源,要遵循从小到大依次关闭,分别对其try catch
		  	     try {
		  	    	 if(stmt != null)
		  	    		 stmt.close();
		  	     } catch(Exception e) {
		  	    	 e.printStackTrace();
		  	     }
		  	     
		  	     try {
		  	    	 if(dbConn != null)
		  	    		dbConn.close();
		  	     } catch(Exception e) {
		  	    	 e.printStackTrace();
		  	     }
		  	  }
	}
}

