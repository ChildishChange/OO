package OO6;

public class Command 
{
	private String command;
	private String path;
	private String name;
	private int trigger=0;//1rename2modified3path4size
	private int task=0;//1recordsummary2recorddetail3recover
	private FileTreeNode FileTree;//文件的fileTree是他的父目录。。。
	private int CommandType = 0;//1file2direcotry
	private String ParentPath;
	
	public void setCommand(String S){this.command = S;}
	public void setPath(String S){this.path = S;}
	public void setTrigger(int i){this.trigger = i;}
	public void setTask(int i){this.task = i;}
	public void setFileTree(String S){this.FileTree = new FileTreeNode(S);}
	public void setFileTreeNode(FileTreeNode FTN){this.FileTree = FTN;}
	public void setCommandType(int i){this.CommandType = i;}
	
	public String getCommand(){return this.command;}
	public String getPath(){return this.path;}
	public int getTrigger(){return this.trigger;}
	public int getTask(){return this.task;}
	public FileTreeNode getFileTree(){return this.FileTree;}public int getCommandType(){return this.CommandType;}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParentPath() {
		return ParentPath;
	}
	public void setParentPath(String parentPath) {
		ParentPath = parentPath;
	}
	

}
