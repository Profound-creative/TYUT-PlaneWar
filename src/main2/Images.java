package main2;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

//import javazoom.jl.decoder.JavaLayerException;
//import javazoom.jl.player.Player;

/*
 * ����ͼƬ���࣬ʵ���˽����������ͼƬ���ص�������������ÿ�ζ�Ҫȥ��ȡ
 */
public class Images {
	public static BufferedImage sky;
	public static BufferedImage[] bullets;
	public static BufferedImage[] bossairplanes;
	public static BufferedImage[] heros;
	public static BufferedImage[] airplanes;
	public static BufferedImage[] bigairplanes;
	public static BufferedImage[] bees;
	public static BufferedImage[] supplys;
	 public static BufferedImage[] bom;
	 
	static{
		
		//���ͼƬ�ļ���
		sky = readImage("images\\background.png");
		
		//Ӣ�ۻ�ͼƬ�ļ���
		heros = new BufferedImage[2];
		heros[0] = readImage("images\\me1.png");
		heros[1] = readImage("images\\me2.png");
		
		//�ӵ�ͼƬ�ļ���
		bullets = new BufferedImage[2];
		bullets[0] = readImage("images\\bullet1.png");
		bullets[1] = readImage("images\\bullet2.png");
		
		//��ը
		  bom = new BufferedImage[5];
		  bom[0] = readImage("images\\\\bom_0.png");
		
		//boss��ͼƬ�ļ���
		bossairplanes = new BufferedImage[5];
		bossairplanes[0] = readImage("images\\enemy3_n1.png");
		
		//С�л�ͼƬ�ļ���
		airplanes = new BufferedImage[5];
		airplanes[0] = readImage("images\\enemy1.png");
		
		//��л��ļ���
		bigairplanes = new BufferedImage[5];
		bigairplanes[0] = readImage("images\\enemy2.png");
		
		//����ͼƬ�ļ���
		bees = new BufferedImage[5];
		bees[0] = readImage("images\\bullet_supply.png");
		
		//�ӵ��ӳֻ�
		supplys = new BufferedImage[5];
		supplys[0] = readImage("images\\bomb_supply.png");
		
		//����ͼƬ�ļ���
			supplys[1] = readImage("images\\bom1.png");
			bees[1] = readImage("images\\bom1.png");
			bom[1] = readImage("images\\bom1.png");
			airplanes[1] = readImage("images\\enemy1_down1.png");
			bigairplanes[1] = readImage("images\\enemy2_down1.png");
			bossairplanes[1] = readImage("images\\enemy3_down1.png");
			
			supplys[2] = readImage("images\\bom2.png");
			bees[2] = readImage("images\\bom2.png");
			bom[2] = readImage("images\\bom2.png");
			airplanes[2] = readImage("images\\enemy1_down2.png");
			bigairplanes[2] = readImage("images\\enemy2_down2.png");
			bossairplanes[2] = readImage("images\\enemy3_down2.png");
			
			supplys[3] = readImage("images\\bom3.png");
			bees[3] = readImage("images\\bom3.png");
			bom[3] = readImage("images\\bom3.png");
			airplanes[3] = readImage("images\\enemy1_down3.png");
			bigairplanes[3] = readImage("images\\enemy2_down3.png");
			bossairplanes[3] = readImage("images\\enemy3_down3.png");
			
			supplys[4] = readImage("images\\bom4.png");
			bees[4] = readImage("images\\bom4.png");
			bom[4] = readImage("images\\bom4.png");
			airplanes[4] = readImage("images\\enemy1_down4.png");
			bigairplanes[4] = readImage("images\\enemy2_down4.png");
			bossairplanes[4] = readImage("images\\enemy3_down4.png");
		
	}
	
	//��ȡͼƬ���ڴ�
	public static BufferedImage readImage(String fileName){
		try{
			BufferedImage img = ImageIO.read(new FileInputStream(fileName));
			return img;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
	}
}
