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
		//Requires:nothing
		//Modifies:nothing
		//Effects:start the system
		HolyCrapTest t = new HolyCrapTest();
		t.start();

	}
	public void run()
	{
		Random rand = new Random();
		
		Scanner input = new Scanner(System.in);
		String Str = new String();
		String Str1 = new String();
		Map map = null;
		while(true)
		{
			System.out.println("请输入指定的地图文件路径：");
			Str = input.nextLine();
			System.out.println("请输入对应地图的交叉情况：");
			Str1 = input.nextLine();
			File f = new File(Str);
			File Crux = new File(Str1);
			
			if(f.exists()!=true||f.canRead()!=true||f.isAbsolute()!=true||f.isFile()!=true)
			{
				System.out.println("地图路径错误！请全部重新输入！");
				continue;
			}
			if(Crux.exists()!=true||Crux.canRead()!=true||Crux.isAbsolute()!=true||Crux.isFile()!=true)
			{
				System.out.println("地图交叉情况路径错误！请全部重新输入！");
				continue;
			}
			map = new Map(f,Crux);
			if(map.isInitialization()==false)
			{
				continue;
			}
			break;
			
		}
		
		input.close();
		

		//地图开始红绿灯系统的运行
		map.initializeTraffic();
		
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
		
		myUber.start();

		for(Taxi taxi: Taxis)
		{
			taxi.start();
		}
		


		//示例关闭道路-1
//		map.closeRoad(77, 79, 1);
		//示例关闭道路-2
//		for(int i = 0;i<80;i++)
//		{
//			map.closeRoad(i,79,1);
//		}
//		
		//示例添加订单-1
//		Order a = new Order(50,50,20,20);
//		Orders.add(a);

		
		//示例添加订单-2
		for(int i =0;i<100;i++)
		{
		
//				Order a = 	if(a.getSuccess()==true)
//					Orders.add(a);
		Orders.add(new Order(50,50,20,20));
//			System.out.println(System.currentTimeMillis());
			try
			{
				Thread.sleep(rand.nextInt(50));
			}catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
//		//示例查看道路流量
//		System.out.println("flow:"+map.getFlow(25, 25, 1));
//		//示例查看出租车状态
//		int i = 65;
//		System.out.println("第"+i+"号出租车目前状态为："+Taxis[i].getstate()+",处于("+Taxis[i].getPositionX()+","+Taxis[i].getPositionY()+")位置。");
//		//示例卡其道路
//		map.openRoad(15, 15, 0);
//		map.openRoad(16, 17, 3);
//		map.openRoad(77, 79, 1);
//		
		
	}
	
}
