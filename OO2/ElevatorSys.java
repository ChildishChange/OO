package OO_2;
import java.util.*;
public class ElevatorSys 
{
	public static void main (String args[])
	{
		RequestList R_L = new RequestList();
		Elevator Ele = new Elevator();
		Dispatcher Ctr = new Dispatcher();
		Scanner input = new Scanner(System.in);
		String Str = new String();
		
		try
		{
			int i = 0;
			do
			{
				Str = input.nextLine();
				//此处改为单行输入
				i = R_L.ReqParse(Str);
			
			}while(i==1||i==2);//此处有bug
			
				
			if(i==0)
			{
				Ctr.ElevatorRun(R_L,Ele);
			}
			else
			{
				System.out.println("不好意思，输入格式错误，本次请求序列不能生成.");
				//输入错误如上所示
			}
			
		
				
			input.close();	
		}
			
		catch (Throwable e)
		{
			System.out.println("------------exception handling------------");
			System.out.println("illegal controlling because of illedge input");
		}
	}
	
}
