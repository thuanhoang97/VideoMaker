package view.fileview;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



public class TextFileView extends JPanel{
	private ListTextFile listFiles;
	private JScrollPane scrollView;
	private JLabel textFolderLb;
	private JTextField textFolderTf;
	private JButton textFolderBtn;

	
	public TextFileView() {
		this.setLayout(null);
		
		listFiles = new ListTextFile();
		
		textFolderLb = new JLabel("Text Folder:");
		textFolderLb.setBounds(20, 0, 100, 20);
		add(textFolderLb);
	
		textFolderTf = new JTextField();
		textFolderTf.setBounds(130, 0, 300,20);
		add(textFolderTf);
		
		textFolderBtn = new JButton("Load text file");
		textFolderBtn.setBounds(125, 30, 200, 30);
		textFolderBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String directory = textFolderTf.getText();
				if(new File(directory).isDirectory()) {
					listFiles.loadFile(textFolderTf.getText());
					if(listFiles.getModel().getSize()==0) {
						JOptionPane.showMessageDialog(null,"No have text file at " + directory, 
								 "No text file", JOptionPane.ERROR_MESSAGE);
					}
				}else {
					JOptionPane.showMessageDialog(null,"Can not file the path " + directory, 
							 "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		add(textFolderBtn);
		
		scrollView = new JScrollPane(listFiles);
		scrollView.setBounds(85, 65, 280,200);
		scrollView.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollView);	

	}
	
	public List<File> getSelectedFile() {
		List<File> filesSelected = new ArrayList<>();
		for(int i=0; i<listFiles.getModel().getSize();i++) {
			FileSelector fileSelector = listFiles.getModel().getElementAt(i);
			if(fileSelector.isSelected()) {
				filesSelected.add(new File(fileSelector.getPath()));
//				System.out.println(file.getPath());
			}
		}
		return filesSelected;
	}
}
