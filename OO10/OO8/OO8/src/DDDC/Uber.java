package DDDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import TAXI.Taxi;

public class Uber extends Thread
{
	//overview：控制系统有自己的出租车数组，地图，和指令数组，分别对应，用于管理分配指令
	private Taxi[] Taxis;
	private Map map;
	private ArrayList<Order> Orders;
	//抽象函数：AF(c) = (Taxis,map,Orders) = where Taxis = c.Taxis , map = c.map , Orders = c.Orders
	//不变式：c.Taxis !=null;&&c.map!= null;&&c.Orders!=null;
	
	public Uber(Taxi[] taxis,Map map,ArrayList<Order> orders)
	{
		this.Taxis = taxis;
		this.map = map;
		this.Orders = orders;
	}
	public boolean repOK()
	{
		if(this.Taxis ==null)
			return false;
		if(this.map ==null)
			return false;
		if(this.Orders==null)
			return false;
		return true;
	}
	public void run()
	{
		
		while(true)
		{

			while(this.Orders.isEmpty()!=true)
			{
				int k = Orders.size();
//				System.out.println("订单数："+k);
				
				
				for(int i = 0;i<k;i++)
				{
					Order order = Orders.get(i);
			
					for(Taxi taxi:Taxis)
					{
						if(order.inTheOrder(taxi))
						{
							taxi.setCredit(1);
							order.pushTaxi(taxi);
							System.out.println("第"+taxi.getNO()+"号出租车抢单：从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")"+"此时该出租车位于("+taxi.getPositionX()+","+taxi.getPositionY()+")");
						}
					}
					if(order.getTime()>=3000)
					{
						order.chooseOne();//所有后续工作交给order来做还是？

						
						Orders.remove(i);
						i--;
						k--;
						continue;
					}
					order.setTime();
				}
				
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e){e.printStackTrace();}

			}
			try 
			{
				Thread.sleep(1);
			} 
			catch (InterruptedException e){e.printStackTrace();}
	
		}
		
	}
}
