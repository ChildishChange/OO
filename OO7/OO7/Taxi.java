package DDDC;

import java.util.ArrayList;

public class Taxi extends Thread{
	private int credit;
	private int NO;
	private int state=0;//1 serving 2be about to serve3waiting for order4stop
	private int positionX;
	private int positionY;
	private Order myOrder = null;
	private Map myCity = null;
	public Taxi(int num,int positionX,int positionY,Map map)
	{
		this.NO = num;
		this.credit = 0;
		this.state = 3;
		this.positionX = positionX;
		this.positionY = positionY;
		this.myCity = map;
	}
	public int getNO(){return this.NO;}
	public int getCredit()
	{
		return this.credit;
	}
	public int getDistance(int X,int Y)
	{
		ArrayList<Integer> Path = new ArrayList<Integer>();
		
		Path = this.myCity.BFS(this.myCity,this.positionX,this.positionY,X,Y);
		return Path.size();
	}
	public int getstate(){return this.state;}
	public void setstate(int i){this.state = i;}
	
	public int getPositionX(){return this.positionX;}
	public int getPositionY(){return this.positionY;}
	
	public void setOrder(Order O){this.myOrder = O;}
	public void setMap(Map P){this.myCity = P;}
	
	public void randomDrive()
	{
		int dir = this.myCity.getRandomDirection(this.positionX, this.positionY);
		switch(dir)
		{
	
			case 1://up
				this.positionX-=1;
				break;
			case 2://down
				this.positionX+=1;
				break;
			case 3://left
				this.positionY-=1;
				break;
			case 4://right
				this.positionY+=1;
				break;
		}

	}
	public void gotoPosition(int X,int Y)
	{
		ArrayList<Integer> Path = new ArrayList<Integer>();
		
		Path = this.myCity.BFS(this.myCity,this.positionX,this.positionY,X,Y);
		
		for(int i = 0;i<Path.size();i++)//1up2down3left
		{
			if(Path.get(i)==1)
			{
				
				this.positionX-=1;
			//	System.out.println("第"+this.NO+"号出租车朝↑方向行驶，到达("+this.positionX+","+this.positionY+")");
			}
			else if(Path.get(i)==2)
			{
			
				this.positionX+=1;
			//	System.out.println("第"+this.NO+"号出租车朝↓方向行驶，到达("+this.positionX+","+this.positionY+")");
			}
			else if(Path.get(i)==3)
			{
		
				this.positionY-=1;
			//	System.out.println("第"+this.NO+"号出租车朝←方向行驶，到达("+this.positionX+","+this.positionY+")");
			}
			else if(Path.get(i)==4)
			{
	
				this.positionY+=1;
			//	System.out.println("第"+this.NO+"号出租车朝→方向行驶，到达("+this.positionX+","+this.positionY+")");
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
		}
	}
	
	
	public void setCredit(int i)
	{
		this.credit+=i;
		
	}
	public void run(){
		int timecount = 0;
		this.state = 3;
		while(true)
		{
			if(this.myOrder==null)
			{
				if(timecount==20000)
				{
					try
					{
						this.state = 4;
						Thread.sleep(1000);
					}catch(Exception e){e.printStackTrace();}
					timecount = 0;
					continue;
				}
				timecount+=100;
				try
				{
					Thread.sleep(100);
				}catch(InterruptedException e){e.printStackTrace();}
				this.randomDrive();
				
			}
			else
			{
				this.state = 2;
				
				this.gotoPosition(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println(this.NO+"号出租车到达:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")位置。");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println(this.NO+"号出租车准备前往:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
				this.gotoPosition(this.myOrder.getDestinationX(),this.myOrder.getDestinationY());
				System.out.println(this.NO+"号出租车到达:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				
				this.state = 3;
				this.myOrder = null;
				timecount = 0;
				
			}
			
				
		}

		
	}


}
