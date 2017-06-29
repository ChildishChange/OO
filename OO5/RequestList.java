import java.util.*;
import java.util.regex.Pattern;




public class RequestList
{
	private Request List[];
	private int Front;
	private int Rear;
	public RequestList()
	{
		this.List = new Request[1010];
		this.Front = 0;
		this.Rear = 0;
	}
	public int getFront()
	{
		return this.Front;
	}
	public int getRear()
	{
		return this.Rear;
	}
	public Request getList(int i)
	{
		
		return this.List[i];
	}
	public boolean IsEmpty()
	{
		return (Front==Rear);
	}
	/*========================================*/
	public Request ReqParse(String S,long time)
	{
		Request temp = new Request();
		S = S.replace(" ","");
		S = S.replace("	","");//ɸѡ�����еĿո��tab
		char judge[] = S.toCharArray();
		if(S.equals(""))
		{
			System.out.println("������һ�㶫���ɡ���");
			temp.setSuccess(false);
			return temp;
		}
		
		//(ER,#2,11),(FR,2,UP),(FR,2,DOWN);
		boolean b1 = Pattern.matches("^(\\(ER,#\\d+,(-|\\+)?\\d+\\))$", S);//һ��ֻ������һ��������һ����������ֻȡ��һ�������ж�
		boolean b2 = Pattern.matches("^(\\(FR,(-|\\+)?\\d+,((UP)|(DOWN))\\))$", S);
		if(!b1&&!b2)
		{
			
			System.out.println("�����������飺"+S);
			System.out.println("1.�Ƿ������˲����Ϲ�����ַ�");//һЩ��ȷ��ʽ������
			System.out.println("2.��Сд�Ƿ����");
			System.out.println("3.���������磺(FR,6,UP)��(ER,#1,2)����������");
			System.out.println("4���������ʱ�䳬���˹涨�ķ�Χ��������0-999999999֮�������");
			temp.setSuccess(false);
			return temp;
		}
		else if(b1)//ER//(ER,#2,11),(FR,2,UP),(FR,2,DOWN);
		{
			if(S.indexOf("-")!=-1)
			{
				System.out.println(S);
				System.out.println("������������¥�����Ƿ���������⣬�縺�������ʱ��");
				temp.setSuccess(false);
				return temp;
			}
			int j = 0;
			int[] parameter = new int[2];
			for(int i = 2;i<judge.length;i++)
			{
				
				if(judge[i]>='0'&&judge[i]<='9')
				{
					while(judge[i]>='0'&&judge[i]<='9')
					{
						//System.out.println(j);
						parameter[j] = parameter[j]*10;
						parameter[j] += judge[i]-'0';
						//System.out.println(parameter[j]);
						i++;//�˴����ܻ����bug
					}
					j++;
				}
				
			
			}
			if(parameter[0]<1||parameter[0]>3)
			{
				System.out.println("Ŀǰֻ֧��һ�����ŵ���");
				temp.setSuccess(false);
				return temp;
			}
			if(parameter[1]>20||parameter[1]<=0)
			{
				System.out.println("��֧�ֵ�¥��");
				temp.setSuccess(false);
				return temp;
			}
			temp.setEle(parameter[0]);
	//		System.out.println(temp.getEle());
			temp.setFloor(parameter[1]);
			temp.setType(2);
			temp.setSuccess(true);
			temp.setBeenDone(false);
		
			return temp;
			
			
			
		}
		else if(b2)//FR//(ER,#2,11),(FR,2,UP),(FR,2,DOWN);
		{
			if(S.indexOf("-")!=-1)
			{
				System.out.println(S);
				System.out.println("������������¥�����Ƿ���������⣬�縺�������ʱ��");
				temp.setSuccess(false);
				return temp;
			}
			int parameter = 0;
			for(int i = 2;i<judge.length;i++)
			{
				
				if(judge[i]>='0'&&judge[i]<='9')
				{
					while(judge[i]>='0'&&judge[i]<='9')
					{
						//System.out.println(j);
						parameter = parameter*10;
						parameter += judge[i]-'0';
						//System.out.println(parameter[j]);
						i++;//�˴����ܻ����bug
					}
					
				}
				
	
			}
			if(parameter>20||parameter<=0)
			{
				System.out.println("��֧�ֵ�¥��");
				temp.setSuccess(false);
				return temp;
			}
			if((parameter==1&&S.indexOf('D')!=-1)||(parameter==20&&S.indexOf('U')!=-1))
			{
				System.out.println("��֧�ֵķ���");
				temp.setSuccess(false);
				return temp;
			}
			
			temp.settime(time);
			temp.setFloor(parameter);
			temp.setType(1);
			temp.setSuccess(true);
			temp.setBeenDone(false);
			if(S.indexOf('D')!=-1)
			{
				temp.setU_D(-1);
			}
			else if(S.indexOf('U')!=-1)
			{
				temp.setU_D(1);
			}//	System.out.println(temp.toString(temp));
			return temp;
		}
		return temp;

		
		
	}
	/*=======================================*/

	public synchronized void push(Request R)//��Ϊ����ʱ��������Ҫ���´���
	{
		while(this.getRear()==1000)
		{
			 try {
	                System.err.println("��������.");
	                this.wait();
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	                e.printStackTrace();
	            }
		}
		
		this.List[Rear] = new Request();
		this.List[Rear].Duplicate(R);
		this.Rear++;
		this.notify();
	}
	public synchronized Request pop()
	{
		
		return this.List[this.Front++];

	} 
	public synchronized boolean judge(Request InputTemp, Request EleTemp,Elevator E)
	{
		if(E.getState()==1)//UP
		{	/*¥��ָ��*/				
			if(InputTemp.getType()==1&&
			   InputTemp.getU_D()==1&&
			   InputTemp.getFloor()<=EleTemp.getFloor()&&
			   InputTemp.getFloor()>E.getFloor())
			{
				return true;
			}
			else if(InputTemp.getType()==2&&InputTemp.getFloor()>E.getFloor())
			{
				return true;
			}
		}
		else if(E.getState()==-1)//DOWN
		{
			if(InputTemp.getType()==1&&
			   InputTemp.getU_D()==-1&&
			   InputTemp.getFloor()>=EleTemp.getFloor()&&
			   InputTemp.getFloor()<E.getFloor())
			{
				return true;
			}
			else if(InputTemp.getType()==2&&InputTemp.getFloor()<E.getFloor())
			{
				return true;
			}
			
		}
	/*	else if(E.getState()==0)
		{	
			if(InputTemp.getEle()==0&&
		       InputTemp.getFloor()==EleTemp.getFloor()&&
			   InputTemp.getU_D()==EleTemp.getU_D())
			return true;
		}*/
		return false;
	}
	public void delete(int i)
	{
		for(int j = i;j<this.Rear;j++)
		{
			this.List[j] = this.List[j+1];
		}
		this.Rear-=1;
	}
	public void move(int i)
	{
		Request temp = new Request();
		temp = this.List[i];
		for(int n = i;n>0;n--)
		{
			this.List[n] = this.List[n-1];
		}
		this.List[0] = temp;
		
	}
}