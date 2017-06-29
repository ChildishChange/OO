package DDDC;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

public class Map 
{
	private int EDGE = 80;
	private int[][] DigitalMap = new int[EDGE][EDGE];
	private int[][] AdjacentMatrix = new int[EDGE*EDGE][EDGE*EDGE];
	public Map(File F)
	{ 
		try
		{
			int c = 0;
			int i = 0;
			int j = 0;
			for(i =0;i<EDGE*EDGE;i++)
			{
				for(j =0;j<EDGE*EDGE;j++)
				{
					if(i==j)
					{	
						this.AdjacentMatrix[i][j]=1;
						continue;
					}
					this.AdjacentMatrix[i][j] = -1;
				}
			}
			i = 0;
			j = 0;
			Reader reader = null;
			reader = new InputStreamReader(new FileInputStream(F));
			while((c= reader.read())!=-1)
			{
				if(c=='\n'||c=='\r')
				{
					continue;
				}
				this.DigitalMap[i][j++] = c-'0';
				if(j==EDGE)
				{
					j=0;
					i++;
				}
			}
			reader.close();
			for(i = 0;i<EDGE;i++)
			{
				for(j = 0;j<EDGE;j++)
				{
					if(this.DigitalMap[i][j]==0){continue;}
					else if(this.DigitalMap[i][j]==1)
					{
						this.AdjacentMatrix[i*EDGE+j][i*EDGE+j+1] = 1;
						this.AdjacentMatrix[i*EDGE+j+1][i*EDGE+j] = 1;
					}
					else if(this.DigitalMap[i][j]==2)
					{
						this.AdjacentMatrix[i*EDGE+j][(i+1)*EDGE+j] = 1;
						this.AdjacentMatrix[(i+1)*EDGE+j][i*EDGE+j] = 1;
					}
					else if(this.DigitalMap[i][j]==3)
					{
						this.AdjacentMatrix[i*EDGE+j][i*EDGE+j+1] = 1;
						this.AdjacentMatrix[i*EDGE+j+1][i*EDGE+j] = 1;
						
						this.AdjacentMatrix[i*EDGE+j][(i+1)*EDGE+j] = 1;
						this.AdjacentMatrix[(i+1)*EDGE+j][i*EDGE+j] = 1;
					}
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
			

	}
	public int getDigitalMap(int x,int y)
	{
		if(x>=0&&x<=79&&y>=0&&y<=79)
		{
			return this.DigitalMap[x][y];
		}
		else
		{
			System.out.println("输入的地图范围有误！");
			return 4;
		}
		
	}
	public ArrayList<Integer> BFS(Map map,int SourceX,int SourceY,int TargetX,int TargetY)
	{
		ArrayList<Position> Queue = new ArrayList<Position>();//将要使用的队列
		ArrayList<Position> Store = new ArrayList<Position>();
		int[][] distance = new int[EDGE*EDGE][2];//每一个变量表示从source到这个点的距离
		for(int i = 0;i<EDGE*EDGE;i++)
		{
			if(i==(SourceX*EDGE+SourceY))//Source到自己的距离是0
			{
				distance[i][0] = 0;
				distance[i][1] = 0;
			}
			else//其他的是无穷大
			{
				distance[i][0] = -1;
				distance[i][1] = 0;
			}
		}
		int[][] temp = map.AdjacentMatrix;//获取邻接矩阵
		Position Source = new Position(SourceX,SourceY);//构造起始位置
//		Source.setPathX(-1);
//		Source.setPathY(-1);
		
		Queue.add(Source);//入队
		int count = 0;
		while(Queue.isEmpty()==false)//当队列不为空
		{
			Position tP = Queue.get(0);
			Queue.remove(0);//出队
			Store.add(tP);
		//	System.out.println(count++);
			
			int i = tP.getX();
			int j = tP.getY();
			
			distance[i*EDGE+j][1] = 1;//将这个位置设为已标记
			if(i==TargetX&&j==TargetY)
			{
				break;
			}
			if(i*EDGE+j>=0&&i*EDGE+j<EDGE*EDGE&&
			   i*EDGE+j+1>=0&&i*EDGE+j+1<EDGE*EDGE&&
			   temp[i*EDGE+j][i*EDGE+j+1]==1&&distance[i*EDGE+j+1][1]!=1)//周围所有可以到达且未被标记的点
			{
				distance[i*EDGE+j+1][0] = distance[i*EDGE+j][0]+1;//距离加一
				
				Position w = new Position(i,(j+1));//构造新位置
				
				w.setPathX(i);
				w.setPathY(j);
				//设置前一步的位置
				
				distance[i*EDGE+j+1][1] = 1;//标记
				
				Queue.add(w);//入队
				
			}
			if(i*EDGE+j>=0&&i*EDGE+j<EDGE*EDGE&&
			   (i+1)*EDGE+j>=0&&(i+1)*EDGE+j<EDGE*EDGE&&
			   temp[i*EDGE+j][(i+1)*EDGE+j]==1&&distance[(i+1)*EDGE+j][1]!=1)
			{
				distance[(i+1)*EDGE+j][0] = distance[i*EDGE+j][0]+1;
				
				Position w = new Position((i+1),j);
				
				w.setPathX(i);
				w.setPathY(j);
				
				distance[(i+1)*EDGE+j][1] = 1;
				
				Queue.add(w);
				
			}
			if(i*EDGE+j>=0&&i*EDGE+j<EDGE*EDGE&&
			   i*EDGE+j-1>=0&&i*EDGE+j-1<EDGE*EDGE&&
			   temp[i*EDGE+j][i*EDGE+j-1]==1&&distance[i*EDGE+j-1][1]!=1)
			{
				distance[i*EDGE+j-1][0] = distance[i*EDGE+j][0]+1;
				
				Position w = new Position(i,(j-1));
				
				w.setPathX(i);
				w.setPathY(j);
				
				distance[i*EDGE+j-1][1] = 1;
				
				Queue.add(w);
				
			}
			if(i*EDGE+j>=0&&i*EDGE+j<EDGE*EDGE&&
			   (i-1)*EDGE+j>=0&&(i-1)*EDGE+j<EDGE*EDGE&&
			   temp[i*EDGE+j][(i-1)*EDGE+j]==1&&distance[(i-1)*EDGE+j][1]!=1)
			{
				distance[(i-1)*EDGE+j][0] = distance[i*EDGE+j][0]+1;
				Position w = new Position((i-1),j);
				
				
				w.setPathX(i);
				w.setPathY(j);
				
				distance[(i-1)*EDGE+j][1] = 1;
				
				Queue.add(w);
			}
			
		}
//		打印最短路径
//		for(Position step:Store)
//		{
//			System.out.println(step.getX()+","+step.getY()+" "+step.getPathX()+","+step.getPathY());
//		}
//		map.PrintPath(Store,TargetX,TargetY);
		
		ArrayList<Position> Path = new ArrayList<Position>();//注意获得到的Path是反的路径
		while(true)
		{
			for(Position step:Store)
			{
	
				if(step.getX()==TargetX&&step.getY()==TargetY)
				{			
					if(step.getPathX()==-1)
					{
						Path.add(step);
						ArrayList<Integer> directions= new ArrayList<Integer>();
						
						int tempX = 0;
						int tempY = 0;
						int temp1X = 0;
						int temp1Y = 0;
						
						for(int i = 0;i<Path.size()-1;i++)
						{
							tempX = Path.get(i+1).getX();
							tempY = Path.get(i+1).getY();
							temp1X = Path.get(i).getX();
							temp1Y = Path.get(i).getY();
							
							if(tempX==temp1X&&tempY>temp1Y)//left
							{
								directions.add(0,3);
					//			System.out.println(3);
							}
							else if(tempX==temp1X&&tempY<temp1Y)//right
							{
								directions.add(0,4);
					//			System.out.println(4);
							}
							else if(tempX>temp1X&&tempY==temp1Y)//up
							{
								directions.add(0,1);
						//		System.out.println(1);
							}
							else if(tempX<temp1X&&tempY==temp1Y)//down
							{
								directions.add(0,2);
					//			System.out.println(2);
							}
							
						}
						return directions;
					//	return Path;
						//System.out.println("("+TargetX+","+TargetY+")");
					}
					else
					{
						TargetX = step.getPathX();
						TargetY = step.getPathY();
						Path.add(step);
						//System.out.println("("+TargetX+","+TargetY+")");
					}
					
					
				}
			}
		}
		
	
		
	}	
	public void PrintPath(ArrayList<Position> store, int targetX, int targetY) 
	{
	//	System.out.println("wsad");
		for(Position step:store)
		{

			if(step.getX()==targetX&&step.getY()==targetY)
			{			
				if(step.getPathX()==-1)
				{
					System.out.println("("+targetX+","+targetY+")");
				}
				else
				{
					PrintPath(store,step.getPathX(),step.getPathY());
					System.out.println("("+targetX+","+targetY+")");
				}
				
				
			}
		}
		
	}
	
	public int getRandomDirection(int X,int Y)
	{
		ArrayList<Integer> direction = new ArrayList<Integer>();
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
			direction.add(4);
		
					
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X+1)*EDGE+Y>=0&&(X+1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			direction.add(2);
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y-1>=0&&X*EDGE+Y-1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			direction.add(3);
					
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X-1)*EDGE+Y>=0&&(X-1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{
			direction.add(1);
		}
		
		Random rand = new Random();
		int randInt = rand.nextInt(10);
		randInt = randInt%direction.size();
		return direction.get(randInt);
	}
	
	
//	public static void main(String[] args)
//	{
//		Map P  = new Map(new File("G:\\课程\\OO\\作业七\\map.txt"));
////		ArrayList<Position> Path = new ArrayList<Position>();
////		Path = P.BFS(P,0,0,79,79);
////		for(int i = Path.size()-1;i>=0;i--)
////		{
////			System.out.println("("+Path.get(i).getX()+","+Path.get(i).getY()+")");
////		}
//		
//		
//		
//		ArrayList<Integer> Path = new ArrayList<Integer>();
//		Path = P.BFS(P,0,0,79,79);
//		for(int i = 0;i<Path.size();i++)//1up2down3left
//		{
//			if(Path.get(i)==1)
//				System.out.println("↑1");
//			else if(Path.get(i)==2)
//				System.out.println("↓2");
//			else if(Path.get(i)==3)
//				System.out.println("←3");
//			else if(Path.get(i)==4)
//				System.out.println("→4");
//		}
//		System.out.println();
////		for(int i = 0;i<P.EDGE;i++)
////		{
////			for(int j = 0;j<P.EDGE;j++)
////			{
////				System.out.print(P.DigitalMap[i][j]);
////			}
////			System.out.println("");
////			
////		}
////		
////		for(int i = 0;i<P.EDGE*P.EDGE;i++)
////		{
////			for(int j = 0;j<P.EDGE*P.EDGE;j++)
////			{
////				System.out.print(P.AdjacentMatrix[i][j]+"	");
////			}
////			System.out.println("");
////			
////		}
//		
//	}

	

}
