
package OO_2;
//˳����ʲô����

//���ڵ�˼·�ǵ���ִ��һ��ָ��ʱ����֮���ָ���ж��Ƿ����Ӵ�һ��ָ�����ִ������ָ�ֱ���������ӵ�ָ�ִ���꣬��ִ�����һ��ָ��
//��������Ҫ���һ������ʱ��ִ�е�  flag
//����û����
//�������������ҵ�����ָ������һ����
//���û�У������

//���������������->ִ�����+�Ӵ���� ͬ�����

//��Ҫ���Է���request���Եķ���
//��Ҫ�ж��Ƿ���Ա��Ӵ��Ĵ���
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
				//System.out.println("��������Ϊ��");
				System.out.println("(1,STAY,0)");
				return ;
		}
		while(!L.IsEmpty())
		{
			temp = L.PopRequest();
			int j = -1;int t = 11;
			Request temp2 = new Request();
			int index[]={-1,-1};//һΪmax����Ϊmin 
			for(int i = L.getFront();i<L.getRear();i++)
			{
				if(temp.judge(temp,L.getList(i),TimeOfDispatch,E))
				{
					System.out.println(temp.toString(temp)+"("+L.getList(i).toString(L.getList(i))+")");
				}
			}
			while(true)//��ֱ���Ҳ�
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
						//�˴����ݼ�������ֱ��жϣ��жϳɹ�����/**/�еĴ�����
					if(temp.judge(temp,L.getList(i),TimeOfDispatch,E)&&
					  (Math.abs(L.getList(i).getFloor()-E.getFloor())<t)&&
					  ((E.getFloor()-L.getList(i).getFloor())*(temp.getFloor()-L.getList(i).getFloor())<0))//������ֻ���Ӵ����һ������������
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
				//����temp2��min
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