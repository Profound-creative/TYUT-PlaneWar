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
		// ��ȡ��꽹��
		userText.setFocusable(true);
		this.add(userText);
		// 2.������������
		pwdText = new JPasswordField();
		pwdText.setBounds(140, 300, 160, 25);
		pwdText.setFocusable(true);
		this.add(pwdText);
		//3.��ӽ����ı���
		titlelabel = new JLabel("img/start.png"); 
		titlelabel.setBounds(40, 80, 330, 101);
		titlelabel.setEnabled(false);           //�����仰��ʲô�ã�
		titlelabel.setEnabled(true);
		this.add(titlelabel);
		//4.��ӵ�¼��ť
		loginLabel = new JLabel(new ImageIcon("img/login.png"));
		loginLabel.setBounds(70, 375, 130, 35);
		loginLabel.setEnabled(false);
		loginLabel.setEnabled(true);
		//Ϊ ��¼Label ע�������
		loginLabel.addMouseListener(this);
		this.add(loginLabel);
		//5.���ע�ᰴť
		registerLabel = new JLabel(new ImageIcon("/img/register.png"));
		registerLabel.setBounds(175, 375, 130, 35);
		registerLabel.setEnabled(false);
		registerLabel.setEnabled(true);
		//Ϊ ��¼Label ע�������
		registerLabel.addMouseListener(this);
		this.add(registerLabel);
		//6.��������
		BackImage back = new BackImage();   
		back.setBounds(0, 0, WIDTH, HEIGHT);
		this.add(back);
		//7.��JFrame������
		this.setTitle("�ɻ���ս");
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
		frame.JDBC();     //�������������ݿ�
	}
	//��500����Ϣ
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
		if(e.getSource() == loginLabel){     //��¼
			username = userText.getText();   //�ı�������
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);

			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			} 
			if (find(username,userpwd)) {
				dispose();// �رյ�ǰ����
				//������Ϸ����
				JFrame frame = new JFrame("�ɻ���ս"); // ���ڶ���
				//	ShootGame game = new ShootGame(Integer.parseInt(score)); // ���
//				ShootGame game = null;
				try {
					game = new ShootGame(getHighestScore(), username);
					System.out.print(username);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
				frame.add(game);      // �������ӵ�������
			    Image image;
				image = new ImageIcon("img/life.png").getImage();  //����ͼ��
				frame.setIconImage(image);
					
				frame.setSize(WIDTH, HEIGHT); // ���ô��ڵĿ�͸�
				frame.setAlwaysOnTop(true); // ����һֱ��������					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				frame.setLocationRelativeTo(null);    // ���ô��ڳ�ʼλ�ã�����)
				frame.setVisible(true); // ���ô���ɼ�
				
				game.action();
			} else {
				JOptionPane.showMessageDialog(null, "�û������������");
			}
		}
		
		if(e.getSource() == registerLabel){ // ע��
			username = userText.getText();
			char[] chPWD = pwdText.getPassword();
			userpwd = new String(chPWD);
			
			if (username.length() == 0 || userpwd.length() == 0) {
				JOptionPane.showMessageDialog(null, "�û��������벻��Ϊ��");
			} else{
				id[i] = username;
				pwd[i++] = userpwd;
//				System.out.println(id[i] + pwd[i++]);
				JOptionPane.showMessageDialog(null, "ע��ɹ�!");
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
		try{              //�������������ݿ⣬����SQL��ѯ���ͷŲ����ɵ����¼��ע�����ʵ��
		  //1.ע��JDBC����
			String driverName="com.mysql.cj.jdbc.Driver";  //����java���似�����ñ��˵Ľӿ���ע��
			Class.forName(driverName);
			System.out.println("���������ɹ���");
		  //2.��ȡ���Ӷ���
			String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  //?useSSL=false&serverTimezone=UTC
			String userName = "root";		
			String userPwd = "123456";
			dbConn = (Connection) DriverManager.getConnection(dbURL, userName, userPwd);
			System.out.println("MySQL���ӳɹ���");
			} catch(Exception e){
				  e.printStackTrace();
			  }
	}
}
	