package main2;
import java.awt.Color;
 
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
 
public class test2
{
public static void main(String[] args)
{
JFrame jf = new JFrame("Demo");
String data[][] = new String[][]{{"1","2"},{"12","22"}};
String column[] = new String[]{"A","B"};
JTable table = new JTable(data,column);
JScrollPane jsp = new JScrollPane(table);
DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
 
renderer.setOpaque(false);//render��Ԫ�������
//��������������У�������Ⱦ������Ϊrenderer
for(int i = 0 ; i < column.length ; i ++)
{
	table.getColumn(column[i]).setCellRenderer(renderer);
}
table.setOpaque(false);//��table����Ϊ͸��
jsp.setOpaque(false);//��jsp���������Ϊ͸��
jsp.getViewport().setOpaque(false);//��jsp��viewport����Ϊ͸��
jf.add(jsp);
jf.getContentPane().setBackground(Color.blue);//��jf�����壩���������Ϊ��ɫ���Ա������Ƿ���Ϊ͸��
jf.setSize(400, 400);
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jf.setVisible(true);
}
}
