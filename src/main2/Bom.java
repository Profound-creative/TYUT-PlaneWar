package main2;

import java.awt.image.BufferedImage;
public class Bom extends FlyingObject{

	private int speed;
	
	Bom(int x,int y){
		super(16,30,x,y);
		speed=-3;
	}
	
	//�ӵ��ƶ�
	public void step() {
		y += speed;
	}
	
	public int getx() {
			return this.x;
	}
	public int gety() {
		return this.y;
	}
	
	private int index=0;
	public BufferedImage getImage() {
	
		
		if(isLife()){
			return Images.bom[0];
		}else if(isDead()){
			if(index==5){
				state = REMOVE;
				return null;
			}
			return Images.bom[index++];
		}
		return null;
	}

	//�ж��Ƿ�Խ��
	public boolean outBround() {
		return y<=World.HEIGHT/3-50 || y>=World.HEIGHT;
	}
	


}



