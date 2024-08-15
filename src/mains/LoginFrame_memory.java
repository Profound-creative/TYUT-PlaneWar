package mains;
import java.awt.Container;
import java.awt.FlowLayout;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.FlowLayout;

public class LoginFrame_memory extends JFrame implements MouseListener { 
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
	java.sql.Connection dbConn = null;
	Statement stmt = null;
	
	public LoginFrame_memory() {
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
//		//3.添加进入后的标题
//		titlelabel = new JLabel("img/start.png"); 
//		titlelabel.setBounds(40, 80, 330, 101);
//		titlelabel.setEnabled(false);           //这两句话有什么用？
//		titlelabel.setEnabled(true);
//		this.add(titlelabel);
		//4.添加登录按钮
		loginLabel = new JLabel(new ImageIcon("img/login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		//为 登录Label 注册监听器
		loginLabel.addMouseListener(this);
		this.add(loginLabel);
		//5.添加注册按钮
		registerLabel = new JLabel(new ImageIcon("img/register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		//为 登录Label 注册监听器
		registerLabel.addMouseListener(this);
		this.add(registerLabel);
		//6.背景设置

//		BackgroundPanel background;
//		Container ct;
//		ct = this.getContentPane();
//		this.setLayout(null);
//		background = new BackgroundPanel((new ImageIcon("img/background.png")).getImage()); //参数是一个Image对象,
//		background.setBounds(0,0,400,300);  
//        ct.add(background);  
		
		//7.对JFrame的设置
		this.setTitle("飞机大战");
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(new ImageIcon("img/life.png").getImage());
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		LoginFrameFinal frame = new LoginFrameFinal();
	}
	
	public boolean find(String uid,String upwd) throws SQLException{
		  //3.获取数据库操作对象
		stmt = ((java.sql.Connection) dbConn).createStatement();
		String sql = "select id from playerinfo where id = '" + 
				uid + "' and pwd = '" + upwd + "'";      //sql查询语句
		ResultSet res = stmt.executeQuery(sql);  //将查询的结果放在一个集合中
		if(res.next())       //查询的结果非空，查询出登录用户信息
			return true;
		else
			return false;
	}

	public void mouseClicked(MouseEvent e) {
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
			if(e.getSource() == loginLabel){     //登录
			username = userText.getText();   //文本框内容
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			try {
				if (username.length() == 0 || userpwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "用户名或密码不能为空");
				} 
				else if (find(username, userpwd)) {
					System.out.println("用户登录成功！");   //在控制台输出登录成功信息
					dispose();// 关闭当前界面
//				//创建游戏窗口
//				JFrame frame = new JFrame("飞机大战"); // 窗口对象
//				//	ShootGame game = new ShootGame(Integer.parseInt(score)); // 面板
////				ShootGame game = null;
//				try {
//					game = new ShootGame(getHighestScore(), username);
//					System.out.print(username);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				
//				frame.add(game);      // 将面板添加到窗口中
//			    Image image;
//				image = new ImageIcon("img/life.png").getImage();  //设置图标
//				frame.setIconImage(image);
//					
//				frame.setSize(WIDTH, HEIGHT); // 设置窗口的宽和高
//				frame.setAlwaysOnTop(true); // 设置一直在最上面					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//				frame.setLocationRelativeTo(null);    // 设置窗口初始位置（居中)
//				frame.setVisible(true); // 设置窗体可见
//				
//				game.action();
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
			
			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "用户名和密码不能为空");
			}
			else{
				try {
					String sql1 = "select id from playerinfo where id = '" + username + "'"; 
					stmt = ((java.sql.Connection) dbConn).createStatement();
					ResultSet res = stmt.executeQuery(sql1);
					if(res.next()) {   //如果集合为空，说明该用户名没有被注册
						JOptionPane.showMessageDialog(null, "该用户名已被注册");
						//将该用户的信息插入到数据库中
					} else {
						JOptionPane.showMessageDialog(null, "注册成功!");
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
	
//	public void setBg() {
//		((JPanel)this.getContentPane()).setOpaque(false);
//		ImageIcon img = new ImageIcon("img/background.png");
//		JLabel background = new JLabel(img);
//		this.getLayeredPane().add(background, new Integer(Integer.MIN_VALUE)); 
//		background.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
//	}
}	
//	@SuppressWarnings("finally")
//	public void JDBC(){
//		try{              //仅仅是连上数据库，至于SQL查询和释放部分由点击登录或注册后再实现
//		  //1.注册JDBC驱动
//			String driverName="com.mysql.cj.jdbc.Driver";  //采用java反射技术采用别人的接口来注册
//			Class.forName(driverName);
//			System.out.println("加载驱动成功！");
//		  //2.获取连接对象
//			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
//			String userName = "root";		
//			String userPwd = "123456";
//			dbConn = (Connection) DriverManager.getConnection(dbURL, userName, userPwd);
//			System.out.println("MySQL连接成功！");
//			} catch(Exception e){
//				  e.printStackTrace();
//			  }
//	}

//class BackgroundPanel extends JPanel  
//{  
//    Image img;  
//    public BackgroundPanel(Image img)  
//    {  
//        this.img = img;  
//        this.setOpaque(true);                    //设置控件不透明,若是false,那么就是透明
//    }  
//    //Draw the background again,继承自Jpanle,是Swing控件需要继承实现的方法,而不是AWT中的Paint()
//    public void paintComponent(Graphics g)       //绘图类,详情可见博主的Java 下 java-Graphics 
//    {  
//        super.paintComponents(g);  
//        g.drawImage(img , 0, 0, this.getWidth(), this.getHeight(), this);  //绘制指定图像中当前可用的图像。图像的左上角位于该图形上下文坐标空间的 (x, y)。图像中的透明像素不影响该处已存在的像素
//
//    }  
//}





	