package DDDC;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;

public class Map 
{
	//overview：地图的属性有：地图边长，地图道路二维数组，地图交叉情况二维数组，邻接矩阵二维数组，车流量三维数组，被关闭的道路的动态数组，地图是否被成功初始化，交通信号灯的二维数组,还有给高级出租车使用的地图和邻接矩阵
	private int EDGE = 80;
	private int[][] DigitalMap = new int[EDGE][EDGE];
	private int[][] CruxMap = new int[EDGE][EDGE];
	private int[][] AdjacentMatrix = new int[EDGE*EDGE][EDGE*EDGE];
	private int[][][] Flow = new int[EDGE][EDGE][2];
	private ArrayList<Position> closed = new ArrayList<Position>();
	private boolean initialization = true;
	private TrafficSignal[][] TraSig = new TrafficSignal[EDGE][EDGE];
	
	private int[][] DigitalMapFor = new int[EDGE][EDGE];
	private int[][] AdjacentMatrixFor = new int[EDGE*EDGE][EDGE*EDGE];	
	//抽象函数：AF(C) = (EDGE,DigitalMap,CruxMap,AdjacentMatrix,Flow,closed,initialization,TraSig,DigitalMapFor,AdjacentMatrixFor) where EDGE = C.EDGE,DigitalMap = C.DigitalMap,CruxMap = C.CruxMap,AdjacentMatrix = C.AdjacentMatrix,Flow = C.Flow,closed = C.closed,initialization = C.initialization,TraSig = C.TraSig,DigitalMapFor = c.DigitalMapFor,AdjacentMatrixFor = C.AdjacentMatrixFor
	//不变式：C.EDGE ==80;&&C.DigitalMap !=null;&&C.CruxMap!=null;&&C.AdajacentMatrix != null;&&C.Flow!=null;&&C.closed != null;&&C.TrafficSignal!=null;&&C.DigitalMapFor!=null;&&C.AdjacentMatrixFor!=null
	public Map(File F,File Crux)
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
						this.AdjacentMatrixFor[i][j]=1;
						continue;
					}
					this.AdjacentMatrix[i][j] = -1;
					this.AdjacentMatrixFor[i][j]=-1;
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
				this.DigitalMapFor[i][j-1] = c-'0';
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
			i = 0;
			j = 0;
			reader = new InputStreamReader(new FileInputStream(Crux));
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
				if((c-'0')>1||(c-'0')<0)
				{
					System.out.println("出现了不合法的数字："+(c-'0'));
					this.initialization = false;
				}
				this.CruxMap[i][j++] = c-'0';
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
						this.AdjacentMatrixFor[i*EDGE+j][i*EDGE+j+1] = 1;
						
						this.AdjacentMatrix[i*EDGE+j+1][i*EDGE+j] = 1;
						this.AdjacentMatrixFor[i*EDGE+j][i*EDGE+j+1] = 1;
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
					
						this.AdjacentMatrixFor[i*EDGE+j][(i+1)*EDGE+j] = 1;
						this.AdjacentMatrixFor[(i+1)*EDGE+j][i*EDGE+j] = 1;
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
						
						this.AdjacentMatrixFor[i*EDGE+j][i*EDGE+j+1] = 1;
						this.AdjacentMatrixFor[i*EDGE+j+1][i*EDGE+j] = 1;
						
						this.AdjacentMatrixFor[i*EDGE+j][(i+1)*EDGE+j] = 1;
						this.AdjacentMatrixFor[(i+1)*EDGE+j][i*EDGE+j] = 1;
					}
				}
			}
		}
		catch(Exception e){e.printStackTrace();}
		
		
	}
	
	public boolean repOK()
	{
		for(int i = 0;i<EDGE;i++)
		{
			for(int j = 0;j<EDGE;j++)
			{
				if(this.DigitalMap[i][j]>3||this.DigitalMap[i][j]<0)
					return false;
			}		
			
		}
		for(int i = 0;i<EDGE;i++)
		{
			for(int j = 0;j<EDGE;j++)
			{
				if(this.CruxMap[i][j]>1||this.CruxMap[i][j]<0)
					return false;
			}		
			
		}
		for(int i = 0;i<EDGE*EDGE;i++)
		{
			for(int j = 0;j<EDGE*EDGE;j++)
			{
				if(this.AdjacentMatrix[i][j]>1||this.AdjacentMatrix[i][j]<0)
					return false;
			}		
			
		}

		return true;
		
	}
	
	public int[][] getAdjacent()
	{
		//REQUIRES:this.adjacentmatrix不为空
		//MODIFIED:nothing
		//EFFECTS:返回整个矩阵
		return this.AdjacentMatrix;
	}
	public int[][] getAdjacentFor()
	{
		//REQUIRES:this.adjacentmatrixFor不为空
		//MODIFIED:nothing
		//EFFECTS:返回整个矩阵
		return this.AdjacentMatrixFor;
	}

	public int getAdjacentLine(int X,int Y)
	{
		//Requires:AdjacentMatrix
		//Modifies:nothing
		//Effects:获取与这个点相邻的点的数量
		int dir = 0;
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
		
			dir++;
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&(X+1)*EDGE+Y>=0&&(X+1)*EDGE+Y<EDGE*EDGE&&this.AdjacentMatrix[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			dir++;

		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&X*EDGE+Y-1>=0&&X*EDGE+Y-1<EDGE*EDGE&&this.AdjacentMatrix[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			dir++;	
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&(X-1)*EDGE+Y>=0&&(X-1)*EDGE+Y<EDGE*EDGE&&this.AdjacentMatrix[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{		
			dir++;
		}			
		return dir;
	}
	public void initializeTraffic()
	{
		//Requires:CruxMap不为空
		//Modifies:CruxMap和红绿灯
		//Effects:根据CruxMap设置红绿灯
		for(int i = 0;i <EDGE;i++)
		{
			for(int j = 0;j <EDGE;j++)
			{
				if(this.CruxMap[i][j]==0)
				{
					this.TraSig[i][j] = null;
				}
				else
				{
//					System.out.print(this.getAdjacentLine(i,j));
					if(this.getAdjacentLine(i,j)==0||this.getAdjacentLine(i,j)==1||this.getAdjacentLine(i,j)==2)
					{
						this.CruxMap[i][j] = 0;
						this.TraSig[i][j] = null;
					}
					else
					{
						this.TraSig[i][j] = new TrafficSignal();
						this.TraSig[i][j].start();
						
					}
				}
				
			}
//			System.out.println();
		}
		
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
	
	public int BFS(int[][] Adjacent,int SourceX,int SourceY,int TargetX,int TargetY)
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
//		int[][] temp = map.AdjacentMatrix;//获取邻接矩阵
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
					   Adjacent[i*EDGE+j][i*EDGE+j+1]==1&&distance[i*EDGE+j+1][1]!=1)//周围所有可以到达且未被标记的点
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
					   Adjacent[i*EDGE+j][(i+1)*EDGE+j]==1&&distance[(i+1)*EDGE+j][1]!=1)
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
					   Adjacent[i*EDGE+j][i*EDGE+j-1]==1&&distance[i*EDGE+j-1][1]!=1)
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
					   Adjacent[i*EDGE+j][(i-1)*EDGE+j]==1&&distance[(i-1)*EDGE+j][1]!=1)
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
	
//	public int getMinFlowDirection(int X,int Y,int targetX,int targetY,Map map)
	public int getMinFlowDirection(int X,int Y,int targetX,int targetY,int[][] Ad)
	{
		//Requires:some variables
		//Modifies:nothing
		//Effects:get the first step of the shortest path which have the least car flow
		int direction = 0;
		int flow = 10000;
		int distance = 10000;
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&
				   Ad[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
			int tempdis = this.BFS(Ad,X,Y+1,targetX,targetY);
			if(tempdis<distance)
			{

				distance = tempdis;
				direction = 4;
				flow = this.Flow[X][Y][0];
//				if(this.Flow[X][Y][0]<flow)
//				{
//					distance = tempdis;
//					direction = 4;
//					flow = this.Flow[X][Y][0];
//				}
			}
			else if(tempdis==distance)
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
				   Ad[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			int tempdis = this.BFS(Ad,X+1,Y,targetX,targetY);
			if(tempdis<distance)
			{
//				if(this.Flow[X][Y][1]<flow)
//				{
					distance = tempdis;
					direction = 2;
					flow = this.Flow[X][Y][1];
//				}
			}	
			else if(tempdis==distance)
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
				   Ad[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			int tempdis = this.BFS(Ad,X,Y-1,targetX,targetY);
			if(tempdis<distance)
			{		
//				if(this.Flow[X][Y-1][0]<flow)
//				{
					distance = tempdis;		
					direction = 3;
					flow = this.Flow[X][Y-1][0];
//				}
				
			}	
			else if(tempdis==distance)
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
				   Ad[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{
			int tempdis = this.BFS(Ad,X-1,Y,targetX,targetY);
			if(tempdis<distance)
			{
//				if(this.Flow[X-1][Y][1]<flow)
//				{
					direction = 1;
						
							
//				}
			}
			else if(tempdis==distance)
			{
				if(this.Flow[X-1][Y][1]<flow)
				{
					direction = 1;
						
							
				}
			}
		}
		
		return direction;
	}
	public int getRandomDirection(int X,int Y,int[][] Ad)
	{
		
		//Requires:some variable
		//Modifies:nothing
		//Effects:choose a road which have the least car flow
		int direction = 0;
		int flow = 10000;
	
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y+1>=0&&X*EDGE+Y+1<EDGE*EDGE&&
				   Ad[X*EDGE+Y][X*EDGE+Y+1]==1)//right
		{
			
			if(this.Flow[X][Y][0]<flow)
			{
				direction = 4;
				flow = this.Flow[X][Y][0];
			}
		
					
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X+1)*EDGE+Y>=0&&(X+1)*EDGE+Y<EDGE*EDGE&&
				   Ad[X*EDGE+Y][(X+1)*EDGE+Y]==1)//down
		{
			if(this.Flow[X][Y][1]<flow)
			{
				
				direction = 2;
				flow = this.Flow[X][Y][1];
			}
			
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   X*EDGE+Y-1>=0&&X*EDGE+Y-1<EDGE*EDGE&&
				   Ad[X*EDGE+Y][X*EDGE+Y-1]==1)//left
		{
			
			if(this.Flow[X][Y-1][0]<flow)
			{
				
				direction = 3;
				flow = this.Flow[X][Y-1][0];
			}
			
		}
		if(X*EDGE+Y>=0&&X*EDGE+Y<EDGE*EDGE&&
		   (X-1)*EDGE+Y>=0&&(X-1)*EDGE+Y<EDGE*EDGE&&
				   Ad[X*EDGE+Y][(X-1)*EDGE+Y]==1)//up
		{
			if(this.Flow[X-1][Y][1]<flow)
			{
				direction = 1;
			
				
			}
			
		}
		
		
		return direction;
	}
	public boolean getTrafficSignal(int X,int Y,int dir)
	{
			//Requires:红绿灯矩阵不为空
			//Modifies:nothing
			//Effects:获取相应点上相应方向上的红绿灯情况
		if(this.CruxMap[X][Y]==0)
		{
			return true;
		}
		else
		{
			if(dir==3||dir==4)//ew
			{
				return this.TraSig[X][Y].getEW();
			
			}
			else if(dir==1||dir==2)//ns
			{
				return this.TraSig[X][Y].getNS();
			}
		
		}
		return false;
		
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
	
	


	

}
