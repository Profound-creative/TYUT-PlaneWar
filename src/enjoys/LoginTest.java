package enjoys;

import Utils.JDBCUtils;

import java.sql.*;
import java.util.Scanner;
 
public class LoginTest {
    public static void main(String[] args)throws Exception {
        //����ɨ����
        Scanner sc = new Scanner(System.in);
        System.out.println("�������˺ţ�");
        String userName = sc.nextLine();
        System.out.println("���������룺");
        String password = sc.nextLine();
        boolean flag = login(userName,password);
        if(flag){
            System.out.println("��ϲ"+userName+"��¼�ɹ�");
        }else {
            System.out.println("�˺Ż��������򲻴���");
        }
    }
 
    private static boolean login(String userName, String password) throws Exception {
        //��ȡ����
        Connection connection = JDBCUtils.getConnection();
        //��ȡSQL������
        String sql = "select*from user where name=? and password=?";
        //�õ�Ԥ�������
        PreparedStatement pst = connection.prepareStatement(sql);
        //������ֵ
        pst.setString(1,userName);
        pst.setString(2,password);
        //ִ��SQL
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
}
