package mains;

import java.awt.Image;

public class Boss extends Flying_Object{

	int life;
	int xSpeed;												//�����ٶ�
	Image[] images;
	
	public Boss() {
		image = ShootGame.boss;									//boss�л�ͼƬ
		width = image.getWidth(null);						//boss�л����
		height = image.getHeight(null);						//boss�л��߶�
		x = (int) (Math.random() * ShootGame.WIDTH);				//boss�л�����x���꣬������Ļ���
		y = -80;											//boss�л���y����										//�����ƶ��ٶ�
		xSpeed=3;
	}

	//ʵ�ֳ������ж����ƶ��ķ���-----------------------------------------------------------------------------
	@Override
	public void move() {
		// TODO Auto-generated method stub
		x += xSpeed;//һ�ε���һ���ٶ�	
		if(x > ShootGame.WIDTH - width) {			//������Ļ���-x���С�۷�����ұ߽�
			xSpeed =-1;
		}
		if(x<=0) {
			xSpeed = 1;								//ը���ƶ�����߽磬�����ƶ�
		}
	}
}
