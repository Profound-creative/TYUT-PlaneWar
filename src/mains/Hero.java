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
	
	//�����ӵ��ķ���------------------------------------------------------------------------------------------
	public Bullet[]  shoot() {
		Bullet[] bs = {};										//�ӵ����飬��ֵΪ��
		if(Fire ==0) {									//��������
			bs = new Bullet[1];
			bs[0] = new Bullet( x+width/2-4,y-14);				//һ���ӵ����������
		}else {													//˫������
			bs =new Bullet[2];
			bs[0] = new Bullet(x+width/4-4,y-14);				//Ӣ�ۻ��Ŀ��1/4-�ӵ���ȵ�һ��
			bs[1] = new Bullet(x+width*3/4-4,y-14);
			Fire -= 2;
		}
			return bs;
	}
		
		  //��ȡ�÷�------------------------------------------------------------------------------------------------
	public int getScore() {
		return 1;
	}
	public int BossgetScore()
	{
		return 2;
	}
		//���ӻ���------------------------------------------------------------------------------------------------
	public void addFire() {
		Fire +=40;	
	}
		
		//��������------------------------------------------------------------------------------------------------
	public void addLife() {
		life += 1;
	}
		//�ж�Ӣ�ۻ��Ƿ��������ײ����-----------------------------------------------------------------------------
	public boolean duang(Flying_Object f) {
		int x = this.x;
		int y = this.y;											//���Ӣ�ۻ���ǰλ��
		int x1 = f.x - this.width;								//����x����-Ӣ�ۻ����
		int x2 = f.x + f.width;									//����x����+���˿��
		int y1 = f.y - this.height;								//����y����-Ӣ�ۻ��߶�
		int y2 = f.y + f.height;								//����y����+���˸߶�	
		return x>x1 && x<x2&&y>y1 && y<y2;								//�ٽ�����
	}
}
