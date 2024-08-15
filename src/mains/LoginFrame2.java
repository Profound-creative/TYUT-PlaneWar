package mains;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginFrame extends JFrame implements MouseListener { 
	//490,737
	public static final int WIDTH = 400; // 窗口的宽
	public static final int HEIGHT = 654; // 窗口的高
	JLabel userLabel;  // 用户姓名
	JLabel pwdLabel;  // 用户密码
	JTextField userText;  // 用户名文本输入框
	JPasswordField pwdText;  // 密码输入框
	JLabel titlelabel;//"飞机大战"
	JLabel registerLabel;
	JLabel loginLabel;    //登录按钮
	String username;  // 文本输入框内容
	String userpwd;  // 密码框内容
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
		// 获取鼠标焦点
		userText.setFocusable(true);
		this.add(userText);
		// 2.添加密码输入框
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);
		//3.添加进入后的标题
		titlelabel = new JLabel("img/start.png"); 
		titlelabel.setBounds(40, 80, 330, 101);
		titlelabel.setEnabled(false);           //这两句话有什么用？
		titlelabel.setEnabled(true);
		this.add(titlelabel);
		//4.添加登录按钮
		loginLabel = new JLabel(new ImageIcon("img/login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		//为 登录Label 注册监听器
		loginLabel.addMouseListener(this);
		this.add(loginLabel);
		//5.添加注册按钮
		registerLabel = new JLabel(new ImageIcon("/img/register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		//为 登录Label 注册监听器
		registerLabel.addMouseListener(this);
		this.add(registerLabel);
		//6.背景设置
		BackImage back = new BackImage();   
		back.setBounds(0, 0, WIDTH, HEIGHT);
		this.add(back);
		//7.对JFrame的设置
		this.setTitle("飞机大战");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("img/life.png").getImage());
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		
		LoginFrame frame = new LoginFrame();
		frame.JDBC();     //仅仅是连上数据库
	}
	//存500个信息
	String[] id = new String[500]; 
	String[] pwd = new String[500];
	int i = 0;
	
	public boolean find(String uid,String upwd){
		for(int i = 0;i < id.length;i++)
			if(id[i].equals(uid) && pwd[i].equals(upwd))
				return true;
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource() == loginLabel){     //登录
			username = userText.getText();   //文本框内容
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);

			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
			} 
			if (find(username,userpwd)) {
				dispose();// 关闭当前界面
				//创建游戏窗口
				JFrame frame = new JFrame("飞机大战"); // 窗口对象
				//	ShootGame game = new ShootGame(Integer.parseInt(score)); // 面板
//				ShootGame game = null;
				try {
					game = new ShootGame(getHighestScore(), username);
					System.out.print(username);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				frame.add(game);      // 将面板添加到窗口中
			    Image image;
				image = new ImageIcon("img/life.png").getImage();  //设置图标
				frame.setIconImage(image);
					
				frame.setSize(WIDTH, HEIGHT); // 设置窗口的宽和高
				frame.setAlwaysOnTop(true); // 设置一直在最上面					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				frame.setLocationRelativeTo(null);    // 设置窗口初始位置（居中)
				frame.setVisible(true); // 设置窗体可见
				
				game.action();
			} else {
				JOptionPane.showMessageDialog(null, "用户名或密码错误");
			}
		}
		
		if(e.getSource() == registerLabel){ // 注册
			username = userText.getText();
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			
			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			} else{
				id[i] = username;
				pwd[i++] = userpwd;
//				System.out.println(id[i] + pwd[i++]);
				JOptionPane.showMessageDialog(null, "注册成功!");
			}
		}
	}

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
	
	@SuppressWarnings("finally")
	public void JDBC(){
		try{              //仅仅是连上数据库，至于SQL查询和释放部分由点击登录或注册后再实现
		  //1.注册JDBC驱动
			String driverName="com.mysql.cj.jdbc.Driver";  //采用java反射技术采用别人的接口来注册
			Class.forName(driverName);
			System.out.println("加载驱动成功！");
		  //2.获取连接对象
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = (Connection) DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL连接成功！");
			} catch(Exception e){
				  e.printStackTrace();
			  }
	}
}
	