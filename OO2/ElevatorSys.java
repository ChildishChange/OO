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
				//�˴���Ϊ��������
				i = R_L.ReqParse(Str);
			
			}while(i==1||i==2);//�˴���bug
			
				
			if(i==0)
			{
				Ctr.ElevatorRun(R_L,Ele);
			}
			else
			{
				System.out.println("������˼�������ʽ���󣬱����������в�������.");
				//�������������ʾ
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
