package main2;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

//import javazoom.jl.decoder.JavaLayerException;

//import java.applet.Applet;
import java.io.File;
//import java.io.FileInputStream;
import java.net.MalformedURLException;
//import java.util.Scanner;
//import javax.sound.sampled.*;
import java.applet.*;

//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;

public class WorldTest extends JPanel implements Runnable,KeyListener {
	
	//java.applet.AudioClip all_bomb,enemy_bomb,bg,hero_bomb,hero_bullet;
	public static final int WIDTH = 490;
	public static final int HEIGHT = 700;
	private Sky sky = new Sky();
	private Hero hero = new Hero();
	private FlyingObject[] enemies = {};
	private Bullet[] heroBullets = {};
	private Bullet[] enemiesBullets = {};
	private Bom bom[]= {};
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage resume;
	public static BufferedImage gameover;
	public static BufferedImage rank;
	public static BufferedImage guide;
	public static BufferedImage begin;
	public static BufferedImage exit;
	static{
		start = Images.readImage("images\\begin.png");
		pause = Images.readImage("images\\pause_nor.png");
		resume = Images.readImage("images\\resume_nor.png");
		gameover = Images.readImage("images\\gameover.png");
		rank = Images.readImage("images\\rank.png");
		guide = Images.readImage("images\\guide.png");
		exit = Images.readImage("images\\exit.png");
	}
	
	/*public World(){
		try{
			//all_bomb = JApplet.newAudioClip(new File("music/all_bomb.wav").toURI().toURL());
			//enemy_bomb = JApplet.newAudioClip(new File("music/enemy_bomb.wav").toURI().toURL());
			//bg = JApplet.newAudioClip(new File("music/bg.wav").toURI().toURL());
			//hero_bomb = JApplet.newAudioClip(new File("music/hero_bomb.wav").toURI().toURL());
			//hero_bullet = JApplet.newAudioClip(new File("music/hero_bomb.wav").toURI().toURL());
			
		}catch(MalformedURLException e){
			e.printStackTrace();
		}
	}*/
	 

	public FlyingObject nextOne(){
		int n = (int)(Math.random()*100);
		if(n>95){
			return new Supply();
		}else if(n>70){
			return new Bee();
		}else if(n>40) {
			return new BigAirplane();
		}else{
			return new Airplane();
		}
	}
	
	private int enemiesIndex = 0;
	public void enterAction(){
		enemiesIndex++;
		if(enemiesIndex%30==0){
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1] = nextOne();
		}
		if(enemiesIndex%1000==0 && score>50){
			enemies = Arrays.copyOf(enemies, enemies.length+1);
			enemies[enemies.length-1] = new BossAirplane();
		}
	}
	
	private int enemiesShootIndex = 0;
	public void enemiesShoot(){
		enemiesShootIndex++;
		if(enemiesShootIndex%100==0){
			for(int i=0;i<enemies.length;i++){
				FlyingObject f = enemies[i];
				if(f.isLife() && !(f instanceof Bee)){
					Bullet[] b = f.shoot();
					enemiesBullets = Arrays.copyOf(enemiesBullets, enemiesBullets.length+b.length);
					System.arraycopy(b, 0, enemiesBullets, enemiesBullets.length-b.length,b.length);
				}
			}
		}
	}
	
	private int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex%10==0){
			Bullet[] b = hero.shoot();
			heroBullets = Arrays.copyOf(heroBullets, heroBullets.length+b.length);
			System.arraycopy(b, 0, heroBullets, heroBullets.length-b.length,b.length);
		}
	}
	
	 public void shootAction11(){
		   Bom[] b = hero.shoot2();
		   bom = Arrays.copyOf(bom, bom.length+b.length);
		   System.arraycopy(b, 0, bom, bom.length-b.length,b.length);
		   
		 }
	
	public void stepAction(){
		sky.step();
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].step();
		}
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].step();
		}
		 for(int i=0;i<bom.length;i++){
			  bom[i].step();
	    }
	}
	
	int score = 0;
	public void bangAction(){
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			for(int j=0;j<heroBullets.length;j++){
				Bullet b = heroBullets[j];
				if(b.isLife() && f.isLife() && b.hit(f)){
					if(f.life>1){
						f.subtractLife();
						b.goDead();
					}else{
						//enemy_bomb.play();
						f.goDead();
						b.goDead();
						if(f instanceof Enemy){
							Enemy e = (Enemy) f;
							score += e.getScore();
						}
						if(f instanceof Award){
							Award a = (Award) f;
							int type = a.getType();
							switch(type){
							case Award.DOUBLE_FIRE:
								hero.addDoubleFire();
								break;
							case Award.LIFE:
								hero.addLife();
								break;
							case Award.BOMB:
								hero.addBomb();
								break;
							}
						}
					}
				}
			}
		}
	}

	public void outOfBoundsAction(){
		int index = 0;
		FlyingObject[] fs = new FlyingObject[enemies.length];
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(!f.isRemove() && !f.outBround()){
				fs[index++] = f;
			}
		}
		enemies = Arrays.copyOf(fs, index);

		index = 0;
		Bullet[] bs = new Bullet[heroBullets.length];
		for(int i=0;i<heroBullets.length;i++){
			Bullet b = heroBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bs[index++] = b;
			}
		}
		heroBullets = Arrays.copyOf(bs, index);
		
		index = 0;
		Bullet[] ebs = new Bullet[enemiesBullets.length];
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(!b.isRemove() && !b.outBround()){
				ebs[index++] = b;
			}
		}
		enemiesBullets = Arrays.copyOf(ebs, index);
		
		index=0;
		  Bom[] bom1 = new Bom[bom.length];
		  for(int i=0;i<bom.length;i++){
		   Bom bom2 = bom[i];
		   if(!bom2.isRemove() && !bom2.outBround()){
		    bom1[index++] = bom2;
		   }
		  }
		  bom = Arrays.copyOf(bom1, index);
	}
	
	public void hitAction(){
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && hero.hit(f)){
				f.goDead();
				//hero_bullet.play();
				hero.clearDoubleFire();
				hero.subtractLife();
			}
		}
		
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(b.hit((FlyingObject)hero) && b.isLife()){
				b.goDead();
				//hero_bullet.play();
				hero.subtractLife();
				hero.clearDoubleFire();
			}
		}
	}
	
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			//hero_bomb.play();
			state = GAME_OVER;
		}
	}
	
	public void action(){
		
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){
					hero.x = e.getX() - hero.width;
					hero.y = e.getY() - hero.height/2;
				}
			}
			public void mouseClicked(MouseEvent e){
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=260&&e.getY()>=220) {
					if(state==START){
						System.out.println("游戏开始！！！");
						//bg.loop();
						state = RUNNING;
					}/*else if(state==GAME_OVER){
						//all_bomb.play();
						state = START;
						score = 0;
						enemies = new FlyingObject[0];
						heroBullets = new Bullet[0];
						enemiesBullets = new Bullet[0];
						hero = new Hero();
					}*/
				}
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=320&&e.getY()>=280) {
					
					if(state==START)
						new ShowRank().init();
					if(state==GAME_OVER){
						//all_bomb.play();
						state = START;
						score = 0;
						enemies = new FlyingObject[0];
						heroBullets = new Bullet[0];
						enemiesBullets = new Bullet[0];
						hero = new Hero();
					}
				}
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=380&&e.getY()>=340) {
					
				}
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=440&&e.getY()>=400) {
					System.out.println("游戏结束！！！");
					System.exit(0);
				}
			}
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
					//bg.stop();
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					//bg.loop();
					state = RUNNING;
				}
			}
		};
		
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				if(state==RUNNING){
					enterAction();
					enemiesShoot();
					shootAction();
					stepAction();
					bangAction();
					outOfBoundsAction();
					hitAction();
					checkGameOverAction();
				}
				repaint();
			}
		}, intervel,intervel);
	}
	
	@Override
	 public void keyPressed(KeyEvent e) {
	  if(state==RUNNING){
	   switch(e.getKeyCode()){
	   case KeyEvent.VK_SPACE:
	    //all_bomb.play();
		if(hero.getBomb()>0) {
	    this.shootAction11();
	    for(int i=0;i<bom.length;i++) {
	     Bom b=bom[i];
	     int bomx=bom[i].getx();
	     int bomy=bom[i].gety();
	     
	 
	      enemies = new FlyingObject[0];
	      enemiesBullets = new Bullet[0];     
	      hero.subBomb();
	    }
	    }
	    

	   /* enemies = new FlyingObject[0];
	    enemiesBullets = new Bullet[0];
	    
	    
	    hero.subBomb();*/
	    break;
	   }
	  }
	  
	 }
	
	public void paint(Graphics g){
		sky.paintObject(g);
		hero.paintObject(g);
		

		for(int i=0;i<bom.length;i++){
		   bom[i].paintObject(g);
		}
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintObject(g);
		}
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].paintObject(g);
		}
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].paintObject(g);
		}
		
		g.setFont(new Font("TimesRoman",Font.ITALIC,25));
		g.drawString("SCORE:"+score, 10, 25);
		g.drawString("LIFE:"+hero.getLife(), 10, 55);
		g.drawString("DOUBLEFIRE:"+hero.getDoubleFire(), 10, 115);
		g.drawString("BOMB:"+hero.getBomb(), 10,85);
		//g.drawImage(pause,400,25,null);
		
		
		switch(state){
		case START:
			g.drawImage(start, 90, 220, null);
			g.drawImage(rank,90,280,null);
			g.drawImage(guide,90,340,null);
			g.drawImage(exit,90,400,null);
			break;
		case PAUSE:
			g.drawImage(resume, 200, 285, null);
			break;
		case GAME_OVER:
			g.drawImage(gameover, 90, 280,null);
			break;
		}
		
	}
	
	@Override
	public void run() {
		action();
	}
	
	public static void main(String[] args) {
		WorldTest world = new WorldTest();
		JFrame frame = new JFrame();
		
		frame.add(world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		frame.addKeyListener(world);
		Thread t=new Thread(world);
		t.start();
		//world.action();
		//System.out.println("游戏结束！！！");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getExtendedKeyCode();
		switch(key) {
		case KeyEvent.VK_P:
			if(state == RUNNING) {
				state = PAUSE;
			}	
			break;
		case KeyEvent.VK_R:
			if(state == PAUSE) {
				state = RUNNING;	
			}	
			break;
		case KeyEvent.VK_F:
			state = GAME_OVER;
			break;
		}
	}
	
}
