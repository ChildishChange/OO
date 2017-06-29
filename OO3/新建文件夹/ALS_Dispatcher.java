
package OO_2;
//顺带是什么鬼呢

//现在的思路是当我执行一条指令时，从之后的指令判断是否能捎带一条指令，能则执行这条指令，直到所有能捎的指令都执行完，再执行最后一条指令
//请求类里要添加一个请求时否被执行的  flag
//好像没有了
//关于输出，如果找到了主指令（，最后一个）
//如果没有，不输出

//所以输出的样子是->执行情况+捎带情况 同步输出

//需要可以访问request属性的方法
//需要判断是否可以被捎带的代码
//
import java.math.*;
import java.util.*;
public class ALS_Dispatcher extends Dispatcher 
{
	private double TimeOfDispatch = 0;
	private double TimeOfTemp = 0;
	public double getTimeOfDispatch()
	{
		return this.TimeOfDispatch;
	}
	public void changeTimeOfDispatch(double t)
	{
		this.TimeOfDispatch+=t;
	}
	public void Synchronization(int n)
	{
		this.TimeOfDispatch = (double)n;
	}
	public void ALS_ElevatorRun(RequestList L,Elevator E)
	{
		
		Request temp = new Request();
		if(L.IsEmpty())
		{
				//System.out.println("请求序列为空");
				System.out.println("(1,STAY,0)");
				return ;
		}
		while(!L.IsEmpty())
		{
			temp = L.PopRequest();
			int j = -1;int t = 11;
			Request temp2 = new Request();
			int index[]={-1,-1};//一为max，二为min 
			for(int i = L.getFront();i<L.getRear();i++)
			{
				if(temp.judge(temp,L.getList(i),TimeOfDispatch,E))
				{
					System.out.println(temp.toString(temp)+"("+L.getList(i).toString(L.getList(i))+")");
				}
			}
			while(true)//找直到找不
			{
				t=11;
				
				for(int i = L.getFront();i<L.getRear();i++)
				{
					if(index[0]==-1&&temp.judge(temp,L.getList(i),TimeOfDispatch,E))
					{
						if((L.getList(i).getFloor()>temp.getFloor())&&(temp.getFloor()>E.getFloor()))
						{
							index[0]=i;
							
						//	 System.out.println(E.getFloor()+" "+temp.getFloor()+" "+L.getList(i).getFloor()+" "+i);
							L.move(index[0]);
						}
						else if((temp.getFloor()<E.getFloor())&&(L.getList(i).getFloor()<temp.getFloor()))
						{
							index[0]=i;
						//	L.move(index[0]);System.out.println(E.getFloor()+" "+temp.getFloor()+" "+L.getList(i).getFloor());
							
						}
						
					}
				}
				for(int i = (index[0]==-1)?L.getFront():L.getFront()+1;i<L.getRear();i++)
				{
					
				//	System.out.println(temp.judge(temp,L.getList(i),TimeOfDispatch,E));
						//此处根据几种情况分别判断，判断成功则用/**/中的代码标记
					if(temp.judge(temp,L.getList(i),TimeOfDispatch,E)&&
					  (Math.abs(L.getList(i).getFloor()-E.getFloor())<t)&&
					  ((E.getFloor()-L.getList(i).getFloor())*(temp.getFloor()-L.getList(i).getFloor())<0))//这样做只会捎带最后一条产生的请求
					{
					//	System.out.println(L.getList(i).getFloor());
						t=Math.abs(L.getList(i).getFloor()-E.getFloor());
						temp2 = L.getList(i);
						index[1] = i;
						j = i;
					}
					
				}
				if(j == -1)
				{
					E.Goto(temp.getFloor(),this);
					
					break;
				}
				//现在temp2是min
				E.Goto(temp2.getFloor(),this);
				//System.out.println(temp.toString(temp)+"("+temp2.toString(temp2)+")");
/*				if(index[0]!=-1)
					L.delete(index[1]+1);
				else*/
				L.delete(index[1]);
				
				j=-1;
			}
		}
		
	}
}