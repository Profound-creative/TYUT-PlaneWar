package main2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class updatetest {
	try{              //�������������ݿ⣬����SQL��ѯ���ͷŲ����ɵ����¼��ע�����ʵ��
		  //1.ע��JDBC����
			String driverName="com.mysql.cj.jdbc.Driver";  //����java���似�����ñ��˵Ľӿ���ע��
			Class.forName(driverName);
			System.out.println("���������ɹ���");
		  //2.��ȡ���Ӷ���
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL���ӳɹ���");
			} catch(Exception e3){
				  e3.printStackTrace();
			  }
}	
	String sql = "select maxscore from playerinfo where id = '" + LoginFrameFinal.username  + "'"; 
	Connection dbConn = null;
	Statement stmt = ((java.sql.Connection) dbConn).createStatement();
	ResultSet res = stmt.executeQuery(sql);  //����ѯ�Ľ������һ��������
	int maxscore = res.getInt(sql);
	if(score > maxscore){
		String sql1 = "update playerinfo set maxscore = '" + score + "' where id = '" + LoginFrameFinal.username  + "'";	
		stmt = ((java.sql.Connection) dbConn).createStatement();
		stmt.executeUpdate(sql1);
}
