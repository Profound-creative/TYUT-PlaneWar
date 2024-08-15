package main;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements MouseListener { 
	public static final int WIDTH = 400; // 窗口的宽
	public static final int HEIGHT = 650; // 窗口的高
	JLabel userLabel;   // 用户姓名
	JLabel pwdLabel;    // 用户密码
	JTextField userText;  // 用户名文本输入框
	JPasswordField pwdText;  // 密码输入框
	JLabel registerLabel;  //注册按钮
	JLabel loginLabel;    //登录按钮
	static String username;  // 文本输入框内容
	static String userpwd;  // 密码框内容
	Connection dbConn = null;
	Statement stmt = null;
	
	public LoginFrame() {
		userLabel = new JLabel("用户名");
		userLabel.setBounds(80, 250, 120, 30);
		this.add(userLabel);
		
		pwdLabel = new JLabel("密   码");
		pwdLabel.setBounds(80, 300, 120, 30);
		this.add(pwdLabel);
		// 1.添加用户名输入框
		userText = new JTextField(10);    //用户名最长10个字符
		userText.setBounds(140, 250, 160, 25);
		userText.setFocusable(true);      // 获取鼠标焦点
		this.add(userText); 
		// 2.添加密码输入框
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);
		// 3.添加登录按钮
		loginLabel = new JLabel(new ImageIcon("images\\login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		loginLabel.addMouseListener(this);     //为"登录Label"注册监听器
		this.add(loginLabel);
		// 4.添加注册按钮
		registerLabel = new JLabel(new ImageIcon("images\\register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		registerLabel.addMouseListener(this);  		//为"登录Label"注册监听器
		this.add(registerLabel);
        // 5.背景设置
		JLabel lbBg = new JLabel(new ImageIcon("images\\background.png"));
		lbBg.setBounds(0, 0, 400, 650);
		this.getContentPane().add(lbBg);
		// 6.对JFrame的设置
		this.setTitle("飞机大战");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("images\\life.png").getImage());
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		final int WIDTH = 400; // 窗口的宽
		final int HEIGHT = 650; // 窗口的高
		LoginFrame frame = new LoginFrame();
	}
	
	public boolean find(String uid,String upwd) throws SQLException{
		stmt = ((java.sql.Connection) dbConn).createStatement(); //获取数据库操作对象 
		String sql = "select id from playerinfo where id = '" + 
				      uid + "' and pwd = '" + upwd + "'";     //sql查询语句
		ResultSet res = stmt.executeQuery(sql);     //将查询的结果放在一个集合中
		if(res.next())        //查询的结果非空，查询出登录用户信息
			return true;
		else
			return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try{              
			//注册JDBC驱动
			String driverName="com.mysql.cj.jdbc.Driver";  
			Class.forName(driverName);    
			System.out.println("加载驱动成功！");
		    //获取连接对象
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  
			String userName = "root";		
			String userPwd = "123456";
			dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL连接成功！");
			} catch(Exception e3){
				  e3.printStackTrace();
			 }
		if(e.getSource() == loginLabel){     //登录
			username = userText.getText();   //文本框内容
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			try {
				if (username.length() == 0 || userpwd.length() == 0) 
					JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
				else if (find(username, userpwd)) {
					System.out.println("用户登录成功！");//在控制台输出登录成功信息
					dispose();    //关闭当前界面
					World world = new World();
					JFrame frame = new JFrame();
					frame.setResizable(false);    //窗体大小不可改变
					frame.add(world);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(490, 700);
					frame.setLocationRelativeTo(null); 
					frame.setVisible(true); 
					frame.addKeyListener(world);
					Thread t=new Thread(world);
					t.start();
				} else {
					JOptionPane.showMessageDialog(null, "用户名或密码错误");
				}
			} catch (HeadlessException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == registerLabel){ // 注册
			username = userText.getText();    
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			
			if (username.length() == 0 || userpwd.length() == 0) 
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			else{
				try {
					String sql1 = "select id from playerinfo where id = '"
				     + username + "'"; 
					stmt = ((java.sql.Connection) dbConn).createStatement();
					ResultSet res = stmt.executeQuery(sql1);
					if(res.next()) {   //如果集合为空，说明该用户名没有被注册
						JOptionPane.showMessageDialog(null, "该用户名已被注册");
					} else {
						JOptionPane.showMessageDialog(null, "注册成功!");
						//将该用户的信息插入到数据库中
						String sql2 = "insert into playerinfo values(0, '" + username + 
								"','" + userpwd + "')";
						stmt = ((java.sql.Connection) dbConn).createStatement();
						stmt.executeUpdate(sql2);
					  }
				} catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
	