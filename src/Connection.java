import java.sql.*;

import javax.swing.JOptionPane;

public class Connection {
	public static void main(String[] args) {
		java.sql.Connection dbConn = null;
		Statement stmt = null;
		String username = "hu";
		try{
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
		  //3.��ȡ���ݿ��������
			stmt = ((java.sql.Connection) dbConn).createStatement();
		  //4.ִ��SQL���
//			String sql1 = "select id from playerinfo where id = '" + username + "'" ; 
//			stmt = ((java.sql.Connection) dbConn).createStatement();
//			ResultSet res = stmt.executeQuery(sql1);
			String sql = "select maxscore from playerinfo where id = 'hu'"; 
			stmt = ((java.sql.Connection) dbConn).createStatement();
			ResultSet res = stmt.executeQuery(sql);  //����ѯ�Ľ������һ��������
			//Ҫ�Լ��ƶ�һ��res��λ�ò���
			res.next();
			int maxscore = res.getInt(1);
			System.out.println(maxscore);
//			System.out.println(maxscore);
//			if(res.next())    //�������Ϊ�գ�˵�����û���û�б�ע��
//				JOptionPane.showMessageDialog(null, "���û��ѱ�ע��!");
//			else
//				JOptionPane.showMessageDialog(null, "ע��ɹ�!");
//			String sql = "insert into playerinfo values(0, '2020001321', 'haha')";
//			//ר��ִ��DML���  ����ֵ��Ӱ�����ݿ��еļ�¼����
//			int cnt = stmt.executeUpdate(sql); 
//			System.out.println(cnt == 1 ? "����ɹ�" : "����ʧ��");
		  	} catch(Exception e){
			  e.printStackTrace();
		  	} finally {
			  //6.�ͷ���Դ
			  //Ϊ�˱�֤��Դһ���ͷţ���finally�йر���Դ,Ҫ��ѭ��С�������ιر�
			  
		  }
	}

}
