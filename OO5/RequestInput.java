import java.util.Scanner;

public class RequestInput implements Runnable
{
	private RequestList HolyCrapList;
	private long SysStartTime;
	public RequestInput(RequestList L,long t)
	{
		this.HolyCrapList = L;
		this.SysStartTime = t;

	}
	/*=====================================*/
	public void run()
	{
		try
		{
			Scanner input = new Scanner(System.in);
			String Str = new String();
			Request temp = new Request();
			while(true)
			{
			//	System.out.println("input");
				Str = input.nextLine();
			//	System.out.println("havedone");
				temp = HolyCrapList.ReqParse(Str,(System.currentTimeMillis()-SysStartTime));
			//	System.out.println(temp.getEle()+" dsf");
				if(temp.getSuccess()==false)
				{
					System.out.println("����δ�ɹ�����������ԭ������");
				}
				else
				{	
					HolyCrapList.push(temp);
				}
				
			//	System.out.println("rear = "+HolyCrapList.getRear());
			}
		}
		catch(Exception e){}
		//�ǵü�try catch
	}
}