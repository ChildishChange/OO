//import java.lang.Math;

public class HolyCrap_Dispatcher implements Runnable
{
	private RequestList HolyCrapList;
//	private RequestList TaskOfEle;
	private Elevator Ele;
	private Elevator Ele1;
	private Elevator Ele2;
	private Elevator Elep[];
	public HolyCrap_Dispatcher(RequestList L,Elevator Ele,Elevator Ele1,Elevator Ele2)
	{
		this.HolyCrapList = L;
//		this.TaskOfEle = M;
		this.Ele = Ele;
		this.Ele1 = Ele1;
		this.Ele2 = Ele2;
		this.Elep = new Elevator[3];
		this.Elep[0] = this.Ele;
		this.Elep[1] = this.Ele1;
		this.Elep[2] = this.Ele2;
	}	
	
	public void run() 
	{
		try{
			Request temp = new Request();			
			int judgement[]={0,0,0};
							int sum = 0;
							int subsum = 0;
							int min = 10000000;
							int numofmin = 0;int k = 0;int i = 0;
			while(true)
			{
				
				while(!HolyCrapList.IsEmpty())
				{
			
					
					for(i = HolyCrapList.getFront();i<HolyCrapList.getRear();i++)
					{
						temp = HolyCrapList.getList(i);
						Thread.sleep(5);
					//	System.out.println(temp.toString(temp));
						if((temp.getEle()!=0&&
								Elep[temp.getEle()-1].getRequestList().IsEmpty()!=true&&
								HolyCrapList.judge(temp, Elep[temp.getEle()-1].getRequestList().getList(0), Elep[temp.getEle()-1]))||
								(temp.getEle()!=0&&
								Elep[temp.getEle()-1].getRequestList().IsEmpty()))//如果指定了特定电梯
						{
							
							HolyCrapList.delete(i);
							i--;
							Elep[temp.getEle()-1].getRequestList().push(temp);
						//	Thread.sleep(10);
					//		System.out.println(temp.toString(temp)+" "+temp.getEle());
						}
						else if(temp.getEle()==0)//没有指定电梯，即为楼层指令
						{
							
							for(k=0;k<3;k++)
							{
							//	System.out.println(Elep[k].getRequestList().IsEmpty()+" scf");
							//	System.out.println((!Elep[k].getRequestList().IsEmpty()&&HolyCrapList.judge(temp,Elep[k].getRequestList().getList(0),Elep[k])));
									
								if(Elep[k].getRequestList().IsEmpty())
								{	
									judgement[k] = 2;
								}	
								else if(!Elep[k].getRequestList().IsEmpty()&&HolyCrapList.judge(temp,Elep[k].getRequestList().getList(0),Elep[k]))
								{
									judgement[k] = 1;
								}
								//System.out.println(judgement[k]);
								
							}
							
							for(k = 0;k<3;k++)
							{
								if(judgement[k]==1)
									sum +=judgement[k];
							}
						//	System.out.println("sum = "+sum);Thread.sleep(100000000);
							if(sum>1)
							{
							
								for( k = 0;k<3;k++)
								{
									if(Elep[k].getMovement()<min&&judgement[k]==1)
									{
										numofmin = k;
										min = Elep[k].getMovement();
									}
								}
								HolyCrapList.delete(i);
								i--;
								temp.setEle(numofmin+1);
							//	System.out.println(k);
							//	Thread.sleep(10000000);
								Elep[numofmin].getRequestList().push(temp);
							//	Thread.sleep(10);
					//			System.out.println(temp.toString(temp)+" "+temp.getEle());
								

							}
							else if(sum==1)
							{
								for(k = 0;k<3;k++)
								{
									if(judgement[k]==1)
									{
										HolyCrapList.delete(i);
										i--;
										temp.setEle(numofmin+1);
									//	System.out.println(k);
									//	Thread.sleep(10000000);
										Elep[numofmin].getRequestList().push(temp);
						//				Thread.sleep(10);
					//					System.out.println(temp.toString(temp)+" "+temp.getEle());
										
									}
								}
							}
							else//等于0
							{
								for( k = 0; k<3 ; k++)
								{
									if(judgement[k]==2)
										subsum+=judgement[k];
								}
								if(subsum==0)
								{
									continue;
								}
								for( k = 0;k<3;k++)
								{
									if(Elep[k].getMovement()<min&&judgement[k]==2)
									{
										numofmin = k;
										min = Elep[k].getMovement();
									}
								}
							/*	if((judgement[0]+judgement[1]+judgement[2])!=0)
								{
									continue;
								}*/
								HolyCrapList.delete(i);
								i--;
								temp.setEle(numofmin+1);
							//	System.out.println(k);
							//	Thread.sleep(10000000);
								Elep[numofmin].getRequestList().push(temp);
							//	Thread.sleep(10);
					//			System.out.println(temp.toString(temp)+" "+temp.getEle());
								
							}
							sum = 0;
							subsum = 0;
							for( k = 0;k<3;k++)
								judgement[k] = 0;
							min = 10000000;numofmin = 0;
							k=0;
//						//	System.out.println(numofmin+"+1 sdfdssss");
//							if(Elep[numofmin].getRequestList().IsEmpty())//如果电梯为空，直接加入
//							{
//								HolyCrapList.delete(i);
//								i--;
//								temp.setEle(numofmin+1);
//								Elep[numofmin].getRequestList().push(temp);
//							//	System.out.println(temp.toString(temp)+" igo "+temp.getEle()+" ele");
//							}
//							else//判断能否捎带，能则加入
//							{
//								if(Elep[numofmin].getRequestList().getList(0)!=null&&HolyCrapList.judge(temp,Elep[numofmin].getRequestList().getList(0),Elep[numofmin]))
//								{
//									temp.setEle(numofmin+1);
//									HolyCrapList.delete(i);
//									i--;
//									Elep[numofmin].getRequestList().push(temp);
//							
//								}
//							}
							
							
							
						}
						//根据运动量来判断应该分配给哪部
						
					}
	
				
				
				}
				try
				{
					Thread.sleep(100);
				}
				catch(InterruptedException e){}
			}
		}

		catch(Exception e){}
		
		
		
	} 
}