package main;

import java.awt.image.BufferedImage;

/*
 * boss���࣬ʵ��boss�����ƶ��������ӵ�
 */

public class BossAirplane extends FlyingObject implements Enemy,Award {

	private int speed;
	
	BossAirplane(){
		super(150,113,50);
		speed = 1;
	}
	
	//����boss���ķ���
	public int getScore() {
		return 10;
	}

	//boss�����ƶ�
	public void step() {
	     x += speed;//һ�ε���һ���ٶ� 
	     if(x >World.WIDTH - width) {   //������Ļ���-x���С�۷�����ұ߽�
	    	 speed =-1;
	     }
	     if(x<=0) {
	    	 speed = 1;        //�ƶ�����߽磬�����ƶ�
	     }
	 }

	/*
	 * ��ȡboss����ͼƬ��״̬ΪLIFEʱ����boss��ͼƬ
	 * ״̬ΪDEADʱ����4�ű���ͼƬ��ȫ�����غ�״̬��ΪREMOVE������null
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
	
	//���������ӵ�
	public Bullet[] shoot(){
		Bullet[] res = new Bullet[2];
		res[0] = new Bullet(x+this.width/3,y+this.height+120,"down");
		res[1] = new Bullet(x+2*this.width/3,y+this.height+120,"down");
		return res;
	}

	//�ж��Ƿ�Խ��
	public boolean outBround() {
		return y>=World.HEIGHT;
	}

	//���ؽ�������
	@Override
	public int getType() {
		return (int)(Math.random()*2);
	}
}

