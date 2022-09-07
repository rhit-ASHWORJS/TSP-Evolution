import java.util.ArrayList;

public class GenerationFactory {
	public static Generation newGeneration(Generation oldGeneration)
	{
		oldGeneration.sortPopsFromBestToWorst();
		ArrayList<Pop> newPops = new ArrayList<Pop>();
		
		for(int i=0; i<Constants.ELITISM; i++)
		{
			newPops.add(oldGeneration.population.get(i));
		}
		
		int popsToMake = oldGeneration.population.size() - newPops.size();
		
		if(Constants.SELECTION_STRATEGY.equals("truncation"))//We always to 50% in truncation
		{
			for(int i=0; i<2; i++)
			{
				for(int popnum=0; popnum<popsToMake/2; popnum++)
				{
					newPops.add(oldGeneration.population.get(popnum).makeMutatedCopy());
				}
			}
		}
		Generation newGeneration = new Generation(newPops, oldGeneration.map);
		return newGeneration;
	}

}
