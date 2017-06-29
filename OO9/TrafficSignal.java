package DDDC;

public class TrafficSignal extends Thread
{
	private boolean NS = false;
	private boolean EW = false;
	public boolean repOK()
	{
		return this.NS^this.EW;
	}
	public TrafficSignal()
	{
	
		this.NS = false;
		this.EW = true;
	}
	public boolean getNS()
	{
		//Requires:nothing
		//Modifies:nothing
		//Effects:change the value of the variable		
		synchronized(this)
		{
			return this.NS;
		}
	}
	public boolean getEW()
	{
		//Requires:nothing
		//Modifies:nothing
		//Effects:change the value of the variable		
		synchronized(this)
		{
			return this.EW;
		}
	}
	public void run()
	{
		while(true)
		{
			synchronized(this)
			{
				this.NS = false;
				this.EW = true;
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
			synchronized(this)
			{
				this.NS = true;
				this.EW = false;
			}
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
