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
	public static final int WIDTH = 400; // ���ڵĿ�
	public static final int HEIGHT = 650; // ���ڵĸ�
	JLabel userLabel;   // �û�����
	JLabel pwdLabel;    // �û�����
	JTextField userText;  // �û����ı������
	JPasswordField pwdText;  // ���������
	JLabel registerLabel;  //ע�ᰴť
	JLabel loginLabel;    //��¼��ť
	static String username;  // �ı����������
	static String userpwd;  // ���������
	Connection dbConn = null;
	Statement stmt = null;
	
	public LoginFrame() {
		userLabel = new JLabel("�û���");
		userLabel.setBounds(80, 250, 120, 30);
		this.add(userLabel);
		
		pwdLabel = new JLabel("��   ��");
		pwdLabel.setBounds(80, 300, 120, 30);
		this.add(pwdLabel);
		// 1.����û��������
		userText = new JTextField(10);    //�û����10���ַ�
		userText.setBounds(140, 250, 160, 25);
		userText.setFocusable(true);      // ��ȡ��꽹��
		this.add(userText); 
		// 2.������������
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);
		// 3.��ӵ�¼��ť
		loginLabel = new JLabel(new ImageIcon("images\\login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		loginLabel.addMouseListener(this);     //Ϊ"��¼Label"ע�������
		this.add(loginLabel);
		// 4.���ע�ᰴť
		registerLabel = new JLabel(new ImageIcon("images\\register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		registerLabel.addMouseListener(this);  		//Ϊ"��¼Label"ע�������
		this.add(registerLabel);
        // 5.��������
		JLabel lbBg = new JLabel(new ImageIcon("images\\background.png"));
		lbBg.setBounds(0, 0, 400, 650);
		this.getContentPane().add(lbBg);
		// 6.��JFrame������
		this.setTitle("�ɻ���ս");
		this.setLayout(null);
		this.setSize(WIDTH, HEIGHT);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("images\\life.png").getImage());
		this.setResizable(false);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		final int WIDTH = 400; // ���ڵĿ�
		final int HEIGHT = 650; // ���ڵĸ�
		LoginFrame frame = new LoginFrame();
	}
	
	public boolean find(String uid,String upwd) throws SQLException{
		stmt = ((java.sql.Connection) dbConn).createStatement(); //��ȡ���ݿ�������� 
		String sql = "select id from playerinfo where id = '" + 
				      uid + "' and pwd = '" + upwd + "'";     //sql��ѯ���
		ResultSet res = stmt.executeQuery(sql);     //����ѯ�Ľ������һ��������
		if(res.next())        //��ѯ�Ľ���ǿգ���ѯ����¼�û���Ϣ
			return true;
		else
			return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try{              
			//ע��JDBC����
			String driverName="com.mysql.cj.jdbc.Driver";  
			Class.forName(driverName);    
			System.out.println("���������ɹ���");
		    //��ȡ���Ӷ���
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  
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
				if (username.length() == 0 || userpwd.length() == 0) 
					JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
				else if (find(username, userpwd)) {
					System.out.println("�û���¼�ɹ���");//�ڿ���̨�����¼�ɹ���Ϣ
					dispose();    //�رյ�ǰ����
					World world = new World();
					JFrame frame = new JFrame();
					frame.setResizable(false);    //�����С���ɸı�
					frame.add(world);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.setSize(490, 700);
					frame.setLocationRelativeTo(null); 
					frame.setVisible(true); 
					frame.addKeyListener(world);
					Thread t=new Thread(world);
					t.start();
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
			
			if (username.length() == 0 || userpwd.length() == 0) 
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			else{
				try {
					String sql1 = "select id from playerinfo where id = '"
				     + username + "'"; 
					stmt = ((java.sql.Connection) dbConn).createStatement();
					ResultSet res = stmt.executeQuery(sql1);
					if(res.next()) {   //�������Ϊ�գ�˵�����û���û�б�ע��
						JOptionPane.showMessageDialog(null, "���û����ѱ�ע��");
					} else {
						JOptionPane.showMessageDialog(null, "ע��ɹ�!");
						//�����û�����Ϣ���뵽���ݿ���
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
	