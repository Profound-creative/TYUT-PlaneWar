import java.sql.*;   //����sql jar��

public class mysql{
	 public static void main(String[] args) throws Exception{
		 //1.ע��jdbc����
		  String driverName = "com.mysql.cj.jdbc.Driver";
		  try{
			  Class.forName(driverName);
			  System.out.println("���������ɹ���");
		  } catch(Exception e){
			  e.printStackTrace();
			  System.out.println("��������ʧ�ܣ�");
		  }
		//2.��ȡ���Ӷ���
		  String dbURL = "jdbc:mysql://127.0.0.1:3306?useSSL=false&serverTimezone=UTC";
		  String userName = "root";		
		  String userPwd = "123456";//���ݿ����루�Լ����õģ�
		  try{
			   dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			  System.out.println("MySQL���ӳɹ���");
		  } catch(Exception e){
			  e.printStackTrace();
			  System.out.print("MySQL����ʧ�ܣ�");
		  }	
		  Connection Conn=DriverManager.getConnection(dbURL,userName,userPwd);
		  Statement stat=Conn.createStatement();
	 }
}
