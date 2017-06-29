package DDDC;

public class Position 
{
	//overview:表示位置的一个有序偶，属性有X,Y,pathX,pathY,Path,dir
	private int X;
	private int Y;
	private int pathX=-1;
	private int pathY=-1;
	private Position Path;
	private int dir = 0;
	//抽象函数：AF(C) = (X,Y,pathX,pathY,Path,dir) where X = C.X,Y = C.Y,pathX = C.pathX,pathY = C.pathY,Path = C.Path,dir = C.dir
	//不变式：C.X<=79&&C.X>=0;&&;C.Y<=79&&C.Y>=0;&&C.dir>=0&&C.dir<=4;
	public Position(int x,int y)
	{
		this.X = x;
		this.Y = y;
	}
	public Position(int x,int y,int px,int py)
	{
		this.X = x;
		this.Y = y;
		this.pathX = px;
		this.pathY = py;
	}
	public boolean repOK()
	{
		if(this.X>79||this.Y<0)
			return false;		
		if(this.Y>79||this.Y<0)
			return false;		
		if(this.dir>4||this.dir<0)
			return false;	
		return true;
	}
	public int getX()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return X;
	}
	public void setX(int x)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		X = x;
	}
	public int getY()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return Y;
	}
	public void setY(int y)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		Y = y;
	}
	public int getPathX()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return pathX;
	}
	public void setPathX(int pathX)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.pathX = pathX;
	}
	public int getPathY()
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return pathY;
	}
	public void setPathY(int pathY)
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.pathY = pathY;
	}

	public int getDir() 
	{
		//Requires:the value of the variable is not a null
		//Modifies:nothing
		//Effects:get the value of the variable
		return dir;
	}

	public void setDir(int dir) 
	{
		//Requires:some parameters
		//Modifies:a variable
		//Effects:change the value of the variable
		this.dir = dir;
	}

	
	
}
