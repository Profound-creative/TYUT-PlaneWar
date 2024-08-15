package main;
//��Ϸ����
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
//��Ӵ���
import javax.swing.JFrame;
import javax.swing.JPanel;

import main2.GuideFinal;
import main2.LoginFrameFinal;
import main2.ShowRank;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
//�������
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class World extends JPanel implements Runnable,KeyListener {
	
	public static final int WIDTH = 490;	//������Ϸ�����
	public static final int HEIGHT = 700;	//������Ϸ�����
	private Sky sky = new Sky();	//����һ�������
	private Hero hero = new Hero();		//����Ӣ�ۻ�����
	private FlyingObject[] enemies = {};	//�������������鴢�������
	private Bullet[] heroBullets = {};		//����Ӣ�ۻ��ӵ����鴢���ӵ�
	private Bullet[] enemiesBullets = {};	//�����л��ӵ����鴢���ӵ�
	private Bomb bom[] = {};	//����ը�����鴢��ը��
	private BossAirplane boss[] = {};	//boss������������boss
	private Bullet[] bossBullets = {};	//����boss���ӵ����鴢���ӵ�
	//������Ϸ״̬����
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	//����ͼƬ
	public static BufferedImage start;
	public static BufferedImage pause;
	public static BufferedImage resume;
	public static BufferedImage gameover;
	public static BufferedImage rank;
	public static BufferedImage guide;
	public static BufferedImage begin;
	public static BufferedImage exit;
	public static BufferedImage bloodImage;
	static{
		//��ȡimages�ļ����µ�ͼƬ
		start = Images.readImage("images\\begin.png");
		pause = Images.readImage("images\\pause_nor.png");
		resume = Images.readImage("images\\resume_nor.png");
		gameover = Images.readImage("images\\gameover.png");
		rank = Images.readImage("images\\rank.png");
		guide = Images.readImage("images\\guide.png");
		exit = Images.readImage("images\\exit.png");
		bloodImage = Images.readImage("images\\blood.png");
	}
	//����������ֵ�Ƶ��
	public FlyingObject nextOne(){
		int n = (int)(Math.random()*100);
		if(n>95){
			return new Bomb_supply();	//ը��
		}else if(n>90){
			return new Life_supply();	//����
		}else if(n>80){
			return new Fire_supply();	//����
		}else if(n>50) {
			return new BigAirplane();	//��л�
		}else{
			return new Airplane();	//С�л�
		}
	}
	
	private int enemiesIndex = 0;   //����һ���������������������Ʋ�ͬ�ַ�����ĳ���ʱ�����
	public void enterAction(){
		enemiesIndex++;		//����������Լ�	
		if(enemiesIndex%30==0){		//���ÿ30msִ��һ��
			enemies = Arrays.copyOf(enemies, enemies.length+1);	//����+1(���ӷ�����)
			enemies[enemies.length-1] = nextOne();	//�Ѳ��ĵ���one��ֵ����ӣ������һ������Ԫ��
		}
		if(enemiesIndex%1000==0 && score>50){	//�ж�boss������
			boss = Arrays.copyOf(boss, boss.length+1);
			boss[boss.length-1] = new BossAirplane();
			//System.out.println(enemiesIndex);
		}
	}
	
	private int enemiesShootIndex = 0;
	public void enemiesShoot(){		//���Ƶл��ӵ�
		enemiesShootIndex++;
		if(enemiesShootIndex%100==0){	//���Ƶл��ӵ����ֿ���
			for(int i=0;i<enemies.length;i++){
				FlyingObject f = enemies[i];
				if(f.isLife() && !(f instanceof Fire_supply)){	//�жϵл��Ƿ����ӵ�
					Bullet[] b = f.shoot();
					enemiesBullets = Arrays.copyOf(enemiesBullets, enemiesBullets.length+b.length);
					System.arraycopy(b, 0, enemiesBullets, enemiesBullets.length-b.length,b.length);//���ӵ�����������ӵ�������
				}
			}
			for(int i=0;i<boss.length;i++) {
				BossAirplane boss1=boss[i];
				if(boss1.isLife()){		//����boss�������ӵ�
					Bullet[] b = boss1.shoot();
					bossBullets = Arrays.copyOf(bossBullets, bossBullets.length+b.length);
					System.arraycopy(b, 0, bossBullets, bossBullets.length-b.length,b.length);//���ӵ�����������ӵ�������
				}
			}
		}
	}
	
	private int shootIndex = 0;
	public void shootAction(){	//����Ӣ�ۻ������ӵ�
		shootIndex++;
		if(shootIndex%10==0){	//�����ӵ������ٶ�
			Bullet[] b = hero.shoot();
			heroBullets = Arrays.copyOf(heroBullets, heroBullets.length+b.length);
			System.arraycopy(b, 0, heroBullets, heroBullets.length-b.length,b.length);//���ӵ�����������ӵ�������
		}
	}
	
	 public void shootAction11(){	//����Ӣ�ۻ�����ը��
		   Bomb[] b = hero.shoot2();
		   bom = Arrays.copyOf(bom, bom.length+b.length);
		   System.arraycopy(b, 0, bom, bom.length-b.length,b.length);//��ը�����������ը��������   
	}
	//���Ƹ��������ƶ�-------------------------------------------------------------------------------------------
	public void stepAction(){
		sky.step();		//��ձ����ƶ�
		for(int i=0;i<enemies.length;i++){
			enemies[i].step();
		}
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].step();
		}
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].step();
		}
		for(int i=0;i<bossBullets.length;i++){
			bossBullets[i].step();
		}
		for(int i=0;i<boss.length;i++){
			boss[i].step();
		}
		for(int i=0;i<bom.length;i++){
			bom[i].step();
	    }
	}
	
	int score = 0;	//��Ϸ�÷�
	
	//�ӵ��������ײ---------------------------------------------------------------------------------------------
	public void bangAction(){
		for(int i=0;i<enemies.length;i++){		//�������з�����
			FlyingObject f = enemies[i];	//��¼��ǰ������
			for(int j=0;j<heroBullets.length;j++){ //���������ӵ�
				Bullet b = heroBullets[j];       //��¼��ǰ�ӵ�
				if(b.isLife() && f.isLife() && b.hit(f)){	//�����ײ
					if(f.life>1){
						f.subtractLife();	//������һ
						b.goDead();		//�ӵ���ʧ
					}else{
						try {	//���ŵл���ը��Ч
							Clip enemy_bomb=AudioSystem.getClip();	
							AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//all_bomb.wav"));
							enemy_bomb.open(audioInput);
							enemy_bomb.start();
							}catch(Exception e) {
								e.printStackTrace();
							}
						f.goDead();	//��������ʧ
						b.goDead();	//�ӵ���ʧ
						if(f instanceof Enemy){		//�ж��Ƿ�Ϊ�л�
							Enemy e = (Enemy) f;	//ǿ������ת��
							score += e.getScore();	//����֮���÷���
						}
						if(f instanceof Award){		//�ж��Ƿ�Ϊ����
							Award a = (Award) f;
							int type = a.getType();
							switch(type){
							case Award.DOUBLE_FIRE:		//����Ϊ����
								hero.addDoubleFire();
								break;
							case Award.LIFE:		//����Ϊ����
								hero.addLife();
								break;
							case Award.BOMB:		//����Ϊը��
								hero.addBomb();
								break;
							}
						}
					}
				}
			}
		}
		for(int i=0;i<boss.length;i++){	//ͬ���ж��ӵ���boss����ײ
			FlyingObject f = boss[i];
			for(int j=0;j<heroBullets.length;j++){
				Bullet b = heroBullets[j];
				if(b.isLife() && f.isLife() && b.hit(f)){
					if(f.life>1){
						f.subtractLife();
						b.goDead();
					}else{
						try {	//����boss����ը��Ч
							Clip enemy_bomb=AudioSystem.getClip();
							AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//all_bomb.wav"));
							enemy_bomb.open(audioInput);
							enemy_bomb.start();
						}catch(Exception e) {
							e.printStackTrace();
						}
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
	//�ж��Ƿ�Խ��---------------------------------------------------------------------------------------------
	public void outOfBoundsAction(){
		int index = 0;
		FlyingObject[] fs = new FlyingObject[enemies.length];
		//�жϷ������Ƿ�Խ��
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(!f.isRemove() && !f.outBround()){
				fs[index++] = f;
			}
		}
		enemies = Arrays.copyOf(fs, index);//��δԽ��ķ������Լ����ŵķ����︴�Ƶ�enemy����
		
		//�ж�BOOS���Ƿ�Խ��
		index = 0;
		BossAirplane[] ba = new BossAirplane[boss.length];
		for(int i=0;i<boss.length;i++){
			BossAirplane f = boss[i];
			if(!f.isRemove() && !f.outBround()){
				ba[index++] = f;
		   	}
		}
		boss = Arrays.copyOf(ba, index);
		//�ж�BOOS�����ӵ��Ƿ�Խ�� 
		index = 0;
		Bullet[] bo = new Bullet[bossBullets.length];
		for(int i=0;i<bossBullets.length;i++){
			Bullet b = bossBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bo[index++] = b;
			}
		}
		bossBullets = Arrays.copyOf(bo, index);
		//�ж�Ӣ�ۻ����ӵ��Ƿ�Խ��
		index = 0;
		Bullet[] bs = new Bullet[heroBullets.length];
		for(int i=0;i<heroBullets.length;i++){
			Bullet b = heroBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bs[index++] = b;
			}
		}
		heroBullets = Arrays.copyOf(bs, index);
		//�жϵл����ӵ��Ƿ�Խ��
		index = 0;
		Bullet[] ebs = new Bullet[enemiesBullets.length];
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(!b.isRemove() && !b.outBround()){
				ebs[index++] = b;
			}
		}
		enemiesBullets = Arrays.copyOf(ebs, index);
		//�ж�ըҩ�Ƿ�Խ��
		index=0;
		Bomb[] bom1 = new Bomb[bom.length];
		for(int i=0;i<bom.length;i++){
			Bomb bom2 = bom[i];
			if(!bom2.isRemove() && !bom2.outBround()){
				bom1[index++] = bom2;
			}
		}
		bom = Arrays.copyOf(bom1, index);
	}
	//��Ʒ��Ӣ�ۻ���ײ---------------------------------------------------------------------------------------------
	public void hitAction(){
		//��������Ӣ�ۻ���ײ
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(f.isLife() && hero.isLife() && hero.hit(f)){
				f.goDead();
				try {
					Clip hero_bullet=AudioSystem.getClip();
					AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//hero_bullet.wav"));
					hero_bullet.open(audioInput);
					hero_bullet.start();
				}catch(Exception e) {
					e.printStackTrace();
				}
				hero.clearDoubleFire();		//Ӣ�ۻ��������
				hero.subtractLife();	//Ӣ�ۻ�������һ
			}
		}
		//boss����Ӣ�ۻ���ײ
		for(int i=0;i<boss.length;i++){
			FlyingObject f = boss[i];
			if(f.isLife() && hero.isLife() && hero.hit(f)){
				f.goDead();
				try {
					Clip hero_bullet=AudioSystem.getClip();
					AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//hero_bullet.wav"));
					hero_bullet.open(audioInput);
					hero_bullet.start();
				}catch(Exception e) {
					e.printStackTrace();
				}
				hero.clearDoubleFire();	//Ӣ�ۻ��������
				hero.subtractLife();	//Ӣ�ۻ�������һ
			}
		}
		//�л��ӵ���Ӣ�ۻ���ײ
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(b.hit((FlyingObject)hero) && b.isLife()){
				b.goDead();
				try {
					Clip hero_bullet=AudioSystem.getClip();
					AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//hero_bullet.wav"));
					hero_bullet.open(audioInput);
					hero_bullet.start();
				}catch(Exception e) {
					e.printStackTrace();
				}
				hero.subtractLife();	//Ӣ�ۻ�������һ
				hero.clearDoubleFire();	//Ӣ�ۻ��������
			}
		}
		//boss�ӵ���Ӣ�ۻ���ײ
		for(int i=0;i<bossBullets.length;i++){
			Bullet b = bossBullets[i];
			if(b.hit((FlyingObject)hero) && b.isLife()){
				b.goDead();
				try {
					Clip hero_bullet=AudioSystem.getClip();
					AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//hero_bullet.wav"));
					hero_bullet.open(audioInput);
					hero_bullet.start();
				}catch(Exception e) {
					e.printStackTrace();
				}
				hero.subtractLife();	//Ӣ�ۻ�������һ
				hero.clearDoubleFire();	//Ӣ�ۻ��������
			}
		}
	}
	//�ж���Ϸ�Ƿ����---------------------------------------------------------------------------------------------
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			try {	//����Ӣ�ۻ����ݻ���Ч
				Clip hero_bomb=AudioSystem.getClip();
			    AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//hero_bomb.wav"));
			    hero_bomb.open(audioInput);
			    hero_bomb.start();
			   }catch(Exception e) {
				   e.printStackTrace();
			   }
			try {
				update(score);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			state = GAME_OVER;
		}
	}
	//���������ж������̵ĸ����¼�---------------------------------------------------------------------------------------------
	public void action(){
		MouseAdapter l = new MouseAdapter(){	//����һ�����������������������ɹ���
			public void mouseMoved(MouseEvent e){	//������Ӣ�ۻ��ƶ�
				if(state==RUNNING){	
					hero.x = e.getX() - hero.width;
					hero.y = e.getY() - hero.height/2;
				}
			}
			//�жϽ������ѡ��
			public void mouseClicked(MouseEvent e){
				//��ʼ��Ϸ
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=260&&e.getY()>=220) {
					if(state==START){
						System.out.println("��Ϸ��ʼ������");
						try {	//������Ϸ��Ч
							Clip bg=AudioSystem.getClip();
							AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//bg.wav"));
							bg.open(audioInput);
							bg.loop(Clip.LOOP_CONTINUOUSLY);
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						state = RUNNING;
					}
				}
				//�鿴���а���Ϸ����
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=320&&e.getY()>=280) {
					
					if(state==START)
						new ShowRank().init();
					if(state==GAME_OVER){
						try {
							Clip all_bomb=AudioSystem.getClip();
							AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//all_bomb.wav"));
							all_bomb.open(audioInput);
							all_bomb.start();
						}catch(Exception ex) {
							ex.printStackTrace();
						}
						state = START;
						score = 0;
						enemies = new FlyingObject[0];
						boss=new BossAirplane[0];
						bossBullets=new Bullet[0];
						heroBullets = new Bullet[0];
						enemiesBullets = new Bullet[0];
						hero = new Hero();
					}
				}
				//��Ϸָ��
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=380&&e.getY()>=340) {
					Guide.init();
				}
				//�˳���Ϸ
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=440&&e.getY()>=400) {
					System.out.println("��Ϸ����������");
					System.exit(0);
				}	
			}
			//��Ϸ��ͣ��������Ƴ����壩
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){
					state = PAUSE;
					try {
						Clip bg=AudioSystem.getClip();
						AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//bg.wav"));
						bg.open(audioInput);
						bg.stop();
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			//��Ϸ�������������봰�壩
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){
					try {
						Clip bg=AudioSystem.getClip();
						AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//bg.wav"));
						bg.open(audioInput);
						bg.loop(Clip.LOOP_CONTINUOUSLY);
					}catch(Exception ex) {
						ex.printStackTrace();
					}
					state = RUNNING;
				}
			}
	};
		//�����ע�������
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		//��Ӷ�ʱ����ÿ����ó���һ�Σ�
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				if(state==RUNNING){         //��������״ִ̬�в���
					enterAction();          //�����볡
					enemiesShoot();         //���˷����ӵ�
					shootAction();			//Ӣ�ۻ������ӵ�
					stepAction();			//�ƶ�
					bangAction();			//Ӣ�ۻ����������ײ
					outOfBoundsAction();	//�ж�Խ��
					hitAction();			//�ӵ���������ײ
					checkGameOverAction();	//�ж���Ϸ����
				}
				repaint();  	//���»��ƣ������µķ����
			}
		}, intervel,intervel);		//ÿ10msִ��һ��
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(state==RUNNING){
			switch(e.getKeyCode()){
			//���ո���ͷ�ը��
			case KeyEvent.VK_SPACE:
				if(hero.getBomb()>0) {		//��ӵ��ը��������0ʱ�����ͷ�
					this.shootAction11();	//����ը��
					for(int i=0;i<bom.length;i++) {
						Bomb b=bom[i];
						int bomx=bom[i].getx();
						int bomy=bom[i].gety();
						//����Ļ���
						enemies = new FlyingObject[0];
						enemiesBullets = new Bullet[0];   
						boss=new BossAirplane[0];
						bossBullets=new Bullet[0];
						hero.subBomb();		//ը������һ
					}
				}
				break;
			}
		}
	}
	//����---------------------------------------------------------------------------------------------
	public void paint(Graphics g){
		sky.paintObject(g);		//�������
		hero.paintObject(g);	//����Ӣ�ۻ�
		//����ը��
		for(int i=0;i<bom.length;i++){
			bom[i].paintObject(g);
		}
		//���Ʒ�����
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintObject(g);
		}
		//����boss
		for(int i=0;i<boss.length;i++){
			boss[i].paintObject(g);
		}
		//����boss�ӵ�
		for(int i=0;i<bossBullets.length;i++){
			bossBullets[i].paintObject(g);
		}
		//����Ӣ�ۻ��ӵ�
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].paintObject(g);
		}
		//���Ƶл��ӵ�
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].paintObject(g);
		}
		
		g.setFont(new Font("TimesRoman",Font.BOLD,25));	//���������ͺźʹ�С
		g.setColor(Color.GRAY);		//����������ɫ
		g.drawString("SCORE:"+score, 10, 25);	//����SCORE
		g.drawString("LIFE:"+hero.getLife(), 10, 55);	//����LIFE
		g.drawString("DOUBLEFIRE:"+hero.getDoubleFire(), 10, 115);	//����DOUBLEFIRE
		g.drawString("BOMB:"+hero.getBomb(), 10,85);	//����BOMB
		g.setColor(Color.RED);	//����Ѫ��Ϊ��ɫ
		for(int i=0;i<boss.length;i++) {
			//����Ѫ������Ƶ��
			g.fillRect(199,25,(int)Math.round(214*(boss[i].getlife()*0.02)), 10);
			g.drawImage(bloodImage, 150, 0,null);
		}
		//�ж���Ϸ״̬
		switch(state){
		case START:		//���ƿ�ʼ������ĸ�ѡ��
			g.drawImage(start, 90, 220, null);
			g.drawImage(rank,90,280,null);
			g.drawImage(guide,90,340,null);
			g.drawImage(exit,90,400,null);
			break;
		case PAUSE:		//������ͣ
			g.drawImage(resume, 200, 285, null);
			break;
		case GAME_OVER:		//������Ϸ����
			g.drawImage(gameover, 90, 280,null);
			break;
		}
		
	}
	//�߳�����
	@Override
	public void run() {
		action();
	}
	
	public static void main(String[] args) {
		World world = new World();
		JFrame frame = new JFrame();
		
		frame.add(world);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocationRelativeTo(null); 
		frame.setVisible(true); 
		frame.addKeyListener(world);
		Thread t=new Thread(world);		//�����߳�
		t.start();		//�����߳�
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
		case KeyEvent.VK_P:		//P��������ͣ
			if(state == RUNNING) {
				state = PAUSE;
			}	
			break;
		case KeyEvent.VK_R:		//R�����Ƽ�����Ϸ
			if(state == PAUSE) {
				state = RUNNING;	
			}	
			break;	
		case KeyEvent.VK_F:		//F��������Ϸ����
			state = GAME_OVER;
			break;
		}
	}
	public void update(int score) throws SQLException {
		Connection dbConn = null;
		try{              
			    //ע��JDBC����
				String driverName="com.mysql.cj.jdbc.Driver";  
				Class.forName(driverName);
				System.out.println("���������ɹ���");
			    //��ȡ���Ӷ���
				String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  
				String userName = "root";		
				String userPwd = "123456";
				dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
				System.out.println("MySQL���ӳɹ���");
			} catch(Exception e3){
					  e3.printStackTrace();
			  }
		String sql = "select maxscore from playerinfo where id = '" + LoginFrame.username  + "'"; 
		Statement stmt = ((java.sql.Connection) dbConn).createStatement();
		ResultSet res = stmt.executeQuery(sql);  //����ѯ�Ľ������һ��������
		res.next();
		int maxscore = res.getInt(1);   //��ò�ѯ���ı�ĵ�1�н��
		if(score > maxscore){
			System.out.println("ˢ�¼�¼��");
			String sql1 = "update playerinfo set maxscore = " + score + " where id = '" + LoginFrame.username + "'";	
			stmt = ((java.sql.Connection) dbConn).createStatement();
			stmt.executeUpdate(sql1);
		}
	}
}
