package OO_2;

public class Elevator 
{
	
	private int Floor;
	
	
	
	public Elevator()
	{
		this.Floor = 1;
	}
	
	//以下三个函数的主要功能是修改时间和电梯状态，然后呢，然后应该没有了
	public void Up(int Floor,Dispatcher Ctr)
	{
		int distance = 0;
		distance = Floor - this.Floor;
		Ctr.changeTimeOfDispatch(distance*0.5);
		Ctr.changeTimeOfDispatch(1);
		System.out.println("("+Floor+",UP,"+Ctr.getTimeOfDispatch()+")");
		this.Floor = Floor;
		
	}
	public void Down(int Floor,Dispatcher Ctr)
	{
		int distance = 0;
		distance = this.Floor - Floor;
		Ctr.changeTimeOfDispatch(distance*0.5);
		Ctr.changeTimeOfDispatch(1);
		System.out.println("("+Floor+",DOWN,"+Ctr.getTimeOfDispatch()+")");
		this.Floor = Floor;
	}
	public void Stay(int Floor,Dispatcher Ctr)
	{
		Ctr.changeTimeOfDispatch(1);
		System.out.println("("+Floor+",STAY,"+Ctr.getTimeOfDispatch()+")");
	}
	public void Goto(int Floor,Dispatcher Ctr)
	{
		if(this.Floor<Floor)
		{
			this.Up(Floor,Ctr);
		}
		else if(this.Floor==Floor)
		{
			this.Stay(Floor,Ctr);
		}
		else
		{
			this.Down(Floor,Ctr);
		}
		
	}

}
