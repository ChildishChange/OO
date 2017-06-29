package OO_2;

public class Request 
{
	private int Type;//1:FR,2:ER
	private int Floor;
	private int U_D;//其实我觉得这个状态没什么卵用，完全是给最高级的设计的//-1:DOWN,0:STAY:1:UP
	private int Time;
	public int getType()
	{
		return this.Type;
	}
	public int getFloor()
	{
		return this.Floor;
	}
	public int getTime()
	{
		return this.Time;
	}
	public void getUD()
	{}
	
	public void RequestInit()
	{
		this.Type = 0;
		this.Floor = 0;
		this.U_D =	0;
		this.Time = 0;
	}
	
	public void setType(int Type)
	{
		this.Type = Type;
	}
	public void setTime(int Time)
	{
		this.Time = Time;
	}
	public void setUD(int UD)
	{
		this.U_D = UD;
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
	/*
	
	*/
	
}
