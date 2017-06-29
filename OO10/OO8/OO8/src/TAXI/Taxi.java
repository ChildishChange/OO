package TAXI;

import java.util.ArrayList;

import DDDC.Map;
import DDDC.Order;

public class Taxi extends Thread{
	//overview�����⳵���Լ�������ֵ����ţ�λ�����꣬��ʻ״̬����һ���ƶ��ķ����Լ�����ִ�еĶ������Լ����ڵĳ��У��ֱ����ζ�Ӧcredit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity,�������ƽʱ�ڳ����ﰴ��һ�������й䣬Ȼ��������ִ��ָ���һ����
	protected int credit;
	protected int NO;
	protected int state=0;//1 serving 2be about to serve3waiting for order4stop
	protected int positionX;
	protected int positionY;
	protected int lastMoveDir=0;
	protected Order myOrder = null;
	protected Map myCity = null;
	//��������AF(c) = (credit,NO,state,positionX,positionY,lastMoveDirection,myOrder,myCity),where credit = c.credit,NO = c.NO,state= c.state,positionX= c.positionX,positionY= c.positionY,lastMoveDirection= c.lastMoveDir,myOrder= c.myOrder,myCity= c.myCity
	//����ʽ��c.credit>=0;&&(c.NO>=0&&c.NO<=99);&&(c.state>0&&c.state<5);&&(c.positionX>=0&&c.positionX<=79);&&(c.positionY>=0&&c.positionY<=79);&&(c.lastMoveDir>=0&&c.lastMoveDir<=4);&&(if(c.state==3||c.state==4) c.myOrder==null);&&(c.myCity!=null);
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
		//EFFECTS�����Զ����ʵ�����Ƿ����㲻��ʽ
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
		//Modifies:ָ���ı���
		//Effects:�޸�ָ������Ϊָ��ֵ
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
		//Effects:���شӳ��⳵��ǰλ�õ�Ŀ��λ�õ����·������

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
		//Requires:һ������OrderҪ���ʵ������
		//Modifies:a variable
		//Effects:���ó��⳵��ǰִ�еĶ���
		this.myOrder = O;
	}
	public void setMap(Map P)
	{
		//Requires:�����ͼҪ��ĵ�ͼ����
		//Modifies:a variable
		//Effects:���ó��⳵���ڵĵ�ͼ
		this.myCity = P;
	}
	
	public void randomDrive()
	{
		//Requires:a map
		//Modifies:the position of the taxi
		//Effects:�����⳵���ڿ���״̬ʱ�����ճ�������Сԭ����ʻ
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
					break;
				case 2://down
					this.myCity.SubFlow(positionX, positionY, 1);
					this.positionX+=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					break;
				case 3://left
					this.myCity.SubFlow(positionX, positionY-1, 0);
					this.positionY-=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					break;
				case 4://right
					this.myCity.SubFlow(positionX, positionY, 0);
					this.positionY+=1;
					System.out.println("��"+this.NO+"�ų��⳵����������ʻ������("+this.positionX+","+this.positionY+")");
					break;
			}
			this.lastMoveDir = dir;
		}
	}

	
	
	public void setCredit(int i)
	{
		//Requires:����Ҫ���i��ֻ����1��3��������Ȼ���ᱨ�����ǻ�Ӱ��ֵ
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
				System.out.println("��"+this.NO+"�ų��⳵׼��ǰ���ÿ�λ��:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")��");
				
				this.gotoPosition1(this.myOrder.getClientX(),this.myOrder.getClientY());
				System.out.println("��"+this.NO+"�ų��⳵�����ÿ�:"+"("+this.myOrder.getClientX()+","+this.myOrder.getClientY()+")λ�á�");
				
				this.state = 1;
				try
				{
					Thread.sleep(1000);
				}catch(Exception e){e.printStackTrace();}
				System.out.println("��"+this.NO+"�ų��⳵׼��ǰ��Ŀ�ĵ�:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")λ�á�");
				
				this.gotoPosition1(this.myOrder.getDestinationX(),this.myOrder.getDestinationY());
				System.out.println("��"+this.NO+"�ų��⳵����Ŀ�ĵ�:"+"("+this.myOrder.getDestinationX()+","+this.myOrder.getDestinationY()+")λ�á�");
				
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
