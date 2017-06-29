package DDDC;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class HolyCrapTest extends Thread
{
	public static void main(String[] args)
	{
		HolyCrapTest t = new HolyCrapTest();
		t.start();
//		Random rand = new Random();
//		
//		Scanner input = new Scanner(System.in);
//		String Str = new String();
//		System.out.println("请输入指定的地图文件路径：");
//		Str = input.nextLine();
//		
//		Map map = new Map(new File(Str));
//		Taxi[] Taxis = new Taxi[100];
//		ArrayList<Order> Orders = new ArrayList<Order>();
//		
//		for(int i = 0;i<100;i++)
//		{
//			int randomX = rand.nextInt(80);
//			int randomY = rand.nextInt(80);
//			Taxis[i] = new Taxi(i,randomX, randomY, map);
//			System.out.println("第"+i+"号出租车初始位置为：("+randomX+","+randomY+")");
//		}
//		
//		Uber myUber = new Uber(Taxis,map,Orders);
//		
//		Order a = new Order(0,0,79,79);
//		Orders.add(a);
//		for(Taxi taxi: Taxis)
//		{
//			taxi.start();
//		}
//		myUber.start();
		
//		for(int i = 0;i<80;i++)
//		{
//			for(int j = 0;j<80;j++)
//			{
//				System.out.print(map.getDigitalMap(i,j));
//			}
//			System.out.println("");
//		}
	}
	public void run()
	{
		Random rand = new Random();
		
		Scanner input = new Scanner(System.in);
		String Str = new String();
		
		while(true)
		{
			System.out.println("请输入指定的地图文件路径：");
			Str = input.nextLine();
			File f = new File(Str);
			if(f.exists()!=true||f.canRead()!=true||f.isAbsolute()!=true||f.isFile()!=true)
			{
				System.out.println("路径错误！请重新输入！");
				continue;
			}
			break;
		}
		
		input.close();
		Map map = new Map(new File(Str));
		
		
		
		Taxi[] Taxis = new Taxi[100];
		ArrayList<Order> Orders = new ArrayList<Order>();
	//	List Orders = Collections.synchronizedList(new ArrayList<Order>());
		for(int i = 0;i<100;i++)
		{
			int randomX = rand.nextInt(80);
			int randomY = rand.nextInt(80);
			Taxis[i] = new Taxi(i,randomX, randomY, map);
			System.out.println("第"+i+"号出租车初始位置为：("+randomX+","+randomY+")");
		}
		
		Uber myUber = new Uber(Taxis,map,Orders);
		
		
		for(Taxi taxi: Taxis)
		{
			taxi.start();
		}
		myUber.start();
		
		//示例添加订单-1
		Order a = new Order(0,0,79,79);
		Orders.add(a);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//示例添加订单-2
		for(int i =0;i<100;i++)
		{
			Orders.add(new Order(25,25,79,79));
			try
			{
				Thread.sleep(rand.nextInt(150));
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//示例查看出租车状态
		int i = 65;
		System.out.println("第"+i+"号出租车目前状态为："+Taxis[i].getstate()+",处于("+Taxis[i].getPositionX()+","+Taxis[i].getPositionY()+")位置。");
		
	}
	
}
