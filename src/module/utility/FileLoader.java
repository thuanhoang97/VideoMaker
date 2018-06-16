package module.utility;

import java.io.File;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import app.App;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;

public class FileLoader {
	
	public static String[] loadResolutions(String path) {
		String rel = "";
		BufferedReader br = null;
		InputStreamReader ir = null;
		try {
			ir = new InputStreamReader(App.class.getResourceAsStream(path), "UTF-8");
			br = new BufferedReader(ir);
			rel = br.readLine();
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rel.split(";");
	}

	private static String loadText(File file) {
		StringBuilder text = new StringBuilder();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
			String line = "";
//			br.read();
			while((line=br.readLine())!=null) {
				if(line.length()==0)
					text.append("$").append(" ");
				else 
					text.append(line).append(" ");
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return text.toString();
	}
	
	public static List<String> getLinesFitWidth(File file, FontMetrics fm,
			int maxWidth){
		List<String> lines = new ArrayList<>();
		String text = loadText(file);
		String[] words = text.split(" ");
		StringBuilder line = new StringBuilder();
		for(int i=0; i<words.length; i++) {
			if(words[i].equals("$")) {
				lines.add("");
				continue;
			}
			int lineW = fm.stringWidth(line.toString()) + fm.stringWidth(words[i] + " ");
//			System.out.println(words[i]);
//			System.out.println("lineW: " + lineW);
//			System.out.println(maxWidth);
			
			if(lineW >= maxWidth) {
//				System.out.println(line.toString());
				lines.add(line.toString());
//				System.out.println(line.toString());
				line.setLength(0);
//				System.out.println("line:" +line.toString());
			}
			line.append(words[i]).append(" ");
		}
		
		//the remaining part.
	    if(line.length() > 0){
	        lines.add(line.toString());
	    }    
	    return lines;
	}
	
	public static BufferedImage loadImage(String path){
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File(path));
//			img = ImageIO.read(path);
		} catch (IOException e) {
			System.err.println("Can not find the path : " + path);
		}
		return img;
	}
	
	public static BufferedImage getImageScaled(BufferedImage img, int width, int height) {	
		BufferedImage imgScaled = new BufferedImage(width, height, img.getType());
		Graphics2D g = imgScaled.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
	            RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, width, height, null);
		g.dispose();
		
		
		
		return imgScaled;
	}
	
}
