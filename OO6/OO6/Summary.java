package OO6;

public class Summary 
{
	private int renamed = 0;
	private int pathchange = 0;
	private int sizechange = 0;
	private int modifiedtimechange = 0;
	public int getRenamed() {
		return renamed;
	}
	public synchronized void setRenamed() {
		this.renamed++;
	}
	public int getPathchange() {
		return pathchange;
	}
	public synchronized void setPathchange() {
		this.pathchange++;
	}
	public int getSizechange() {
		return sizechange;
	}
	public synchronized void setSizechange() {
		this.sizechange++;
	}
	public int getModifiedtimechange() {
		return modifiedtimechange;
	}
	public synchronized void setModifiedtimechange() {
		this.modifiedtimechange++;
	}
	
}
