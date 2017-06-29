package OO_2;

public class Dispatcher 
{
	private double TimeOfDispatch = 0;
	public double getTimeOfDispatch()
	{
		return this.TimeOfDispatch;
	}
	public void changeTimeOfDispatch(double t)
	{
		this.TimeOfDispatch+=t;
	}
	public void Synchronization(int n)
	{
		this.TimeOfDispatch = (double)n;
	}
	//现在的问题是时间轴上有两个t，一是请求生成的t 二是请求执行的t->这个地方有个小问题
	public void ElevatorRun(RequestList L,Elevator E)
	{
		Request temp = new Request();
		if(L.IsEmpty())
		{
				//System.out.println("请求序列为空");
				System.out.println("(1,STAY,0)");
				return ;
		}
		while(!L.IsEmpty())
		{
			
			temp = L.PopRequest();
			if(TimeOfDispatch<temp.getTime())
			{
				this.Synchronization(temp.getTime());
			}
		//	E.Goto(temp.getFloor(),this);
			
			
		}
		return;
		
	}
}
