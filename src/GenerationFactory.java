import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

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
		else if(Constants.SELECTION_STRATEGY.equals("crossover"))
		{
			Random rand = SeededRandom.rnd;
			for(int popnum=0; popnum<popsToMake/2; popnum++)
			{
				int random = rand.nextInt(popsToMake/2);
				int random2 = rand.nextInt(popsToMake/2);
				while (random == random2) {
					random2 = rand.nextInt(popsToMake/2);
				}
				Pop parent1 = oldGeneration.population.get(random);	
				Pop parent2 = oldGeneration.population.get(random2);
				Pop child1 = parent1.makeCrossOverCopy(parent2, parent1).makeMutatedCopy();
				Pop child2 = parent2.makeCrossOverCopy(parent1, parent2).makeMutatedCopy();
				
					newPops.add(child1);
					newPops.add(child2);


			
		}}
		Generation newGeneration = new Generation(newPops, oldGeneration.map);
		return newGeneration;
	}
}