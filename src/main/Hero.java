package main;


import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/*
 * 英雄机类，实现了英雄机随鼠标的坐标移动
 * 根据火力发射不同火力的子弹
 */
public class Hero extends FlyingObject {

	//火力值
	private int doubleFire;
	
	Hero(){
		super(46,66,180,500);
		life = 3;
		doubleFire = 0;
		bomb=0;
	}
	
	//随着鼠标的x，y移动
	public void moveTo(int x,int y){
		this.x = x - this.width/2;
		this.y = y - this.height/2;
	}
	
	//根据火力生成想上的子弹数组
	public Bullet[] shoot(){
		int xStep = this.width/4;
		int yStep = -20;
		if(doubleFire>0){
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
	
	 //发射爆炸
	 public Bomb[] shoot2() {
		 Bomb[] b = new Bomb[1];
		 b[0] = new Bomb(World.WIDTH/4,World.HEIGHT/3);
		 return b;
	 }
	 
	//返回生命数
	public int getLife(){
		return life;
	}
	//返回炸弹值
	public int getBomb() {
		return bomb;
	}
	//返回回火力值
	public int getDoubleFire(){
		return doubleFire;
	}
	
	//火力增加50
	public void addDoubleFire(){
		doubleFire += 50;
	}
	
	//生命数加一
	public void addLife(){
		life++;
	}
	
	//清空火力值
	public void clearDoubleFire(){
		doubleFire = 0;
	}
	//增加清屏炸弹
	public void addBomb() {
		bomb++;
	}
	//减少清屏炸弹
	public void subBomb() {
		bomb--;
	}
	public void step() {
	}

	//英雄机活着时每次返回不同的图片实现英雄机的喷火
	private int index = 0;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.heros[index++%2];
		}
		return null;
	}

	//英雄机不存在越界行为
	public boolean outBround() {
		return false;
	}
}

