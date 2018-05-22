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
		return path.compareTo(fs.getTextName());
	}
}
