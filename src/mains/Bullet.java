package mains;

public class Bullet extends Flying_Object{

	int ySpeed;												//�ӵ������ٶ�
	
	public Bullet(int x,int y) {
		image = ShootGame.bullet;							//�����ӵ�ͼƬ
		width = image.getWidth(null);						//����ӵ����
		height = image.getHeight(null);						//����ӵ��߶�
		this.x = x;											//�ӵ�ʵʱ���꣨��Ӣ�ۻ��任��
		this.y = y;
		ySpeed = 10;
		
	}


	//ʵ�ֳ������ж����ƶ��ķ���
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y -= ySpeed;
		
	}
}
