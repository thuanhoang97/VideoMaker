package module.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import app.App;

public class IconMaker {
	public static ImageIcon create(String path, int width, int height) {
		BufferedImage icon;
		java.awt.Image iconBtn=null;
		try {
			icon = ImageIO.read(App.class.getResource(path));
			iconBtn = icon.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		return new ImageIcon(iconBtn);
	}
}
