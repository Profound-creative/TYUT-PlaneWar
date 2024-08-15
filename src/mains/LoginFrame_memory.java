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
	public static final int WIDTH = 400; // ���ڵĿ�
	public static final int HEIGHT = 654; // ���ڵĸ�
	JLabel userLabel;  // �û�����
	JLabel pwdLabel;  // �û�����
	JTextField userText;  // �û����ı������
	JPasswordField pwdText;  // ���������
	JLabel titlelabel;//"�ɻ���ս"
	JLabel registerLabel;
	JLabel loginLabel;    //��¼��ť
	String username;  // �ı����������
	String userpwd;  // ���������
	java.sql.Connection dbConn = null;
	Statement stmt = null;
	
	public LoginFrame_memory() {
		userLabel = new JLabel("�û���");
		userLabel.setBounds(80, 250, 120, 30);
		this.add(userLabel);
		
		pwdLabel = new JLabel("��   ��");
		pwdLabel.setBounds(80, 300, 120, 30);
		this.add(pwdLabel);
		// 1.����û��������
		userText = new JTextField(10);    //�û����10���ַ�
		userText.setBounds(140, 250, 160, 25);
		// ��ȡ��꽹��
		userText.setFocusable(true);
		this.add(userText);
		// 2.������������
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);
//		//3.��ӽ����ı���
//		titlelabel = new JLabel("img/start.png"); 
//		titlelabel.setBounds(40, 80, 330, 101);
//		titlelabel.setEnabled(false);           //�����仰��ʲô�ã�
//		titlelabel.setEnabled(true);
//		this.add(titlelabel);
		//4.��ӵ�¼��ť
		loginLabel = new JLabel(new ImageIcon("img/login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		//Ϊ ��¼Label ע�������
		loginLabel.addMouseListener(this);
		this.add(loginLabel);
		//5.���ע�ᰴť
		registerLabel = new JLabel(new ImageIcon("img/register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		//Ϊ ��¼Label ע�������
		registerLabel.addMouseListener(this);
		this.add(registerLabel);
		//6.��������

//		BackgroundPanel background;
//		Container ct;
//		ct = this.getContentPane();
//		this.setLayout(null);
//		background = new BackgroundPanel((new ImageIcon("img/background.png")).getImage()); //������һ��Image����,
//		background.setBounds(0,0,400,300);  
//        ct.add(background);  
		
		//7.��JFrame������
		this.setTitle("�ɻ���ս");
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
		  //3.��ȡ���ݿ��������
		stmt = ((java.sql.Connection) dbConn).createStatement();
		String sql = "select id from playerinfo where id = '" + 
				uid + "' and pwd = '" + upwd + "'";      //sql��ѯ���
		ResultSet res = stmt.executeQuery(sql);  //����ѯ�Ľ������һ��������
		if(res.next())       //��ѯ�Ľ���ǿգ���ѯ����¼�û���Ϣ
			return true;
		else
			return false;
	}

	public void mouseClicked(MouseEvent e) {
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
			if(e.getSource() == loginLabel){     //��¼
			username = userText.getText();   //�ı�������
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			try {
				if (username.length() == 0 || userpwd.length() == 0) {
					JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
				} 
				else if (find(username, userpwd)) {
					System.out.println("�û���¼�ɹ���");   //�ڿ���̨�����¼�ɹ���Ϣ
					dispose();// �رյ�ǰ����
//				//������Ϸ����
//				JFrame frame = new JFrame("�ɻ���ս"); // ���ڶ���
//				//	ShootGame game = new ShootGame(Integer.parseInt(score)); // ���
////				ShootGame game = null;
//				try {
//					game = new ShootGame(getHighestScore(), username);
//					System.out.print(username);
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
//				
//				frame.add(game);      // �������ӵ�������
//			    Image image;
//				image = new ImageIcon("img/life.png").getImage();  //����ͼ��
//				frame.setIconImage(image);
//					
//				frame.setSize(WIDTH, HEIGHT); // ���ô��ڵĿ�͸�
//				frame.setAlwaysOnTop(true); // ����һֱ��������					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
//				frame.setLocationRelativeTo(null);    // ���ô��ڳ�ʼλ�ã�����)
//				frame.setVisible(true); // ���ô���ɼ�
//				
//				game.action();
				} else {
					JOptionPane.showMessageDialog(null, "�û������������");
				}
			} catch (HeadlessException | SQLException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == registerLabel){ // ע��
			username = userText.getText();    
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			
			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			}
			else{
				try {
					String sql1 = "select id from playerinfo where id = '" + username + "'"; 
					stmt = ((java.sql.Connection) dbConn).createStatement();
					ResultSet res = stmt.executeQuery(sql1);
					if(res.next()) {   //�������Ϊ�գ�˵�����û���û�б�ע��
						JOptionPane.showMessageDialog(null, "���û����ѱ�ע��");
						//�����û�����Ϣ���뵽���ݿ���
					} else {
						JOptionPane.showMessageDialog(null, "ע��ɹ�!");
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
//		try{              //�������������ݿ⣬����SQL��ѯ���ͷŲ����ɵ����¼��ע�����ʵ��
//		  //1.ע��JDBC����
//			String driverName="com.mysql.cj.jdbc.Driver";  //����java���似�����ñ��˵Ľӿ���ע��
//			Class.forName(driverName);
//			System.out.println("���������ɹ���");
//		  //2.��ȡ���Ӷ���
//			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
//			String userName = "root";		
//			String userPwd = "123456";
//			dbConn = (Connection) DriverManager.getConnection(dbURL, userName, userPwd);
//			System.out.println("MySQL���ӳɹ���");
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
//        this.setOpaque(true);                    //���ÿؼ���͸��,����false,��ô����͸��
//    }  
//    //Draw the background again,�̳���Jpanle,��Swing�ؼ���Ҫ�̳�ʵ�ֵķ���,������AWT�е�Paint()
//    public void paintComponent(Graphics g)       //��ͼ��,����ɼ�������Java �� java-Graphics 
//    {  
//        super.paintComponents(g);  
//        g.drawImage(img , 0, 0, this.getWidth(), this.getHeight(), this);  //����ָ��ͼ���е�ǰ���õ�ͼ��ͼ������Ͻ�λ�ڸ�ͼ������������ռ�� (x, y)��ͼ���е�͸�����ز�Ӱ��ô��Ѵ��ڵ�����
//
//    }  
//}





	