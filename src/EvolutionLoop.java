import java.util.ArrayList;

public class EvolutionLoop {
	
	
	
	public static void main(String[] args)
	{
		String filename = Constants.PATH_CONF_FILE;
		PropParser.load(filename);
		
		int runs = Constants.RUNS;
		for(int i=0; i<runs; i++)
		{
			loopWithMutation();
		}
	}
	
	public static void loopWithMutation()
	{
		Map map = new Map(Constants.NUM_POINTS);
		Generation initialGen = new Generation(Constants.POP_SIZE, map);
		
		ArrayList<Generation> generations = new ArrayList<Generation>();
		generations.add(initialGen);
		
		for(int gen=0; gen<Constants.GENERATIONS; gen++)//Main loop
		{
			generations.add(GenerationFactory.newGeneration(generations.get(generations.size()-1)));
		}
		
		//Console output for testing
		for(int i=0; i<Constants.GENERATIONS; i++)
		{
			System.out.println("Generation "+i);
			generations.get(i).printGenStatistics();
		}
	}
}
