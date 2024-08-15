package main2;


import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/*
 * Ӣ�ۻ��࣬ʵ����Ӣ�ۻ������������ƶ�
 * ���ݻ������䲻ͬ�������ӵ�
 */
public class Hero extends FlyingObject {

	//����ֵ
	private int doubleFire;
	
	Hero(){
		super(46,66,180,500);
		life = 3;
		doubleFire = 0;
		bomb=0;
	}
	
	//��������x��y�ƶ�
	public void moveTo(int x,int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	/*private ImageIcon hero1 = new ImageIcon("images/m1.png");
	private ImageIcon Hero=change(hero1,0.4);
	    //��������С
	private ImageIcon change(ImageIcon image, double i) {
	        int width=(int)(image.getIconWidth()*i);
	        int height=(int)(image.getIconHeight()*i);
	        Image img=image.getImage().getScaledInstance(width,height,Image.SCALE_DEFAULT);
	        ImageIcon image2=new ImageIcon((img));
	        return image2;
	    }*/
	//���ݻ����������ϵ��ӵ�����
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = -20;
		/*if(doubleFire>1000){
			Bullet[] b = new Bullet[5];
			for(int i=0;i<5;i++){
				b[i] = new Bullet(this.x+i*xStep,y + yStep,"up");
			}
			doubleFire -= 2;
			return b;
		}else */if(doubleFire>0){
			Bullet[] b = new Bullet[3];
			for(int i=0;i<3;i++){
				b[i] = new Bullet(this.x+i*3*xStep+5,y + yStep,"up");
			}
			doubleFire -= 2;
			return b;
		}else{
			Bullet[] b = new Bullet[1];
			b[0] = new Bullet(this.x+4*xStep+5,this.y+yStep,"up");
			return b;
		}
	}
	
	 //���䱬ը
	 public Bom[] shoot2() {
	  Bom[] b = new Bom[1];
	   b[0] = new Bom(World.WIDTH/4,World.HEIGHT/3);
	  return b;
	 }
	 
	//����������
	public int getLife(){
		return life;
	}
	//����ը��ֵ
	public int getBomb() {
		return bomb;
	}
	//���ػػ���ֵ
	public int getDoubleFire(){
		return doubleFire;
	}
	
	//��������50
	public void addDoubleFire(){
		doubleFire += 50;
	}
	
	//��������һ
	public void addLife(){
		life++;
	}
	
	//��ջ���ֵ
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	//��������ը��
	public void addBomb() {
		bomb++;
	}
	//��������ը��
	public void subBomb() {
		bomb--;
	}
	public void step() {
	}

	//Ӣ�ۻ�����ʱÿ�η��ز�ͬ��ͼƬʵ��Ӣ�ۻ������
	private int index = 0;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.heros[index++%2];
		}
		return null;
	}

	//Ӣ�ۻ�������Խ����Ϊ
	public boolean outBround() {
		return false;
	}
}

