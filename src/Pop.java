import java.util.HashSet;
import java.util.Set;

public class Pop implements Comparable<Pop>{
	Map map;
	int[] route;
	
	public Pop(Map map, int[] route) 
	{ //For predetermined routes
		this.map = map;
		this.route = route;
	}
	
	public Pop(Map map) 
	{ //Randomly generated route
		this.map = map;
		this.route = new int[map.numPoints()];
		for(int i=0; i<map.numPoints(); i++)
		{
			route[i]=i;
		}
		mutate(map.numPoints());//Should throughly randomize our route
	}
	
	//As opposed to normal mutate, this doesn't guarantee a mutation, it just has a chance of mutating as a parameter
	//chance should be a double from 0-1, i.e. 0.45 means 45% chance to mutate
	private void mutateChance(double chance)
	{
		for(int i=0; i<route.length; i++) {
			double roll = SeededRandom.rnd.nextDouble();
			if(roll <= chance)
			{
				mutate(1);
			}
		}
	}
	
	private void mutate(int numMutations)
	{
		for(int i=0; i<numMutations; i++)
		{
			int pos1 = SeededRandom.rnd.nextInt(route.length);
			int pos2 = SeededRandom.rnd.nextInt(route.length);
			while(pos2 == pos1)//make sure we don't just swap the same position with itself
			{
				pos2 = SeededRandom.rnd.nextInt(route.length);
			}
			
			int temp = route[pos1];
			route[pos1] = route[pos2];
			route[pos2] = temp;
		}
	}
	
	public Pop makeExactCopy()
	{
		int[] newRoute = new int[route.length];
		for(int i=0; i<route.length; i++)
		{
			newRoute[i]=route[i];
		}
		Pop copy = new Pop(this.map,newRoute);
		return copy;
	}
	
	public Pop makeMutatedCopy()
	{
		Pop copy = makeExactCopy();
		copy.mutateChance(Constants.MUT_RATE);
		return copy;
	}
	public Pop makeCrossOverCopy(Pop parent1, Pop parent2) {
		int[] newRoute = new int[route.length];
		Set<Integer> list1 = new HashSet();
		Set<Integer> list2 = new HashSet();
		for (int k=0; k<route.length; k++) {
			list1.add(k);
			list2.add(k);
		}
		int i;
		for(i=0; i<route.length/2; i++)
		{
			newRoute[i]=parent1.route[i];
			list1.remove(i);
		}
		for(int j=0; j<route.length; j++)
		{
			if (list1.contains(parent2.route[j]))
			newRoute[i]=parent2.route[j];
			list1.remove(parent2.route[i]);
			i++;
			if (i == route.length)
				break;
		}
	
		Pop copy = new Pop(this.map,newRoute);
		return copy;
		
	}
	public int routeDistance()
	{
		int distance = 0;
		for(int i=0; i<map.numPoints(); i++)
		{
			if(i<map.numPoints()-1)
			{
				distance += map.distanceBetweenPoints(route[i], route[i+1]);
			}
			else
			{
				distance += map.distanceBetweenPoints(route[0], route[i]);
			}
		}
		return distance;
	}

	/**
	 * NOTE:
	 * 
	 * This is set up to sort by route distance, so you may want to sort the list backwards
	 * depending on use
	 */
	@Override
	public int compareTo(Pop o) {
		if(this.routeDistance() > o.routeDistance()) //We are longer
		{
			return 1;
		}
		else if(this.routeDistance() == o.routeDistance()) //We are equal
		{
			return 0;
		}
		else //They are longer
		{
			return -1;
		}
	}
}
