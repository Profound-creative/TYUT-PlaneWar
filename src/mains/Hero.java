package mains;

import java.awt.Image;

public class Hero extends Flying_Object{
	
	int life;
	int Fire;
	Image[] images;
	
	public Hero() {
		image = ShootGame.me1;
		width = image.getWidth(null);
		height = image.getHeight(null);
		x = 200;
		y =550;
		life = 3;
		Fire = 0;
		//images[0] = Game.me1;
		//images[1] = Game.me2;
		images =new Image[] {ShootGame.me1, ShootGame.me2};
	}
	
	int index = 0;

	@Override
	public void move() {
		// TODO Auto-generated method stub
		image = images[index++/10%images.length];
	}
	
	//发射子弹的方法------------------------------------------------------------------------------------------
	public Bullet[]  shoot() {
		Bullet[] bs = {};										//子弹数组，初值为空
		if(Fire ==0) {									//单倍火力
			bs = new Bullet[1];
			bs[0] = new Bullet( x+width/2-4,y-14);				//一颗子弹的坐标计算
		}else {													//双倍火力
			bs =new Bullet[2];
			bs[0] = new Bullet(x+width/4-4,y-14);				//英雄机的宽度1/4-子弹宽度的一半
			bs[1] = new Bullet(x+width*3/4-4,y-14);
			Fire -= 2;
		}
			return bs;
	}
		
		  //获取得分------------------------------------------------------------------------------------------------
	public int getScore() {
		return 1;
	}
	public int BossgetScore()
	{
		return 2;
	}
		//增加火力------------------------------------------------------------------------------------------------
	public void addFire() {
		Fire +=40;	
	}
		
		//增加生命------------------------------------------------------------------------------------------------
	public void addLife() {
		life += 1;
	}
		//判断英雄机是否与敌人相撞方法-----------------------------------------------------------------------------
	public boolean duang(Flying_Object f) {
		int x = this.x;
		int y = this.y;											//获得英雄机当前位置
		int x1 = f.x - this.width;								//敌人x坐标-英雄机宽度
		int x2 = f.x + f.width;									//敌人x坐标+敌人宽度
		int y1 = f.y - this.height;								//敌人y坐标-英雄机高度
		int y2 = f.y + f.height;								//敌人y坐标+敌人高度	
		return x>x1 && x<x2&&y>y1 && y<y2;								//临介条件
	}
}
