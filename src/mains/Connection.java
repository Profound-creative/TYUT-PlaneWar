package mains;

import java.sql.*;

public class Connection {
	public static void main(String[] args) {
		java.sql.Connection dbConn = null;
		Statement stmt = null;
		try{
		  //1.ע��JDBC����
			String driverName="com.mysql.cj.jdbc.Driver";  //����java���似�����ñ��˵Ľӿ���ע��
			Class.forName(driverName);
			System.out.println("���������ɹ���");
			//2.��ȡ���Ӷ���
			String dbURL = "jdbc:mysql://127.0.0.1:3306/test1";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL���ӳɹ���");
		  //3.��ȡ���ݿ��������
			stmt = dbConn.createStatement();
		  //4.ִ��SQL���
			String sql = "insert into playerinfo values('2020001321', 'haha',200)";
			//ר��ִ��DML���  ����ֵ��Ӱ�����ݿ��еļ�¼����
			int cnt = stmt.executeUpdate(sql); 
			System.out.println(cnt == 1 ? "����ɹ�" : "����ʧ��");
		  	} catch(Exception e){
			  e.printStackTrace();
		  	} finally { 
			  //6.�ͷ���Դ
			  //Ϊ�˱�֤��Դһ���ͷţ���finally�йر���Դ,Ҫ��ѭ��С�������ιر�,�ֱ����try catch
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

