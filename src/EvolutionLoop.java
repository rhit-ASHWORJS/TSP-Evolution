import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Timer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class EvolutionLoop {
	
	public static ArrayList<Point> pts;
	public static boolean goTime = false;
	public static JFrame frame = new JFrame();

	public static void main(String[] args)throws HeadlessException, FileNotFoundException {
		String filename = Constants.PATH_CONF_FILE;
		PropParser.load(filename);
		EvolutionLoop evo = new EvolutionLoop();
		evo.main();
	}
	
	public void main() throws HeadlessException, FileNotFoundException {
		int runs = Constants.RUNS;
		for (int i = 0; i < runs; i++) {
			if (Constants.MAP_GENERATION.equals("gui")) {
				MapGUI mapGUI = new MapGUI();
				mapGUI.runViewer(); //loopWithMutation is called from within here
				while(!goTime)
				{
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				loopWithMutation(new Map(pts), EvolutionLoop.frame);
			} else {

					loopWithMutation(new Map(Constants.NUM_POINTS), new JFrame());
				
			}
		}
	}

	public void loopWithMutation(Map map, JFrame frame) throws FileNotFoundException {
		FittestRouteViewer routeViewer = new FittestRouteViewer();
		routeViewer.setUpViewer(frame);
		CSVLogger logger = new CSVLogger("");
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Generation");
		headers.add("Shortest Distance");
		headers.add("Average Distance");
		headers.add("Worst Distance");
		
		logger.createNewFile("output.CSV", headers);
		
		Generation initialGen = new Generation(Constants.POP_SIZE, map);

		ArrayList<Generation> generations = new ArrayList<Generation>();
		generations.add(initialGen);

		
		for (int gen = 0; gen < Constants.GENERATIONS; gen++)// Main loop
		{
			generations.add(GenerationFactory.newGeneration(generations.get(generations.size() - 1)));


			// used to slow the run down enough to actually see the visualizer working
			try {
				routeViewer.updateRoute(generations.get(gen).population.get(0));
				routeViewer.setGenIndex(gen);
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		// Console output for testing
		for (int i = 0; i < Constants.GENERATIONS; i++) {
			System.out.println("Generation " + i);
//			generations.get(i).printGenStatistics();
			if (i%10==0) {
				ArrayList<String> contents = new ArrayList<String>();
//				contents.add(Integer.toString(i));
//				contents.add(generations.get(i).getShortestDistance());
//				contents.add(generations.get(i).getWorstDistance());
//				contents.add(generations.get(i).getAverageDistance());
				logger.appendRowToFile("output.CSV", ""+Integer.toString(i)+","+generations.get(i).getShortestDistance()+","+generations.get(i).getAverageDistance()+","+generations.get(i).getWorstDistance());
//				logger.appendToFile("output.CSV", contents);
			}
			
		}
		logger.closeAll();
	}
	
	public class MapGUI{
	    private JFrame frame2;
	    private MapComponent map = new MapComponent();
	    private JLabel pointCounter;
	    private JButton undoButton;
	    private JButton doneButton;

	    public void runViewer(){
	        this.frame2 = EvolutionLoop.frame;
	        this.frame2.setTitle("MapInputGUI");
	        this.frame2.setSize(550, 600);
//	        this.frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//	        this.frame2.setLayout(new BorderLayout());
	        this.frame2.setLocation(600, 0);

	        this.frame2.add(map, BorderLayout.CENTER);

	        JPanel controls = new JPanel();

	        pointCounter = new JLabel("Num. of points: " + map.getPoints().size());
	        undoButton = new JButton("Undo");
	        undoButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (map.getPoints().size() > 0) {
	                    map.removeLastPoint();
	                    pointCounter.setText("Num. of points: " + map.getPoints().size());
	                    map.repaint();
	                }
	            }
	        });

	        doneButton = new JButton("Done");
	        doneButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) { 
	                if (map.getPoints().size() > 0) {
//	                    frame2.dispose();
//	                	frame2.setVisible(false);
//	                	frame2.removeAll();
	                	frame2.remove(controls);
	                	frame2.remove(map);
	                	frame2.repaint();
	                    try {
//	                    	EvolutionLoop evo = new EvolutionLoop();
//	                        evo.loopWithMutation(new Map(map.getPoints()), new JFrame());
	                    } catch(Exception ex) {
	                        System.out.println(ex);
	                    }
	                    frame2.setVisible(false);
	                    EvolutionLoop.pts = map.getPoints();
	                    EvolutionLoop.goTime=true;
	                }
	            }
	        });

	        controls.add(pointCounter);
	        controls.add(undoButton);
	        controls.add(doneButton);

	        this.frame2.add(controls, BorderLayout.SOUTH);

	        class ClickListener implements MouseListener {

	            @Override
	            public void mouseClicked(MouseEvent arg0) {
	                map.addPoint(arg0.getX(), arg0.getY());
	                map.repaint();
	                pointCounter.setText("Num. of points: " + map.getPoints().size());
	            }

	            @Override
	            public void mouseEntered(MouseEvent e) {
	            }

	            @Override
	            public void mouseExited(MouseEvent e) {
	            }

	            @Override
	            public void mousePressed(MouseEvent e) {
	            }

	            @Override
	            public void mouseReleased(MouseEvent e) {
	            }

	        }

	        frame2.addMouseListener(new ClickListener());
	        this.frame2.setVisible(true);
	    }

	    public class MapComponent extends JComponent {
	        public ArrayList<Point> points;

	        public MapComponent() {
	            this.points = new ArrayList<Point>();
	        }

	        @Override
	        protected void paintComponent(Graphics g) {
	            Graphics2D g2d = (Graphics2D) g;

	            for (Point p : this.points) {
	                g2d.fillOval(p.x - 10, p.y - 35, 10, 10);
	            }
	        }

	        public void addPoint(int x, int y) {
	            if (y <= 500) {
	                this.points.add(new Point(x, y));
	            }
	        }

	        public ArrayList<Point> getPoints() {
	            return this.points;
	        }

	        public void removeLastPoint() {
	            this.points.remove(this.points.size() - 1);
	        }
	    }
	}

}
