public class Elevator implements Runnable
{
	private int Floor;
	private int State;
	private long starttime;
	private int Movement=0;
	private RequestList taskInWaiting;
	
/*==============================*/
	public Elevator(RequestList taskInWaiting,long starttime)
	{
		this.Floor = 1;
		this.State = 0;
		this.taskInWaiting = taskInWaiting;
	//	System.out.println("taskofele"+this.taskInWaiting.IsEmpty());
		this.starttime = starttime;
	}
/*==========set/get=============*/
	public int getFloor(){return Floor;}
	public void setFloor(int i){this.Floor = i;}
	public int getState(){return State;}
	public void setState(int i){this.State = i;}	
	public int getMovement(){return this.Movement;}
	public RequestList getRequestList(){return this.taskInWaiting;}
/*==============================*/
	public void Down(int Floor,Request R)
	{
		this.State = -1;
		Request temp = new Request();
		int j = 0;
		while((this.Floor - Floor)>0)
		{
			try 
			{
				System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"DOWN,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")");
				Thread.sleep(3000);
				this.Floor--;//
				
			}
			catch (InterruptedException e){}
			for(int i =taskInWaiting.getFront()+1;i < taskInWaiting.getRear();i++)
			{
				if(this.Floor == taskInWaiting.getList(i).getFloor())
				{
					System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"STAY,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")"+taskInWaiting.getList(i).toString(taskInWaiting.getList(i)));
					//System.out.println(taskInWaiting.getList(i).toString(taskInWaiting.getList(i)));
					try 
					{
						Thread.sleep(6000);		
					}
					catch (InterruptedException e){}
					taskInWaiting.delete(i);
					i--;
				}
			}
			this.Movement++;

		}
		System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"STAY,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")"+R.toString(R));
		//System.out.println(R.toString(R));

		try 
		{
			Thread.sleep(6000);		
		}
		catch (InterruptedException e){}

	}

	public void Up(int Floor,Request R)
	{
		this.State = 1;
		Request temp = new Request();
		int j = 0;
		while((Floor - this.Floor)>0)
		{
			try 
			{
				System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"UP,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")");
				Thread.sleep(3000);
				this.Floor++;//
				
			}
			catch (InterruptedException e){}
			for(int i = taskInWaiting.getFront()+1;i < taskInWaiting.getRear();i++)
			{
				if(this.Floor == taskInWaiting.getList(i).getFloor())
				{
					System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"STAY,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")"+taskInWaiting.getList(i).toString(taskInWaiting.getList(i)));
					//System.out.println(taskInWaiting.getList(i).toString(taskInWaiting.getList(i)));
					try 
					{
						Thread.sleep(6000);		
					}
					catch (InterruptedException e){}
					taskInWaiting.delete(i);
					i--;
				}
			}
			this.Movement++;

		}
		System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"STAY,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")"+R.toString(R));
	//	System.out.println(R.toString(R));
		try 
		{
			Thread.sleep(6000);		
		}
		catch (InterruptedException e){}
	
	}
	public void Stay(int Floor,Request R)
	{
		this.State = 0;
		System.out.println("("+"#"+R.getEle()+","+"#"+this.Floor+","+"STAY,"+this.getMovement()+","+((System.currentTimeMillis()-starttime)/1000)+")"+R.toString(R));
		
		try 
		{
			Thread.sleep(6000);		
		}
		catch (InterruptedException e){}
	}
	public void Goto(int Floor,Request R)
	{
		if(this.Floor<Floor)
		{
			this.Up(Floor,R);
		}
		else if(this.Floor==Floor)
		{
			this.Stay(Floor,R);
		}
		else
		{
			this.Down(Floor,R);
		}
	}
/*==============================*/	
	public void run()
	{
		try
		{
			Request temp = new Request();
			while(true)
			{
				while(!taskInWaiting.IsEmpty())
				{
					temp = taskInWaiting.getList(0);
					
				//	System.out.println(taskInWaiting.IsEmpty()+"ssd");
		//			System.out.println(temp.toString(temp));
					this.Goto(temp.getFloor(),temp);
					taskInWaiting.delete(0);
					temp = null;
					this.setState(0);
	
				}
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e){}
			}
		}
		catch(Exception e){}
	}
}