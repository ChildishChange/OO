package DDDC;

import java.util.ArrayList;

public class Order 
{
	private int time = 0;
	private ArrayList<Taxi> Taxis = new ArrayList<Taxi>();
	private Taxi theTaxi;
	private boolean success = false;
	
	private int ClientPositionX;
	private int ClientPositionY;
	private int DestinationX;
	private int DestinationY;
	
	public Order(int CPX,int CPY,int DX,int DY)
	{
		if(CPX>79||CPX<0||CPY>79||CPY<0||DX>79||DX<0||DY>79||DY<0)
		{	
			this.success = false;
			System.out.println("从("+CPX+","+CPY+")到("+DX+","+DY+")的指令有误，可能不在地图内。该请求无效");
		}
		this.ClientPositionX = CPX;
		this.ClientPositionY = CPY;
		this.DestinationX = DX;
		this.DestinationY = DY;
		this.success = true;
		
		
	}
	public boolean repOK()
	{
		if(this.time<0)
			return false;
		if(this.ClientPositionX>79||this.ClientPositionX<0)
			return false;		
		if(this.ClientPositionY>79||this.ClientPositionY<0)
			return false;		
		if(this.DestinationX>79||this.DestinationX<0)
			return false;		
		if(this.DestinationY>79||this.DestinationY<0)
			return false;	
		return true;
	}
	public int getClientX()
	{
		//Requires:ClientPositionX is not null;
		//Modifies:nothing
		//Effects:get the value of ClientPositionX
		return ClientPositionX;
		
	}
	public boolean getSuccess()
	{
		//Requires:Success is not null;
		//Modifies:nothing
		//Effects:get the value of success
		return success;
		
	}
	public void setClientX(int clientPositionX)
	{
		//Requires:an integer
		//Modifies:value of ClientPositionX
		//Effects:change the value of the variable
		ClientPositionX = clientPositionX;
	}
	public int getDestinationX()
	{
		//Requires:DestinationX is not null;
		//Modifies:nothing
		//Effects:get the value of DestinationX	
		return DestinationX;
	}
	public void setDestinationX(int destinationX)
	{
		//Requires:an integer
		//Modifies:value of DestinationX
		//Effects:change the value of the variable
		DestinationX = destinationX;
	}
	public int getDestinationY()
	{
		//Requires:DestinationY is not null;
		//Modifies:nothing
		//Effects:get the value of DestinationY	
		return DestinationY;
	}
	public void setDestinationY(int destinationY)
	{
		//Requires:an integer
		//Modifies:value of DestinationY
		//Effects:change the value of the variable
		
		DestinationY = destinationY;
	}
	public int getClientY()
	{
		//Requires:ClientPositionY is not null;
		//Modifies:nothing
		//Effects:get the value of ClientPositionY

		return ClientPositionY;
	}
	public void setClientY(int clientPositionY)
	{
		//Requires:an integer
		//Modifies:value of ClientPositionY
		//Effects:change the value of the variable
		ClientPositionY = clientPositionY;
	}
	
	public boolean inTheOrder(Taxi taxi)
	{
		//Requires:the position of the order and the position of the taxi
		//Modifies:nothing 
		//Effects:judge that whether the order is reachable for the taxi
		if(this.Taxis.isEmpty()==false)
		{
			if(this.Taxis.contains(taxi))
			{				
				return false;
			}
			return  taxi.getstate()==3&&
					(taxi.getPositionX()<=this.ClientPositionX+2)&&
					(taxi.getPositionX()>=this.ClientPositionX-2)&&
					(taxi.getPositionY()<=this.ClientPositionY+2)&&
					(taxi.getPositionY()>=this.ClientPositionY-2); 
		}
		else
		{	return  taxi.getstate()==3&&
					(taxi.getPositionX()<=this.ClientPositionX+2)&&
					(taxi.getPositionX()>=this.ClientPositionX-2)&&
					(taxi.getPositionY()<=this.ClientPositionY+2)&&
					(taxi.getPositionY()>=this.ClientPositionY-2); 
		}
	
	}
	
	public void pushTaxi(Taxi taxi)
	{
		//Requires:an arraylist of taxi,a taxi
		//Modifies:the arraylist of taxi in the object order
		//Effects:push the taxi into the arraylist
		this.Taxis.add(taxi);
	}
	public int getTime()
	{
		//Requires:time is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return this.time;
	}
	public void chooseOne()
//	public Taxi chooseOne()
	{
		//Requires:an arraylist of taxi,which have all the taxi in it that can response the order 
		//Modifies:the taxi in the object order
		//Effects:get the very taxi that can execute the order
		Taxi temp = null;
		if(this.Taxis.isEmpty())
		{
			System.out.println("从("+this.ClientPositionX+","+this.ClientPositionY+")到("+this.DestinationX+","+this.DestinationY+")的订单长时间无响应，订单取消");
//			return null;
			return;
		}
		int credit = 0;
		int distance = 100000;
		for(Taxi taxi:this.Taxis)
		{
			if(taxi.getstate()!=3)
			{
				
				continue;
			}
			if(temp==null)
			{	
				temp = taxi;
				continue;
			}
			if(taxi.getCredit()>credit)
			{
				temp = taxi;
				credit = taxi.getCredit();
			}
			else if(taxi.getCredit()==credit)
			{
				int tempdis = taxi.getDistance(this.ClientPositionX,this.ClientPositionY);
				if(tempdis<distance)
				{
					temp = taxi;
					distance = tempdis;
				}
			}
//			先选择等待服务中信用度最高的
//			多量信用度相同，选择离用户最近的
//			多量相同，选择第一辆

		}

		if(temp==null)
		{
			System.out.println("从("+this.ClientPositionX+","+this.ClientPositionY+")到("+this.DestinationX+","+this.DestinationY+")的订单长时间无响应，订单取消");
			return;
//			return null;
		}
		this.theTaxi = temp;
		temp.setOrder(this);
		temp.setCredit(3);
		temp.setstate(2);
		System.out.println("第"+temp.getNO()+"号出租车抢到了从("+this.getClientX()+","+this.getClientY()+")到("+this.getDestinationX()+","+this.getDestinationY()+")的订单。"/*+System.currentTimeMillis()*/);
//			
//		return temp;
	}

	public void setTime()
	{
		//Requires:nothing
		//Modifies:value of time
		//Effects:change the value of the variable
		this.time+=100;
	}

	public Taxi getTheTaxi() 
	{
		//Requires:nothing
		//Modifies:nothing
		//Effects:get the value of the variable,whether it is a null or an exist variable
		return theTaxi;
	}

	public void setTheTaxi(Taxi theTaxi) 
	{
		//Requires:a taxi
		//Modifies:the value of the variable named "theTaxi" in the object order
		//Effects:set the value of the variable named "theTaxi" in the object order
		this.theTaxi = theTaxi;
	}
	
	
}
