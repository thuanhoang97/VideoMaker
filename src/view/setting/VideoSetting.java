package view.setting;

import module.video.VideoSpecs;
import view.mainview.VideoCreator;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import module.utility.FileLoader;
import module.utility.IconMaker;
import module.utility.Validator;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class VideoSetting extends JPanel{
	private VideoSpecs videoSpecs;
	
	private FileDialog fc;
	private JLabel introLb;
	private JTextField introTf;
	private JButton introBtn;
	private JLabel imgLb;
	private JTextField imgTf;
	private JButton imgBtn;
	private JLabel mp3Lb;
	private JTextField mp3Tf;
	private JButton mp3Btn;
	private JLabel fontLb;
	private JComboBox<String> fontName;
	private JComboBox<Integer> fontSize;
	private JToggleButton fontStyleBoldBtn;
	private JToggleButton fontStyleItalicBtn;
	private JCheckBox custFont;
	private JTextField custFontTf;
	private JButton custFontBtn;
	private JLabel speedLb;
	private JTextField speedTf;
	private JCheckBox timeUse;
	private JLabel timeLb;
	private JTextField timeTf;
	private JLabel outputFolderLb;
	private JTextField outputFolderTf;
	private JLabel colorLb;
	private JTextField colorTf;
	private JLabel resolutionLb;
	private JComboBox<String> resolution;
	private JComboBox<String> timeUnit;
	
	
	public VideoSetting() {
		init();
		try {
			videoSpecs = VideoSpecs.readPreviousSpecs();
			imgTf.setText(videoSpecs.getImgPath());
			mp3Tf.setText(videoSpecs.getMp3Path());
			outputFolderTf.setText(videoSpecs.getOutputFolder());
			speedTf.setText(String.valueOf(videoSpecs.getSpeed()));
			fontSize.setSelectedItem(videoSpecs.getFontSize());
			resolution.setSelectedItem(videoSpecs.getResolutionInString());
			colorTf.setText(videoSpecs.getColor());
			introTf.setText(videoSpecs.getIntroPath());
			if(videoSpecs.hasTime()) {
				timeUse.setSelected(true);
				timeTf.setText(String.valueOf(videoSpecs.getTime()));
	
			}
			if(videoSpecs.hasUseCustFont()) {
				fontName.setEnabled(false);
				custFont.setSelected(true);
				custFontTf.setText(videoSpecs.getFontPath());
			}
			if(videoSpecs.getFontStyle() == Font.BOLD) {
				fontStyleBoldBtn.setSelected(true);
				fontStyleItalicBtn.setSelected(false);
			}else if(videoSpecs.getFontStyle() == Font.ITALIC){
				fontStyleItalicBtn.setSelected(true);
				fontStyleBoldBtn.setSelected(false);
			}

		} catch (Exception e) {			
			videoSpecs =  new VideoSpecs();
		}
//				
	}
	
	private String getImgPath() throws IOException{
		String path = imgTf.getText();
		if(!Validator.validatePath(path))
			throw new IOException("Can not find the image path!");
		return path;
	}
	
	private String getMp3Path() throws IOException{
		String path = mp3Tf.getText();
		if(!Validator.validatePath(path))
			throw new IOException("Can not find the mp3 path!");
		return path;
	}
	
	private String getIntroPath() throws IOException{
		String path = introTf.getText();
		if(!Validator.validatePath(path))
			throw new IOException("Can not find the mp3 path!");
		return path;
	}
	
	private int getFontsize() {
		return (int) fontSize.getSelectedItem();
	}
	
	private int getFontSyle() {
		if(fontStyleBoldBtn.isSelected())
			return Font.BOLD;
		if(fontStyleItalicBtn.isSelected())
			return Font.ITALIC;
		return Font.PLAIN;

	}
	
	private float getSpeed() throws Exception{
		return Float.parseFloat(speedTf.getText());
	}
	
	private String getColor() throws IOException{
		String color = colorTf.getText();
		if(!Validator.validateColor(color))
			throw new IOException("Hex color format is not valid!");
		return color;
	}
	
	private Dimension getResolution() {
		String[] res = ((String)resolution.getSelectedItem()).split("x");
		return new Dimension(Integer.parseInt(res[0]), Integer.parseInt(res[1]));
	}
	
	private String getOutputFolder() throws IOException{
		String path = outputFolderTf.getText();
		if(!Validator.validatePath(path))
			throw new IOException("Can not find the output folder");
		return path;
	}
	
	private String getFontPath() throws IOException {
		String fontPath;
		if(custFont.isSelected()) {
			fontPath = custFontTf.getText();
			if(!Validator.validatePath(fontPath))
				throw new IOException("Can not find the font path!");
			
		}else {
			fontPath = (String)fontName.getSelectedItem();
		}
		return fontPath;
	}
	
	private double getTimeinSeconds() throws Exception {
		if(!timeUse.isSelected()) return -1.0;
		double time = Double.parseDouble(timeTf.getText());
		String unit = (String)timeUnit.getSelectedItem();
		if(unit.equals("minute")) {
			time*=60;
		}else if(unit.equals("hour")) {
			time*=3600;
		}
		return time;
	}
	
	public VideoSpecs getVideoSpecs() {
		try {
			if(hasFieldBlank()) throw new IOException("Has Blank Field!");
			videoSpecs.setImgPath(getImgPath());
			videoSpecs.setMp3Path(getMp3Path());
			videoSpecs.setColor(getColor());
			videoSpecs.setFontSize(getFontsize());
			videoSpecs.setFontStyle(getFontSyle());
			videoSpecs.setFontPath(getFontPath());
			videoSpecs.setResolution(getResolution());
			videoSpecs.setSpeed(getSpeed());
			videoSpecs.setTime(getTimeinSeconds());
			videoSpecs.setOutputFolder(getOutputFolder());
			videoSpecs.setIntroPath(getIntroPath());
		} catch (IOException ioEx) {
			JOptionPane.showMessageDialog(VideoSetting.this.getParent(),
					ioEx.getMessage(), "Path Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(VideoSetting.this.getParent(),
					ex.getMessage(),"Number Error",
					JOptionPane.ERROR_MESSAGE);
			return null;
		}
		return videoSpecs;
	}
	
	public void save() {
		videoSpecs.saveToFile();
	}
	
	public boolean hasFieldBlank() {
		if(imgTf.getText().equals("")) 
			return true;
		if(mp3Tf.getText().equals(""))
			return true;
		if(custFont.isSelected() && custFontTf.getText().equals(""))
			return true;
		if(timeUse.isSelected() && timeTf.getText().equals(""))
			return true;
		if(colorTf.getText().equals(""))
			return true;
		if(outputFolderTf.getText().equals(""))
			return true;
		return false;
	}
	
	
	private void init() {
		this.setLayout(null);
		
		Dimension size ;
		fc = new FileDialog((Frame) this.getParent(), "Chooser a file", FileDialog.LOAD);
		fc.setDirectory("C:\\");
		ImageIcon loadIcon = IconMaker.create("res/load.png", 30, 20);
		
		imgLb = new JLabel("Image Background: ");
		imgLb.setBounds(20,10,120,16);
		add(imgLb);
		
		imgTf = new JTextField();
		imgTf.setEditable(false);
		imgTf.setBounds(135, 10, 300,20);
		add(imgTf);
		
		imgBtn = new JButton();
		imgBtn.setIcon(loadIcon);
		imgBtn.setBounds(445, 10, loadIcon.getIconWidth(), loadIcon.getIconHeight());
		imgBtn.addActionListener(new LoadFile("image", imgTf));
		add(imgBtn);
		
		mp3Lb = new JLabel("Mp3 Source: ");
		size = mp3Lb.getPreferredSize();
		mp3Lb.setBounds(20,40,120,16);
		add(mp3Lb);
		
		mp3Tf = new JTextField();
		mp3Tf.setEditable(false);
		mp3Tf.setBounds(135, 40, 300,20);
		add(mp3Tf);
		
		mp3Btn = new JButton();
		mp3Btn.setIcon(loadIcon);
		mp3Btn.setBounds(445, 40, loadIcon.getIconWidth(), loadIcon.getIconHeight());
		mp3Btn.addActionListener(new LoadFile("mp3", mp3Tf));
		add(mp3Btn);
		
		introLb = new JLabel("Intro video: ");
		size = introLb.getPreferredSize();
		introLb.setBounds(550, 180, 150, 20);
		add(introLb);
		
		introTf = new JTextField();
		introTf.setEditable(false);
		introTf.setBounds(660, 180, 250,20);
		add(introTf);
		
		introBtn = new JButton();
		introBtn.setIcon(loadIcon);
		introBtn.setBounds(920, 180, loadIcon.getIconWidth(), loadIcon.getIconHeight());
		introBtn.addActionListener(new LoadFile("video", introTf));
		add(introBtn);
		
		fontLb = new JLabel("Font: ");
		fontLb.setBounds(20, 80,120, 16);
		add(fontLb);
		
		String[] fontNames = GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getAvailableFontFamilyNames();
		fontName = new JComboBox<String>(fontNames);
		fontName.setSelectedItem("Arial");
		fontName.setBounds(135, 80, 150, 20);
		add(fontName);
		
		Integer[] fontSizes = new Integer[500];
		for(int i=1; i<=500; i++)
			fontSizes[i-1] = i;
		fontSize = new JComboBox<Integer>(fontSizes);
		fontSize.setSelectedItem(100);
		fontSize.setBounds(300, 80, 60, 20);
		add(fontSize);
		
		fontStyleBoldBtn = new JToggleButton();
		fontStyleBoldBtn.setName("bold");
		fontStyleBoldBtn.addActionListener(new ClickFontStyle());
		fontStyleBoldBtn.setIcon(IconMaker.create("res/bold.png", 15, 15));
		fontStyleBoldBtn.setBounds(380, 80, 30,20);
		add(fontStyleBoldBtn);
		
		fontStyleItalicBtn = new JToggleButton();
		fontStyleItalicBtn.setName("italic");
		fontStyleItalicBtn.addActionListener(new ClickFontStyle());
		fontStyleItalicBtn.setIcon(IconMaker.create("res/italic.png", 15, 15));
		fontStyleItalicBtn.setBounds(420, 80, 30,20);
		add(fontStyleItalicBtn);
		
		custFont = new JCheckBox("Use custom font", false);
		custFont.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(custFont.isSelected()) {
					custFontBtn.setEnabled(true);
					fontName.setEnabled(false);
				}else {
					custFontBtn.setEnabled(false);
					fontName.setEnabled(true);
				}
				
			}
		});
		custFont.setBounds(130, 110, 300, 20);
		add(custFont);
		
		custFontTf = new JTextField();
		custFontTf.setEditable(false);
		custFontTf.setBounds(130, 140, 300,20);
		add(custFontTf);
		
		custFontBtn = new JButton();
		custFontBtn.setEnabled(false);
		custFontBtn.setIcon(loadIcon);
		custFontBtn.setBounds(445, 140, loadIcon.getIconWidth(), loadIcon.getIconHeight());
		custFontBtn.addActionListener(new LoadFile("font", custFontTf));
		add(custFontBtn);
		
		speedLb = new JLabel("Speed:");
		speedLb.setBounds(550, 10, 100, 20);
		add(speedLb);
		
		speedTf = new JTextField();
		speedTf.setText("1.0");
		speedTf.setBounds(660, 10, 100, 20);
		speedTf.addKeyListener(new KeyCheck());
		add(speedTf);
		
		timeUse = new JCheckBox("Choose time", false);
		timeUse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(timeUse.isSelected()) {
					timeTf.setEditable(true);
					timeUnit.setEnabled(true);
				}else {
					timeTf.setEditable(false);
					timeUnit.setEnabled(false);
					
				}
				
			}
		});
		timeUse.setBounds(545, 40, 300, 20);
		add(timeUse);
		
		timeLb = new JLabel("Time(s):");
		timeLb.setBounds(550, 60, 100, 20);
		add(timeLb);
		
		timeTf = new JTextField();
		timeTf.setEditable(false);
		timeTf.setBounds(660, 60, 100, 20);
		timeTf.addKeyListener(new KeyCheck());
		add(timeTf);
		
		timeUnit = new JComboBox<>(new String[] {
				"hour",
				"minute",
				"second"
		});
		timeUnit.setBounds(800, 60, 100, 20);
		timeUnit.setSelectedItem("second");
		timeUnit.setEnabled(false);
		add(timeUnit);
		
		colorLb = new JLabel("Color:");
		colorLb.setBounds(550, 90, 100,20);
		add(colorLb);
		
		colorTf = new JTextField();
		colorTf.setText("#FFFFFF");
		colorTf.setBounds(660, 90, 100,20);
		add(colorTf);
		
		resolutionLb = new JLabel("Resolution:");
		resolutionLb.setBounds(550, 120, 100, 20);
		add(resolutionLb);
		
		resolution = new JComboBox<String>(FileLoader.loadResolutions("res/resolutions.txt"));
		resolution.setSelectedIndex(1);
		
		resolution.setBounds(660, 120, 150, 20);
		add(resolution);
		
		outputFolderLb = new JLabel("Output Folder:");
		outputFolderLb.setBounds(20, 180, 100, 20);
		add(outputFolderLb);
		
		outputFolderTf = new JTextField();
		outputFolderTf.setBounds(130, 180, 300,20);
		outputFolderTf.setText("D:\\video\\");
		add(outputFolderTf);
		
	}
	
	
	
	class LoadFile implements ActionListener{
		
		private String fileType;
		private JTextField textField;
		
		public LoadFile(String fileType, JTextField textField) {
			this.fileType = fileType;
			this.textField = textField;
		} 

		@Override
		public void actionPerformed(ActionEvent arg0) {
			fc.setVisible(true);
			String filePath = fc.getDirectory() + fc.getFile();
			if(filePath == null)
				return;
			if(fileType.equals("image") 
					&& (filePath.contains(".jpg") || filePath.equals(".png"))) 
				textField.setText(filePath);
			else if(fileType.equals("mp3") && filePath.contains(".mp3")) 
				textField.setText(filePath);
			else if(fileType.equals("font") && filePath.contains(".ttf")) 
				textField.setText(filePath);
			else if(fileType.equals("video") && filePath.contains(".mp4")) 
				textField.setText(filePath);
			else {
				JOptionPane.showMessageDialog(null,"No "+ fileType+" file selected", 
						 "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	 class ClickFontStyle implements ActionListener {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JToggleButton button = (JToggleButton) e.getSource();
			if(button.isSelected()) {
				String btnName = button.getName();
				if(btnName.equals("bold"))
					fontStyleItalicBtn.setSelected(false);
				else
					fontStyleBoldBtn.setSelected(false);
			}else {
				System.out.println("un select");
			}
			
			
		}
	}
	 
	class KeyCheck implements KeyListener{
		@Override
		public void keyTyped(KeyEvent e) {
			char c = e.getKeyChar();
			if(!Character.isDigit(c) && c!='.') {
				getToolkit().beep();
		           e.consume();
			}
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			
		}
		@Override
		public void keyPressed(KeyEvent arg0) {}
	}
	
	
	
}
