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
 
renderer.setOpaque(false);//render单元格的属性
//遍历表格中所有列，将其渲染器设置为renderer
for(int i = 0 ; i < column.length ; i ++)
{
	table.getColumn(column[i]).setCellRenderer(renderer);
}
table.setOpaque(false);//将table设置为透明
jsp.setOpaque(false);//将jsp根面板设置为透明
jsp.getViewport().setOpaque(false);//将jsp的viewport设置为透明
jf.add(jsp);
jf.getContentPane().setBackground(Color.blue);//将jf（窗体）的面板设置为蓝色。以便区分是否表格为透明
jf.setSize(400, 400);
jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
jf.setVisible(true);
}
}
