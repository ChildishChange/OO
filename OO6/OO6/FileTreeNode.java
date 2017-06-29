package OO6;
import java.util.*;
import java.io.*;
public class FileTreeNode implements Runnable
{
	private String AbsolutePath;
	private String ParentFileName;
	private String FileName;
	private File FileNode;
	private ArrayList<FileTreeNode> Contents;
	private long FileSize;
	private long lastModifiedTime;
	private int FOD = 0;//0为file,1为Directory
//	private int NumOfObj = 0;

	FileTreeNode()
	{
		
	}
	FileTreeNode(String S)
	{
		this.AbsolutePath = S;
		this.FileNode = new File(S);
	//	System.out.println();
		if(FileNode.isDirectory()==false&&FileNode.isFile()==false)
		{
			System.out.println("Error:Invalid Pathname!");
		
		} 

		if(FileNode.isDirectory())	
		{
			this.FOD = 1;
			this.Contents = this.TraverseBulid(this);
		}
		this.FileName = this.FileNode.getName();
		this.FileSize = this.getSize(this);
		this.setParentFileName(this.FileNode.getParent());
		this.lastModifiedTime = this.FileNode.lastModified();
//		this.NumOfObj = this.Contents.size();
//		else
//		{
//			this.Contents.clear();
//		}
		
		
	}
	public String getParentFileName() {
		return ParentFileName;
	}
	public void setParentFileName(String parentFileName) {
		ParentFileName = parentFileName;
	}
	public File getFileNode()
	{
		return this.FileNode;
	}
	public FileTreeNode Search(FileTreeNode FTN,String Path)
	{
		FileTreeNode temp = null;
		if(FTN.AbsolutePath.equals(Path))
			return FTN;

		for(FileTreeNode ChildFTN : FTN.Contents)
		{
			if(ChildFTN.AbsolutePath.equals(Path))
			{
				return ChildFTN;
			}
			else if(ChildFTN.FOD==1&&(ChildFTN.AbsolutePath.equals(Path)==false))
			{
				temp = Search(ChildFTN,Path);
				if(temp!=null)
					return temp;
			}
		}
		return null;
	}
	public FileTreeNode SearchName(FileTreeNode FTN,FileTreeNode node,FileTreeNode Curr)
	{
//		System.out.println("啊飒飒、");
	//	FTN.PrintDirectory(FTN);
		
		//FTN 中找node
		FileTreeNode temp = null;

			for(FileTreeNode ChildFTN : FTN.Contents)
			{
//				System.out.println("loop:"+ChildFTN.FileName);
				if(ChildFTN.FOD==0)
				{					
//					System.out.println("1:"+ChildFTN.FileName+"\n  "+node.FileName);
//					System.out.println("2:"+ChildFTN.FileSize+"\n  "+node.FileSize);
//					System.out.println("3:"+ChildFTN.lastModifiedTime+"\n  "+node.lastModifiedTime);
//					System.out.println("4:"+ChildFTN.AbsolutePath+"\n  "+node.AbsolutePath);
					if(ChildFTN.FileName.equals(node.FileName)&&//名字相同
					   ChildFTN.FileSize==node.FileSize&&//文件大小相同
					   ChildFTN.lastModifiedTime==node.lastModifiedTime&&//最后修改时间相同
					   !ChildFTN.AbsolutePath.equals(node.AbsolutePath)&&
					   ChildFTN.Search(Curr, ChildFTN.AbsolutePath)==null)//绝对路径不一样
					{
						return ChildFTN;
					}

					
				}
				else if(ChildFTN.FOD==1)
				{
//					System.out.println(ChildFTN.AbsolutePath);
					temp = SearchName(ChildFTN,node,Curr);
					if(temp!=null)
					{
						return temp;
					}
					else
					{
//						System.out.println("In Path:"+ChildFTN.AbsolutePath+",Not Found!");
					}
						
				}
				
			}			
	
	//	
		return null;
	}
	public FileTreeNode SearchReName(FileTreeNode Prev,FileTreeNode ReNamedFile,FileTreeNode Current)
	{
		for(FileTreeNode CurrChildFile : Prev.Contents)//从当前目录的每一个文件开始查找
		{
			if(CurrChildFile.FOD==0)
			{
				if(ReNamedFile.FileName.equals(CurrChildFile.FileName)==false&&//名字相同
				   ReNamedFile.FileSize==CurrChildFile.FileSize&&//文件大小相同
	     		   ReNamedFile.lastModifiedTime==CurrChildFile.lastModifiedTime&&
	     		   CurrChildFile.Search(Current,CurrChildFile.AbsolutePath)==null)//最后修改时间相同
		     	//   !ReNamedFile.AbsolutePath.equals(CurrChildFile.AbsolutePath))//绝对路径不一样
				{
					return CurrChildFile;
		    	}
			}
		}
		return null;
	}
	public long getSize(FileTreeNode Node)
	{
		long length = 0L;
		if(Node.FileNode.isFile())
		{	
			length+=Node.FileNode.length();
			return length;
		}
		else if(Node.FileNode.isDirectory()&&!Node.Contents.isEmpty())
		{
			for(FileTreeNode ChildFTN : Node.Contents)
			{
				if(ChildFTN.FileNode.isFile())
				{
					length+=ChildFTN.FileNode.length();
				}
				else
				{
					length+=getSize(ChildFTN);
				}
				
			}
		}
		
		return length;
	}
	public static void TreeComparison1(FileTreeNode Current,FileTreeNode Prev,FileTreeNode Main,String thisFile,Summary summary,Command command,int trigger,int task)
	{
		try 
		{
            File file = new File("D:\\detail.txt");
            PrintStream bw = new PrintStream(new FileOutputStream(file));
			
			for(FileTreeNode CurrChildFile : Current.Contents)//从当前目录的每一个文件开始查找
			{
				if(CurrChildFile.FileNode.isDirectory())//从当前目录中提取出一个目录
				{
					FileTreeNode searchresult = null;
					searchresult = Prev.Search(Prev,CurrChildFile.AbsolutePath);//直接找老目录里有没有相同绝对路径且同名的东西
					if(searchresult==null)
					{
				//		System.out.println("Directory "+CurrChildFile.AbsolutePath+" had been added into "+CurrChildFile.FileNode.getParent()+"." );
						
					}
					TreeComparison1(CurrChildFile,Prev,Main,thisFile,summary,command,trigger,task);
					
					
				}
				else//从当前目录中提取出一个文件
				{
					
					FileTreeNode searchresult = null;
					searchresult = Prev.Search(Prev,CurrChildFile.AbsolutePath);//直接找老目录里有没有相同绝对路径且同名的东西
					if(searchresult!=null)//找到了
					{
				//		System.out.println(searchresult.FileName+" Found!");
						if(searchresult.lastModifiedTime==CurrChildFile.lastModifiedTime//修改时间相同
						 &&searchresult.FileSize==CurrChildFile.FileSize//大小相同
						 &&searchresult.FileName.equals(thisFile))//文件名是那个文件名
						{
			//				System.out.println(searchresult.FileName+" Have not changed!");
							continue;
						}
						else
						{//1rename2modified3path4size
							//1recordsummary2recorddetail3recover
							if(searchresult.lastModifiedTime!=CurrChildFile.lastModifiedTime
							 &&searchresult.FileName.equals(thisFile)
							 &&trigger==2)//修改时间有变化
							{
								
								summary.setModifiedtimechange();
								if(task==1)//summary
								{
									bw.append("Have finished "+summary.getModifiedtimechange()+" times modified.");
									System.out.println("Have finished "+summary.getModifiedtimechange()+" times modified.");
								}
								else if(task==2)//detail
								{
									bw.append(searchresult.FileName+"\'s Last modified time changed.");
									System.out.println(searchresult.FileName+"\'s Last modified time changed.");
								}
							}
							if(searchresult.FileSize!=CurrChildFile.FileSize
							 &&searchresult.FileName.equals(thisFile)
							 &&trigger==4)//大小有变化
							{
								
								summary.setSizechange();
								if(task==1)//summary
								{
									bw.append(searchresult.FileName+"\'s Last modified time changed.");
									System.out.println("Have finished "+summary.getSizechange()+" time size changed.");
								}
								else if(task==2)//detail
								{
									bw.append(searchresult.FileName+"\'s Size changed");
									System.out.println(searchresult.FileName+"\'s Size changed");
								}
							}
						}
						
					}
					else //没有找到绝对路径相同且同名的东西,查找移动目录
					{
		//				System.out.println(CurrChildFile.FileName+" Not Found!");
						FileTreeNode changedir = null;
						changedir = Prev.SearchName(Prev,CurrChildFile,Main);
						if(changedir!=null&&changedir.FileName.equals(thisFile)&&trigger==3)
						{
							
							summary.setPathchange();
							if(task==1)//summary
							{
								bw.append("Have finished "+summary.getPathchange()+" time path changed.");
								System.out.println("Have finished "+summary.getPathchange()+" time path changed.");
							}
							else if(task==2)//detail
							{
								bw.append(changedir.FileName+" had changed direcory from:\n"+changedir.AbsolutePath+"\nto:"+CurrChildFile.AbsolutePath);
								System.out.println(changedir.FileName+" had changed direcory from:\n"+changedir.AbsolutePath+"\nto:"+CurrChildFile.AbsolutePath);
							}
							else
							{
								CurrChildFile.FileNode.renameTo(changedir.FileNode);
								CurrChildFile.AbsolutePath = changedir.AbsolutePath;
							}
	
						}
						else
						{
							changedir = Prev.SearchReName(Prev.Search(Prev, Current.AbsolutePath),CurrChildFile,Current);
							if(changedir!=null&&changedir.FileName.equals(thisFile)&&trigger==1)
							{
								summary.setRenamed();
								if(task==1)
								{
									bw.append("Have finished"+summary.getRenamed()+" time path changed.");
									System.out.println("Have finished"+summary.getRenamed()+" time path changed.");
								}
								else if(task==2)
								{
									bw.append(changedir.FileName+" have been renamed as:"+CurrChildFile.FileName);
									System.out.println(changedir.FileName+" have been renamed as:"+CurrChildFile.FileName);
								}
								else
								{
									CurrChildFile.FileNode.renameTo(changedir.FileNode);
									CurrChildFile.AbsolutePath = changedir.AbsolutePath;
								}
								
								changedir = Current.Search(Current, CurrChildFile.AbsolutePath);
								command.setName(CurrChildFile.FileName);
								
								continue;
							}
						//	System.out.println(CurrChildFile.FileName+" had been added into Directory "+CurrChildFile.FileNode.getParent()+"." );
						}
					}
				}
				//在就树立找到这个目录。。。
			}
		/*	for(FileTreeNode PrevChildFile : Prev.Contents)
			{
				
			}*/


		}
		catch(Exception e)
		{
			
		}

	}
	public static void TreeComparison(FileTreeNode Current,FileTreeNode Prev,FileTreeNode Main,Summary summary,int trigger,int task)
	{
		try
		{
			
			File file = new File("D:\\detail.txt");
            PrintStream bw = new PrintStream(new FileOutputStream(file));
			for(FileTreeNode CurrChildFile : Current.Contents)//从当前目录的每一个文件开始查找
			{
				if(CurrChildFile.FileNode.isDirectory())//从当前目录中提取出一个目录
				{
					FileTreeNode searchresult = null;
					searchresult = Prev.Search(Prev,CurrChildFile.AbsolutePath);//直接找老目录里有没有相同绝对路径且同名的东西
					if(searchresult==null)
					{
					//	System.out.println("Directory "+CurrChildFile.AbsolutePath+" had been added into "+CurrChildFile.FileNode.getParent()+"." );
						
					}
					
					TreeComparison(CurrChildFile,Prev,Main,summary,trigger,task);
					
					
				}
				else//从当前目录中提取出一个文件
				{
					//1rename2modified3path4size
					//1recordsummary2recorddetail3recover
					FileTreeNode searchresult = null;
					searchresult = Prev.Search(Prev,CurrChildFile.AbsolutePath);//直接找老目录里有没有相同绝对路径且同名的东西
					if(searchresult!=null)//找到了
					{
				//		System.out.println(searchresult.FileName+" Found!");
						if(searchresult.lastModifiedTime==CurrChildFile.lastModifiedTime//修改时间相同
						 &&searchresult.FileSize==CurrChildFile.FileSize)//文件大小没有变化
						{
			//				System.out.println(searchresult.FileName+" Have not changed!");
							continue;
						}
						else
						{
							if(searchresult.lastModifiedTime!=CurrChildFile.lastModifiedTime&&trigger==2)//修改时间有变化
							{
								summary.setModifiedtimechange();
								if(task==1)//summary
								{
									bw.append("Have finished "+summary.getModifiedtimechange()+" times modified.");
									System.out.println("Have finished "+summary.getModifiedtimechange()+" times modified.");
								}
								else if(task==2)//detail
								{
									bw.append(searchresult.FileName+"\'s Last modified time changed");
									System.out.println(searchresult.FileName+"\'s Last modified time changed");
								}
								
								
							}
							if(searchresult.FileSize!=CurrChildFile.FileSize&&trigger==4)//大小有变化
							{
								summary.setSizechange();
								if(task==1)//summary
								{
									bw.append("Have finished "+summary.getSizechange()+" time size changed.");
									System.out.println("Have finished "+summary.getSizechange()+" time size changed.");
								}
								else if(task==2)//detail
								{
									bw.append(searchresult.FileName+"\'s Size changed");
									System.out.println(searchresult.FileName+"\'s Size changed");
								}
							}
						}
						
					}
					else //没有找到绝对路径相同且同名的东西,查找移动目录
					{
		//				System.out.println(CurrChildFile.FileName+" Not Found!");
						FileTreeNode changedir = null;
						changedir = Prev.SearchName(Prev,CurrChildFile,Main);
						if(changedir!=null&&trigger==3)
						{
							summary.setPathchange();
							if(task==1)//summary
							{
								bw.append("Have finished "+summary.getPathchange()+" time path changed.");
								System.out.println("Have finished "+summary.getPathchange()+" time path changed.");
							}
							else if(task==2)//detail
							{
								bw.append(changedir.FileName+" had changed direcory from:\n"+changedir.AbsolutePath+"\nto:"+CurrChildFile.AbsolutePath);
								System.out.println(changedir.FileName+" had changed direcory from:\n"+changedir.AbsolutePath+"\nto:"+CurrChildFile.AbsolutePath);
							}
							else
							{
								CurrChildFile.FileNode.renameTo(changedir.FileNode);
								CurrChildFile.AbsolutePath = changedir.AbsolutePath;
							}
						}
						else
						{
							changedir = Prev.SearchReName(Prev.Search(Prev, Current.AbsolutePath),CurrChildFile,Current);
							if(changedir!=null)
							{
								if(trigger==1)
								{
									summary.setRenamed();
									if(task==1)
									{
										bw.append("Have finished"+summary.getRenamed()+" time path changed.");
										System.out.println("Have finished"+summary.getRenamed()+" time path changed.");
									}
									else if(task==2)
									{
										bw.append(changedir.FileName+" have been renamed as:"+CurrChildFile.FileName);
										System.out.println(changedir.FileName+" have been renamed as:"+CurrChildFile.FileName);
									}
									else
									{
										CurrChildFile.FileNode.renameTo(changedir.FileNode);
										CurrChildFile.AbsolutePath = changedir.AbsolutePath;
									}
		
									continue;
								}
							}
							else
							{
						//		System.out.println(CurrChildFile.FileName+" had been added into Directory "+CurrChildFile.FileNode.getParent()+"." );
							}
						}
					}
				}
				//在就树立找到这个目录。。。
			}
		/*	for(FileTreeNode PrevChildFile : Prev.Contents)
			{
				
			}*/
			
		}
		catch(Exception e)
		{
			
		}


	}
	public ArrayList<FileTreeNode> TraverseBulid(FileTreeNode Node)//build a directory tree in a traverse way
	{
		File[] ChildFiles = Node.FileNode.listFiles();
		ArrayList<FileTreeNode> Directory  = new ArrayList<FileTreeNode>();
		for(File ChildFile : ChildFiles )
		{
			FileTreeNode ChildNode = new FileTreeNode(ChildFile.toString());
			if(ChildFile.isDirectory())
			{
				//将这个Directory添加到节点中
				ChildNode.Contents = ChildNode.TraverseBulid(ChildNode); 
				Directory.add(ChildNode);
			}
			else
			{
				Directory.add(ChildNode);
				//将这个file添加到节点中
			}
		}
		return Directory;
		
	
	}
	public void PrintDirectory(FileTreeNode Node)//only for print
	{
		System.out.println(Node.AbsolutePath);
		for(FileTreeNode ChildNode : Node.Contents)
		{
			if(ChildNode.FOD==1)
			{
				PrintDirectory(ChildNode);
			}
			else
			{
				System.out.println(ChildNode.AbsolutePath);
			}
		}
	}
	
//	public static void main(String args[])//only for test
//	{
////		FileTreeNode prev = new FileTreeNode("G:\\课程\\OO\\作业六\\A");
////		
////		prev.PrintDirectory(prev);
////		FileTreeNode searchtest = null;
////		try 
////		{
////			Thread.sleep(5000);
////		} 
////		catch (InterruptedException e) 
////		{
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
////		FileTreeNode test = new FileTreeNode("G:\\课程\\OO\\作业六\\A\\新建文件夹\\新建文件夹 (2)\\新建文本文档.txt");
////		searchtest = prev.SearchName(prev,test);
////		if(searchtest==null)
////		{
////			System.out.println("Error.");
////		}
////		else
////		{
////			System.out.println("Search Result:"+searchtest.AbsolutePath+" "+searchtest.FileSize+"B");
////		}
////
////		
////		
////		
////		//prev.PrintDirectory(prev);
////		
////		
////		
////		
//		
//		
//		
//		
//		FileTreeNode prev = new FileTreeNode("G:\\课程\\OO\\作业六\\A\\新建文本文档.txt");
//		FileTreeNode PrevFather =  new FileTreeNode(prev.ParentFileName);
//		while(true)
//		{
////		prev.PrintDirectory(prev);
//			try 
//			{
//				Thread.sleep(5000);
//			} 
//			catch (InterruptedException e) 
//			{
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			FileTreeNode Current = new FileTreeNode("G:\\课程\\OO\\作业六\\A\\新建文本文档.txt");
//			//如果名字或者路径有变化 要记得修改command，此处应该使用command中的FileTreeNode
//			FileTreeNode CurrFather =  new FileTreeNode(Current.ParentFileName);
////			Current.PrintDirectory(Current);
//			FileTreeNode.TreeComparison1(CurrFather,PrevFather,CurrFather,Current);
//			
//			PrevFather = CurrFather;
//			
//			//Current = new FileTreeNode("G:\\课程\\OO\\作业六\\A");
//		}
//	}
	public void run()
	{
		
	}

}
