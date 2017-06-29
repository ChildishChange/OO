package OO_2;

public class Elevator implements EleBehavior
{
	
	private int Floor;
	private int UD;
	private double time;
	//电梯应该设置一个方向
	
	public Elevator()
	{
		this.Floor = 1;
	}
	public int getFloor()
	{
		return this.Floor;
	}
	public String toString()
	{
		String output = new String();
		output = this.Floor + " ";
		
		if (this.UD == 1)
			output += "UP";
		else if (this.UD == -1)
			output += "DOWN";
		else
			output += "STAY";
		
		return output;
	}
	
	//以下三个函数的主要功能是修改时间和电梯状态，然后呢，然后应该没有了
	public void Up(int Floor,Dispatcher Ctr)
	{
		this.UD = 1;
		int distance = 0;
		distance = Floor - this.Floor;
	//	System.out.println(Ctr.getTimeOfDispatch());
	//	System.out.println(distance*0.5);
		Ctr.changeTimeOfDispatch(distance*0.5);
	//	System.out.println(Ctr.getTimeOfDispatch());
		System.out.println("("+Floor+",UP,"+Ctr.getTimeOfDispatch()+")");
		//设置电梯状态为UP
		Ctr.changeTimeOfDispatch(1);
		this.time = Ctr.getTimeOfDispatch()-1;
		this.Floor = Floor;
		
	}
	public void Down(int Floor,Dispatcher Ctr)
	{
		this.UD = -1;
		int distance = 0;
		distance = this.Floor - Floor;
	//	System.out.println(Ctr.getTimeOfDispatch());
		Ctr.changeTimeOfDispatch(distance*0.5);
	//	System.out.println(distance*0.5);
	//	System.out.println(Ctr.getTimeOfDispatch());
		System.out.println("("+Floor+",DOWN,"+Ctr.getTimeOfDispatch()+")");
		//设置电梯状态为down
		Ctr.changeTimeOfDispatch(1);
		this.time = Ctr.getTimeOfDispatch()-1;
		this.Floor = Floor;
	}
	public void Stay(int Floor,Dispatcher Ctr)
	{
		this.UD = 0;
		Ctr.changeTimeOfDispatch(1);
		System.out.println("("+Floor+",STAY,"+Ctr.getTimeOfDispatch()+")");
		this.time = Ctr.getTimeOfDispatch()-1;
		//设置电梯状态为stay
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
