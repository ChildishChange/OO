package DDDC;

public class Position 
{
	private int X;
	private int Y;
	private int pathX=-1;
	private int pathY=-1;
	private Position Path;
	
	public Position(int x,int y)
	{
		this.X = x;
		this.Y = y;
	}
	
	public int getX(){return X;}
	public void setX(int x){X = x;}
	public int getY(){return Y;}
	public void setY(int y){Y = y;}
	public int getPathX(){return pathX;}
	public void setPathX(int pathX){this.pathX = pathX;}
	public int getPathY(){return pathY;}
	public void setPathY(int pathY){this.pathY = pathY;}

	
	
}
