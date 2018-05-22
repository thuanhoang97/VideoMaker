package view.logview;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class LogView extends JPanel{
	private JLabel runLogLb;
	private JTextArea logger;
	
	public LogView() {
		this.setLayout(null);
		
		runLogLb = new JLabel("Running Log: ");
		runLogLb.setBounds(20, 38, 100, 20);
		add(runLogLb);
		
		logger = new JTextArea(12,40);
		logger.setEditable(false);
		Dimension logSize = logger.getPreferredSize();
		add(logger);
		JScrollPane runLog = new JScrollPane(logger);
		runLog.setBounds(20, 68, logSize.width, logSize.height);
		add(runLog);
	}
	
	public void addLog(String log) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		Date date = new Date();
		logger.append(formatter.format(date) + " : " +log+"\n");
		repaint();
	}
	
	public void clearLog() {
		logger.setText("");
		repaint();
	}
	
}
