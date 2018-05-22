package module.video;

import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;

import app.App;

public class VideoSpecs {
	private String imgPath;
	private String mp3Path;
	private String fontPath;
	private int fontStyle;
	private int fontSize;
	private float speed;
	private double time;
	private Dimension resolution;
	private String hexColor;
	private String outputFolder;
	private String introPath;
	
	public VideoSpecs() {
	}
	
	public VideoSpecs(VideoSpecs specs) {
		imgPath = specs.imgPath;
		mp3Path = specs.mp3Path;
		fontPath = specs.fontPath;
		fontStyle = specs.fontStyle;
		fontSize = specs.fontSize;
		speed = specs.speed;
		time = specs.time;
		resolution = new Dimension(specs.resolution.width, specs.resolution.height);
		hexColor = specs.hexColor;
		outputFolder = specs.outputFolder;
		introPath = specs.introPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
	public void setResolution(Dimension resolution) {
		this.resolution = resolution;
	}
	
	public void setMp3Path(String mp3Path) {
		this.mp3Path = mp3Path;
	}
	
	public void setFontPath(String fontPath) {
		this.fontPath = fontPath;
	}
	
	public void setFontStyle(int fontStyle) {
		this.fontStyle = fontStyle;
	}
	
	
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public void setTime(double time) {
		this.time = time;
	}
	
	public void setColor(String hexColor) {
		this.hexColor = hexColor;
	}
	
	public void setOutputFolder(String outputFolder) {
		this.outputFolder = outputFolder;
	}
	
	public void setIntroPath(String introPath) {
		this.introPath = introPath;
	}
	
	public String getMp3Path() {
		return mp3Path;
	}
	
	public String getIntroPath() {
		return introPath;
	}
	public String getImgPath() {
		return imgPath;
	}
	
	public String getFontPath() {
		return fontPath;
	}
	
	public int getFontStyle() {
		return fontStyle;
	} 
	
	public String getFontStyleName() {
		if(fontStyle == Font.BOLD)
			return "bold";
		if(fontStyle == Font.ITALIC)
			return "italic";
		return "regular";
	}
	
	public int getFontSize() {
		return fontSize;
	}
	
	public float getSpeed() {
		return speed;
	}
	
	public double getTime() {
		return time;
	}
	
	
	
	public boolean hasTime() {
		return time>0;
	}
	
	
	public boolean hasUseCustFont() {
		return fontPath.contains(".ttf");
	}
	
	public String getColor() {
		return hexColor;
	}
	
	public String getOutputFolder() {
		return outputFolder;
	}
	
	public Dimension getResolution() {
		return resolution;
	}
	
	public String getResolutionInString() {
		return resolution.width + "x" + resolution.height;
	}
	
	public void saveToFile() {
		File file = new File(App.class.getResource("/").getFile());
		try (FileWriter writer = new FileWriter(file+"/videospecs.json")){
			Gson gson = new Gson();
			gson.toJson(this, writer);
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public static VideoSpecs readPreviousSpecs() throws Exception{
		VideoSpecs specs = null ;
		File file = new File(App.class.getResource("/").getFile());
		Reader reader = new FileReader(file + "/videospecs.json");
		Gson gson = new Gson();
		specs = gson.fromJson(reader, VideoSpecs.class);
		return specs;
	}
	
}
