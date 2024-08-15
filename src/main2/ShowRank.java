package main2;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;
//import java.util.*;


public class ShowRank extends JPanel{
	 
		static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
	    static final String DB_URL = "jdbc:mysql://localhost:3306/planewar?useSSL=false&serverTimezone=UTC";
	    static final String USER = "root";
	    static final String PASS = "123456";
	    public static void main(String[] args) {
	    	init();
	    }
	    public static void init() {
        try {
            // ����MySql����
            Class.forName(JDBC_DRIVER);
            // �������ݿ�����
            
            Connection conn = null;
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.println("���ݿ����ӳɹ���");
            // ����Statment����
            Statement stmt = conn.createStatement();
            // ��ȡ��ѯ�����
            ResultSet rs = stmt
                    .executeQuery("select id, maxscore FROM playerinfo order by maxscore desc");
            System.out.println("��ѯ�ɹ���");
            //��ȡ����
            int len  = 0;
            
            for (int i = 0; rs.next(); i++) len++;
            rs = stmt.executeQuery("select id, maxscore FROM playerinfo order by maxscore desc");
            
            String[][] data = new String[(len)][];
            for (int i = 0; rs.next(); i++) {

                data[i] = new String[]{
                		Integer.toString(i + 1),
                        rs.getString("id"),
                        rs.getString("maxscore"),
                      //  rs.getString("(����ֶ���)"),
                       // rs.getString("(����ֶ���)")
                };
            }

            JFrame frm;
            JTable jTable;

            String[] columnNames = {"����", "�û���", "��ߵ÷�"};
            jTable = new JTable(data, columnNames);
            jTable.setEnabled(false);        //�������а񲻿����޸�
            JTableHeader head = jTable.getTableHeader(); // �������������
            head.setFont(new Font("����", Font.PLAIN, 18));// ���ñ������
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setHorizontalAlignment(JLabel.CENTER);     //����������
            jTable.setDefaultRenderer(Object.class,renderer);
            jTable.setFocusable(false);
            jTable.setFont(new Font("����", Font.PLAIN, 15));    //�Ա�������ʽ�������
            JScrollPane jScrollPane = new JScrollPane(jTable);
            
//            renderer.setOpaque(true);   //render��Ԫ�������
//            for(int i = 0 ; i < columnNames.length ; i ++)
//            {
//            	jTable.getColumn(columnNames[i]).setCellRenderer(renderer);
//            }
//
//            jTable.setOpaque(false);//��table����Ϊ͸��
//            jScrollPane.setOpaque(false);//��jsp���������Ϊ͸��
//            jScrollPane.getViewport().setOpaque(false);//��jsp��viewport����Ϊ͸��
            
            
            
            frm = new JFrame("��Ϸ���а�");
//            frm.setLayout(null);
            frm.setSize(300, 300);
//    		JLabel lbBg = new JLabel(new ImageIcon("images\\background.png"));
//    		lbBg.setBounds(0, 0, 300, 300);
//    		frm.getContentPane().add(lbBg);
            
            frm.setLocationRelativeTo(null);
            frm.add(jScrollPane, BorderLayout.CENTER);

            frm.setVisible(true);
            //�ر���Դ
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

	
	
	
  
