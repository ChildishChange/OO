package TAXI;

import java.util.ArrayList;

import DDDC.Map;
import DDDC.Order;

public class Taxi extends Thread{
	//overview：出租车有自己的信誉值，编号，位置坐标，行驶状态，上一次移动的方向，自己正在执行的订单与自己所在的城市，分别依次对应credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,这个类是平时在城市里按照一定规则闲逛，然后抢单，执行指令的一个类
	protected int credit;
	protected int NO;
	protected int state=0;//1 serving 2be about to serve3waiting for order4stop
	protected int positionX;
	protected int positionY;
	protected int lastMoveDir=0;
	protected Order myOrder = null;
	protected Map myCity = null;
	//抽象函数：AF(c) = (credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity),where credit = c.credit,NO = c.NO,state= c.state,positionX= c.positionX,positionY= c.positionY,lastMoveDirection= c.lastMoveDir,myOrder= c.myOrder,myCity= c.myCity
	//不变式：c.credit>=0;&&(c.NO>=0&&c.NO<=99);&&(c.state>0&&c.state<5);&&(c.positionX>=0&&c.positionX<=79);&&(c.positionY>=0&&c.positionY<=79);&&(c.lastMoveDir>=0&&c.lastMoveDir<=4);&&(if(c.state==3||c.state==4) c.myOrder==null);&&(c.myCity!=null);
	public Taxi(int num,int positionX,int positionY,Map map)
	{
		this.NO = num;
		this.credit = 0;
		this.state = 3;
		this.positionX = positionX;
		this.positionY = positionY;
		this.myCity = map;
	}
	

	
	
	public boolean repOK()
	{
		//EFFECTS：测试对象的实例化是否满足不变式
		if(this.credit<0)
			return false;
		if(this.NO<0)
			return false;
		if(this.state>4||this.state<0)
			return false;
		if(this.positionX>79||this.positionX<0)
			return false;
		if(this.positionY>79||this.positionY<0)
			return false;
		if(this.lastMoveDir<0||this.lastMoveDir>4)
			return false;
		if(myCity == null)
			return false;
		return true;
	}
	
	public Map getMyCity()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.myCity;
	}
	
	public int getLastMoveDir()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.lastMoveDir;
	}
	public void setLastMoveDir(int i)
	{
		//Requires:nothing
		//Modifies:指定的变量
		//Effects:修改指定变量为指定值
		this.lastMoveDir = i;
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
		//Effects:返回从出租车当前位置到目标位置的最短路径长度

		return this.myCity.BFS(this.myCity.getAdjacent(),this.positionX,this.positionY,X,Y);

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
		//Requires:nothing
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
		//Requires:一个满足Order要求的实例对象
		//Modifies:a variable
		//Effects:设置出租车当前执行的订单
		this.myOrder = O;
	}
	public void setMap(Map P)
	{
		//Requires:满足地图要求的地图对象
		//Modifies:a variable
		//Effects:设置出租车所在的地图
		this.myCity = P;
	}
	
	public void randomDrive()
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:当出租车处于空闲状态时，按照车流量最小原则行驶
		int dir = this.myCity.getRandomDirection(this.positionX, this.positionY,this.myCity.getAdjacent());
		
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
	public void gotoPosition1(int X,int Y)
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:make the taxi go to a certain position
		while(this.positionX!=X||this.positionY!=Y)
		{
			int dir = this.myCity.getMinFlowDirection(this.positionX, this.positionY, X, Y,this.myCity.getAdjacent());
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
			this.lastMoveDir = dir;
		}
	}

	
	
	public void setCredit(int i)
	{
		//Requires:满足要求的i，只能是1或3，否则虽然不会报错，但是会影响值
		//Modifies:a variable
		//Effects:change the value of the variable
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
				System.out.println("第"+this.NO+"号出租车准备前往旅客位置:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")。");
				
				this.gotoPosition1(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println("第"+this.NO+"号出租车到达旅客:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")位置。");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("第"+this.NO+"号出租车准备前往目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
				this.gotoPosition1(this.myOrder.getDestinationX(),this.myOrder.getDestinationY());
				System.out.println("第"+this.NO+"号出租车到达目的地:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")位置。");
				
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
