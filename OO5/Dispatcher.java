
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
	//���ڵ�������ʱ������������t��һ���������ɵ�t ��������ִ�е�t->����ط��и�С����
	public void ElevatorRun(RequestList L,Elevator E)
	{
		Request temp = new Request();
		if(L.IsEmpty())
		{
				//System.out.println("��������Ϊ��");
				System.out.println("(1,STAY,0)");
				return ;
		}
		while(!L.IsEmpty())
		{
			
			temp = L.pop();
			if(TimeOfDispatch<temp.gettime())
			{
				this.Synchronization((int)(temp.gettime()));
			}
		//	E.Goto(temp.getFloor(),this);
			
			
		}
		return;
		
	}
}
