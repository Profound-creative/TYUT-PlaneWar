package main2;
import javax.swing.*;
import java.awt.*;

public class Guide {
	public static void main(String[] args) {
		JFrame frm = new JFrame("сно╥ж╦до");
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final ImageIcon imageIcon = new ImageIcon("images\\background.png");
		JTextArea tf = new JTextArea(25, 30);
		tf.setEditable(false);
		tf.setText(null);
		
//		JLabel lbBg = new JLabel(new ImageIcon("images\\background.png"));
//		lbBg.setSize(340, 500);
//		frm.add(lbBg);
		JPanel p = new JPanel();
		p.add(tf);
		JScrollPane jsp = new JScrollPane();
		jsp.setViewportView(p);
		frm.setContentPane(jsp);
		frm.setSize(340, 500);
		frm.setLocationRelativeTo(null);
		frm.setVisible(true);
	}
}
