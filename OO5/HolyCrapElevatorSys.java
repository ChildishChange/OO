public class HolyCrapElevatorSys
{
	private static long time;
	public static void main(String args[])
	{
		try{
		time = System.currentTimeMillis();
		RequestList L = new RequestList();//调度器与请求模拟器公用
		RequestList M1 = new RequestList();//调度器与电梯公用
		RequestList M2 = new RequestList();//调度器与电梯公用
		RequestList M3 = new RequestList();//调度器与电梯公用
		Runnable R_IN = new RequestInput(L,time);
		Runnable Ele1 = new Elevator(M1,time);
		Runnable Ele2 = new Elevator(M2,time);
		Runnable Ele3 = new Elevator(M3,time);
		Runnable H_DIS = new HolyCrap_Dispatcher(L,(Elevator)Ele1,(Elevator)Ele2,(Elevator)Ele3);
		Thread t1 = new Thread(R_IN);
		Thread t2 = new Thread(H_DIS);
		Thread t3 = new Thread(Ele1);
        Thread t4 = new Thread(Ele2);
        Thread t5 = new Thread(Ele3);
 
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		}
		catch(Exception e)
		{
			System.out.println("错误");
		}
		

        
	}
}