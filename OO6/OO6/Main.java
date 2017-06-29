package OO6;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main 
{
	/*
	 * 在这里有输入
	 * 调用ParseCommand
	 * 
	 * */
	
	public static void main(String[] args)
	{
		ExecutorService threadPool = Executors.newCachedThreadPool();  
		Summary summary = new Summary();
		CommandList list = new CommandList();
		Scanner input = new Scanner(System.in);
		String Str = new String();
		Command temp = new Command();
		while(true)
		{
		//	System.out.println("input");
			Str = input.nextLine();
		//	System.out.println("havedone");
			temp = list.ParseCommand(Str);
			if(temp!=null)
			{
			//	temp.getFileTree().PrintDirectory(temp.getFileTree());
				list.push(temp);
			}
			else if(temp==null&&Str.equals("FINISH"))
			{
				break;
			}
			else
			{
				continue;
			}

		}
		
		input.close();
		for(int i = 0;i < list.getLength();i++)
		{  
            final int taskID = i;  
		//	System.out.println("第"+i+"个for循环");
            threadPool.execute(new Runnable() 
			{  
                public void run() 
				{
                	try
                	{
                		if(list.getList(taskID).getCommandType()==1)//文件
	                	{
	                		FileTreeNode prev = list.getList(taskID).getFileTree();
	                		while(true)
	    					{
	//    					prev.PrintDirectory(prev);
	    						try 
	    						{
	    							Thread.sleep(50);
	    						} 
	    						catch (InterruptedException e) 
	    						{
	    							// TODO Auto-generated catch block
	    							e.printStackTrace();
	    						}
	    							
	    						FileTreeNode Current = new FileTreeNode(list.getList(taskID).getParentPath());
	    				//		Current.PrintDirectory(Current);
	    						Current.TreeComparison1(Current,prev,Current,list.getList(taskID).getName(),summary,list.getList(taskID),list.getList(taskID).getTrigger(),list.getList(taskID).getTask());
	    				//		list.getList(taskID);//yaoqiugai
	    						prev = Current;
	    				
	    						//Current = new FileTreeNode("G:\\课程\\OO\\作业六\\A");
	    					}
	                	}
	                	else
	                	{
	    					FileTreeNode prev = list.getList(taskID).getFileTree();
	    					while(true)
	    					{
	//    					prev.PrintDirectory(prev);
	    						try 
	    						{
	    							Thread.sleep(50);
	    						} 
	    						catch (InterruptedException e) 
	    						{
	    							// TODO Auto-generated catch block
	    							e.printStackTrace(); 
	    						}
	    							
	    						FileTreeNode Current = new FileTreeNode(list.getList(taskID).getPath());
	    				//		Current.PrintDirectory(Current);
	    						Current.TreeComparison(Current,prev,Current,summary,list.getList(taskID).getTrigger(),list.getList(taskID).getTask());
	    							
	    						prev = Current;
	    				
	    						//Current = new FileTreeNode("G:\\课程\\OO\\作业六\\A");
	    					}
	                	}
                	}
                	catch(Exception e)
                	{
                		
                	}
                	
					
			    
                }  
            });  
        }  
        threadPool.shutdown();// 任务执行完毕，关闭线程池  
	}

	
	//然后是start
	
}
