package OO_2;
import java.util.*;
import java.util.regex.Pattern;
public class RequestList 
{
	//一个request的
	private Request list[];
	private int Front;
	private int Rear;
	public RequestList()
	{
		this.list = new Request[1010];
		this.Front = 0;
		this.Rear = 0;
	}
	public void PushRequest(Request R)//因为是临时变量所以要重新创建
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
	//一个根据输入生成请求队列的方法
	public int ReqParse(String S)//0，输入结束准备运行，1输入正确且有效，2输入无效不正确的标识符，不正确的方向，不正确的数字范围，多余的其他非空字符，，3不满足时间顺序，整个请求队列无效，溢出
	{
		if(this.Rear>1000)
		{
			System.out.println("请求溢出，请求序列无法生成");
			return 3;
			
		}
		S = S.replace(" ","");
		S = S.replace("	","");//筛选掉所有的空格和tab
		char judge[] = S.toCharArray();
		if(S.equals(""))
		{
			System.out.println("请输入一点东西吧。。");
			return 2;
		}
		int[] parameter = new int[2];//第一个是楼层，第二个是请求时间
		if(S.equals("FINISH"))
		{
			return 0;
		}

		boolean b = Pattern.matches("^(\\(ER,(-|\\+)?\\d{1,2},(-|\\+)?\\d{1,9}\\))|(\\(FR,(-|\\+)?\\d{1,2},((UP)|(DOWN)),(-|\\+)?\\d{1,9}\\))$", S);//一次只能输入一个请求，若一行输入多个，只取第一个进行判断
		if(!b)
		{
			System.out.println("输入有误，请检查：");
			System.out.println("1.是否输入了不符合规则的字符");//一些正确格式的提醒
			System.out.println("2.大小写是否错误");
			System.out.println("3.请输入形如：(FR,N,UP/DOWN,T)或(ER,N,T)这样的请求");
			System.out.println("4：请求产生时间超过了规定的范围，请输入0-999999999之间的整数");
			return 2;////
		}
		/*===================负号问题=======================================*/
		if(S.indexOf("-")!=-1)
		{
			System.out.println("输入有误：请检查楼层数或时间是否出现了问题，如负数层或负数时间");
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
					i++;//此处可能会产生bug
				}
				j++;
			}
			
			//j++;
		}//第一个是楼层，第二个是请求时间
		
		/*=============================时间问题=========================================================*/
		
		if(Rear!=0&&this.list[Rear-1].getTime() > parameter[1])
		{
			System.out.println("没有按照时间顺序输入，无法调度.");
			return 3;
		}
		else if(Rear!=0&&this.list[Rear-1].getTime() == parameter[1])
		{
			System.out.println("不好意思。。本调度器不支持同时生成的请求，请继续输入：");
			return 2;
		}
		/*=============================上天入地=========================================================*/
		if(judge[1]=='F')
		{
			
			if(S.indexOf("U")!=-1&&parameter[0]==10)
			{
				System.out.println("不好意思，这栋楼只有10楼，本条数据无效，请继续输入");
				return 2;
			}
			else if(S.indexOf("D")!=-1&&parameter[0]==1)
			{
				System.out.println("不好意思，这栋楼没有地下设施，本条请求无效，请继续输入");
				return 2;
			}
		}
		if(parameter[0]>10||parameter[0]==0)
		{
			System.out.println("不好意思，这栋楼只有十层,没有地下室");
			return 2;
		}
		/* 楼层请求(FR, n, UP/DOWN, t)：F_R为标识，n为楼层号，UP为向上，DOWN为向下
		电梯内请求(ER, n, t)：E_R为标识，n为目标楼层号 */
	
		
		Request temp = new Request();
		temp.setFloor(parameter[0]);
		temp.setTime(parameter[1]);
		temp.setUD(0);
		temp.setType(0);
		this.PushRequest(temp);
		return 1;
		
		
		
	}	
}
