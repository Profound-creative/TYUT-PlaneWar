package main;

import java.awt.image.BufferedImage;

/*
 * boss机类，实现boss机的移动，生成子弹
 */

public class BossAirplane extends FlyingObject implements Enemy,Award {

	private int speed;
	
	BossAirplane(){
		super(150,113,50);
		speed = 1;
	}
	
	//返回boss机的分数
	public int getScore() {
		return 10;
	}

	//boss机的移动
	public void step() {
	     x += speed;//一次递增一个速度 
	     if(x >World.WIDTH - width) {   //大于屏幕宽度-x宽度小蜜蜂大于右边界
	    	 speed =-1;
	     }
	     if(x<=0) {
	    	 speed = 1;        //移动至左边界，向右移动
	     }
	 }

	/*
	 * 获取boss机的图片，状态为LIFE时返回boss机图片
	 * 状态为DEAD时返回4张爆破图片，全部返回后将状态改为REMOVE，返回null
	 */
	int index = 1;
	public BufferedImage getImage() {
		if(isLife()){
			return Images.bossairplanes[0];
		}else if(isDead()){
			if(index==5){
				state = REMOVE;
				return null;
			}
			return Images.bossairplanes[index++];
		}
		return null;
	}
	
	//生成两颗子弹
	public Bullet[] shoot(){
		Bullet[] res = new Bullet[2];
		res[0] = new Bullet(x+this.width/3,y+this.height+120,"down");
		res[1] = new Bullet(x+2*this.width/3,y+this.height+120,"down");
		return res;
	}

	//判断是否越界
	public boolean outBround() {
		return y>=World.HEIGHT;
	}

	//返回奖励类型
	@Override
	public int getType() {
		return (int)(Math.random()*2);
	}
}

