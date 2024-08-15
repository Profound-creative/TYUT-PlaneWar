package mains;
import java.awt.Image;

	public abstract class Flying_Object {

		int x,y;
		int width,height;
		Image image;
		
		public abstract void move();
		
		public boolean hitBy(Bullet bullet) {
			int x = bullet.x;									//�ӵ�x ����
			int y = bullet.y;
			
			return x > this.x && x < this.x + this.width 
					   && y>this.y && y<this.y+height;				//�ӵ��������ײ���ٽ�״̬
		}
	}
