import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class FittestRouteViewer{
	private JFrame frame;
	private RouteComponent component = new RouteComponent();
	private int genIndex = 0;
	private JLabel genNum;
	private JLabel fitnessText;

	public void setUpViewer(JFrame frame) {
		this.frame = frame;
		this.frame.setTitle("FittestOrganismViewer");
//		this.frame.setSize(500, 550);
//		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.frame.setLayout(new BorderLayout());

		this.frame.add(component, BorderLayout.CENTER);


		JPanel texts = new JPanel();

		genNum = new JLabel("Gen: " + genIndex);
		fitnessText = new JLabel("Distance: ");
		texts.add(genNum);
		texts.add(fitnessText);

		this.frame.add(texts, BorderLayout.SOUTH);

		this.frame.setVisible(true);
		this.frame.setSize(550,600);
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocation(600, 0);
	}

	public void updateRoute(Pop pop) {
		this.component.setPop(pop);
		genNum.setText("Gen: " + genIndex);
		fitnessText.setText("Distance: " + pop.routeDistance());
//		this.component.repaint();
		this.frame.repaint();
		this.frame.setSize(550,600);
	}

	public void setGenIndex(int i) {
		this.genIndex = i;
	}

	public class RouteComponent extends JComponent {
		private Pop pop;

		public RouteComponent() {
			this.pop = new Pop(new Map());
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;

			int[] route = this.pop.route;
			ArrayList<Point> points = this.pop.map.getPoints();

			for (int i = 0; i < route.length - 1; i++) {
				Point p1 = points.get(route[i]);
				Point p2 = points.get(route[i + 1]);
				g2d.fillOval(p1.x - 5, p1.y - 5, 10, 10);
				g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
			// Finish up the drawing
			if (points.size() > 0) {
				Point p1 = points.get(route[points.size() - 1]);
				Point p2 = points.get(route[0]);
				g2d.fillOval(p1.x - 5, p1.y - 5, 10, 10);
				g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
			}
		}

		public void setPop(Pop pop) {
			this.pop = pop;
		}
	}
}
