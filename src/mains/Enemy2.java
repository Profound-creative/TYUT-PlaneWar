package mains;

public class Enemy2 extends Flying_Object{

	int ySpeed;												//�����ٶ�
	
	public Enemy2() {
		image = ShootGame.enemy2;							//С�л�ͼƬ
		width = image.getWidth(null);						//С�л����
		height = image.getHeight(null);						//С�л��߶�
		x = (int) (Math.random() * ShootGame.WIDTH);			//С�л�����x���꣬������Ļ���
		y = -height;										//С�л���y����
		ySpeed = 8;											//�����ƶ��ٶ�
	}

	//ʵ�ֳ������ж����ƶ��ķ���-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y += ySpeed;										//һ�ε���һ���ٶ�	
	}
}
