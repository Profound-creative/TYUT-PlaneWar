package mains;

public class Enemy1 extends Flying_Object{

	int ySpeed;												//�����ٶ�
	
	public Enemy1() {
		image = ShootGame.enemy1;							//С�л�ͼƬ
		width = image.getWidth(null);						//С�л����
		height = image.getHeight(null);						//С�л��߶�
		x = (int) (Math.random() * ShootGame.WIDTH);			//С�л�����x���꣬������Ļ���
		y = -height;										//С�л���y����
		ySpeed = 8;											//�����ƶ��ٶ�
	}
	/*public boolean hitPlane() {
		
	}*/
	//ʵ�ֳ������ж����ƶ��ķ���-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y += ySpeed;										//һ�ε���һ���ٶ�	
	}
}
