package DDDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Uber extends Thread
{
	private Taxi[] Taxis;
	private Map map;
	private ArrayList<Order> Orders;
//	private List Orders = Collections.synchronizedList(new ArrayList<Order>());
//	public Uber(Taxi[] taxis,Map map,List orders)
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
				int j = Orders.size();
//				System.out.println("1 "+/*"第"+temp.getNO()+"号出租车抢到了从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")的订单。"+*/System.currentTimeMillis());
				
			//	System.out.println("订单数："+j);
				for(int i = 0;i<j;i++)
				{
					Order order = Orders.get(i);
				//	Order order = (Order) Orders.get(i);
					for(Taxi taxi:Taxis)
					{
						if(order.inTheOrder(taxi))
						{
							taxi.setCredit(1);
							order.pushTaxi(taxi);
							System.out.println("第"+taxi.getNO()+"号出租车抢单：从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")");
						}
					}
					if(order.getTime()>=3000)
					{
//						System.out.println("3 "+/*"第"+temp.getNO()+"号出租车抢到了从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")的订单。"+*/System.currentTimeMillis());
						order.chooseOne();//所有后续工作交给order来做还是？
//						Taxi temp = order.chooseOne();//所有后续工作交给order来做还是？
//						System.out.println("4 "+/*"第"+temp.getNO()+"号出租车抢到了从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")的订单。"+*/System.currentTimeMillis());
						
//						if(temp!=null)
//						{
//							order.setTheTaxi(temp); 
//							temp.setOrder(order);
//							temp.setCredit(3);
//							temp.setstate(2);
//							System.out.println("5 "+/*"第"+temp.getNO()+"号出租车抢到了从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")的订单。"+*/System.currentTimeMillis());
//							
//						}
						
						Orders.remove(i);
						i--;
						j--;
						continue;
					}
					order.setTime();
				}
//				System.out.println("2 "+/*"第"+temp.getNO()+"号出租车抢到了从("+order.getClientX()+","+order.getClientY()+")到("+order.getDestinationX()+","+order.getDestinationY()+")的订单。"+*/System.currentTimeMillis());
				
				try 
				{
					Thread.sleep(100);
				} 
				catch (InterruptedException e){e.printStackTrace();}

			}
			
	
		}
		
	}
}
