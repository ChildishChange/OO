public class Request
{
	private int Type;//1F2E
	private int Floor;
	private int U_D;//-1D1UP
	private long time;//请求生成时间
	private int Elev = 0;
	private boolean SuccessInit = false;
	private boolean BeenDone = false;
	public Request()
	{
		this.Type = 0;
		this.Floor = 0;
		this.U_D =	0;
		this.time = 0;
		this.Elev = 0;
		this.SuccessInit = false;
		this.BeenDone = false;
	}
	public String toString(Request R)
	{
		String Str = new String();
		Str = ("("+((R.getType()==1)?"FR,"+R.getFloor()+","+((R.getU_D()==1)?"UP":"DOWN"):"ER,#"+R.getEle()+","+R.getFloor())+")");
		return Str;


	}
/*=================set/get=======================*/
	public void setType(int i){this.Type = i;}
	public int getType(){return Type;}	
	public void setFloor(int i){this.Floor = i;}
	public int getFloor(){return Floor;}
	public void setU_D(int i){this.U_D = i;}
	public int getU_D(){return	U_D;}
	public void settime(long i){this.time = i;}
	public long gettime(){return time;}
	public void setSuccess(boolean x){this.SuccessInit = x;}
	public boolean getSuccess(){return SuccessInit;}
	public void setBeenDone(boolean x){this.BeenDone = x;}
	public boolean getBeenDone(){return BeenDone;}
	public void setEle(int i){this.Elev = i;}
	public int getEle(){return this.Elev;}
/*===============================================*/

	public void Duplicate(Request R)
	{
		this.Type = R.Type;
		this.Elev = R.Elev;
		this.Floor = R.Floor;
		this.U_D = R.U_D;
		this.time = R.time;
		this.SuccessInit = R.SuccessInit;
		this.BeenDone = R.BeenDone;
	}
}