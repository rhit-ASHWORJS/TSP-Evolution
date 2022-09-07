import java.util.ArrayList;

//The map of points that the routes will be on
public class Map {
	private ArrayList<Point> points;//List of points, should have IDs from 0 to an integer
	
	//Max X and Y value, with the minimums implicitly zero.  Set these to the display size once visualizer is complete.
	private final int MAX_X = 500;
	private final int MAX_Y = 500;
	
	
	public Map() 
	{
		points = new ArrayList<Point>();
	}
	
	public Map(int numPoints)//random map with specified number of points
	{
		points = new ArrayList<Point>();
		for(int i=0; i<numPoints; i++)
		{
			int x = SeededRandom.rnd.nextInt(MAX_X);
			int y = SeededRandom.rnd.nextInt(MAX_Y);
			points.add(new Point(x, y));
		}
	}
	
	public Map(ArrayList<Point> points) 
	{
		this.points = points;
	}
	
	public ArrayList<Point> getPoints()
	{
		return points;
	}
	
	public void addPoint(Point p) 
	{
		points.add(p);
	}
	
	public int numPoints() 
	{
		return points.size();
	}
	
	public double distanceBetweenPoints(int aIndex, int bIndex)
	{
		Point a = points.get(aIndex);
		Point b = points.get(bIndex);
		return Math.sqrt(Math.pow(a.x-b.x, 2) + Math.pow(a.y-b.y, 2));
	}
}
