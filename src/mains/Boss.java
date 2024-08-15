package mains;

import java.awt.Image;

public class Boss extends Flying_Object{

	int life;
	int xSpeed;												//向下速度
	Image[] images;
	
	public Boss() {
		image = ShootGame.boss;									//boss敌机图片
		width = image.getWidth(null);						//boss敌机宽度
		height = image.getHeight(null);						//boss敌机高度
		x = (int) (Math.random() * ShootGame.WIDTH);				//boss敌机出现x坐标，背景屏幕宽度
		y = -80;											//boss敌机的y坐标										//向下移动速度
		xSpeed=3;
	}

	//实现抽象父类中对象移动的方法-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		x += xSpeed;//一次递增一个速度	
		if(x > ShootGame.WIDTH - width) {			//大于屏幕宽度-x宽度小蜜蜂大于右边界
			xSpeed =-1;
		}
		if(x<=0) {
			xSpeed = 1;								//炸弹移动至左边界，向右移动
		}
	}
}
