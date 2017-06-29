package DDDC;

public class TrafficSignal extends Thread
{
	//overview：红绿灯有控制南北向行驶的信号灯，有东西方向形式的信号等，分别对应NS与EW，每隔300毫秒变化一次状态
	private boolean NS = false;
	private boolean EW = false;
	//抽象函数：AF(c) = (NS_trafficlight,EW_trafficlight),where NS_trafficlight = c.NS,EW_trafficlight = c.EW
	//不变式：c.NS^c.EW ==true;
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
