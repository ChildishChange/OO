package OO_2;

public class Request 
{
	private int Type;//1:FR,2:ER
	private int Floor;
	private int U_D;//其实我觉得这个状态没什么卵用，完全是给最高级的设计的//-1:DOWN,0:STAY:1:UP
	private int Time;
	private int Runtime;
	private boolean notDone = true;
	public void setUD(int i)
	{
		this.U_D = i;
	}
	public int getUD()
	{
		return this.U_D;
	}
	public int getType()
	{
		return this.Type;
	}
	public int getRuntime()
	{
		return this.Runtime;
	}
	public void setRuntime(int i)
	{
		this.Runtime =  i;
	}
	public int getFloor()
	{
		return this.Floor;
	}
	public int getTime()
	{
		return this.Time;
	}
	public boolean getNotDone()
	{
		return this.notDone;
	}
	public void setNotDone()
	{
		this.notDone = false;
	}
	public void setDone()
	{
		this.notDone = true;
	}
	public void RequestInit()
	{
		this.Type = 0;
		this.Floor = 0;
		this.U_D =	0;
		this.Time = 0;
		this.notDone = true;
	}
	
	public void setType(int Type)
	{
		this.Type = Type;
	}
	public void setTime(int Time)
	{
		this.Time = Time;
	}
	public void setFloor(int Floor)
	{
		this.Floor = Floor;
	}
	public void Duplicate(Request R)
	{
		this.Type = R.Type;
		this.Floor = R.Floor;
		this.U_D = R.U_D;
		this.Time = R.Time;
	}
	public String toString(Request R)
	{
		
		if(R.getType()==1&&R.getUD()==-1)	
			return "(FR,"+R.getFloor()+","+"DOWN,"+R.getTime()+")";
		else if(R.getType()==1&&R.getUD()==1)	
			return "(FR,"+R.getFloor()+","+"UP,"+R.getTime()+")";
		else if(R.getType()==2)	
			return "(ER,"+R.getFloor()+","+R.getTime()+")";
		else if(R.getType()==2)	
			return "(ER,"+R.getFloor()+","+R.getTime()+")";
		return null;

	}
	
	
	public boolean judge(Request temp,Request temp2,double t,Elevator E)
	{
		if(temp.getType()==1/*LOUCENG*/)
		{
			if(temp.getFloor()>E.getFloor())//UP
			{
				if((temp2.getType()==1/*louceng*/)&&
				   (temp2.getUD()==1)&&//方向相同
				   (temp2.getTime()<(t+0.5*(temp2.getFloor()-E.getFloor())))&&//请求生成在电梯到达该楼层前
				   (temp2.getFloor()<temp.getFloor()&&
				   (temp2.getFloor()>E.getFloor())))//
				{
					return true;
					
				}
				else if((temp2.getType()==2/*电梯*/)&&
						(temp2.getTime()<(t+0.5*(temp2.getFloor()-E.getFloor())))&&
						(temp2.getTime()>=temp.getTime())&&
						(temp2.getFloor()>E.getFloor())
						)
				{
					if(temp.getUD()==-1&&temp2.getFloor()>temp.getFloor())
						return true;
					return true;
				}
			}
			else if(temp.getFloor()<E.getFloor())//DOWN
			{
				if((temp2.getType()==1/*louceng*/)&&
				   (temp2.getUD()==-1)&&
				   (temp2.getTime()<(t+0.5*(E.getFloor()-temp2.getFloor())))&&//请求生成在电梯到达该楼层前
				   (temp2.getFloor()>temp.getFloor())&&
					(temp2.getFloor()<E.getFloor()))//
				{
					return true;
				}
				else if((temp2.getType()==2)&&
						(temp2.getTime()<(t+0.5*(E.getFloor()-temp2.getFloor())))&&
						(temp2.getTime()>=temp.getTime())&&
						(temp2.getFloor()<E.getFloor())&&
						(temp2.getFloor()<temp.getFloor()))
				{
					if(temp.getUD()==1&&temp2.getFloor()<temp.getFloor())
						return true;
					return true;
				}
			}
		}
		else if(temp.getType()==2)
		{
			if(temp.getFloor()>E.getFloor())//UP
			{
				if((temp2.getType()==1/*louceng*/)&&
				   (temp2.getUD()==1)&&
				   (temp2.getTime()<(t+0.5*(temp2.getFloor()-E.getFloor())))&&
				   (temp2.getFloor()<temp.getFloor())&&
					(temp2.getFloor()>E.getFloor()))
				{
					return true;
				}
				else if((temp2.getType()==2)&&
						(temp2.getFloor()>E.getFloor())&&
						(temp2.getTime()<(t+0.5*(temp2.getFloor()-E.getFloor()))&&
								(temp2.getFloor()>E.getFloor())))
				{
					return true;
				}
			}
			else if(temp.getFloor()<E.getFloor())//DOWN
			{
				if((temp2.getType()==1/*louceng*/)&&
				   (temp2.getUD()==-1)&&
				   (temp2.getTime()<(t+0.5*(E.getFloor()-temp2.getFloor())))&&
				   (temp2.getFloor()<temp.getFloor()&&
							(temp2.getFloor()<E.getFloor())))
				{
					return true;
				}
				else if((temp2.getType()==2)&&
						(temp2.getFloor()<E.getFloor())&&
						(temp2.getTime()<(t+0.5*(E.getFloor()-temp2.getFloor()))&&
								(temp2.getFloor()<E.getFloor())))
				{
					return true;
				}
			}
		}
		
		return false;
	}
}
