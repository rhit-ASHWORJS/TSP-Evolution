import java.util.ArrayList;
import java.util.Random;

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
		else if(Constants.SELECTION_STRATEGY.equals("roulette"))
		{
			int[] totalFitness= new int[oldGeneration.population.size()];
				totalFitness[0] = oldGeneration.population.get(oldGeneration.population.size()-1).routeDistance();
				for(int popnum=popsToMake-2; popnum>-1; popnum--)
				{
					totalFitness[popnum] = totalFitness[popnum+1] + oldGeneration.population.get(popnum).routeDistance();
				}
				//System.out.println(totalFitness[0]);
				Random rand = new Random();
				for (int popnum = 0; popnum <popsToMake; popnum++) {
				double rouletteWheel = rand.nextDouble() * totalFitness[0];
					for (int k = 0; k <popsToMake; k++) {
						
						if (totalFitness[k]>= rouletteWheel) {
							newPops.add(oldGeneration.population.get(k).makeMutatedCopy());
							break;
						}
						
					}
				
				
				}
				
			
		}
	
		Generation newGeneration = new Generation(newPops, oldGeneration.map);
		return newGeneration;
	}
}