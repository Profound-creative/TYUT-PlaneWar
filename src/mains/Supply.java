package mains;

import java.util.Random;

public class Supply extends Flying_Object{

	int ySpeed;//向下速度
	int xSpeed;//左右速度
	int awardType;

	public Supply() {
		Random r = new Random();							//随机产生数
		int random = r.nextInt(10);
		if(random >= 8)
		{
			image = ShootGame.supply_bomb;				   //调用炸弹补给图片
			width = image.getWidth(null);				//获取图片宽度
			height = image.getHeight(null);				//获取图片高度
			x = (int) (Math.random() * ShootGame.WIDTH);		//炸弹x坐标（随机进入）
			y = -height;								//炸弹高度，设为窗体下方，方便提前看到小蜜蜂
			xSpeed = 1;									//炸弹x轴方向速度
			ySpeed = 1;									//炸弹y轴方向速度
			awardType = 2;		//得分(随机性)
		}
		else {
			image = ShootGame.supply_fire;				    //调用弹药炸弹图片
			width = image.getWidth(null);				//获取图片宽度
			height = image.getHeight(null);				//获取图片高度
			x = (int) (Math.random() * ShootGame.WIDTH);		//炸弹x坐标（随机进入）
			y = -height;								//炸弹高度，设为窗体下方，方便提前看到小蜜蜂
			xSpeed = 1;									//炸弹x轴方向速度
			ySpeed = 1;									//炸弹y轴方向速度
			awardType =(int) (Math.random()*2);
		}
	}

	//实现抽象父类中对象移动的方法-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y += ySpeed;
		x += xSpeed;
		if(x > ShootGame.WIDTH - width) {			//大于屏幕宽度-x宽度小蜜蜂大于右边界
			xSpeed =-1;
		}
		if(x<=0) {
			xSpeed = 1;								//炸弹移动至左边界，向右移动
		}
		
	}
	
	//获取得分方法--------------------------------------------------------------------------------------------
	public int getAwardType() {
		return awardType;
	}
}
