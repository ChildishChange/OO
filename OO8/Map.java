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
	private int[][][] Flow = new int[EDGE][EDGE][2];
	private ArrayList<Position> closed = new ArrayList<Position>();
	private boolean initialization = true;
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
					if(j!=0)
					{
						System.out.println("错误的地图！");
						this.initialization = false;
						break;
					}
//					if(i==EDGE)
//					{
//						System.out.println("错误的地图！");
//						this.initialization = false;
//						break;
//					}
					continue;
				}
				if((c-'0')>3||(c-'0')<0)
				{
					System.out.println("出现了不合法的数字："+(c-'0'));
					this.initialization = false;
				}
				this.DigitalMap[i][j++] = c-'0';
				if(j==EDGE)
				{
					j=0;
					i++;
				}
			}
			
			if(i!=EDGE)
			{
				System.out.println("错误的地图！");
				this.initialization = false;
			}
			reader.close();
			for(i = 0;i<EDGE;i++)
			{
				for(j = 0;j<EDGE;j++)
				{
					if(this.DigitalMap[i][j]==0)
					{
						this.Flow[i][j][0] = -1;
						this.Flow[i][j][1] = -1;
						continue;
					}
					else if(this.DigitalMap[i][j]==1)
					{
						if(j==(EDGE-1))
						{
							System.out.println("地图错误：坐标("+i+","+j+")指向了地图外的点。");
							this.initialization = false;
							continue;
						}
						this.Flow[i][j][0] = 0;
						this.Flow[i][j][1] = -1;
						this.AdjacentMatrix[i*EDGE+j][i*EDGE+j+1] = 1;
						this.AdjacentMatrix[i*EDGE+j+1][i*EDGE+j] = 1;
					}
					else if(this.DigitalMap[i][j]==2)
					{
						if(i==(EDGE-1))
						{
							System.out.println("地图错误：坐标("+i+","+j+")指向了地图外的点。");
							this.initialization = false;
							continue;
						}
						this.Flow[i][j][0] = -1;
						this.Flow[i][j][1] = 0;
						
						this.AdjacentMatrix[i*EDGE+j][(i+1)*EDGE+j] = 1;
						this.AdjacentMatrix[(i+1)*EDGE+j][i*EDGE+j] = 1;
					}
					else if(this.DigitalMap[i][j]==3)
					{
						if(i==(EDGE-1))
						{
							System.out.println("地图错误：坐标("+i+","+j+")指向了地图外的点。");
							this.initialization = false;
							continue;
						}
						else if(j==(EDGE-1))
						{
							System.out.println("地图错误：坐标("+i+","+j+")指向了地图外的点。");
							this.initialization = false;
							continue;
						}
						
						this.Flow[i][j][0] = 0;
						this.Flow[i][j][1] = 0;
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
		//Requires:the value of x and y is in the range of 0 to 79 and the value in the coordinate is not a null;
		//Modifies:nothing
		//Effects:get the value
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
	
	public int BFS(Map map,int SourceX,int SourceY,int TargetX,int TargetY)
//	public ArrayList<Integer> BFS(Map map,int SourceX,int SourceY,int TargetX,int TargetY)
	{
		//Requires:so many things which I've ALREADY ANNOUNCED IN THE NAME OF THE FUNCTION!
		//Modifies:nothing
		//Effects:return the length of the shortest way between this two coordinates in a BFS way
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
//					    return directions;
						return directions.size();
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
		//Requires:some parameters
		//Modifies:nothing
		//Effects:print the path to the target coordinate

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
	
	public int getMinFlowDirection(int X,int Y,int targetX,int targetY,int distance)
	{
		//Requires:some variables
		//Modifies:nothing
		//Effects:get the first step of the shortest path which have the least car flow
		int direction = 0;
		int flow = 10000;
		
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
			int tempdis = this.BFS(this,X,Y+1,targetX,targetY);
			if(tempdis<distance)
			{
				if(this.Flow[X][Y][0]<flow)
				{
					distance = tempdis;
					direction = 4;
					flow = this.Flow[X][Y][0];
				}
			}
			
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X+1)*EDGE+Y>=0&&(X+1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			int tempdis = this.BFS(this,X+1,Y,targetX,targetY);
			if(tempdis<distance)
			{
				if(this.Flow[X][Y][1]<flow)
				{
					distance = tempdis;
					direction = 2;
					flow = this.Flow[X][Y][1];
				}
			}	
		}
		
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y-1>=0&&X*EDGE+Y-1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			int tempdis = this.BFS(this,X,Y-1,targetX,targetY);
			if(tempdis<distance)
			{		
				if(this.Flow[X][Y-1][0]<flow)
				{
					distance = tempdis;		
					direction = 3;
					flow = this.Flow[X][Y-1][0];
				}
			}	
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X-1)*EDGE+Y>=0&&(X-1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{
			int tempdis = this.BFS(this,X-1,Y,targetX,targetY);
			if(tempdis<distance)
			{
				if(this.Flow[X-1][Y][1]<flow)
				{
					direction = 1;
						
							
				}
			}
		}
		
		return direction;
	}
	public int getRandomDirection(int X,int Y)
	{
		//Requires:some variable
		//Modifies:nothing
		//Effects:choose a road which have the least car flow
		int direction = 0;
		int flow = 10000;
	
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
			if(this.Flow[X][Y][0]<flow)
			{
				direction = 4;
				flow = this.Flow[X][Y][0];
			}
		
					
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X+1)*EDGE+Y>=0&&(X+1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			if(this.Flow[X][Y][1]<flow)
			{
				
				direction = 2;
				flow = this.Flow[X][Y][1];
			}
			
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y-1>=0&&X*EDGE+Y-1<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			
			if(this.Flow[X][Y-1][0]<flow)
			{
				
				direction = 3;
				flow = this.Flow[X][Y-1][0];
			}
			
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X-1)*EDGE+Y>=0&&(X-1)*EDGE+Y<EDGE*EDGE&&
		   this.AdjacentMatrix[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{
			if(this.Flow[X-1][Y][1]<flow)
			{
				direction = 1;
			
				
			}
			
		}
		
		
		return direction;
	}
	
	public void closeRoad(int x,int y,int j)
	{
		//Requires:some variable and some prerequisites
		//Modifies:the DigitalMap, the AdajacentMatrix , the Flow
		//Effects:close a road
		if((x<0||x>79)||(y>79||y<0))
		{
			System.out.println("不存在这样的道路！错误！");
			return;
		}
		Position closedRoad = new Position(x,y);
		
		closedRoad.setDir(j);
		if(j==0)//------------
		{
			if(this.DigitalMap[x][y]==0)
			{
				System.out.println("不存在这样的道路！错误！");
				return;
			}
			else if(this.DigitalMap[x][y]==1)
			{
				this.DigitalMap[x][y]=0;
			}
			else if(this.DigitalMap[x][y]==2)
			{
				System.out.println("不存在这样的道路！错误！");
				return;
			}
			else if(this.DigitalMap[x][y]==3)
			{
				this.DigitalMap[x][y]=2;
			}
			this.AdjacentMatrix[x*this.EDGE+y][x*this.EDGE+y+1] = 0;
			this.AdjacentMatrix[x*this.EDGE+y+1][x*this.EDGE+y] = 0;
		}
		else if(j==1)//||||||||
		{
			if(this.DigitalMap[x][y]==0)
			{
				System.out.println("不存在这样的道路！错误！");
				return;
			}
			else if(this.DigitalMap[x][y]==1)
			{
				System.out.println("不存在这样的道路！错误！");
				return;
			}
			else if(this.DigitalMap[x][y]==2)
			{
				this.DigitalMap[x][y] = 0;
			}
			else if(this.DigitalMap[x][y]==3)
			{
				this.DigitalMap[x][y] = 1;
			}
			this.AdjacentMatrix[x*this.EDGE+y][(x+1)*this.EDGE+y] = 0;
			this.AdjacentMatrix[(x+1)*this.EDGE+y][x*this.EDGE+y] = 0;
		}
		else
		{
			System.out.println("不存在这样的道路！错误！");
			return;
		}
		
		this.Flow[x][y][j] = -1;
		closedRoad.setDir(j);
		this.closed.add(closedRoad);
	}
	public void openRoad(int x,int y,int j)
	{
		//Requires:some variable and some prerequisites
		//Modifies:the DigitalMap, the AdajacentMatrix , the Flow
		//Effects:open a road
		if((x<0||x>79)||(y>79||y<0))
		{
			System.out.println("不存在这样的道路！错误！");
			return;
		}
		int i = 0;
		for(i = 0;i<this.closed.size();i++)
		{
			Position X = this.closed.get(i);
			if(X.getX()==x&&X.getY()==y&&X.getDir()==j)
			{
				break;
			}
		}
		if(i==this.closed.size())
		{
			System.out.println("这条路并没有被关闭！或并不存在这样的路！");
			return;
		}
		this.DigitalMap[x][y]+=(j+1);
		
		this.Flow[x][y][j] = 0;
		if(j==0)
		{
			this.AdjacentMatrix[x*this.EDGE+y][x*this.EDGE+y+1]=1;
			this.AdjacentMatrix[x*this.EDGE+y+1][x*this.EDGE+y]=1;
		}
		else if(j==1)
		{
			this.AdjacentMatrix[x*this.EDGE+y][(x+1)*this.EDGE+y]=1;
			this.AdjacentMatrix[(x+1)*this.EDGE+y][x*this.EDGE+y]=1;
		}

		this.closed.remove(i);
			
	}
	
	public synchronized void AddFlow(int x,int y,int dir)
	{
		//Requires:some variable and some prerequisites
		//Modifies:the Flow
		//Effects:change the flow of a certain road
		this.Flow[x][y][dir]+=1;
	}
	public synchronized void SubFlow(int x,int y,int dir)
	{
		//Requires:some variable and some prerequisites
		//Modifies:the Flow
		//Effects:change the flow of a certain road
		this.Flow[x][y][dir]-=1;
	}
	
	
	
	public int getFlow(int x,int y,int j)
	{
		//Requires:some variable and some prerequisites
		//Modifies:nothing
		//Effects:get the flow of a certain road
		return this.Flow[x][y][j];
	}
	
	public boolean isInitialization() {
		//Requires:some variable and some prerequisites
		//Modifies:nothing
		//Effects:return the condition that the initialization
		return initialization;
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
