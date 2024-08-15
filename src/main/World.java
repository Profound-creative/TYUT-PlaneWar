package main;
//游戏内容
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
//添加窗体
import javax.swing.JFrame;
import javax.swing.JPanel;

import main2.GuideFinal;
import main2.LoginFrameFinal;
import main2.ShowRank;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
//添加音乐
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class World extends JPanel implements Runnable,KeyListener {
	
	public static final int WIDTH = 490;	//设置游戏界面宽
	public static final int HEIGHT = 700;	//设置游戏界面高
	private Sky sky = new Sky();	//创建一个天空类
	private Hero hero = new Hero();		//创建英雄机对象
	private FlyingObject[] enemies = {};	//创建飞行物数组储存飞行物
	private Bullet[] heroBullets = {};		//创建英雄机子弹数组储存子弹
	private Bullet[] enemiesBullets = {};	//创建敌机子弹数组储存子弹
	private Bomb bom[] = {};	//创建炸弹数组储存炸弹
	private BossAirplane boss[] = {};	//boss机数组来储存boss
	private Bullet[] bossBullets = {};	//创建boss机子弹数组储存子弹
	//定义游戏状态变量
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = START;
	//定义图片
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
		//读取images文件夹下的图片
		start = Images.readImage("images\\begin.png");
		pause = Images.readImage("images\\pause_nor.png");
		resume = Images.readImage("images\\resume_nor.png");
		gameover = Images.readImage("images\\gameover.png");
		rank = Images.readImage("images\\rank.png");
		guide = Images.readImage("images\\guide.png");
		exit = Images.readImage("images\\exit.png");
		bloodImage = Images.readImage("images\\blood.png");
	}
	//各飞行物出现的频数
	public FlyingObject nextOne(){
		int n = (int)(Math.random()*100);
		if(n>95){
			return new Bomb_supply();	//炸弹
		}else if(n>90){
			return new Life_supply();	//生命
		}else if(n>80){
			return new Fire_supply();	//火力
		}else if(n>50) {
			return new BigAirplane();	//大敌机
		}else{
			return new Airplane();	//小敌机
		}
	}
	
	private int enemiesIndex = 0;   //创建一个飞行物出现索引方便控制不同种飞行物的出现时间快慢
	public void enterAction(){
		enemiesIndex++;		//飞行物对象自加	
		if(enemiesIndex%30==0){		//大概每30ms执行一次
			enemies = Arrays.copyOf(enemies, enemies.length+1);	//扩容+1(增加飞行物)
			enemies[enemies.length-1] = nextOne();	//把产的敌人one赋值（添加）给最后一个数组元素
		}
		if(enemiesIndex%1000==0 && score>50){	//判断boss机出现
			boss = Arrays.copyOf(boss, boss.length+1);
			boss[boss.length-1] = new BossAirplane();
			//System.out.println(enemiesIndex);
		}
	}
	
	private int enemiesShootIndex = 0;
	public void enemiesShoot(){		//控制敌机子弹
		enemiesShootIndex++;
		if(enemiesShootIndex%100==0){	//控制敌机子弹出现快慢
			for(int i=0;i<enemies.length;i++){
				FlyingObject f = enemies[i];
				if(f.isLife() && !(f instanceof Fire_supply)){	//判断敌机是否发射子弹
					Bullet[] b = f.shoot();
					enemiesBullets = Arrays.copyOf(enemiesBullets, enemiesBullets.length+b.length);
					System.arraycopy(b, 0, enemiesBullets, enemiesBullets.length-b.length,b.length);//将子弹对象添加至子弹数组中
				}
			}
			for(int i=0;i<boss.length;i++) {
				BossAirplane boss1=boss[i];
				if(boss1.isLife()){		//控制boss机发射子弹
					Bullet[] b = boss1.shoot();
					bossBullets = Arrays.copyOf(bossBullets, bossBullets.length+b.length);
					System.arraycopy(b, 0, bossBullets, bossBullets.length-b.length,b.length);//将子弹对象添加至子弹数组中
				}
			}
		}
	}
	
	private int shootIndex = 0;
	public void shootAction(){	//控制英雄机发射子弹
		shootIndex++;
		if(shootIndex%10==0){	//控制子弹发射速度
			Bullet[] b = hero.shoot();
			heroBullets = Arrays.copyOf(heroBullets, heroBullets.length+b.length);
			System.arraycopy(b, 0, heroBullets, heroBullets.length-b.length,b.length);//将子弹对象添加至子弹数组中
		}
	}
	
	 public void shootAction11(){	//控制英雄机发射炸弹
		   Bomb[] b = hero.shoot2();
		   bom = Arrays.copyOf(bom, bom.length+b.length);
		   System.arraycopy(b, 0, bom, bom.length-b.length,b.length);//将炸弹对象添加至炸弹数组中   
	}
	//控制各个对象移动-------------------------------------------------------------------------------------------
	public void stepAction(){
		sky.step();		//天空背景移动
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
	
	int score = 0;	//游戏得分
	
	//子弹与敌人相撞---------------------------------------------------------------------------------------------
	public void bangAction(){
		for(int i=0;i<enemies.length;i++){		//遍历所有飞行物
			FlyingObject f = enemies[i];	//记录当前飞行物
			for(int j=0;j<heroBullets.length;j++){ //遍历所有子弹
				Bullet b = heroBullets[j];       //记录当前子弹
				if(b.isLife() && f.isLife() && b.hit(f)){	//如果相撞
					if(f.life>1){
						f.subtractLife();	//生命减一
						b.goDead();		//子弹消失
					}else{
						try {	//播放敌机爆炸音效
							Clip enemy_bomb=AudioSystem.getClip();	
							AudioInputStream audioInput=AudioSystem.getAudioInputStream(new File("sound//all_bomb.wav"));
							enemy_bomb.open(audioInput);
							enemy_bomb.start();
							}catch(Exception e) {
								e.printStackTrace();
							}
						f.goDead();	//飞行物消失
						b.goDead();	//子弹消失
						if(f instanceof Enemy){		//判断是否为敌机
							Enemy e = (Enemy) f;	//强制类型转换
							score += e.getScore();	//击毁之后获得分数
						}
						if(f instanceof Award){		//判断是否为奖励
							Award a = (Award) f;
							int type = a.getType();
							switch(type){
							case Award.DOUBLE_FIRE:		//奖励为火力
								hero.addDoubleFire();
								break;
							case Award.LIFE:		//奖励为生命
								hero.addLife();
								break;
							case Award.BOMB:		//奖励为炸弹
								hero.addBomb();
								break;
							}
						}
					}
				}
			}
		}
		for(int i=0;i<boss.length;i++){	//同理判断子弹与boss机相撞
			FlyingObject f = boss[i];
			for(int j=0;j<heroBullets.length;j++){
				Bullet b = heroBullets[j];
				if(b.isLife() && f.isLife() && b.hit(f)){
					if(f.life>1){
						f.subtractLife();
						b.goDead();
					}else{
						try {	//播放boss机爆炸音效
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
	//判断是否越界---------------------------------------------------------------------------------------------
	public void outOfBoundsAction(){
		int index = 0;
		FlyingObject[] fs = new FlyingObject[enemies.length];
		//判断飞行物是否越界
		for(int i=0;i<enemies.length;i++){
			FlyingObject f = enemies[i];
			if(!f.isRemove() && !f.outBround()){
				fs[index++] = f;
			}
		}
		enemies = Arrays.copyOf(fs, index);//将未越界的飞行物以及活着的飞行物复制到enemy数组
		
		//判断BOOS机是否越界
		index = 0;
		BossAirplane[] ba = new BossAirplane[boss.length];
		for(int i=0;i<boss.length;i++){
			BossAirplane f = boss[i];
			if(!f.isRemove() && !f.outBround()){
				ba[index++] = f;
		   	}
		}
		boss = Arrays.copyOf(ba, index);
		//判断BOOS机的子弹是否越界 
		index = 0;
		Bullet[] bo = new Bullet[bossBullets.length];
		for(int i=0;i<bossBullets.length;i++){
			Bullet b = bossBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bo[index++] = b;
			}
		}
		bossBullets = Arrays.copyOf(bo, index);
		//判断英雄机的子弹是否越界
		index = 0;
		Bullet[] bs = new Bullet[heroBullets.length];
		for(int i=0;i<heroBullets.length;i++){
			Bullet b = heroBullets[i];
			if(!b.isRemove() && !b.outBround()){
				bs[index++] = b;
			}
		}
		heroBullets = Arrays.copyOf(bs, index);
		//判断敌机的子弹是否越界
		index = 0;
		Bullet[] ebs = new Bullet[enemiesBullets.length];
		for(int i=0;i<enemiesBullets.length;i++){
			Bullet b = enemiesBullets[i];
			if(!b.isRemove() && !b.outBround()){
				ebs[index++] = b;
			}
		}
		enemiesBullets = Arrays.copyOf(ebs, index);
		//判断炸药是否越界
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
	//物品与英雄机相撞---------------------------------------------------------------------------------------------
	public void hitAction(){
		//飞行物与英雄机相撞
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
				hero.clearDoubleFire();		//英雄机火力清空
				hero.subtractLife();	//英雄机生命减一
			}
		}
		//boss机与英雄机相撞
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
				hero.clearDoubleFire();	//英雄机火力清空
				hero.subtractLife();	//英雄机生命减一
			}
		}
		//敌机子弹与英雄机相撞
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
				hero.subtractLife();	//英雄机生命减一
				hero.clearDoubleFire();	//英雄机火力清空
			}
		}
		//boss子弹与英雄机相撞
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
				hero.subtractLife();	//英雄机生命减一
				hero.clearDoubleFire();	//英雄机火力清空
			}
		}
	}
	//判断游戏是否结束---------------------------------------------------------------------------------------------
	public void checkGameOverAction(){
		if(hero.getLife()<=0){
			try {	//播放英雄机被摧毁音效
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
	//监听大类判断鼠标键盘的各个事件---------------------------------------------------------------------------------------------
	public void action(){
		MouseAdapter l = new MouseAdapter(){	//创建一个鼠标适配器，用匿名类完成构造
			public void mouseMoved(MouseEvent e){	//鼠标控制英雄机移动
				if(state==RUNNING){	
					hero.x = e.getX() - hero.width;
					hero.y = e.getY() - hero.height/2;
				}
			}
			//判断进入界面选项
			public void mouseClicked(MouseEvent e){
				//开始游戏
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=260&&e.getY()>=220) {
					if(state==START){
						System.out.println("游戏开始！！！");
						try {	//播放游戏音效
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
				//查看排行榜，游戏结束
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
				//游戏指南
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=380&&e.getY()>=340) {
					Guide.init();
				}
				//退出游戏
				if(e.getX()>=90&&e.getX()<=400&&e.getY()<=440&&e.getY()>=400) {
					System.out.println("游戏结束！！！");
					System.exit(0);
				}	
			}
			//游戏暂停（当鼠标移出窗体）
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
			//游戏继续（当鼠标进入窗体）
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
		//给鼠标注册监听器
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		//添加定时器（每隔多久出现一次）
		Timer timer = new Timer();
		int intervel = 10;
		timer.schedule(new TimerTask(){
			public void run() {
				if(state==RUNNING){         //处于运行状态执行操作
					enterAction();          //敌人入场
					enemiesShoot();         //敌人发射子弹
					shootAction();			//英雄机发射子弹
					stepAction();			//移动
					bangAction();			//英雄机与飞行物相撞
					outOfBoundsAction();	//判断越界
					hitAction();			//子弹与物体相撞
					checkGameOverAction();	//判断游戏结束
				}
				repaint();  	//重新绘制（产生新的飞行物）
			}
		}, intervel,intervel);		//每10ms执行一次
	
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(state==RUNNING){
			switch(e.getKeyCode()){
			//按空格会释放炸弹
			case KeyEvent.VK_SPACE:
				if(hero.getBomb()>0) {		//当拥有炸弹数大于0时才能释放
					this.shootAction11();	//发射炸弹
					for(int i=0;i<bom.length;i++) {
						Bomb b=bom[i];
						int bomx=bom[i].getx();
						int bomy=bom[i].gety();
						//将屏幕清空
						enemies = new FlyingObject[0];
						enemiesBullets = new Bullet[0];   
						boss=new BossAirplane[0];
						bossBullets=new Bullet[0];
						hero.subBomb();		//炸弹数减一
					}
				}
				break;
			}
		}
	}
	//绘制---------------------------------------------------------------------------------------------
	public void paint(Graphics g){
		sky.paintObject(g);		//绘制天空
		hero.paintObject(g);	//绘制英雄机
		//绘制炸弹
		for(int i=0;i<bom.length;i++){
			bom[i].paintObject(g);
		}
		//绘制飞行物
		for(int i=0;i<enemies.length;i++){
			enemies[i].paintObject(g);
		}
		//绘制boss
		for(int i=0;i<boss.length;i++){
			boss[i].paintObject(g);
		}
		//绘制boss子弹
		for(int i=0;i<bossBullets.length;i++){
			bossBullets[i].paintObject(g);
		}
		//绘制英雄机子弹
		for(int i=0;i<heroBullets.length;i++){
			heroBullets[i].paintObject(g);
		}
		//绘制敌机子弹
		for(int i=0;i<enemiesBullets.length;i++){
			enemiesBullets[i].paintObject(g);
		}
		
		g.setFont(new Font("TimesRoman",Font.BOLD,25));	//设置字体型号和大小
		g.setColor(Color.GRAY);		//设置字体颜色
		g.drawString("SCORE:"+score, 10, 25);	//绘制SCORE
		g.drawString("LIFE:"+hero.getLife(), 10, 55);	//绘制LIFE
		g.drawString("DOUBLEFIRE:"+hero.getDoubleFire(), 10, 115);	//绘制DOUBLEFIRE
		g.drawString("BOMB:"+hero.getBomb(), 10,85);	//绘制BOMB
		g.setColor(Color.RED);	//设置血条为空色
		for(int i=0;i<boss.length;i++) {
			//控制血条减少频率
			g.fillRect(199,25,(int)Math.round(214*(boss[i].getlife()*0.02)), 10);
			g.drawImage(bloodImage, 150, 0,null);
		}
		//判断游戏状态
		switch(state){
		case START:		//绘制开始界面的四个选项
			g.drawImage(start, 90, 220, null);
			g.drawImage(rank,90,280,null);
			g.drawImage(guide,90,340,null);
			g.drawImage(exit,90,400,null);
			break;
		case PAUSE:		//绘制暂停
			g.drawImage(resume, 200, 285, null);
			break;
		case GAME_OVER:		//绘制游戏结束
			g.drawImage(gameover, 90, 280,null);
			break;
		}
		
	}
	//线程运行
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
		Thread t=new Thread(world);		//创建线程
		t.start();		//启动线程
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
		case KeyEvent.VK_P:		//P键控制暂停
			if(state == RUNNING) {
				state = PAUSE;
			}	
			break;
		case KeyEvent.VK_R:		//R键控制继续游戏
			if(state == PAUSE) {
				state = RUNNING;	
			}	
			break;	
		case KeyEvent.VK_F:		//F键控制游戏结束
			state = GAME_OVER;
			break;
		}
	}
	public void update(int score) throws SQLException {
		Connection dbConn = null;
		try{              
			    //注册JDBC驱动
				String driverName="com.mysql.cj.jdbc.Driver";  
				Class.forName(driverName);
				System.out.println("加载驱动成功！");
			    //获取连接对象
				String dbURL = "jdbc:mysql://127.0.0.1:3306/planewar";  
				String userName = "root";		
				String userPwd = "123456";
				dbConn = DriverManager.getConnection(dbURL, userName, userPwd);
				System.out.println("MySQL连接成功！");
			} catch(Exception e3){
					  e3.printStackTrace();
			  }
		String sql = "select maxscore from playerinfo where id = '" + LoginFrame.username  + "'"; 
		Statement stmt = ((java.sql.Connection) dbConn).createStatement();
		ResultSet res = stmt.executeQuery(sql);  //将查询的结果放在一个集合中
		res.next();
		int maxscore = res.getInt(1);   //获得查询出的表的第1列结果
		if(score > maxscore){
			System.out.println("刷新记录！");
			String sql1 = "update playerinfo set maxscore = " + score + " where id = '" + LoginFrame.username + "'";	
			stmt = ((java.sql.Connection) dbConn).createStatement();
			stmt.executeUpdate(sql1);
		}
	}
}
