package OO_2;

public interface EleBehavior 
{
	final int MaxFloor = 10;
	final int MinFloor = 1;
	
	//电梯应该设置一个方向
	
	
	public void Up(int Floor,Dispatcher Ctr);
	public void Down(int Floor,Dispatcher Ctr);
	public void Stay(int Floor,Dispatcher Ctr);
	public void Goto(int Floor,Dispatcher Ctr);

}
