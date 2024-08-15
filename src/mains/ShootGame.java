package mains;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class ShootGame extends JPanel implements MouseMotionListener,MouseListener, KeyListener{
	
	public static Image background2;
	public static Image enemy1;
	public static Image enemy2;
	public static Image boss;
	public static Image me1;
	public static Image me2;
	public static Image supply_bomb;
	public static Image supply_fire;
	public static Image start;
	public static Image pause;
	public static Image paused;
	public static Image resume;
	public static Image resumed;
	public static Image life;
	public static Image enemy_destory;
	public static Image me_destroy;
	public static Image boss_destory;
	public static Image bullet;
	public static Image bullet1;//boss�������ӵ�
	public static Image gameover;
	public static Image down1;
	public static Image bomb;

	public static final int WIDTH = 490;
	public static final int HEIGHT = 737;
	
	public static final int START = 0;									//��ʼ״̬
	public static final int RUNNING = 1;								//����״̬
	public static final int PAUSE = 2;									//��ͣ״̬
	public static final int GAMEOVER = 3;								//����״̬
	public static int state = START;
	
	public Hero m = new Hero();
	Bullet[] bullets = {};												//�ӵ����ڶࣩ�������ӵ�����
	Flying_Object[] flyings = {};
	Bullet[] bullets1 = {};
	Bomb[] bombs = {};
	
	static {
		try {
			background2 = ImageIO.read(new File("images\\background.png"));
			enemy1 = ImageIO.read(new File("images\\enemy1.png"));
			enemy2 = ImageIO.read(new File("images\\enemy2.png"));
			supply_bomb = ImageIO.read(new File("images\\bomb_supply.png"));
			supply_fire=ImageIO.read(new File("images\\bullet_supply.png"));
			bullet = ImageIO.read(new File("images\\bullet1.png"));
			bullet1 = ImageIO.read(new File("images\\bullet2.png"));
			gameover = ImageIO.read(new File("images\\gameover.png"));
			me1 = ImageIO.read(new File("images\\me1.png"));
			me2 = ImageIO.read(new File("images\\me2.png"));
			pause = ImageIO.read(new File("images\\resume_nor.png"));
		//	resume = ImageIO.read(new File("images\\"))
			start = ImageIO.read(new File("images\\again.png"));
			boss=ImageIO.read(new File("images\\enemy3_n1.png"));
			down1=ImageIO.read(new File("images\\enemy1_down.png"));
			bomb=ImageIO.read(new File("images\\bomb.png"));
		} catch (IOException e) {
			e.toString();
		}
	}
	
	
	//�������˷���-------------------------------------------------------------------------------------------
	public Flying_Object nextOne() {
		Flying_Object f;									//С�л�����
		Random r = new Random();							//���������
		int random = r.nextInt(70);							//����[0,20)�����
		if(random >= 30) {
			f = new Enemy1();//�����Ϊ[10,30)  ����С�л�
		}
		else if(random>=15) {
			f = new Enemy2();									//�����Ϊ[5,10]  ����Boos��
		}
		else if(random>=10) {
			f = new Boss();									//�����Ϊ[5,10]  ����Boos��
		}
		else {
			f=new Supply();                                 //��������
		}
		return f;											//����С�л�
	}
		
		
	int flyingEnterIndex = 0;								//���˶����ʼ��Ϊ0
		
		//�Ѳ����ĵ�����ӵ����������з���------------------------------------------------------------------------
	public void enterAction() {
		flyingEnterIndex++;									//���˶����Լ�
		if(flyingEnterIndex % 30 == 0) {					//50 100...ÿ��50����ִ��һ�Σ����ڿ��Ƶ��˳����ٶ�
				
		//1���������˶���
		Flying_Object one = nextOne();						//�������˶��󣬸�ֵ�� One
			
		//2.�����˶�����ӵ�flyings����������
		flyings = Arrays.copyOf(flyings, flyings.length+1);	//����+1(����С�л�)
		flyings[flyings.length-1] = one;					//�Ѳ��ĵ���one��ֵ����ӣ������һ������Ԫ��
		
		}
	}
		
	//��Ϸ�и���������ƶ���ʽ--------------------------------------------------------------------------------
	 public void stepAction() {
	   
	  //���˵��ƶ������˴��ڵ��������У��������飩
	  for(int i=0; i<flyings.length; i++) {    //ͨ��ѭ��ʵ��ÿ�������ƶ�
	   flyings[i].move();        //����step������ʵ�ֵ��˵��ƶ�
	  }
	   
	  //�ӵ��ƶ����ӵ������ӵ������У������ӵ����飩
	  for(int i=0; i<bullets.length; i++) {    //ͨ��ѭ��ʵ��ÿ���ӵ��ƶ�
	   bullets[i].move();        //����step������ʵ���ӵ����ƶ�
	  }
	  for(int i=0; i<bullets1.length; i++) {    //ͨ��ѭ��ʵ��ÿ���ӵ��ƶ�
	   bullets1[i].move();        //����step������ʵ���ӵ����ƶ�
	  }
	  for(int i = 0;i<bombs.length;i++) {
		  bombs[i].move();
	  }
	  //Ӣ�ۻ����ƶ�
	  m.move();          //����step������ʵ��Ӣ�ۻ����ƶ�
	 }
		
		
	int shootIndex =0;
	//Ӣ�ۻ������ӵ�����-------------------------------------------------------------------------------------
	public void shootAction() {
		shootIndex++;
		if(shootIndex % 10 == 0) {											//10*10=100����
		Bullet[] bs = m.shoot();											//���������ӵ�
			
		//���ݣ���bs�����bullets�ӵ������У�
		bullets = Arrays.copyOf(bullets, bullets.length+bs.length);			//�ӵ�����+�����ɶ���
			
		//���ӵ�����������ӵ�������
		System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
		
	int score = 0;
	//�ӵ��������ײ-----------------------------------------------------------------------------------------
	public void hitAction() {
		  //���������ӵ�
		for(int i= 0; i<bullets.length; i++) {
		    
			Bullet b = bullets[i];          //��¼��ǰ�ӵ�
		    
		   //�������е���
		   
			for(int j = 0; j<flyings.length; j++) {
		     
				Flying_Object f = flyings[j];       //��¼��ǰ����
		    //�ж��Ƿ���ײ
//		    if(f instanceof Boos) {
//		         int k=3;
//		         if(f.hitBy(b))
//		         {
//		               k--;
//		         }
//		         if(k==0)
//		         {
//		        	 if(f.hitBy(b)) {
//		   		      
//		    		     //������ʧ
//		    		     
//		    		     //1����ǰ���������һ������Ԫ�ؽ���
//		    		     Flying_Object temp = flyings[j];
//		    		     flyings[j] = flyings[flyings.length-1];
//		    		     flyings[flyings.length-1] = temp;
//		    		      
//		    		     //2.����-1��������ˣ�
//		    		     flyings=Arrays.copyOf(flyings, flyings.length-1);
//		    		      
//		    		     //�ӵ���ʧ
//		    		      
//		    		     //1.��ǰ�ӵ������һ������Ԫ�ؽ���
//		    		     Bullet t =bullets[i];
//		    		     bullets[i] = bullets[bullets.length-1];
//		    		     bullets[bullets.length-1] = t;
//		    		      
//		    		     //2.����-1���ӵ���ʧ��
//		    		     bullets=Arrays.copyOf(bullets, bullets.length-1);
//		    		      
//		    		     //�õ��ӷֻ���,��Ҫ������С�л�����С�۷�
//		    		    }
//		    		   }
//		         }
//		    else {
				if(f.hitBy(b)) {
		      
		     //������ʧ
		     
		     //1����ǰ���������һ������Ԫ�ؽ���
					Flying_Object temp = flyings[j];
					flyings[j] = flyings[flyings.length-1];
					flyings[flyings.length-1] = temp;
		      
		     //2.����-1��������ˣ�
					flyings=Arrays.copyOf(flyings, flyings.length-1);
		      
		     //�ӵ���ʧ
		      
		     //1.��ǰ�ӵ������һ������Ԫ�ؽ���
					Bullet t =bullets[i];
					bullets[i] = bullets[bullets.length-1];
					bullets[bullets.length-1] = t;
		      
		     //2.����-1���ӵ���ʧ��
					bullets=Arrays.copyOf(bullets, bullets.length-1);
		      
		     //�õ��ӷֻ���,��Ҫ������С�л�����С�۷�
					if(f instanceof Enemy1) {
		         //��С�л�
						score += m.getScore();      //�ӽ�����
					}
					if(f instanceof Boss) {
		         //��С�л�
						score += m.BossgetScore();      //�ӽ�����
					}
					if(f instanceof Supply){
						Supply supply=(Supply)f;        //����ת��Supply
						int award = supply.getAwardType();
						switch(award) {
						case 0:
							m.addFire();      //���ӻ���
							break;
						case 1:
							m.addLife();        //��������ֵ
							break;
						case 2:
							m.bomb_numb++;
							bombs = Arrays.copyOf(bombs,bombs.length+1);
							//flyings=Arrays.copyOf(flyings, flyings.length-flyings.length);
						}
					}
				}
			}
		}
	}
	//Boss�����ӵ�
	 //ÿ��һ��ʱ��������˵���������ûһ���������鷢���ӵ���ӵ������ӵ�����
	 private int enemiesShootIndex = 0;
	 public void enemiesShoot(){
	  enemiesShootIndex++;
	  if(enemiesShootIndex%40==0){
	   //������������
	   for(int i=0;i<flyings.length;i++){
	    Flying_Object f = flyings[i];
	    //���˻������벻������ʱ�����ӵ�
	    if(f instanceof Boss){
	     //���˵���shoot���������ӵ�����
	     Bullet[] b = f.shoot();
	     //�����ɵ��ӵ�������ӵ������ӵ�����
	     bullets1 = Arrays.copyOf(bullets1,bullets1.length+b.length);
	     System.arraycopy(b, 0, bullets1,bullets1.length-b.length,b.length);
	    }
	   }
	  }
	 }

		
	//Ӣ�ۻ��������ײ����------------------------------------------------------------------------------------
	public void duangAction() {
		//�������е���
		for(int i = 0; i<flyings.length; i++) {
			if(m.duang(flyings[i])) {
					
				//������ʧ
				Flying_Object temp = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = temp;
				flyings=Arrays.copyOf(flyings, flyings.length-1);		//���ݣ���������
				//flyings[i].paintdown(new Graphics() {drawImage(down1,flyings[i].x,flyings[i].y,null)} );	
				//Ӣ�ۻ���������ֵ����������
				m.life--;
				m.Fire = 0;
			}
		}
	}
	 public void hitAction2(){
		  //������������
		  /*for(int i=0;i<flyings.length;i++){
		   Flying_Object f = flyings[i];
		   //���˺�Ӣ�ۻ��������ţ�Ӣ�ۻ�����hit���������ײ
		   if(f.isLife() && hero.isLife() && hero.hit(f)){
		    //��ײ��ı����״̬ΪDEAD
		    f.goDead();
		    //������ײ��Ч
		    hero_bullet.play();
		    //Ӣ�ۻ���������ջ���
		    hero.clearDoubleFire();
		    hero.subtractLife();
		   }
		  }*/
		  
		  //���������ӵ�����
		  for(int i=0;i<bullets1.length;i++){
		   Bullet b = bullets1[i];
		   //���Ӣ�ۻ���״̬ΪLIFE������hit�����ж��Ƿ�����ײ
		   if(b.hit((Flying_Object)m)){
		    
		     //�ӵ���ʧ
		         
		        //1.��ǰ�ӵ������һ������Ԫ�ؽ���
		        Bullet t =bullets1[i];
		        bullets1[i] = bullets1[bullets1.length-1];
		        bullets1[bullets1.length-1] = t;
		         
		        //2.����-1���ӵ���ʧ��
		        bullets1=Arrays.copyOf(bullets1, bullets1.length-1);
		         
		   
		    //������ײ��Ч
		    
		    //Ӣ�ۻ���������ջ���
		    m.life--;
		   // hero.clearDoubleFire();
		   }
		  }
		  
		 }	
	//���Ӣ�ۻ�����ֵ����------------------------------------------------------------------------------------
	public void checkGame() {
		if(m.life <= 0) {
		state = GAMEOVER;
		}
	}
		
		
	//��Ϸ�еĸ�����Ϊ����-----------------------------------------------------------------------------------
	 public void action() {
	   
	  //��Ӷ�ʱ����ÿ����ó���һ�Σ�
	  Timer timer = new Timer();//util����
	   
	  //����ָ�������ָ�����ӳٺ�ʼ�����ظ��Ĺ̶��ӳ�ִ��
	  timer.schedule(new TimerTask() {

	   @Override
	   public void run() {
	    // TODO Auto-generated method stub
	    if(state == RUNNING) {        //��������״ִ̬�в���
	     enterAction();          //�����볡
	     stepAction();          //���ö�����ƶ���ʽ
	     shootAction();          //Ӣ�ۻ������ӵ�
	     enemiesShoot();
	     hitAction();          //�ӵ��������ײ
	     //hitAction1() ;
	     hitAction2();
	     duangAction();          //Ӣ�ۻ������ײ
	     checkGame();          //���Ӣ�ۻ�����ֵ
	    }
	    repaint();           //���»��ƣ������µ�С�л��� 
	   }}, 10,10);
	 }
	  
		
		
	//��ͼ����-----------------------------------------------------------------------------------------------
	public void paint(Graphics g) {									//Jpanel����paint����
			
		super.paint(g);
		g.drawImage(background2,0,0,null);						//ͨ������ͼƬ���л滭�������꣨0,0����ʼ�滭
		//g.drawImage(airplane, 100, 100, null);����С�л��̶�λ��ͼ��
			
		//ͨ����������ʵ�ֶ�̬ʵʱ��ͼ
			
		//���Ƶ���
		paintEnemy(g);
		
		paintEnemy2(g);
		
		//����ը��
		paintBomb(g);
		
		//�����ӵ�
		paintBullet(g);
		paintBullet1(g);
			
		//����Ӣ�ۻ�
		paintme(g);
			
		//���Ʒ�ֵ
		paintScore(g);
			
		//����״̬ͼ
		paintState(g);
		
		
	}
	
	//����ը������--------------------------------------------------------------
	public void paintBomb(Graphics g) {
		for(int i = 0;i<bombs.length;i++) {
			g.drawImage(bombs[i].image,bombs[i].x,bombs[i].y,null);
		}
	}
		
		//����Ӣ�ۻ�����-----------------------------------------------------------------------------------------
	public void paintme(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(m.image, m.x, m.y, null);
			
	}
		
	
	//�����ӵ�����-------------------------------------------------------------------------------------------
	public void paintBullet(Graphics g) {
		// TODO Auto-generated method stub
		for(int i = 0; i<bullets.length; i++) {
			g.drawImage(bullets[i].image, bullets[i].x, bullets[i].y, null);
		}
	}
	public void paintBullet1(Graphics g) {
		// TODO Auto-generated method stub
		for(int i = 0; i<bullets1.length; i++) {
			g.drawImage(bullets1[i].image, bullets1[i].x, bullets1[i].y, null);
		}
	}
		
	//���Ƶ��˷���-------------------------------------------------------------------------------------------
	public  void paintEnemy(Graphics g) {
		// TODO Auto-generated method stub
		for(int i = 0; i<flyings.length; i++) {
			g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
			//if()
		}
	}
		
	public  void paintEnemy2(Graphics g) {
		// TODO Auto-generated method stub
		for(int i = 0; i<flyings.length; i++) {
			g.drawImage(flyings[i].image, flyings[i].x, flyings[i].y, null);
		}
	}
	//���Ʒ�������-------------------------------------------------------------------------------------------
	public  void paintScore(Graphics g) {
		g.setFont(new Font("����",Font.BOLD,25));					//��ɫ�����壬�ֺ�
		g.drawString("SCORE:"+score, 30, 30);						//����SCORE������������λ�ã�
		g.drawString("LIFE:"+m.life,30,60);						//����LIFE�� ����ֵ������λ�ã�
		g.drawString("DOUBLEFIRE:"+m.Fire, 30, 90);				//FIRE������ֵ������λ�ã�
		g.drawString("BOMB:"+m.bomb_numb,30,120);
	}

	//����״̬ͼ����-----------------------------------------------------------------------------------------
	public void paintState(Graphics g) {
		switch(state) {
			case START:
				g.drawImage(start, 100, 330, null);							//����startͼƬ�����꣨0,0��
				break;
			case GAMEOVER:
				g.drawImage(gameover, 100, 330, null);						//����gameoverͼƬ�����꣨0,0��
				break;
			case PAUSE:
				g.drawImage(pause, 200, 300 ,null);
		}
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frm = new JFrame("Aircraft-War");
		frm.setSize(WIDTH, HEIGHT);		//���ô����С�ͳ�ʼλ��
		frm.setLocationRelativeTo(null);	//���ô������
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//���ô���رշ�ʽ
		frm.setVisible(true);		//���ô���ɼ�
		
		ShootGame game = new ShootGame();
		frm.add(game);
		
		
		game.action();
		frm.addMouseListener(game);
		frm.addMouseMotionListener(game);
		frm.addKeyListener(game);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state == START) {
			state = RUNNING;
		}
		if(state == GAMEOVER) {
			state = START;											//���ؿ�ʼ״̬
			m = new Hero();
			flyings = new Flying_Object[] {};
			bullets = new Bullet[] {};
			score = 0;
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if(state == RUNNING) {
			m.x = e.getX() - m.width/2;
			m.y = e.getY() - m.height/2;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getExtendedKeyCode();
		switch(key) {
		case KeyEvent.VK_P:
			if(state == RUNNING) {
				state = PAUSE;
				break;
			}
		case KeyEvent.VK_R:
			if(state == PAUSE) {
				state = RUNNING;
				break;
			}
		case KeyEvent.VK_F:
			state = GAMEOVER;
			break;
		case KeyEvent.VK_SPACE:
			
				
													
			{Bomb[] bo = m.shoot2();											//���������ӵ�
					
				//���ݣ���bs�����bullets�ӵ������У�
				//bullets = Arrays.copyOf(bullets, bullets.length+bs.length);			//�ӵ�����+�����ɶ���
					
				//���ӵ�����������ӵ�������
				System.arraycopy(bo,0,bombs,bombs.length-bo.length,bo.length);}
				
			
			
		/*case KeyEvent.VK_LEFT:
			m.x -= 10;
			break;
		case KeyEvent.VK_UP:
			m.y -= 10;
			break;
		case KeyEvent.VK_RIGHT:
			m.x += 10;
			break;
		case KeyEvent.VK_DOWN:
			m.y += 10;*/
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}