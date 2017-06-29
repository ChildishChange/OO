package TAXI;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import DDDC.Map;
import DDDC.Position;

public class TraceableTaxi extends Taxi
{
	//overview�����⳵���Լ�������ֵ����ţ�λ�����꣬��ʻ״̬����һ���ƶ��ķ����Լ�����ִ�еĶ������Լ����ڵĳ��У��ֱ����ζ�Ӧcredit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,�������ƽʱ�ڳ����ﰴ��һ�������й䣬Ȼ��������ִ��ָ���һ���࣬Ȼ����һ���Ǹ߼����⳵��������ʻ֮ǰ���رյĵ�·�����Ҽ�¼ÿһ�νӿ͵���ʻ·��
	private ArrayList<ArrayList<Position>> list;
	private int times;
	
	//��������AF(c) = (credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,list,times),where credit = c.credit,NO = c.NO,state= c.state,positionX= c.positionX,positionY= c.positionY,lastMoveDirection= c.lastMoveDir,myOrder= c.myOrder,myCity= c.myCity,list = c.list,times = c.times;
	//����ʽ��c.credit>=0;&&(c.NO>=0&&c.NO<=99);&&(c.state>0&&c.state<5);&&(c.positionX>=0&&c.positionX<=79);&&(c.positionY>=0&&c.positionY<=79);&&(c.lastMoveDir>=0&&c.lastMoveDir<=4);&&(if(c.state==3||c.state==4) c.myOrder==null);&&(c.myCity!=null);&&(c.times>=0);&&(c.list!=null)
	
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
		//REQUIRES:����Ҫ���i
		//MODIFIED��nothing
		//EFFECTS������i��ó��⳵ĳ�νӿ͵ĵ�����
		if(list.size()<=i)
			return null;
		else
			return this.list.get(i).iterator();
	}

	public int getTime()
	{
		//REQUIRES:this.times!=null
		//MODIFIED:nothing
		//EFFECTS�������Ӧ������ֵ
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
		//Effects:���شӳ��⳵��ǰλ�õ�Ŀ��λ�õ����·������

		return this.myCity.BFS(this.myCity.getAdjacentFor(),this.positionX,this.positionY,X,Y);

	}
	
	public ArrayList<Position> gotoPosition(int X,int Y)
	{
		//Requires:a map��xy��ֵ�ڵ�ͼ��Χ��
		//Modifies:the position of the taxi
		//Effects:�������·����С��������ʻר�õ�·��ԭ���ó��⳵��ʻ������¼���⳵��λ���ƶ�
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
								System.out.println("��"+this.NO+"�ų��⳵��("+this.positionX+","+this.positionY+")�����̵ƶ�ס��");
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
								System.out.println("��"+this.NO+"�ų��⳵��("+this.positionX+","+this.positionY+")�����̵ƶ�ס��");
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
								System.out.println("��"+this.NO+"�ų��⳵��("+this.positionX+","+this.positionY+")�����̵ƶ�ס��");
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
								System.out.println("��"+this.NO+"�ų��⳵��("+this.positionX+","+this.positionY+")�����̵ƶ�ס��");
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
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX+1,this.positionY,this.positionX,this.positionY));
					break;
				case 2://down
					this.myCity.SubFlow(positionX, positionY, 1);
					this.positionX+=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX-1,this.positionY,this.positionX,this.positionY));
					break;
				case 3://left
					this.myCity.SubFlow(positionX, positionY-1, 0);
					this.positionY-=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					Path.add(new Position(this.positionX,this.positionY+1,this.positionX,this.positionY));
					break;
				case 4://right
					this.myCity.SubFlow(positionX, positionY, 0);
					this.positionY+=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
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
				System.out.println("��"+this.NO+"�ų��⳵׼��ǰ���ÿ�λ��:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")��");
				
				this.gotoPosition(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println("��"+this.NO+"�ų��⳵�����ÿ�:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")λ�á�");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("��"+this.NO+"�ų��⳵׼��ǰ��Ŀ�ĵ�:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")λ�á�");
				
				
				
				
				
				this.list.add(this.gotoPosition(this.myOrder.getDestinationX(),this.myOrder.getDestinationY()));
				
				
				System.out.println("��"+this.NO+"�ų��⳵����Ŀ�ĵ�:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")λ�á�");
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
