package mains;

public class Enemy1 extends Flying_Object{

	int ySpeed;												//向下速度
	
	public Enemy1() {
		image = ShootGame.enemy1;							//小敌机图片
		width = image.getWidth(null);						//小敌机宽度
		height = image.getHeight(null);						//小敌机高度
		x = (int) (Math.random() * ShootGame.WIDTH);			//小敌机出现x坐标，背景屏幕宽度
		y = -height;										//小敌机的y坐标
		ySpeed = 8;											//向下移动速度
	}
	/*public boolean hitPlane() {
		
	}*/
	//实现抽象父类中对象移动的方法-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y += ySpeed;										//一次递增一个速度	
	}
}
