import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapGUI {
    private JFrame frame;
    private MapComponent map = new MapComponent();
    private JLabel pointCounter;
    private JButton undoButton;
    private JButton doneButton;
    private boolean done = false;

    public Map runViewer() {
        this.frame = new JFrame();
		this.frame.setTitle("MapInputGUI");
		this.frame.setSize(500, 550);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setLayout(new BorderLayout());
        this.frame.setLocation(600, 0);
	
		this.frame.add(map, BorderLayout.CENTER);
		
		JPanel controls = new JPanel(); 
		
		
		pointCounter = new JLabel("Num. of points: " + map.getPoints().size());
		undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent e) {
                if(map.getPoints().size() > 0){
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
                if(map.getPoints().size()>0){
                    done = true;
                }
            }
        });

        controls.add(pointCounter);
        controls.add(undoButton);
        controls.add(doneButton);
		
		this.frame.add(controls,BorderLayout.SOUTH);

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

		frame.addMouseListener(new ClickListener());
	
		this.frame.setVisible(true);
        while(!done){
        }
        return new Map(map.getPoints());
    }

    public class MapComponent extends JComponent {
        private ArrayList<Point> points;

        public MapComponent() {
            points = new ArrayList<Point>();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g;
            
            for(Point p: points){
                g2d.fillOval(p.x, p.y - 5 - 25, 10, 10);
            }
        }

        public void addPoint(int x, int y) {
            if(y <= 500) {
                points.add(new Point(x, y));
            }
        }

        public ArrayList<Point> getPoints() {
            return points;
        }

        public void removeLastPoint() {
            points.remove(points.size()-1);
        }
    }
}
