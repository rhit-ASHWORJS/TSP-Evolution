import java.util.ArrayList;
import java.util.Collections;

public class Generation {
	ArrayList<Pop> population;
	Map map;
	
	public Generation(int size, Map map) {
		this.map = map;
		population = new ArrayList<Pop>();
		for(int i=0; i<size; i++)
		{
			population.add(new Pop(map));
		}
	}
	
	public Generation(ArrayList<Pop> population, Map map)
	{
		this.population = population;
		this.map = map;
	}
	
	public void sortPopsFromBestToWorst()
	{
		Collections.sort(population);
	}
	
	public void printGenStatistics()
	{
		this.sortPopsFromBestToWorst();
		
		System.out.println("Num Individuals:"+population.size());
		
		System.out.println("Shortest Distance:"+population.get(0).routeDistance());
		
		int avgDistance=0;
		for(int i=0; i<population.size(); i++)
		{
			avgDistance += population.get(i).routeDistance();
		}
		avgDistance /= population.size();
		
		System.out.println("Average Distance:"+avgDistance);
		System.out.println("Worst Distance:"+population.get(population.size()-1).routeDistance());
	}

}


