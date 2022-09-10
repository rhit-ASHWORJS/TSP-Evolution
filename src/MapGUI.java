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

public class MapGUI{
    private JFrame frame2;
    private MapComponent map = new MapComponent();
    private JLabel pointCounter;
    private JButton undoButton;
    private JButton doneButton;

    public void runViewer(){
        this.frame2 = new JFrame();
        this.frame2.setTitle("MapInputGUI");
        this.frame2.setSize(550, 600);
//        this.frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.frame2.setLayout(new BorderLayout());
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
//                    frame2.dispose();
//                	frame2.setVisible(false);
//                	frame2.removeAll();
                	frame2.remove(controls);
                	frame2.remove(map);
                	frame2.repaint();
                    try {
                    	EvolutionLoop evo = new EvolutionLoop();
                        evo.loopWithMutation(new Map(map.getPoints()), frame2);
                    } catch(Exception ex) {
                        System.out.println(ex);
                    }
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
        private ArrayList<Point> points;

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
