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
	public int getNO()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.NO;
	}
	public int getCredit()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.credit;
	}
	public int getDistance(int X,int Y)
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
//		ArrayList<Integer> Path = new ArrayList<Integer>();
		return this.myCity.BFS(this.myCity,this.positionX,this.positionY,X,Y);
//		Path = this.myCity.BFS(this.myCity,this.positionX,this.positionY,X,Y);
//		return Path.size();
	}
	public int getstate()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.state;
	}
	public void setstate(int i)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.state = i;
	}
	
	public int getPositionX()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.positionX;
	}
	public int getPositionY()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.positionY;
		}
	
	public void setOrder(Order O)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.myOrder = O;
	}
	public void setMap(Map P)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.myCity = P;
	}
	
	public void randomDrive()
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:change the position of the taxi while it is idle
		int dir = this.myCity.getRandomDirection(this.positionX, this.positionY);
		switch(dir)
		{
	
			case 1://up
				this.myCity.AddFlow(positionX-1, positionY, 1);
				break;
			case 2://down
				this.myCity.AddFlow(positionX, positionY, 1);
				break;
			case 3://left
				this.myCity.AddFlow(positionX, positionY-1, 0);
				break;
			case 4://right
				this.myCity.AddFlow(positionX, positionY, 0);
				break;
		}
		try 
		{
			Thread.sleep(100);
		} 
		catch (InterruptedException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		switch(dir)
		{
	
			case 1://up
				this.myCity.SubFlow(positionX-1, positionY, 1);
				this.positionX-=1;
				break;
			case 2://down
				this.myCity.SubFlow(positionX, positionY, 1);
				this.positionX+=1;
				break;
			case 3://left
				this.myCity.SubFlow(positionX, positionY-1, 0);
				this.positionY-=1;
				break;
			case 4://right
				this.myCity.SubFlow(positionX, positionY, 0);
				this.positionY+=1;
				break;
		}

	}
	public void gotoPosition1(int X,int Y)
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:make the taxi go to a certain position
		while(this.positionX!=X||this.positionY!=Y)
		{
			int dir = this.myCity.getMinFlowDirection(this.positionX, this.positionY, X, Y,this.getDistance(X, Y));
			switch(dir)
			{
		
				case 1://up
					this.myCity.AddFlow(positionX-1, positionY, 1);
					break;
				case 2://down
					this.myCity.AddFlow(positionX, positionY, 1);
					break;
				case 3://left
					this.myCity.AddFlow(positionX, positionY-1, 0);
					break;
				case 4://right
					this.myCity.AddFlow(positionX, positionY, 0);
					break;
			}
			try 
			{
				Thread.sleep(100);
			} 
			catch (InterruptedException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			switch(dir)
			{
		
				case 1://up
					this.myCity.SubFlow(positionX-1, positionY, 1);
					this.positionX-=1;
					System.out.println("第"+this.NO+"号出租车朝↑方向行驶，到达("+this.positionX+","+this.positionY+")");
					break;
				case 2://down
					this.myCity.SubFlow(positionX, positionY, 1);
					this.positionX+=1;
					System.out.println("第"+this.NO+"号出租车朝↓方向行驶，到达("+this.positionX+","+this.positionY+")");
					break;
				case 3://left
					this.myCity.SubFlow(positionX, positionY-1, 0);
					this.positionY-=1;
					System.out.println("第"+this.NO+"号出租车朝←方向行驶，到达("+this.positionX+","+this.positionY+")");
					break;
				case 4://right
					this.myCity.SubFlow(positionX, positionY, 0);
					this.positionY+=1;
					System.out.println("第"+this.NO+"号出租车朝→方向行驶，到达("+this.positionX+","+this.positionY+")");
					break;
			}
			
		}
	}
//	public void gotoPosition(int X,int Y)
//	{
//		ArrayList<Integer> Path = new ArrayList<Integer>();
//		
//		Path = this.myCity.BFS(this.myCity,this.positionX,this.positionY,X,Y);
//		
//		for(int i = 0;i<Path.size();i++)//1up2down3left
//		{
//			if(Path.get(i)==1)
//			{
//				
//				this.positionX-=1;
//			//	System.out.println("第"+this.NO+"号出租车朝↑方向行驶，到达("+this.positionX+","+this.positionY+")");
//			}
//			else if(Path.get(i)==2)
//			{
//			
//				this.positionX+=1;
//			//	System.out.println("第"+this.NO+"号出租车朝↓方向行驶，到达("+this.positionX+","+this.positionY+")");
//			}
//			else if(Path.get(i)==3)
//			{
//		
//				this.positionY-=1;
//			//	System.out.println("第"+this.NO+"号出租车朝←方向行驶，到达("+this.positionX+","+this.positionY+")");
//			}
//			else if(Path.get(i)==4)
//			{
//	
//				this.positionY+=1;
//			//	System.out.println("第"+this.NO+"号出租车朝→方向行驶，到达("+this.positionX+","+this.positionY+")");
//			}
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//				
//		}
//	}
	
	
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
//				try
//				{
//					Thread.sleep(100);
//				}catch(InterruptedException e){e.printStackTrace();}
				this.randomDrive();
				
			}
			else
			{
				this.state = 2;
				System.out.println(this.NO+"号出租车准备前往旅客位置:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")。");
				
				this.gotoPosition1(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println(this.NO+"号出租车到达旅客:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")位置。");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println(this.NO+"号出租车准备前往目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
				this.gotoPosition1(this.myOrder.getDestinationX(),this.myOrder.getDestinationY());
				System.out.println(this.NO+"号出租车到达目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
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
