package module.video;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import module.utility.FileLoader;
import view.logview.LogView;

public class VideoRender {
	public final static int SPEED_STANDER = 40;
	private VideoSpecs specs;
	private LogView logView;
	private FontMetrics fm;
	private BufferedImage image;
	private List<String> lines;

	
	public VideoRender(VideoSpecs specs, LogView logView) {
		this.specs = new VideoSpecs(specs);
		this.logView = logView;
		this.image = FileLoader.loadImage(specs.getImgPath());
		this.setFontMetric();
		
	}
	

	
	private void setFontMetric() {
		Font font;
		try {
			if(specs.hasUseCustFont()) {
				font = Font.createFont(Font.TRUETYPE_FONT, new File(specs.getFontPath()));
				font.deriveFont(specs.getFontStyle(), specs.getFontSize());
			}else {
				font = new Font(specs.getFontPath(), specs.getFontStyle(), specs.getFontSize());
			}
			
			fm = image.getGraphics().getFontMetrics(font);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void render(File txtFile) {
		String outputPath = specs.getOutputFolder()+ "\\" + txtFile.getName().split("\\.")[0];
		File coppyTxtFile = createTextFile(txtFile);
		runFFmpeg(coppyTxtFile, outputPath);
		coppyTxtFile.delete();
	}
	
	private void runFFmpeg(File file, String outputPath) {
		
		File bat = createBatFile(file,outputPath);
		ProcessBuilder pb = new ProcessBuilder("cmd", "/c","start","/wait",bat.getAbsolutePath());
		Process p;
		try {
			p = pb.start();
			p.waitFor();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bat.delete();
	}
	
	private File createTextFile(File txtFile) {
		File file = new File(txtFile.getName().hashCode()+".txt");
		lines = FileLoader.getLinesFitWidth(txtFile, fm, image.getWidth());
		PrintWriter writer =null;
		try {
			writer = new PrintWriter(file, "UTF-8");
			for(int i=0; i<lines.size(); i++) {
				writer.println(lines.get(i));
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.close();
		}
		return file;
		
	}
	
	private File createBatFile(File file, String outputPath) {
		File bat = new File(file.getName().hashCode()+".bat");
		float speed = specs.getSpeed()*SPEED_STANDER;
		double time;
		if(specs.hasTime()) {
			time = specs.getTime();
			if(time < 10*60) {
				time = 11*60;
			}
		}else {
			int height = image.getHeight();
			for(int i=0; i<lines.size();i++) {
				height+=fm.getHeight();
			}
			time = height*1.0/speed + 1;
		}
		String fontPath = specs.getFontPath();
		if(specs.hasUseCustFont()) {
			fontPath = fontPath.replaceAll(":", "\\\\:");
		}
		
		String rel = specs.getResolution().width + ":" + specs.getResolution().height;
		
		String cmd = "echo y | ffmpeg -loop 1"
				+" -i \"" + specs.getImgPath() + "\""
				+" -i \"" +specs.getMp3Path() + "\""
				+" -vf drawtext='fontfile=\"" + fontPath +"\""
				+"\\:style=" + specs.getFontStyleName()
				+":fontsize=" + specs.getFontSize()
				+":textfile=\""  + file.getAbsolutePath().replaceAll("\\\\","/").replaceAll(":", "\\\\:") + "\""
				+":fontcolor=" + specs.getColor()+"'"
				+":x=0:y=h+" + (lines.size()* fm.stringWidth(lines.get(0))) +"-t*" + speed
				+",format=yuv420p,scale=" + specs.getResolutionInString()
				+",setsar=1:1 -t " + time 
				+ " -vcodec libx264 -b:v 1000k -preset superfast \"" +outputPath + "_o.mp4\"";
		
		String concatCmd1= "echo y | ffmpeg "
				+ "-i \"" + outputPath + "_o.mp4\" "
				+ "-c copy -bsf:v h264_mp4toannexb -f mpegts \"" + outputPath+"_pp.mp4\"";
		String concatCmd2="echo y | ffmpeg "
				+ "-i \"concat:\""+specs.getIntroPath()+"\"|\""+ outputPath+"_pp.mp4\"\" "
				+ "-c copy -bsf:a aac_adtstoasc \""+outputPath+".mp4\"";
//		String concatCmd = "echo y | ffmpeg "
//				+ "-i \"" + specs.getIntroPath() + "\"  "
//				+ "-i \"" + outputPath + "_o.mp4\" "
//				+ "-filter_complex \"[0:v:0] [0:a:0] [1:v:0] [1:a:0] concat=n=2:v=1:a=1 [v] [a]\"  -map \"[v]\" -map \"[a]\" \""
//				+ outputPath  + ".mp4\" ";
//		System.out.println(cmd);
		PrintWriter writer =null;
		try {
			writer = new PrintWriter(bat);
			writer.println(cmd);
			writer.println(concatCmd1);
			writer.println(concatCmd2);
			writer.println("exit");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			writer.close();
		}
		return bat;
	}
}
