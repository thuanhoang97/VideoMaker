package view.preview;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import module.video.VideoRender;
import module.video.VideoSpecs;

public class Preview extends JFrame {
	private VideoSpecs specs;
	private BufferedImage img;
	private double y;
	private long timeStart;
	
	public Preview(VideoSpecs specs) {
		this.specs = specs;
		this.timeStart = System.currentTimeMillis();
		this.setTitle("Preview");
		this.setResizable(false);
		this.setSize(specs.getResolution().width, specs.getResolution().height);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		
		this.loadImage(specs.getImgPath());
		this.setContentPane(new JPanel() {
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(Preview.this.getImageScaled(img), 0, 0, null);
				moveText();
			}
		});
	}
	
	private void loadImage(String path) {
		try {
			img = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.err.println("Can not find the path : " + path);
		}
	}
	
	private Font getFontFromSpecs() {
		Font font = null;
		if(specs.hasUseCustFont()) {
			try {
				font = Font.createFont(Font.TRUETYPE_FONT, new File(specs.getFontPath()));
			} catch (FontFormatException e) {
				return new Font("Arial", Font.BOLD, 20);
			} catch (IOException e) {
				return new Font("Arial", Font.BOLD, 20);
			}
			font.deriveFont(specs.getFontStyle(), specs.getFontSize());
		}else {
			font = new Font(specs.getFontPath(), specs.getFontStyle(), specs.getFontSize());
		}
		return font;
	}
	
	private BufferedImage getImageScaled(BufferedImage img) {
		int imgW = img.getWidth();
		int imgH = img.getHeight();
		int w = getWidth();
		int h = getHeight();
		
		BufferedImage imgTemp = new BufferedImage(imgW, imgH,img.getType());
		Graphics tempG = imgTemp.createGraphics();
		tempG.drawImage(img, 0, 0,null);
		tempG = imgTemp.getGraphics();
		tempG.setFont(getFontFromSpecs());
		tempG.setColor(Color.decode(specs.getColor()));
		tempG.drawString("This is demo text", 0, (int)y);
		tempG.dispose();
				
		BufferedImage imgScaled = new BufferedImage(w, h, imgTemp.getType());
		Graphics2D g = imgScaled.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(imgTemp, 0, 0, w, h, null);
		g.dispose();
		
		return imgScaled;
	}
	
	private void moveText() {
		if(y<0)
			timeStart = System.currentTimeMillis();
		double dur = (System.currentTimeMillis() - timeStart)*1.0/1000;
		System.out.println(dur);
		System.out.println(specs.getSpeed()*VideoRender.SPEED_STANDER);
		y = img.getHeight() - dur*specs.getSpeed();
		repaint();
	}
}
