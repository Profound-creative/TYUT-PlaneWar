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
	public static Image bullet1;//boss产生的子弹
	public static Image gameover;
	public static Image down1;
	public static Image bomb;

	public static final int WIDTH = 490;
	public static final int HEIGHT = 737;
	
	public static final int START = 0;									//开始状态
	public static final int RUNNING = 1;								//运行状态
	public static final int PAUSE = 2;									//暂停状态
	public static final int GAMEOVER = 3;								//结束状态
	public static int state = START;
	
	public Hero m = new Hero();
	Bullet[] bullets = {};												//子弹（众多），定义子弹数组
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
	
	
	//产生敌人方法-------------------------------------------------------------------------------------------
	public Flying_Object nextOne() {
		Flying_Object f;									//小敌机类型
		Random r = new Random();							//随机产生数
		int random = r.nextInt(70);							//产生[0,20)随机数
		if(random >= 30) {
			f = new Enemy1();//随机数为[10,30)  产生小敌机
		}
		else if(random>=15) {
			f = new Enemy2();									//随机数为[5,10]  产生Boos机
		}
		else if(random>=10) {
			f = new Boss();									//随机数为[5,10]  产生Boos机
		}
		else {
			f=new Supply();                                 //产生补给
		}
		return f;											//返回小敌机
	}
		
		
	int flyingEnterIndex = 0;								//敌人对象初始化为0
		
		//把产生的敌人添加到敌人数组中方法------------------------------------------------------------------------
	public void enterAction() {
		flyingEnterIndex++;									//敌人对象自加
		if(flyingEnterIndex % 30 == 0) {					//50 100...每隔50毫秒执行一次，用于控制敌人出现速度
				
		//1、创建敌人对象
		Flying_Object one = nextOne();						//创建敌人对象，赋值给 One
			
		//2.将敌人对象添加到flyings敌人数组中
		flyings = Arrays.copyOf(flyings, flyings.length+1);	//扩容+1(增加小敌机)
		flyings[flyings.length-1] = one;					//把产的敌人one赋值（添加）给最后一个数组元素
		
		}
	}
		
	//游戏中各个对象的移动方式--------------------------------------------------------------------------------
	 public void stepAction() {
	   
	  //敌人的移动（敌人处于敌人数组中，调用数组）
	  for(int i=0; i<flyings.length; i++) {    //通过循环实现每个敌人移动
	   flyings[i].move();        //调用step方法，实现敌人的移动
	  }
	   
	  //子弹移动（子弹处于子弹数组中，调用子弹数组）
	  for(int i=0; i<bullets.length; i++) {    //通过循环实现每个子弹移动
	   bullets[i].move();        //调用step方法，实现子弹的移动
	  }
	  for(int i=0; i<bullets1.length; i++) {    //通过循环实现每个子弹移动
	   bullets1[i].move();        //调用step方法，实现子弹的移动
	  }
	  for(int i = 0;i<bombs.length;i++) {
		  bombs[i].move();
	  }
	  //英雄机的移动
	  m.move();          //调用step方法，实现英雄机的移动
	 }
		
		
	int shootIndex =0;
	//英雄机发射子弹方法-------------------------------------------------------------------------------------
	public void shootAction() {
		shootIndex++;
		if(shootIndex % 10 == 0) {											//10*10=100毫秒
		Bullet[] bs = m.shoot();											//用数组存放子弹
			
		//扩容（将bs存放至bullets子弹数组中）
		bullets = Arrays.copyOf(bullets, bullets.length+bs.length);			//子弹数组+新生成对象
			
		//将子弹对象添加至子弹数组中
		System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length);
		}
	}
		
	int score = 0;
	//子弹与敌人相撞-----------------------------------------------------------------------------------------
	public void hitAction() {
		  //遍历所有子弹
		for(int i= 0; i<bullets.length; i++) {
		    
			Bullet b = bullets[i];          //记录当前子弹
		    
		   //遍历所有敌人
		   
			for(int j = 0; j<flyings.length; j++) {
		     
				Flying_Object f = flyings[j];       //记录当前敌人
		    //判断是否相撞
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
//		    		     //敌人消失
//		    		     
//		    		     //1，当前敌人与最后一个数组元素交换
//		    		     Flying_Object temp = flyings[j];
//		    		     flyings[j] = flyings[flyings.length-1];
//		    		     flyings[flyings.length-1] = temp;
//		    		      
//		    		     //2.扩容-1（消灭敌人）
//		    		     flyings=Arrays.copyOf(flyings, flyings.length-1);
//		    		      
//		    		     //子弹消失
//		    		      
//		    		     //1.当前子弹与最后一个数组元素交换
//		    		     Bullet t =bullets[i];
//		    		     bullets[i] = bullets[bullets.length-1];
//		    		     bullets[bullets.length-1] = t;
//		    		      
//		    		     //2.扩容-1（子弹消失）
//		    		     bullets=Arrays.copyOf(bullets, bullets.length-1);
//		    		      
//		    		     //得到加分或奖励,需要区分是小敌机还是小蜜蜂
//		    		    }
//		    		   }
//		         }
//		    else {
				if(f.hitBy(b)) {
		      
		     //敌人消失
		     
		     //1，当前敌人与最后一个数组元素交换
					Flying_Object temp = flyings[j];
					flyings[j] = flyings[flyings.length-1];
					flyings[flyings.length-1] = temp;
		      
		     //2.扩容-1（消灭敌人）
					flyings=Arrays.copyOf(flyings, flyings.length-1);
		      
		     //子弹消失
		      
		     //1.当前子弹与最后一个数组元素交换
					Bullet t =bullets[i];
					bullets[i] = bullets[bullets.length-1];
					bullets[bullets.length-1] = t;
		      
		     //2.扩容-1（子弹消失）
					bullets=Arrays.copyOf(bullets, bullets.length-1);
		      
		     //得到加分或奖励,需要区分是小敌机还是小蜜蜂
					if(f instanceof Enemy1) {
		         //是小敌机
						score += m.getScore();      //加奖励分
					}
					if(f instanceof Boss) {
		         //是小敌机
						score += m.BossgetScore();      //加奖励分
					}
					if(f instanceof Supply){
						Supply supply=(Supply)f;        //向下转型Supply
						int award = supply.getAwardType();
						switch(award) {
						case 0:
							m.addFire();      //增加火力
							break;
						case 1:
							m.addLife();        //增加生命值
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
	//Boss发射子弹
	 //每隔一段时间遍历敌人敌人数组让没一个敌人数组发射子弹添加到敌人子弹数组
	 private int enemiesShootIndex = 0;
	 public void enemiesShoot(){
	  enemiesShootIndex++;
	  if(enemiesShootIndex%40==0){
	   //遍历敌人数组
	   for(int i=0;i<flyings.length;i++){
	    Flying_Object f = flyings[i];
	    //敌人还活着与不是侦察机时发射子弹
	    if(f instanceof Boss){
	     //敌人调用shoot方法生成子弹数组
	     Bullet[] b = f.shoot();
	     //将生成的子弹数组添加到敌人子弹数组
	     bullets1 = Arrays.copyOf(bullets1,bullets1.length+b.length);
	     System.arraycopy(b, 0, bullets1,bullets1.length-b.length,b.length);
	    }
	   }
	  }
	 }

		
	//英雄机与敌人相撞方法------------------------------------------------------------------------------------
	public void duangAction() {
		//遍历所有敌人
		for(int i = 0; i<flyings.length; i++) {
			if(m.duang(flyings[i])) {
					
				//敌人消失
				Flying_Object temp = flyings[i];
				flyings[i] = flyings[flyings.length-1];
				flyings[flyings.length-1] = temp;
				flyings=Arrays.copyOf(flyings, flyings.length-1);		//扩容，减掉敌人
				//flyings[i].paintdown(new Graphics() {drawImage(down1,flyings[i].x,flyings[i].y,null)} );	
				//英雄机减掉生命值，火力清零
				m.life--;
				m.Fire = 0;
			}
		}
	}
	 public void hitAction2(){
		  //遍历敌人数组
		  /*for(int i=0;i<flyings.length;i++){
		   Flying_Object f = flyings[i];
		   //敌人和英雄机都还活着，英雄机调用hit方法检测碰撞
		   if(f.isLife() && hero.isLife() && hero.hit(f)){
		    //碰撞后改变敌人状态为DEAD
		    f.goDead();
		    //播放碰撞音效
		    hero_bullet.play();
		    //英雄机减命和清空火力
		    hero.clearDoubleFire();
		    hero.subtractLife();
		   }
		  }*/
		  
		  //遍历敌人子弹数组
		  for(int i=0;i<bullets1.length;i++){
		   Bullet b = bullets1[i];
		   //如果英雄机的状态为LIFE，调用hit方法判断是否发生碰撞
		   if(b.hit((Flying_Object)m)){
		    
		     //子弹消失
		         
		        //1.当前子弹与最后一个数组元素交换
		        Bullet t =bullets1[i];
		        bullets1[i] = bullets1[bullets1.length-1];
		        bullets1[bullets1.length-1] = t;
		         
		        //2.扩容-1（子弹消失）
		        bullets1=Arrays.copyOf(bullets1, bullets1.length-1);
		         
		   
		    //播放碰撞音效
		    
		    //英雄机减命和清空火力
		    m.life--;
		   // hero.clearDoubleFire();
		   }
		  }
		  
		 }	
	//检测英雄机生命值方法------------------------------------------------------------------------------------
	public void checkGame() {
		if(m.life <= 0) {
		state = GAMEOVER;
		}
	}
		
		
	//游戏中的各种行为方法-----------------------------------------------------------------------------------
	 public void action() {
	   
	  //添加定时器（每隔多久出现一次）
	  Timer timer = new Timer();//util包下
	   
	  //安排指定任务从指定的延迟后开始进行重复的固定延迟执行
	  timer.schedule(new TimerTask() {

	   @Override
	   public void run() {
	    // TODO Auto-generated method stub
	    if(state == RUNNING) {        //处于运行状态执行操作
	     enterAction();          //敌人入场
	     stepAction();          //设置对象的移动方式
	     shootAction();          //英雄机发射子弹
	     enemiesShoot();
	     hitAction();          //子弹与敌人相撞
	     //hitAction1() ;
	     hitAction2();
	     duangAction();          //英雄机与敌人撞
	     checkGame();          //检测英雄机生命值
	    }
	    repaint();           //重新绘制（产生新的小敌机） 
	   }}, 10,10);
	 }
	  
		
		
	//绘图方法-----------------------------------------------------------------------------------------------
	public void paint(Graphics g) {									//Jpanel类下paint方法
			
		super.paint(g);
		g.drawImage(background2,0,0,null);						//通过背景图片进行绘画，从坐标（0,0）开始绘画
		//g.drawImage(airplane, 100, 100, null);绘制小敌机固定位置图像
			
		//通过方法调用实现动态实时绘图
			
		//绘制敌人
		paintEnemy(g);
		
		paintEnemy2(g);
		
		//绘制炸弹
		paintBomb(g);
		
		//绘制子弹
		paintBullet(g);
		paintBullet1(g);
			
		//绘制英雄机
		paintme(g);
			
		//绘制分值
		paintScore(g);
			
		//绘制状态图
		paintState(g);
		
		
	}
	
	//绘制炸弹方法--------------------------------------------------------------
	public void paintBomb(Graphics g) {
		for(int i = 0;i<bombs.length;i++) {
			g.drawImage(bombs[i].image,bombs[i].x,bombs[i].y,null);
		}
	}
		
		//绘制英雄机方法-----------------------------------------------------------------------------------------
	public void paintme(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(m.image, m.x, m.y, null);
			
	}
		
	
	//绘制子弹方法-------------------------------------------------------------------------------------------
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
		
	//绘制敌人方法-------------------------------------------------------------------------------------------
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
	//绘制分数方法-------------------------------------------------------------------------------------------
	public  void paintScore(Graphics g) {
		g.setFont(new Font("黑体",Font.BOLD,25));					//颜色，字体，字号
		g.drawString("SCORE:"+score, 30, 30);						//绘制SCORE：分数（坐标位置）
		g.drawString("LIFE:"+m.life,30,60);						//绘制LIFE： 生命值（坐标位置）
		g.drawString("DOUBLEFIRE:"+m.Fire, 30, 90);				//FIRE：火力值（坐标位置）
		g.drawString("BOMB:"+m.bomb_numb,30,120);
	}

	//绘制状态图方法-----------------------------------------------------------------------------------------
	public void paintState(Graphics g) {
		switch(state) {
			case START:
				g.drawImage(start, 100, 330, null);							//绘制start图片，坐标（0,0）
				break;
			case GAMEOVER:
				g.drawImage(gameover, 100, 330, null);						//绘制gameover图片，坐标（0,0）
				break;
			case PAUSE:
				g.drawImage(pause, 200, 300 ,null);
		}
	}
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JFrame frm = new JFrame("Aircraft-War");
		frm.setSize(WIDTH, HEIGHT);		//设置窗体大小和初始位置
		frm.setLocationRelativeTo(null);	//设置窗体居中
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		//设置窗体关闭方式
		frm.setVisible(true);		//设置窗体可见
		
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
			state = START;											//返回开始状态
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
			
				
													
			{Bomb[] bo = m.shoot2();											//用数组存放子弹
					
				//扩容（将bs存放至bullets子弹数组中）
				//bullets = Arrays.copyOf(bullets, bullets.length+bs.length);			//子弹数组+新生成对象
					
				//将子弹对象添加至子弹数组中
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