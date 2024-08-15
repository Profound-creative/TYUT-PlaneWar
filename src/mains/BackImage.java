package mains;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

class BackImage extends JPanel {
	Image background;

	public BackImage(){
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(background, 0, 0, null);
	}
}
