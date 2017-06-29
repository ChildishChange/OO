package OO_2;
import java.util.*;
import java.util.regex.Pattern;
public class RequestList 
{
	//һ��request��
	private Request list[];
	private int Front;
	private int Rear;
	public RequestList()
	{
		this.list = new Request[1010];
		this.Front = 0;
		this.Rear = 0;
	}
	public void PushRequest(Request R)//��Ϊ����ʱ��������Ҫ���´���
	{
		this.list[Rear] = new Request();
		this.list[Rear].Duplicate(R);
		this.Rear++;
	}
	public Request PopRequest()
	{
		return this.list[this.Front++];
		
	}
	public boolean IsEmpty()
	{
		return (Front==Rear);
	}
	//һ��������������������еķ���
	public int ReqParse(String S)//0���������׼�����У�1������ȷ����Ч��2������Ч����ȷ�ı�ʶ��������ȷ�ķ��򣬲���ȷ�����ַ�Χ������������ǿ��ַ�����3������ʱ��˳���������������Ч�����
	{
		if(this.Rear>1000)
		{
			System.out.println("������������������޷�����");
			return 3;
			
		}
		S = S.replace(" ","");
		S = S.replace("	","");//ɸѡ�����еĿո��tab
		char judge[] = S.toCharArray();
		if(S.equals(""))
		{
			System.out.println("������һ�㶫���ɡ���");
			return 2;
		}
		int[] parameter = new int[2];//��һ����¥�㣬�ڶ���������ʱ��
		if(S.equals("FINISH"))
		{
			return 0;
		}

		boolean b = Pattern.matches("^(\\(ER,(-|\\+)?\\d{1,2},(-|\\+)?\\d{1,9}\\))|(\\(FR,(-|\\+)?\\d{1,2},((UP)|(DOWN)),(-|\\+)?\\d{1,9}\\))$", S);//һ��ֻ������һ��������һ����������ֻȡ��һ�������ж�
		if(!b)
		{
			System.out.println("�����������飺");
			System.out.println("1.�Ƿ������˲����Ϲ�����ַ�");//һЩ��ȷ��ʽ������
			System.out.println("2.��Сд�Ƿ����");
			System.out.println("3.���������磺(FR,N,UP/DOWN,T)��(ER,N,T)����������");
			System.out.println("4���������ʱ�䳬���˹涨�ķ�Χ��������0-999999999֮�������");
			return 2;////
		}
		/*===================��������=======================================*/
		if(S.indexOf("-")!=-1)
		{
			System.out.println("������������¥������ʱ���Ƿ���������⣬�縺�������ʱ��");
			return 2;
		}
		
		int j = 0;
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
			
			//j++;
		}//��һ����¥�㣬�ڶ���������ʱ��
		
		/*=============================ʱ������=========================================================*/
		
		if(Rear!=0&&this.list[Rear-1].getTime() > parameter[1])
		{
			System.out.println("û�а���ʱ��˳�����룬�޷�����.");
			return 3;
		}
		else if(Rear!=0&&this.list[Rear-1].getTime() == parameter[1])
		{
			System.out.println("������˼��������������֧��ͬʱ���ɵ�������������룺");
			return 2;
		}
		/*=============================�������=========================================================*/
		if(judge[1]=='F')
		{
			
			if(S.indexOf("U")!=-1&&parameter[0]==10)
			{
				System.out.println("������˼���ⶰ¥ֻ��10¥������������Ч�����������");
				return 2;
			}
			else if(S.indexOf("D")!=-1&&parameter[0]==1)
			{
				System.out.println("������˼���ⶰ¥û�е�����ʩ������������Ч�����������");
				return 2;
			}
		}
		if(parameter[0]>10||parameter[0]==0)
		{
			System.out.println("������˼���ⶰ¥ֻ��ʮ��,û�е�����");
			return 2;
		}
		/* ¥������(FR, n, UP/DOWN, t)��F_RΪ��ʶ��nΪ¥��ţ�UPΪ���ϣ�DOWNΪ����
		����������(ER, n, t)��E_RΪ��ʶ��nΪĿ��¥��� */
	
		
		Request temp = new Request();
		temp.setFloor(parameter[0]);
		temp.setTime(parameter[1]);
		temp.setUD(0);
		temp.setType(0);
		this.PushRequest(temp);
		return 1;
		
		
		
	}	
}
