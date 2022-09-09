import java.util.ArrayList;

public class EvolutionLoop {
	private static FittestRouteViewer routeViewer;

	public static void main(String[] args) {
		String filename = Constants.PATH_CONF_FILE;
		PropParser.load(filename);

		int runs = Constants.RUNS;
		for (int i = 0; i < runs; i++) {
			if (Constants.MAP_GENERATION.equals("gui")) {
				MapGUI mapGUI = new MapGUI();
				mapGUI.runViewer(); //loopWithMutation is called from within here
			} else {
				loopWithMutation(new Map(Constants.NUM_POINTS));
			}
		}
	}

	public static void loopWithMutation(Map map) {
		routeViewer = new FittestRouteViewer();
		routeViewer.setUpViewer();
		
		Generation initialGen = new Generation(Constants.POP_SIZE, map);

		ArrayList<Generation> generations = new ArrayList<Generation>();
		generations.add(initialGen);

		for (int gen = 0; gen < Constants.GENERATIONS; gen++)// Main loop
		{
			generations.add(GenerationFactory.newGeneration(generations.get(generations.size() - 1)));

			routeViewer.updateRoute(generations.get(gen).population.get(0));
			routeViewer.setGenIndex(gen);

			// used to slow the run down enough to actually see the visualizer working
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Console output for testing
		for (int i = 0; i < Constants.GENERATIONS; i++) {
			System.out.println("Generation " + i);
			generations.get(i).printGenStatistics();
		}
	}

}
