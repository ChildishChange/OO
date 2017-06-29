package TAXI;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import DDDC.Map;
import DDDC.Position;

public class TraceableTaxi extends Taxi
{
	//overview：出租车有自己的信誉值，编号，位置坐标，行驶状态，上一次移动的方向，自己正在执行的订单与自己所在的城市，分别依次对应credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,这个类是平时在城市里按照一定规则闲逛，然后抢单，执行指令的一个类，然后这一种是高级出租车，可以行驶之前被关闭的道路，并且记录每一次接客的行驶路径
	private ArrayList<ArrayList<Position>> list;
	private int times;
	
	//抽象函数：AF(c) = (credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,list,times),where credit = c.credit,NO = c.NO,state= c.state,positionX= c.positionX,positionY= c.positionY,lastMoveDirection= c.lastMoveDir,myOrder= c.myOrder,myCity= c.myCity,list = c.list,times = c.times;
	//不变式：c.credit>=0;&&(c.NO>=0&&c.NO<=99);&&(c.state>0&&c.state<5);&&(c.positionX>=0&&c.positionX<=79);&&(c.positionY>=0&&c.positionY<=79);&&(c.lastMoveDir>=0&&c.lastMoveDir<=4);&&(if(c.state==3||c.state==4) c.myOrder==null);&&(c.myCity!=null);&&(c.times>=0);&&(c.list!=null)
	
	public TraceableTaxi(int num, int positionX, int positionY, Map map) {
		super(num, positionX, positionY, map);
		// TODO Auto-generated constructor stub
		this.list = new ArrayList<ArrayList<Position>>();
		this.times = 0;
	}
	
	public boolean repOK()
	{
		return (super.repOK()&&(this.times>=0)&&(this.list!=null));
	}

	public Iterator getIterator(int i)
	{
		//REQUIRES:满足要求的i
		//MODIFIED：nothing
		//EFFECTS：根据i获得出租车某次接客的迭代器
		if(list.size()<=i)
			return null;
		else
			return this.list.get(i).iterator();
	}

	public int getTime()
	{
		//REQUIRES:this.times!=null
		//MODIFIED:nothing
		//EFFECTS：获得相应变量的值
		return times;
	}
	public void randomDrive()
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:change the position of the taxi while it is idle
		int dir = this.myCity.getRandomDirection(this.positionX, this.positionY,this.myCity.getAdjacentFor());
		
		switch(dir)
		{
	
			case 1://up
				if(this.lastMoveDir == 3||this.lastMoveDir ==0)
				{
					this.myCity.AddFlow(positionX-1, positionY, 1);
					break;
				}
				else
				{
					while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.myCity.AddFlow(positionX-1, positionY, 1);
					break;
				}
			case 2://down
				if(this.lastMoveDir ==4||this.lastMoveDir ==0)
				{
					this.myCity.AddFlow(positionX, positionY, 1);
					break;
				}
				else
				{
					while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.myCity.AddFlow(positionX, positionY, 1);
					break;
				}
			case 3://left
				if(this.lastMoveDir ==2||this.lastMoveDir ==0)
				{
					this.myCity.AddFlow(positionX, positionY-1, 0);
					break;
				}
				else
				{
					while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.myCity.AddFlow(positionX, positionY-1, 0);
					break;
				}
				
			case 4://right
				if(this.lastMoveDir ==1||this.lastMoveDir ==0)
				{
					this.myCity.AddFlow(positionX, positionY, 0);
					break;
				}
				else
				{
					while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
					{
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					this.myCity.AddFlow(positionX, positionY, 0);
					break;
				}
		}
		try 
		{
			Thread.sleep(100);
		} 
		catch(InterruptedException e) 
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
		this.lastMoveDir = dir;

	}
	
	public int getDistance(int X,int Y)
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:返回从出租车当前位置到目标位置的最短路径长度

		return this.myCity.BFS(this.myCity.getAdjacentFor(),this.positionX,this.positionY,X,Y);

	}
	
	public ArrayList<Position> gotoPosition(int X,int Y)
	{
		//Requires:a map，xy的值在地图范围内
		//Modifies:the position of the taxi
		//Effects:根据最短路径最小车流可行驶专用道路的原则，让出租车行驶，并记录出租车的位置移动
		ArrayList<Position> Path = new ArrayList<Position>();
		while(this.positionX!=X||this.positionY!=Y)
		{
			
			int dir = this.myCity.getMinFlowDirection(this.positionX, this.positionY, X, Y,this.myCity.getAdjacentFor());
			switch(dir)
			{
				case 1://up
					if(this.lastMoveDir == 3||this.lastMoveDir ==0)
					{
						this.myCity.AddFlow(positionX-1, positionY, 1);
						break;
					}
					else
					{
						boolean loop = false;
						while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
						{
							if(loop == false)
							{
								System.out.println("第"+this.NO+"号出租车在("+this.positionX+","+this.positionY+")被红绿灯堵住了");
								loop = true;
							}
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						this.myCity.AddFlow(positionX-1, positionY, 1);
						break;
					}
				case 2://down
					if(this.lastMoveDir ==4||this.lastMoveDir ==0)
					{
						this.myCity.AddFlow(positionX, positionY, 1);
						break;
					}
					else
					{
						boolean loop = false;
						while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
						{
							if(loop == false)
							{
								System.out.println("第"+this.NO+"号出租车在("+this.positionX+","+this.positionY+")被红绿灯堵住了");
								loop = true;
							}
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						this.myCity.AddFlow(positionX, positionY, 1);
						break;
					}
				case 3://left
					if(this.lastMoveDir ==2||this.lastMoveDir ==0)
					{
						
						this.myCity.AddFlow(positionX, positionY-1, 0);
						break;
					}
					else
					{
						boolean loop = false;
						while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
						{
							
							if(loop == false)
							{
								System.out.println("第"+this.NO+"号出租车在("+this.positionX+","+this.positionY+")被红绿灯堵住了");
								loop = true;
							}
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						this.myCity.AddFlow(positionX, positionY-1, 0);
						break;
					}
					
				case 4://right
					if(this.lastMoveDir ==1||this.lastMoveDir ==0)
					{
						this.myCity.AddFlow(positionX, positionY, 0);
						break;
					}
					else
					{
						boolean loop = false;
						while(this.myCity.getTrafficSignal(this.positionX,this.positionY,this.lastMoveDir)!=true)
						{
							
							if(loop == false)
							{
								System.out.println("第"+this.NO+"号出租车在("+this.positionX+","+this.positionY+")被红绿灯堵住了");
								loop = true;
							}
							try {
								Thread.sleep(10);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						this.myCity.AddFlow(positionX, positionY, 0);
						break;
					}
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
					Path.add(new Position(this.positionX+1,this.positionY,this.positionX,this.positionY));
					break;
				case 2://down
					this.myCity.SubFlow(positionX, positionY, 1);
					this.positionX+=1;
					System.out.println("第"+this.NO+"号出租车朝↓方向行驶，到达("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX-1,this.positionY,this.positionX,this.positionY));
					break;
				case 3://left
					this.myCity.SubFlow(positionX, positionY-1, 0);
					this.positionY-=1;
					System.out.println("第"+this.NO+"号出租车朝←方向行驶，到达("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX,this.positionY+1,this.positionX,this.positionY));
					break;
				case 4://right
					this.myCity.SubFlow(positionX, positionY, 0);
					this.positionY+=1;
					System.out.println("第"+this.NO+"号出租车朝→方向行驶，到达("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX,this.positionY-1,this.positionX,this.positionY));
					break;
			}
			this.lastMoveDir = dir;
		}
		return Path;
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
				System.out.println("第"+this.NO+"号出租车准备前往旅客位置:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")。");
				
				this.gotoPosition(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println("第"+this.NO+"号出租车到达旅客:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")位置。");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("第"+this.NO+"号出租车准备前往目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
				
				
				
				
				this.list.add(this.gotoPosition(this.myOrder.getDestinationX(),this.myOrder.getDestinationY()));
				
				
				System.out.println("第"+this.NO+"号出租车到达目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				times++;
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
