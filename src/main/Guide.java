package main;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Guide {
	public static void main() {
		init();
	}
	
	public static void init() {
		JFrame frame = new JFrame("��Ϸָ��");
		ImageIcon imageIcon = new ImageIcon("images\\background.png");
		JTextArea textArea = new JTextArea() {
		Image image = imageIcon.getImage();
		Image grayImage = GrayFilter.createDisabledImage(image);
		{
			setOpaque(false);
		}
			public void paint(Graphics g) {
				g.drawImage(grayImage, 0, 0, this);
				super.paint(g);
			}
		};
		textArea.setColumns(20);
		String s = "1.��Ϸ��Ӣ�ۻ���С�л�����л���Boss����\n\r��ҩ����������������ը���������ɡ�\n\r"
				 + "2.Ӣ�ۻ����ƶ��������ơ���P�����߽���\n\r���Ƴ���Ϸ����֮�������ͣ��Ϸ����R����\n\r��"
				 + "�ɼ�����Ϸ����F������ֱ��ʹ��Ϸ������\n\r"
				 + "3.��Ӣ�ۻ������ӵ����з�����ʱ����������\n\r����һ��������ֵΪ��ʱ�������ﱻ�ݻ١�\n\r"
				 + "4.���л������ӵ�����Ӣ�ۻ�ʱ��Ӣ�ۻ�����\n\rֵ��һ�������ӳ���գ�������ֵΪ��ʱ��Ϸ\n\r��������������¼��\n\r"
				 + "5.Ӣ�ۻ��ɰ��ո��ͷ�ը����ʹ��Ļ���з���\n\r�����(����ע�⣬�����򲻵��Ѳ�Ҫʹ��ը\n\r������Ϊ����ܻ����㶪ʧ����)��\n\r"
				 + "6.�����ٷ�����ʱ����ý���������С�л�\n\r��1�֣����ٴ�л���3�֣�����boss����10�֣�\n\r����������������ֵ��1������ը��"
				 + "�������\n\rһ��ը�������ٻ��������������������\n\r"
				 + "7.ף����Ϸ��죡";
		textArea.setText(s);
		textArea.setFont(new Font("����", Font.BOLD, 20));  //���������ʽ
		textArea.setEditable(false);     //�����ı��򲻿ɱ༭
		JScrollPane scrollPane = new JScrollPane(textArea);
		//��ʹ��ˮƽ������
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Container content = frame.getContentPane();
		content.add(scrollPane, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setSize(430, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
