package view.mainview;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JButton;
import module.utility.IconMaker;
import view.fileview.TextFileView;
import view.logview.LogView;
import view.preview.Preview;
import view.setting.VideoSetting;
import module.video.VideoRender;



public class VideoCreator extends JFrame{
	
	private VideoSetting setting;
	private TextFileView fileView;
	private LogView logView;
	private JButton runBtn;
	private JButton previewBtn;
	
	
	public VideoCreator() {
		init();
	}
	
	private void initFrame() {
		this.setTitle("Video Creater");
		this.setSize(1000,600);
		this.setResizable(false);
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new WindowListener() {
			@Override
			public void windowOpened(WindowEvent e) {}
			@Override
			public void windowIconified(WindowEvent e) {}
			@Override
			public void windowDeiconified(WindowEvent e) {}
			@Override
			public void windowDeactivated(WindowEvent e) {}
			@Override
			public void windowClosing(WindowEvent e) {
//				JOptionPane.showMessageDialog(new JFrame(), App.class.getResource("res/videospecs.json"),
//						"", JOptionPane.WARNING_MESSAGE);
				setting.save();;
			}
			@Override
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {
	
			}
		});
	}
	
	private void init() {
		initFrame();
		
		setting = new VideoSetting();
		setting.setBounds(0, 0, getWidth(), 200);
		this.add(setting);
		
		fileView = new TextFileView();
		fileView.setBounds(0,220, getWidth()/2,280);
		this.add(fileView);
		
		logView = new LogView();
		logView.setBounds(getWidth()/2, 220, getWidth()/2, 280);
		add(logView);
		
		runBtn = new JButton();
		
		runBtn.setIcon(IconMaker.create("res/run.png", 150, 60));
		runBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(hasVideoSettingCompleted()) {
					logView.clearLog();
					new Thread() {
						public void run() {
							VideoRender videoRender = new VideoRender(setting.getVideoSpecs(), logView);
							List<File> filesSelected = fileView.getSelectedFile();
							for(int i=0; i<filesSelected.size(); i++) {
								File txtFile = filesSelected.get(i);
								logView.addLog("Rendering video with " + txtFile.getName());
								videoRender.render(txtFile);
								logView.addLog("Finished video with " + txtFile.getName());
							}
							runBtn.setEnabled(true);
						}
					}.start();
					runBtn.setEnabled(false);
				}
			}
		});
		runBtn.setBounds(290, 500, 150, 60);
		add(runBtn);
		
		previewBtn = new JButton();
		previewBtn.setIcon(IconMaker.create("res/preview.png", 150, 60));
		previewBtn.setBounds(540, 500, 150, 60);
		previewBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(hasVideoSettingCompleted()) {
					new Preview(setting.getVideoSpecs())
						.setVisible(true);
				}
				
			}
		});
		add(previewBtn);
	}
	
	
	
	private boolean hasVideoSettingCompleted() {
		return setting.getVideoSpecs() != null;
	}
	
}
