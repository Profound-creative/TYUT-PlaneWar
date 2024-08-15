package mains;

import java.util.Random;

public class Supply extends Flying_Object{

	int ySpeed;//�����ٶ�
	int xSpeed;//�����ٶ�
	int awardType;

	public Supply() {
		Random r = new Random();							//���������
		int random = r.nextInt(10);
		if(random >= 8)
		{
			image = ShootGame.supply_bomb;				   //����ը������ͼƬ
			width = image.getWidth(null);				//��ȡͼƬ���
			height = image.getHeight(null);				//��ȡͼƬ�߶�
			x = (int) (Math.random() * ShootGame.WIDTH);		//ը��x���꣨������룩
			y = -height;								//ը���߶ȣ���Ϊ�����·���������ǰ����С�۷�
			xSpeed = 1;									//ը��x�᷽���ٶ�
			ySpeed = 1;									//ը��y�᷽���ٶ�
			awardType = 2;		//�÷�(�����)
		}
		else {
			image = ShootGame.supply_fire;				    //���õ�ҩը��ͼƬ
			width = image.getWidth(null);				//��ȡͼƬ���
			height = image.getHeight(null);				//��ȡͼƬ�߶�
			x = (int) (Math.random() * ShootGame.WIDTH);		//ը��x���꣨������룩
			y = -height;								//ը���߶ȣ���Ϊ�����·���������ǰ����С�۷�
			xSpeed = 1;									//ը��x�᷽���ٶ�
			ySpeed = 1;									//ը��y�᷽���ٶ�
			awardType =(int) (Math.random()*2);
		}
	}

	//ʵ�ֳ������ж����ƶ��ķ���-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		y += ySpeed;
		x += xSpeed;
		if(x > ShootGame.WIDTH - width) {			//������Ļ���-x���С�۷�����ұ߽�
			xSpeed =-1;
		}
		if(x<=0) {
			xSpeed = 1;								//ը���ƶ�����߽磬�����ƶ�
		}
		
	}
	
	//��ȡ�÷ַ���--------------------------------------------------------------------------------------------
	public int getAwardType() {
		return awardType;
	}
}
