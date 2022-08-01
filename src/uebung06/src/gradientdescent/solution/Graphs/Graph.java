package uebung06.src.gradientdescent.solution.Graphs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *A class which represents a graphable surface with features of a Cartesian
 *Plane as well as axes, a grid, tick marks, and numbers.
 *
 * @author Blaine Nelson
 * @version 11/9/1999 (Prog 6)
 */
public class Graph extends PlanePlotter
{
	private static final long serialVersionUID = 3176925067370013111L;
	
	/**
     *The default constructor for a Cartesian Plane area.
     *
     * @throws PlaneException the plane's properties are illegal 
     */
    public Graph() throws PlaneException
    {
        this( true, true, true, true );
    }

    /**
     *A graph constructor which creates an ordinary Cartesian plane. 
     *The X and Y boundaries passed to it are the 1st 4 args,
     *the right, left, top, and bottom borders are passed as the next
     *4 arguments, and the last 4 arguments are the enabled or disabled
     *properties of the axes, grid, tick marks, and numbering.
     *
     * @param sAxes true: axes on, false: axes off
     * @param sGrid true: grid on, false: grid off
     * @param sTicks true: tick marks on, false: tick marks off
     * @param sNumbers true: numbering on, false: numbering off
     * @throws PlaneException the plane has illegal properties.
     */
    public Graph( boolean sAxes, boolean sGrid, boolean sTicks, boolean sNumbers ) throws PlaneException
    {
    	super();

        //AXES STUFF
        axes = sAxes;
        axesColor = Color.black;

        //GRID STUFF
        grid = sGrid;
        gridXSpacing = 1;
        gridYSpacing = 1;
        gridColor = Color.black;

        //TICK MARK STUFF
        tickMarks = sTicks;
        xTickIncrement = 1;
        yTickIncrement = 1;
        tickColor = Color.blue;

        //NUMBERING STUFF
        numbers = sNumbers;
        xNumberedTick = 3;
        yNumberedTick = 3;
        numColor = Color.black;
    }

    /**
     *Redefined method from CartesianPlane which draws the graph and
     *its components.  If the axes, grid, tick marks, or numbers
     *boolean values are set to true, that component is draw.  Finally,
     *it calls the overridden paint method from CartesianPlane to
     *draw the actual graphs.
     *
     * @param g the graphics area to be drawn on
     */
    public void paint( Graphics g, CartesianPlane p )
    {
        //TITLE
        //g.drawString( title, (int)(this.getWidth()/2 - 50), 20 );
        //HORZONTAL LABEL
        //g.drawString( horzLabel, 10, (int)(this.getHeight() / 2));
        //VERTICAL LABEL
        //g.drawString( vertLabel, (int)(this.getWidth() / 2), 
        //              this.getHeight() -10);
    	
    	// TODO: a clogue: ticks should be specified in the integer 20!
    	this.xTickIncrement = Math.abs(p.getXMax()-p.getXMin())/20;
    	this.yTickIncrement = Math.abs(p.getYMax()-p.getYMin())/20;
        
        if ( axes )               //AXES ON -> DRAW IT
            this.drawAxes( g, p );
        if ( grid )               //GRID ON -> DRAW IT
            this.drawGrid( g, p );
        if ( tickMarks )          //TICK MARKS ON -> DRAW THEM
            this.drawTicks( g, p );
        if ( numbers )            //NUMBERS ON -> DRAW THEM
            this.drawNumbers( g, p );
    }
    
    @Override
	public OptionsPanel getOptions() 
	{
		return new GraphDialog(this);
	}

	@Override
	public String getName() 
	{
		return "Axes Options";
	}

    /**
     *Accessor method to set grid spacing on the x and y axis.
     *
     * @param x the grid spacing on the x axis
     * @param y the grid spacing on the y axis
     */
    public void setGridSpacing( int x, int y )
    {
        gridXSpacing = x;
        gridYSpacing = y;
    }

    /**
     *Accessor method to set tick mark spacing on the x and y axis.
     *
     * @param x the tick mark spacing on the x axis
     * @param y the tick mark spacing on the y axis
     */
    public void setTickIncrement( double x, double y )
    {
        xTickIncrement = x;
        yTickIncrement = y;
    }

    /**
     *Accessor method to set the multiple by which tick marks are numbered.
     *
     * @param x every xth tick mark on the x axis is numbered
     * @param y every yth tick mark on the y axis is numbered
     */
    public void numberedTicks( int x, int y )
    {
        xNumberedTick = x;
        yNumberedTick = y;
    }

    /**
     *Accessor method to set whether the axes will be drawn.
     *
     * @param show true: show the axes, false: don't
     */
    public void showAxes( boolean show )
    {
        axes = show;
    }

    /**
     *Accessor method to set whether the axes will be drawn.
     *
     * @param show true: show the axes, false: don't
     */
    public void showGrid( boolean show )
    {
        grid = show;
    }

    /**
     *Accessor method to set whether the axes will be drawn.
     *
     * @param show true: show the axes, false: don't
     */
    public void showTicks( boolean show )
    {
        tickMarks = show;
    }

    /**
     *Accessor method to set whether the axes will be drawn.
     *
     * @param show true: show the axes, false: don't
     */
    public void showNumbers( boolean show )
    {
        numbers = show;
    }

    /**
     *Accessor method which returns whether the axes are being shown.
     *
     * @return true: axes are shown, false: not shown
     */
    public boolean getShowAxes()
    {
        return axes;
    }

    /**
     *Accessor method which returns whether the grid is being shown.
     *
     * @return true: grid is shown, false: not shown
     */
    public boolean getShowGrid()
    {
        return grid;
    }

    /**
     *Accessor method which returns whether the tick marks are being shown.
     *
     * @return true: tick marks are shown, false: not shown
     */
    public boolean getShowTicks()
    {
        return tickMarks;
    }

    /**
     *Accessor method which returns whether the numbering is being shown.
     *
     * @return true: numbering is shown, false: not shown
     */
    public boolean getShowNumbers()
    {
        return numbers;
    }

    /**
     *Accessor method to set the graph's title, and its vertical and 
     *horizontal axis labels.
     *
     * @param title the graph's new title
     * @param vLabel the graph's new label for the vertical axis
     * @param hLabel the graph's new label for the horizontal axis
     */
    public void setLabels( String title, String vLabel, String hLabel )
    {
        this.title = title;
        this.vertLabel = vLabel;
        this.horzLabel = hLabel;
    }

    /**
     *Accessor method to return the graph's current title.
     *
     * @return the graph's title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     *Accessor method to return the graph's current label for the vertical
     *axis.
     *
     * @return the vertical label
     */
    public String getVertLabel()
    {
        return vertLabel;
    }

    /**
     *Accessor method to return the graph's current label for the
     *horizontal axis.
     *
     * @return the horizontal label
     */
    public String getHorzLabel()
    {
        return horzLabel;
    }

    
    protected class GraphDialog extends OptionsPanel implements ActionListener 
    {
		private static final long serialVersionUID = -5807500511630335835L;

		protected Graph graph;
    	
    	private static final String AXES_ON = "Axes Visible Button";
    	private static final String GRID_ON = "Grid Visible Button";
    	private static final String TICKS_ON = "Ticks Visible Button";
    	private static final String NUMS_ON = "Numbers Visible Button";
    	
    	JTextField xmin, xmax, xres, ymin, ymax, yres;
    	
    	public GraphDialog(Graph graph)
    	{
    		super();
    		
    		this.graph = graph;
    		
    		this.setLayout(new GridLayout(1,4));
    		
    		JRadioButton axesOn = new JRadioButton("Axes?",graph.axes);
    		axesOn.setActionCommand(AXES_ON);
    		axesOn.addActionListener(this);
    		this.add(axesOn);
    		JRadioButton gridOn = new JRadioButton("Grid?",graph.grid);
    		gridOn.setActionCommand(GRID_ON);
    		gridOn.addActionListener(this);
    		this.add(gridOn);
    		JRadioButton ticksOn = new JRadioButton("Tick-Marks?",graph.tickMarks);
    		ticksOn.setActionCommand(TICKS_ON);
    		ticksOn.addActionListener(this);
    		this.add(ticksOn);
    		JRadioButton numsOn = new JRadioButton("Axes Numbered?",graph.numbers);
    		numsOn.setActionCommand(NUMS_ON);
    		numsOn.addActionListener(this);
    		this.add(numsOn);
    	}

		public void actionPerformed(ActionEvent arg0) 
		{
			if(arg0.getActionCommand().equals(AXES_ON))
				graph.axes = !graph.axes;
			else if(arg0.getActionCommand().equals(GRID_ON))
				graph.grid = !graph.grid;
			else if(arg0.getActionCommand().equals(TICKS_ON))
				graph.tickMarks = !graph.tickMarks;
			else if(arg0.getActionCommand().equals(NUMS_ON))
				graph.numbers = !graph.numbers;
			else 
				return;
			
			this.graph.repaint();
		}
    }

    //HELPER METHOD TO DRAW THE AXES
    private void drawAxes( Graphics g, CartesianPlane p )
    {
        g.setColor( axesColor );
        //X-AXIS
		g.drawLine( (int)p.doubleXPixel(p.getXMin()), (int)p.doubleYPixel(0), 
					(int)p.doubleXPixel(p.getXMax()), (int)p.doubleYPixel(0) );
        //Y-AXIS
        g.drawLine( (int)p.doubleXPixel(0), (int)p.doubleYPixel(p.getYMin()), 
					(int)p.doubleXPixel(0), (int)p.doubleYPixel(p.getYMax()) );
    }

    //HELPER METHOD TO DRAW GRID
    private void drawGrid( Graphics g, CartesianPlane p )
    {
//        if( gridX <= .01 )
//            gridX = 1;
//        if( gridY < .01 )
//            gridY = 1;
        g.setColor( gridColor );
        //DRAW GRID FOR POSITIVE X'S
        for( double x = gridXSpacing ; x <= p.getXMax(); x += gridXSpacing )
        {
            //DRAW GRID FOR POSITIVE Y'S
            for( double y = gridYSpacing; y <= p.getYMax(); y += gridYSpacing)
            {
				g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(y), 
							(int)p.doubleXPixel(x), (int)p.doubleYPixel(y) );
            }
            //DRAW GRID FOR NEGATIVE Y'S
            for( double y = -gridYSpacing;y >= p.getYMin(); y -= gridYSpacing)
            {
				g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(y), 
							(int)p.doubleXPixel(x), (int)p.doubleYPixel(y) );
            }
        }
        //DRAW GRID FOR NEGATIVE X'S
        for( double x = -gridXSpacing ; x >= p.getXMin(); x -= gridXSpacing )
        {
            //DRAW GRID FOR POSITIVE Y'S
            for( double y = gridYSpacing; y <= p.getYMax(); y += gridYSpacing)
            {
				g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(y), 
							(int)p.doubleXPixel(x), (int)p.doubleYPixel(y) );
            }
            //DRAW GRID FOR NEGATIVE Y'S
            for( double y = -gridYSpacing;y >= p.getYMin(); y -= gridYSpacing)
            {
				g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(y), 
							(int)p.doubleXPixel(x), (int)p.doubleYPixel(y) );
            }
        }
    }

    //HELPER METHOD TO DRAW TICK MARKS
    private void drawTicks( Graphics g, CartesianPlane p )
    {
        double xTickLength = (p.getXMax()-p.getXMin())/100;
		double yTickLength = (p.getYMax()-p.getYMin())/100;
                                         
        g.setColor( tickColor );
        //DRAW POSITIVE X TICK MARKS
        for( double x = xTickIncrement; x <= p.getXMax(); x += xTickIncrement)
        {
            g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(0),
						(int)p.doubleXPixel(x), (int)p.doubleYPixel(xTickLength) );
        }
        //DRAW NEGATIVE X TICK MARKS
        for( double x = -xTickIncrement; x >= p.getXMin(); x -= xTickIncrement)
        {
			g.drawLine( (int)p.doubleXPixel(x), (int)p.doubleYPixel(0),
						(int)p.doubleXPixel(x), (int)p.doubleYPixel(xTickLength) );
        }
        //DRAW POSITIVE Y TICK MARKS
        for( double y = yTickIncrement; y <= p.getYMax(); y += yTickIncrement)
        {
			g.drawLine( (int)p.doubleXPixel(0), (int)p.doubleYPixel(y),
						(int)p.doubleXPixel(yTickLength), (int)p.doubleYPixel(y) );
        }
        //DRAW NEGATIVE Y TICK MARKS
        for( double y = -yTickIncrement; y >= p.getYMin(); y -= yTickIncrement)
        {
			g.drawLine( (int)p.doubleXPixel(0), (int)p.doubleYPixel(y),
						(int)p.doubleXPixel(yTickLength), (int)p.doubleYPixel(y) );
        }
    }

    //HELPER METHOD TO DRAW NUMBERING FOR TICK MARKS
    private void drawNumbers( Graphics g, CartesianPlane p )
    {
        int count;
        double numberX = xTickIncrement * xNumberedTick;
        double numberY = yTickIncrement * yNumberedTick;
        g.setColor( numColor );
        count = 0;
        
        //DRAW NUMBERING ON POSITIVE X AXIS
        for( double x = 0; x <= p.getXMax(); x += numberX)
        {
        	double d = ((int)(100*(count * numberX)))/100.0;
            g.drawString( Double.toString(d), (int)p.doubleXPixel(x), (int)p.doubleYPixel(0) + 20 );
            count++;
        }
        count = 0;
        //DRAW NUMBERING ON NEGATIVE X AXIS
        for( double x = 0; x >= p.getXMin(); x -= numberX)
        {
        	double d = ((int)(100*(-count * numberX)))/100.0;
			g.drawString( Double.toString(d), (int)p.doubleXPixel(x), (int)p.doubleYPixel(0) + 20 );
            count++;
        }
        count = 0;
        //DRAW NUMBERING ON POSITIVE Y AXIS
        for( double y = 0; y <= p.getYMax(); y += numberY)
        {
        	double d = ((int)(100*(count * numberY)))/100.0;
            g.drawString( Double.toString(d), (int)p.doubleXPixel(0) - 30, (int)p.doubleYPixel(y) );
            count++;
        }
        count = 0;
        //DRAW NUMBERING ON NEGATIVE Y AXIS
        for( double y = 0; y >= p.getYMin(); y -= numberY)
        {
        	double d = ((int)(100*(-count * numberY)))/100.0;
			g.drawString( Double.toString(d), (int)p.doubleXPixel(0) - 30, (int)p.doubleYPixel(y) );
            count++;
        }
    }

    private boolean axes;        //DRAW AXES?
    private Color axesColor;     //COLOR OF AXES

    private boolean grid;        //DRAW GRID?
    private int gridXSpacing;    //GRID SPACING FOR X VALUES
    private int gridYSpacing;    //GRID SPACING FOR Y VALUES
    private Color gridColor;     //COLOR OF GRID

    private boolean tickMarks;      //DRAW TICK MARKS?
    private double xTickIncrement;  //TICK MARKS ON X AXIS SPACING
    private double yTickIncrement;  //TICK MARKS ON Y AXIS SPACING
    private Color tickColor;         //COLOR OF TICK MARKS

    private boolean numbers;     //DRAW NUMBERING FOR TICKS
    private int xNumberedTick;   //NUMBER EVERY _ TICKS ON X AXIS
    private int yNumberedTick;   //NUMBER EVERY _ TICKS ON Y AXIS
    private Color numColor;      //COLOR OF TICK MARKS

    String title = "";           //TITLE OF GRAPH
    String vertLabel = "";       //VERTICAL LABEL
    String horzLabel = "";       //HORIZONTAL LABEL
}
