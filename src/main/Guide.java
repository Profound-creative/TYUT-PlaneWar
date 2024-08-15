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
		JFrame frame = new JFrame("游戏指南");
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
		String s = "1.游戏由英雄机、小敌机、大敌机、Boss机、\n\r弹药补给、生命补给、炸弹补给构成。\n\r"
				 + "2.英雄机的移动由鼠标控制、按P键或者将鼠\n\r标移出游戏窗体之外可以暂停游戏、按R健可\n\r以"
				 + "由继续游戏、按F键可以直接使游戏结束。\n\r"
				 + "3.当英雄机发射子弹击中飞行物时，飞行物生\n\r命减一，当生命值为零时，飞行物被摧毁。\n\r"
				 + "4.当敌机发射子弹击中英雄机时，英雄机生命\n\r值减一，火力加成清空；当生命值为零时游戏\n\r结束，分数被记录。\n\r"
				 + "5.英雄机可按空格释放炸弹，使屏幕所有飞行\n\r物清空(但是注意，不到万不得已不要使用炸\n\r弹，因为这可能会让你丢失分数)。\n\r"
				 + "6.当击毁飞行物时你会获得奖励：击毁小敌机\n\r加1分，击毁大敌机加3分，击毁boss机加10分，\n\r击毁生命补给生命值加1，击毁炸弹"
				 + "补给获得\n\r一颗炸弹，击毁火力补给获得三倍火力。\n\r"
				 + "7.祝您游戏愉快！";
		textArea.setText(s);
		textArea.setFont(new Font("楷体", Font.BOLD, 20));  //设置字体格式
		textArea.setEditable(false);     //设置文本域不可编辑
		JScrollPane scrollPane = new JScrollPane(textArea);
		//不使用水平滚动条
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		Container content = frame.getContentPane();
		content.add(scrollPane, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.setSize(430, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
