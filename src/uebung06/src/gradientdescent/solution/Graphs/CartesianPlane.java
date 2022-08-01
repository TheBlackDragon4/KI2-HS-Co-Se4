package uebung06.src.gradientdescent.solution.Graphs;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *A class to represent graphing surface with cartesian coordinates. 
 *It contains methods for graphing on those coordinates, changing
 *or finding the properties of the plane, and adding/removing graphs
 *from the plane.
 *
 * @author Blaine Nelson
 * @version 11/9/1999 (Prog 6)
 */
public class CartesianPlane extends DrawingCanvas
{
	private static final long serialVersionUID = 2683705439662751801L;
	
	protected Zoom zoomer;
	protected ArrayList<PlanePlotter> plotters;
	protected Image backup = null;
	
	private double dxMin, dyMin, dxMax, dyMax;    //BOUNDARIES
	
	private double xMin, yMin, xMax, yMax;    //BOUNDARIES
	private int xRes, yRes; // RESOLUTIONS
	
    /**
     *Default constructor which creates an ordinary Cartesian Plane with
     *the following coordinate space: Domain = [-10, 10], 
     *Range = [-10, 10].
     */
    public CartesianPlane() 
    {
		this(-10,-10,10,10,100,100);
    }

    /**
     *Constructor which takes 4 double arguments to create a Cartesian
     *Plane of the dimensions given by its parameters.
     *
     * @param xMin the minium x on the plane
     * @param yMin the minium y on the plane
     * @param xMax the maxium x on the plane
     * @param yMax the maxium y on the plane 
     * @throws PlaneException the bounds of the plane were illegal
     */
    public CartesianPlane( double xMin, double yMin,
                           double xMax, double yMax,
                           int xRes, int yRes ) throws PlaneException
    {
		this(xMin,yMin,xMax,yMax,0.05,0.05,0.05,0.05,xRes,yRes);
    }
    
    /**
     * 
     * @param xMin the minium graphable x value
     * @param yMin the minium graphable y value
     * @param xMax the maxium graphable x value
     * @param yMax the maxium graphable y value
     * @param rBorder the % of the width used as a border
     * @param lBorder the % of the width used as a border
     * @param tBorder the % of the height used as a border
     * @param bBorder the % of the height used as a border
     * @param xRes
     * @param yRes
     * @throws PlaneException
     */
    public CartesianPlane( double xMin, double yMin, double xMax, double yMax, 
    					   double rBorder, double lBorder, double tBorder, double bBorder, 
    					   int xRes, int yRes ) throws PlaneException
    {
    	super();
    	
    	// create the plotters
    	this.plotters = new ArrayList<PlanePlotter>();
    	
    	this.dxMin = xMin;
        this.dxMax = xMax;
        this.dyMin = yMin;
        this.dyMax = yMax;
    	
    	restoreDefaultBounds();
    	setOffsets( rBorder, lBorder, tBorder, bBorder );
    	setResolution(xRes,yRes);

    	// Set up the zooming features
    	zoomer = new Zoom(this);
    	addMouseListener(zoomer);
    	addMouseMotionListener(zoomer);
    }
    

    public void addPlotter(PlanePlotter plotter)
    {
    	this.plotters.add(plotter);
    	plotter.addView(this);
    }
    
    public JComponent createOptionsDialog()
    {
    	JTabbedPane ret = new JTabbedPane();
    	ret.addTab("Plane Options", new PlaneOptions(this));
    	for(PlanePlotter plot : plotters)
    		ret.addTab(plot.getName(),plot.getOptions());
    	return ret;
    }
    
    public void invalidateAll() { for(PlanePlotter plot : plotters) plot.inValidate(); }
    
    public void paint(Graphics g)
    {
    	Point zPoint = this.zoomer.getCurrentCorner();
		Point cPoint = this.zoomer.getCurrentPoint();
		if(zPoint == null)
		{
			backup = createImage(getWidth(), getHeight());
			for(PlanePlotter plot : plotters)
				plot.paintPlot(backup.getGraphics(), this);
			prepareImage(backup, this);
    		g.drawImage(backup, getX(), getY(), getWidth(), getHeight(), this);
		}
    	else
    	{
    		prepareImage(backup, this);
    		g.drawImage(backup, getX(), getY(), getWidth(), getHeight(), this);
    		
			int x1 = zPoint.x;
			int y1 = zPoint.y;
			int x2 = cPoint.x;
			int y2 = cPoint.y;
			
			int xmn = Math.min(x1,x2);
			int xmx = Math.max(x1,x2);
			
			int ymn = Math.min(y1,y2);
			int ymx = Math.max(y1,y2);
			
			//g.setClip(xmn,ymn,(xmx-xmn),(ymx-ymn));
			g.setColor(Color.GRAY);
			g.drawRect(xmn,ymn,(xmx-xmn),(ymx-ymn));
			g.setColor(new Color(Color.GREEN.getRed(),
					Color.GREEN.getGreen(),
					Color.GREEN.getBlue(),50));
			g.fillRect(xmn,ymn,(xmx-xmn),(ymx-ymn));
		}
    }

    /**
     *Converts an x coordinate to its double pixel equivalent.
     *
     * @param x the coordiate to be converted
     * @return the double pixel equivalent of x
     */
    public double doubleXPixel( double x )
    {
        return ((x-xMin)/(xMax-xMin))
        		* (this.getWidth()- getRight() - getLeft())
        		+ getLeft();
    }    

    /**
     *Converts an y coordinate to its double pixel equivalent.
     *Warning, a negative y is actually graphed towards top and a postive
     *y towards the bottom.
     *
     * @param y the coordiate to be converted
     * @return the double pixel equivalent of y
     */
    public double doubleYPixel( double y )
    {
        return ((y-yMin)/(yMax - yMin))
        		* (getTop() + getBottom() - this.getHeight())
        		- getBottom() + this.getHeight();
    } 
    
    public double invertX(int x)
    {
    	return (x-getLeft()) * (xMax-xMin) / (getWidth()-getRight()-getLeft()) + xMin;
    }
    
	public double invertY( double y )
	{
		return (y-getHeight()+getBottom()) * (yMax - yMin) / (getTop()+getBottom()-getHeight()) + yMin;
	}



    /**
     *Accessor method to set the bounds of the Plane.
     *
     * @param xMin the minium graphable point on the x axis
     * @param yMin the minium graphable point on the y axis
     * @param xMax the maxium graphable point on the x axis
     * @param yMax the maxium graphable point on the y axis
     */
    public void setPlaneBounds( double xMin, double yMin,
                           double xMax, double yMax ) throws PlaneException
    {
        if ( (xMin >= xMax) || (yMin >= yMax) )
            throw new PlaneException();  
        this.xMin = xMin;
        this.yMin = yMin;
        this.xMax = xMax;
        this.yMax = yMax;
        
        invalidateAll();
    }

    /**
     *Accessor method that returns the minium X value plottable on the
     *Cartesian Plane not including its borders.  This value will be
     *located on the left hand side of the plane.
     *
     * @return the minium plottable x value
     */
    public double getXMin() { return xMin; }

    /**
     *Accessor method to return the minium Y value plottable on the 
     *Cartesian Plane not including its borders.  This minium
     *is built around cartesian coordinates so the minium will be
     *below the maxium on the computer screen.
     *
     * @return the minium plottable y value
     */
    public double getYMin() { return yMin; }

    /**
     *Accessor method that returns the maxium X value plottable on the
     *Cartesian Plane not including its borders.  This value will be
     *located on the right hand side of the plane.
     *
     * @return the maxium plottable x value
     */
    public double getXMax() { return xMax; }

    /**
     *Accessor method to return the maxium Y value plottable on the 
     *Cartesian Plane not including its borders.  This maxium
     *is built around cartesian coordinates so the maxium will be
     *above the minium on the computer screen.
     *
     * @return the maxium plottable y value
     */
    public double getYMax() { return this.yMax; }
    
    /**
     * Accessor method that returns the resolution of the X-axis on the
     * Cartesian Plane not including its borders.
     *
     * @return the x resolution
     */
    public int getXRes() { return this.xRes; }
    
    /**
     * Accessor method that returns the resolution of the Y-axis on the
     * Cartesian Plane not including its borders.
     *
     * @return the y resolution
     */
    public int getYRes() { return this.yRes; }
    
    /**
     * Method that set the resolution of the plane.
     * 
     * @param xRes The new x resolution
     * @param yRes The new y resolution
     */
    public void setResolution(int xRes, int yRes)
    {
    	if(xRes > 0 && yRes > 0)
    	{
    		this.xRes = xRes;
    		this.yRes = yRes;
    		
    		invalidateAll();
    	}
    	else
    		throw new PlaneException("Resolution values must be strictly positive.");
    }
    
    public double xPixelSize() { return (getXMax()-getXMin())/xRes; }
    
    public double yPixelSize() { return (getYMax()-getYMin())/yRes; }
    
    public Point getPoint(double x, double y) {
		Point p = translate(x,y);
		if(p.x >= 0 && p.x < getXRes() && p.y >= 0 && p.y < getYRes())
		{
			return p;
		}
		return null;
	}
    
    protected Point translate(double x, double y) { return new Point(translateX(x),translateY(y)); }

	protected int translateX(double x) { return (int)((x - this.getXMin())/(this.getXMax()-this.getXMin())*this.xRes); }

	protected int translateY(double y) { return (int)((y - this.getYMin())/(this.getYMax()-this.getYMin())*this.yRes); }
	
	protected void restoreDefaultBounds() { setPlaneBounds(this.dxMin,this.dyMin,this.dxMax,this.dyMax); }
	
    protected class Zoom extends MouseInputAdapter
	{
		private CartesianPlane plane;
		protected Point startPos;
		protected Point currentPos;

		public Zoom(CartesianPlane plane)
		{
			this.plane = plane;
			this.startPos = null;
			this.currentPos = null;
		}
		
		public Point getCurrentCorner()
		{
			return this.startPos;
		}
		
		public Point getCurrentPoint()
		{
			return this.currentPos;
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
		 */
		public void mouseClicked(MouseEvent arg0) 
		{
			if(arg0.getButton() == MouseEvent.BUTTON3)
			{
				System.out.println("Restoring Default");
				
				plane.restoreDefaultBounds();
				plane.repaint();
			}
			else if(arg0.isControlDown())
			{
				JOptionPane options = new JOptionPane(createOptionsDialog());
				JDialog d = options.createDialog(plane,"Set View Parameters");
				d.setVisible(true);
			}
			else if(arg0.isShiftDown())
			{
				Point queryPoint = arg0.getPoint();
				double x = plane.invertX(queryPoint.x);
				double y = plane.invertY(queryPoint.y);
				
				System.out.println("Click at (" + x + "," + y + ")");
			}
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
		 */
		public void mousePressed(MouseEvent arg0) 
		{
			// TODO Auto-generated method stub
			startPos = arg0.getPoint();
			currentPos = arg0.getPoint();
		}

		/* (non-Javadoc)
		 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
		 */
		public void mouseReleased(MouseEvent arg0) 
		{
			Point endPos = arg0.getPoint();
			
			if(startPos != null && endPos != null  && 
			   Math.abs(startPos.x - endPos.x) > 3 && Math.abs(startPos.y - endPos.y) > 3)
				setZoomBounds(startPos,endPos);
			this.startPos = null;
			this.currentPos = null;
		}

		public void mouseDragged(MouseEvent e)
		{
			currentPos = e.getPoint();
			
			plane.repaint();//0,currentPos.x,currentPos.y,startPos.x,startPos.y);
		}
		
		private void setZoomBounds(Point p1, Point p2)
		{
			double x1 = plane.invertX(p1.x);
			double y1 = plane.invertY(p1.y);
			
			double x2 = plane.invertX(p2.x);
			double y2 = plane.invertY(p2.y);
			
			double xmn = Math.min(x1,x2);
			double xmx = Math.max(x1,x2);
			
			double ymn = Math.min(y1,y2);
			double ymx = Math.max(y1,y2);
			
			if(xmn == xmx || ymn == ymx) return;
			
			plane.setPlaneBounds(xmn,ymn,xmx,ymx);
			
			plane.repaint();
		}
	}

    
    protected class PlaneOptions extends OptionsPanel implements ActionListener 
    {
		private static final long serialVersionUID = -5807500511630335835L;
    	
    	private static final String AXES_SET = "Axes Values Reset Button";
    	private JTextField xmin, xmax, xres, ymin, ymax, yres;
    	private CartesianPlane plane;
    	
    	public PlaneOptions(CartesianPlane plane)
    	{
    		super();
    		this.plane = plane;
    		
    		this.setLayout(new BorderLayout());
    		this.setBorder(BorderFactory.createEtchedBorder());
    			JPanel axesSettings = new JPanel();
    			axesSettings.setLayout(new GridLayout(2,3));
    		
    			JPanel group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("X Min"));
    			xmin = new JTextField(String.valueOf(plane.getXMin()));
    			group.add(xmin);
    			axesSettings.add(group);
    			
    			group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("X Max"));
    			xmax = new JTextField(String.valueOf(plane.getXMax()));
    			group.add(xmax);
    			axesSettings.add(group);
    			
    			group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("X Res(int)"));
    			xres = new JTextField(String.valueOf(plane.getXRes()));
    			group.add(xres);
    			axesSettings.add(group);
    			
    			group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("Y Min"));
    			ymin = new JTextField(String.valueOf(plane.getYMin()));
    			group.add(ymin);
    			axesSettings.add(group);
    			
    			group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("Y Max"));
    			ymax = new JTextField(String.valueOf(plane.getYMax()));
    			group.add(ymax);
    			axesSettings.add(group);
    			
    			group = new JPanel();
    			group.setLayout(new GridLayout(1,2));
    			group.add(new JLabel("Y Res(int)"));
    			yres = new JTextField(String.valueOf(plane.getYRes()));
    			group.add(yres);
    			axesSettings.add(group);
    		
    			this.add(axesSettings,"Center");
    		JButton axesSetter = new JButton("Set Axes");
    		axesSetter.setActionCommand(AXES_SET);
    		axesSetter.addActionListener(this);
    		this.add(axesSetter,"South");
    	}
    	

		public void actionPerformed(ActionEvent arg0) 
		{
			if(arg0.getActionCommand().equals(AXES_SET))
			{
				plane.setPlaneBounds( Double.parseDouble(xmin.getText()),
									  Double.parseDouble(ymin.getText()),
									  Double.parseDouble(xmax.getText()),
									  Double.parseDouble(ymax.getText()) );
				plane.setResolution( Integer.parseInt(xres.getText()),
									 Integer.parseInt(yres.getText()) );
				
				plane.repaint();
			}
		}
    }
}
