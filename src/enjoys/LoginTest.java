package enjoys;

import Utils.JDBCUtils;

import java.sql.*;
import java.util.Scanner;
 
public class LoginTest {
    public static void main(String[] args)throws Exception {
        //创建扫描器
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入账号：");
        String userName = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        boolean flag = login(userName,password);
        if(flag){
            System.out.println("恭喜"+userName+"登录成功");
        }else {
            System.out.println("账号或密码错误或不存在");
        }
    }
 
    private static boolean login(String userName, String password) throws Exception {
        //获取对象
        Connection connection = JDBCUtils.getConnection();
        //获取SQL运输器
        String sql = "select*from user where name=? and password=?";
        //得到预编译对象
        PreparedStatement pst = connection.prepareStatement(sql);
        //给？赋值
        pst.setString(1,userName);
        pst.setString(2,password);
        //执行SQL
        ResultSet rs = pst.executeQuery();
        return rs.next();
    }
}
