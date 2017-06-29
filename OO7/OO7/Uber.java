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
	
	public void run()
	{
		
		while(true)
		{
			while(this.Orders.isEmpty()!=true)
			{
				int j = Orders.size();
			//	System.out.println("��������"+j);
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
							System.out.println("��"+taxi.getNO()+"�ų��⳵��������("+order.getClientX()+","+order.getClientY()+")��("+order.getDestinationX()+","+order.getDestinationY()+")");
						}
					}
					if(order.getTime()==3000)
					{
						Taxi temp = order.chooseOne();//���к�����������order�������ǣ�
						if(temp!=null)
						{
							order.setTheTaxi(temp); 
							temp.setOrder(order);
							temp.setCredit(3);
							temp.setstate(2);
							System.out.println("��"+temp.getNO()+"�ų��⳵�����˴�("+order.getClientX()+","+order.getClientY()+")��("+order.getDestinationX()+","+order.getDestinationY()+")�Ķ�����");
							
						}
						
						Orders.remove(i);
						i--;
						j--;
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
			
	
		}
		
	}
}
