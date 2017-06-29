package DDDC;

import java.util.ArrayList;

public class Order 
{
	private int time = 0;
	private ArrayList<Taxi> Taxis = new ArrayList<Taxi>();
	private Taxi theTaxi;
	
	
	private int ClientPositionX;
	private int ClientPositionY;
	private int DestinationX;
	private int DestinationY;
	
	public Order(int CPX,int CPY,int DX,int DY)
	{
		this.ClientPositionX = CPX;
		this.ClientPositionY = CPY;
		this.DestinationX = DX;
		this.DestinationY = DY;
	}
	
	public int getClientX(){return ClientPositionX;}
	public void setClientX(int clientPositionX){ClientPositionX = clientPositionX;}
	public int getDestinationX(){return DestinationX;}
	public void setDestinationX(int destinationX){DestinationX = destinationX;}
	public int getDestinationY(){return DestinationY;}
	public void setDestinationY(int destinationY){DestinationY = destinationY;}
	public int getClientY(){return ClientPositionY;}
	public void setClientY(int clientPositionY){ClientPositionY = clientPositionY;}
	
	public boolean inTheOrder(Taxi taxi)
	{
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
					(taxi.getPositionX()>=this.ClientPositionY-2);
		}
		else
		{	return  taxi.getstate()==3&&
					(taxi.getPositionX()<=this.ClientPositionX+2)&&
					(taxi.getPositionX()>=this.ClientPositionX-2)&&
					(taxi.getPositionY()<=this.ClientPositionY+2)&&
					(taxi.getPositionX()>=this.ClientPositionY-2);
		}
	
	}
	
	public void pushTaxi(Taxi taxi)
	{
		this.Taxis.add(taxi);
	}
	public int getTime()
	{
		return this.time;
	}
	public Taxi chooseOne()
	{
		Taxi temp = null;
		if(this.Taxis.isEmpty())
		{
			System.out.println("��("+this.ClientPositionX+","+this.ClientPositionY+")��("+this.DestinationX+","+this.DestinationY+")�Ķ�����ʱ������Ӧ������ȡ��");
			return null;
		}
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
			if(taxi.getCredit()>temp.getCredit())
			{
				temp = taxi;
			}
			else if(taxi.getCredit()==temp.getCredit())
			{
				if(taxi.getDistance(this.ClientPositionX,this.ClientPositionY)<temp.getDistance(this.ClientPositionX,this.ClientPositionY))
				{
					temp = taxi;
				}
			}
//			��ѡ��ȴ����������ö���ߵ�
//			�������ö���ͬ��ѡ�����û������
//			������ͬ��ѡ���һ��

		}
//		this.theTaxi = temp;
//		temp.setOrder(this);
//		temp.setCredit(3);
//		System.out.println("��"+temp.getNO()+"�ų��⳵������("+this.ClientPositionX+","+this.ClientPositionY+")��("+this.getDestinationX()+","+this.DestinationY+")�ĵ���");
//		
		if(temp==null)
		{
			System.out.println("��("+this.ClientPositionX+","+this.ClientPositionY+")��("+this.DestinationX+","+this.DestinationY+")�Ķ�����ʱ������Ӧ������ȡ��");
			
			return null;
		}
			
		return temp;
	}

	public void setTime()
	{
		this.time+=100;
	}

	public Taxi getTheTaxi() {
		return theTaxi;
	}

	public void setTheTaxi(Taxi theTaxi) {
		this.theTaxi = theTaxi;
	}
	
	
}
