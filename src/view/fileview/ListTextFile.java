package view.fileview;

import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListTextFile extends JList<FileSelector>{
	private DefaultListModel model = new DefaultListModel<>();
	public ListTextFile() {
		setModel(model);
		setCellRenderer(new CellRenderer());
		addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				 int index = locationToIndex(e.getPoint());
//				 System.out.println(index);
	                if (index != -1) {
	                	FileSelector file = (FileSelector) getModel().getElementAt(index);
	                	file.setSelected(!file.isSelected());
	                    repaint();
	                }
			}
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
			
		});
	}
	
	public void loadFile(String folderPath) {
		File directory = new File(folderPath);
		File[] files = directory.listFiles();
		List<FileSelector> listFile = new ArrayList<>();
		for(File file : files) {
			String fileName = file.getName();
			if(fileName.contains(".txt")) {
				listFile.add(new FileSelector(fileName, true, file.getAbsolutePath()));
			}
		}
		
		FileSelector[] fileArr = listFile.toArray(new FileSelector[listFile.size()]);
		Arrays.sort(fileArr);
		this.setListData(fileArr);
	}
	
	class CellRenderer implements ListCellRenderer<FileSelector>{

		@Override
		public Component getListCellRendererComponent(JList<? extends FileSelector> list,
				FileSelector file, int index,
				boolean isSelected, boolean cellHasFocus) {
			
			file.setBackground(isSelected?
					getSelectionBackground():getBackground());
			file.setForeground(isSelected?
					getSelectionForeground():getForeground());
			file.setEnabled(isEnabled());
			file.setFont(getFont());
			file.setFocusPainted(false);
			file.setBorderPainted(true);
			
			return file;
		}
		
	}
}
