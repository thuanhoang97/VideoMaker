package view.fileview;

import javax.swing.JCheckBox;

public class FileSelector extends JCheckBox implements Comparable<FileSelector>{
	private String path;
	private String textName;
	
	public FileSelector(String textName, boolean isCheck, String path) {
		super(textName, isCheck);
		this.path = path;
		this.textName = textName;
		
	}
	
	public String getPath() {
		return path;
	}
	
	public String getTextName() {
		return textName;
	}

	@Override
	public int compareTo(FileSelector fs) {
		int file1=0, file2=0;
		try {
			file1 = Integer.parseInt(this.textName.split("\\.")[0]);
			file2 = Integer.parseInt(fs.getTextName().split("\\.")[0]);
		}catch(Exception ex){
			
		}
		return file1-file2; 
	}
}
