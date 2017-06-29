package OO6;

import java.util.*;
import java.util.regex.Pattern;
import java.io.*;

public class CommandList implements Runnable
{
//	private Command Commad[];
	private ArrayList<Command> CommandList;
	CommandList(){this.CommandList = new ArrayList<Command>();}
	public void push(Command A){this.CommandList.add(A);}
	public Command getList(int i){return this.CommandList.get(i);}
	public int getLength(){return this.CommandList.size();}
	
	
	public Command ParseCommand(String S)
	{
		try
		{
			Command temp = new Command();
			String [] str=S.split(",");
			if(str.length!=5)
			{
				if(S.equals("FINISH"))
				{
					return null;
				}
				System.out.println("Invalid Input!");
				return null;
			}
	//		for(String substr : str)
	//		{
	//			System.out.println(substr);
	//		}
			
			if(S.equals(""))
			{
				System.out.println("Error,Invalid Command!");
				return null;
			}
			
			
			if(str[0].equals("IF")==false)
			{
				return null;
			}
			else
			{
				File tempfile = new File(str[1]);
				if(tempfile.isFile()==false&&tempfile.isDirectory()==false)
				{
					System.out.println("Invalid Path!");
					return null;
				}
				else
				{
				//	System.out.println("Valid Path!");
					temp.setPath(str[1]);
					
					if(str[2].equals("renamed")==false&&
					   str[2].equals("modified")==false&&
					   str[2].equals("path-changed")==false&&
					   str[2].equals("size-changed")==false)
					{
						System.out.println("Invalid Trigger!");
					}
					else 
					{
						
				//		System.out.println("Valid Trigger!");
						if(str[2].equals("renamed"))
						{
							temp.setTrigger(1);
						}
						else if(str[2].equals("modified"))
						{
							temp.setTrigger(2);
						}
						else if(str[2].equals("path-changed"))
						{
							temp.setTrigger(3);
						}
						else
						{
							temp.setTrigger(4);
						}
						
						if(str[3].equals("THEN")==false)
						{
							return null;
						}
						else
						{
							if(str[4].equals("record-summary")==false&&
							   str[4].equals("record-detail")==false&&
							   str[4].equals("recover")==false)
							{
								System.out.println("Invalid task!");
								return null;
							}
							else
							{
								if(str[4].equals("record-summary"))
								{
									temp.setTask(1);
								}
								else if(str[4].equals("record-detail"))
								{
									temp.setTask(2);
								}
								else
								{
									temp.setTask(3);
								}
								temp.setCommand(S);
								temp.setPath(str[1]);
								temp.setFileTree(str[1]);
								temp.setParentPath(temp.getFileTree().getParentFileName());
								if(temp.getFileTree().getFileNode().isFile())
								{
									temp.setCommandType(1);
									temp.setName(temp.getFileTree().getFileNode().getName());
									temp.setFileTree(temp.getFileTree().getParentFileName());
								}
								else
								{
									temp.setName(temp.getFileTree().getFileNode().getName());
									temp.setCommandType(2);
								}
								
								return temp;
							}
								
						}
						
					}
				}
				
			}
	
			
			//IF Absolutepath trigger THEN task
			
			return null;			
		}
		catch(Exception e)
		{
			
		}
		return null;	
	}
	
	public void run()
	{
		
	}
}
