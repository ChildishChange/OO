package DDDC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import TAXI.Taxi;

public class Uber extends Thread
{
	//overview������ϵͳ���Լ��ĳ��⳵���飬��ͼ����ָ�����飬�ֱ��Ӧ�����ڹ������ָ��
	private Taxi[] Taxis;
	private Map map;
	private ArrayList<Order> Orders;
	//��������AF(c) = (Taxis,map,Orders) = where Taxis = c.Taxis , map = c.map , Orders = c.Orders
	//����ʽ��c.Taxis !=null;&&c.map!= null;&&c.Orders!=null;
	
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
//				System.out.println("��������"+k);
				
				
				for(int i = 0;i<k;i++)
				{
					Order order = Orders.get(i);
			
					for(Taxi taxi:Taxis)
					{
						if(order.inTheOrder(taxi))
						{
							taxi.setCredit(1);
							order.pushTaxi(taxi);
							System.out.println("��"+taxi.getNO()+"�ų��⳵��������("+order.getClientX()+","+order.getClientY()+")��("+order.getDestinationX()+","+order.getDestinationY()+")"+"��ʱ�ó��⳵λ��("+taxi.getPositionX()+","+taxi.getPositionY()+")");
						}
					}
					if(order.getTime()>=3000)
					{
						order.chooseOne();//���к�����������order�������ǣ�

						
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
