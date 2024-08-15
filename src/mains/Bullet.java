package mains;

public class Bullet extends Flying_Object{

	int ySpeed;												//子弹向下速度
	
	public Bullet(int x,int y) {
		image = ShootGame.bullet;							//调用子弹图片
		width = image.getWidth(null);						//获得子弹宽度
		height = image.getHeight(null);						//获得子弹高度
		this.x = x;											//子弹实时坐标（随英雄机变换）
		this.y = y;
		ySpeed = 10;
		
	}


	//实现抽象父类中对象移动的方法
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y -= ySpeed;
		
	}
}
