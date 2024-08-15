package mains;
import java.awt.Image;

	public abstract class Flying_Object {

		int x,y;
		int width,height;
		Image image;
		
		public abstract void move();
		
		public boolean hitBy(Bullet bullet) {
			int x = bullet.x;									//子弹x 坐标
			int y = bullet.y;
			
			return x > this.x && x < this.x + this.width 
					   && y>this.y && y<this.y+height;				//子弹与敌人相撞的临界状态
		}
	}
