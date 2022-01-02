package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.naming.OperationNotSupportedException;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import com.sao.java.paint.tools.DrawingTool;

import java.awt.Point;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.BorderLayout;
import com.sao.java.paint.tools.DrawingMouseEvent;


/**
 * @author julio
 * Class where drawing is done
 */
public class DrawingPanel
	extends JPanel
	implements MouseMotionListener, MouseListener, AdjustmentListener, Coloreable

{
	public static final int BORDER = 1;
	public static final int FILL = 2;
	public static final Color TRANSPARENT = new Color(255,255,255,0);

	private BufferedImage image;
	private BufferedImage toolingLayer;
	private DrawingTool dtool;
	private int zoom,x,y;
	private JScrollBar vScrollBar;
	private JScrollBar hScrollBar;
	private Color strokeColor;
	private Color fillColor;
	private BasicStroke stroke;
	private int shapeMode = BORDER;

	private LinkedList<BufferedImage> history = new LinkedList<>();
	private LinkedList<BufferedImage> fordwardHistory = new LinkedList<>();

	/**
	 * Class constructor
	 */
	public DrawingPanel()
	{
		super();
		image = null;
		toolingLayer = null;
		dtool = null;
		zoom = 100;
		x = 0;
		y = 0;
		addMouseMotionListener(this);
		addMouseListener(this);

		setLayout(new BorderLayout());

		vScrollBar = new JScrollBar();
		vScrollBar.setMaximum(0);
		vScrollBar.setMinimum(0);
		vScrollBar.setValue(0);
		vScrollBar.setUnitIncrement(10);
		vScrollBar.setBlockIncrement(100);
		add(vScrollBar,BorderLayout.EAST);
		vScrollBar.addAdjustmentListener(this);

		hScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		hScrollBar.setMaximum(0);
		hScrollBar.setMinimum(0);
		hScrollBar.setValue(0);
		hScrollBar.setUnitIncrement(10);
		hScrollBar.setBlockIncrement(100);
		add(hScrollBar,BorderLayout.SOUTH);
		hScrollBar.addAdjustmentListener(this);

		fillColor = Color.WHITE;
		strokeColor = Color.BLACK;
		stroke = new BasicStroke();
	}

	/**
	 * Occurs when is needed to redraw panel
	 * @param g Graphics context where to draw the updates
	 */
	@Override
	public void paintComponent(Graphics g)
	{
		updateScrolls();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g2d.setColor(Color.lightGray);
		g2d.fillRect(0,0,getWidth(),getHeight());
		
		if(image != null)
		{
			int w = image.getWidth();
			int h = image.getHeight();
			int realW = zoom*w/100-x-1;
			int realH = zoom*h/100-y-1;
			
			g2d.setColor(Color.white);
			for(int i=0; i<realW; i+=10)
			{
				boolean even = (i/10)%2==1;
				for(int j=0; j<realH; j+=10)
				{
					if(even)
					{
						final int rw = i+10>realW ? realW%10 : 10;
						final int rh = j+10>realH ? realH%10 : 10;
						g2d.fillRect(i,j,rw,rh);
					}
					even=!even;
				}
			}

			g2d.setColor(Color.black);
			g2d.drawRect(-x-1,-y-1,zoom*w/100, zoom*h/100);

			g2d.drawImage(image, -x-1,-y-1, zoom*w/100, zoom*h/100 , this);

			if(toolingLayer != null)
			{
				g2d.drawImage(toolingLayer, -x-1,-y-1, zoom*w/100, zoom*h/100 , this);
			}
		}
	}


	public void notifyChanged()
	{
		if(history.size()>16)
			history.removeFirst();

		final int w = image.getWidth();
		final int h = image.getHeight();

		BufferedImage tmp = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		g.setBackground(TRANSPARENT);		
		g.clearRect(0, 0, w, h);
		g.drawImage(image,0,0,null);
		g.dispose();
		fordwardHistory.clear();
		history.push(tmp);
	}

	/**
	 * Undoes last change in the history buffer
	 */
	public void undo()
	{
		if(history.size()==0)
			return;

		final int w = image.getWidth();
		final int h = image.getHeight();


		BufferedImage forward = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = forward.createGraphics();
		g.setBackground(TRANSPARENT);		
		g.clearRect(0, 0, w, h);
		g.drawImage(image,0,0,null);
		g.dispose();
		fordwardHistory.push(forward);

		BufferedImage tmp = history.pop();
		g = image.createGraphics();
		g.setBackground(TRANSPARENT);		
		g.clearRect(0, 0, w, h);
		g.drawImage(tmp,0,0,null);
		g.dispose();
		setDrawingTool(dtool);
		updateUI();
		
	}

	/**
	 * Redoes last change in history fordward buffer
	 */
	public void redo()
	{
		if(fordwardHistory.size()==0)
			return;

		final int w = image.getWidth();
		final int h = image.getHeight();

		BufferedImage tmp = new BufferedImage(w,h,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = tmp.createGraphics();
		g.setBackground(TRANSPARENT);		
		g.clearRect(0, 0, w, h);
		g.drawImage(image,0,0,null);
		g.dispose();
		history.push(tmp);

		tmp = fordwardHistory.pop();
		g = image.createGraphics();
		g.setBackground(TRANSPARENT);		
		g.clearRect(0, 0, w, h);
		g.drawImage(tmp,0,0,null);
		g.dispose();
		setDrawingTool(dtool);
		updateUI();
		
	}

	/**
	 * Sets the current drawing tool
	 * @param t The current drawing tool
	 */
	public void setDrawingTool(DrawingTool t)
	{
		if(dtool != null)
			dtool.onFinished(this);

		dtool = t;
		toolingLayer = null;
		dtool.onSelected(this);
		setCursor(dtool.getCursor());
		updateUI();
	}

	/**
	 * Gets the current drawing tool
	 * @return
	 */
	public DrawingTool getDrawingTool()
	{
		return dtool;
	}

	/**
	 * Sets the image to edit
	 * @param img The image to edit
	 */
	public void setImage(BufferedImage img)
	{
		if(img.getType() == BufferedImage.TYPE_INT_ARGB )
			image = img;
		else
		{
			image = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(img, 0, 0, null);
			g.dispose();
		}

		updateUI();
		notifyChanged();
	}

	/**
	 * Gets the image to edit
	 * @return The image to edit
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	@Override
	public void mouseDragged(java.awt.event.MouseEvent evt) {
		if(dtool != null && image != null)
		{
			Point current =  evt.getPoint();
			DrawingMouseEvent dme = new DrawingMouseEvent((int)((current.getX()+x)*100/zoom),(int)((current.getY()+y)*100/zoom),evt.getButton());
			dtool.onMouseDragged(this,dme);
		}
		updateUI();
	}

   @Override
   public void mousePressed(MouseEvent me) {
		if(dtool != null && image != null)
		{
			Point current =  me.getPoint();
			DrawingMouseEvent dme = new DrawingMouseEvent((int)((current.getX()+x)*100/zoom),(int)((current.getY()+y)*100/zoom),me.getButton());
			dtool.onMousePressed(this, dme);
		}
		updateUI();
   }

   @Override
   public void mouseReleased(MouseEvent me) {
		if(dtool != null && image != null)
		{
			Point current =  me.getPoint();
			DrawingMouseEvent dme = new DrawingMouseEvent((int)((current.getX()+x)*100/zoom),(int)((current.getY()+y)*100/zoom),me.getButton() );
			dtool.onMouseReleased(this, dme);
		}
		updateUI();
   }

	@Override
	public void mouseMoved(MouseEvent evt) {
		if(dtool != null && image != null)
		{
			Point current =  evt.getPoint();
			DrawingMouseEvent dme = new DrawingMouseEvent((int)((current.getX()+x)*100/zoom),(int)((current.getY()+y)*100/zoom),evt.getButton());
			dtool.onMouseFlight(this, dme);
		}
		updateUI();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// Does nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// Does nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// Does nothing
	}

	/**
	 * Moves scroll to given coordinates
	 * @param newX x-coordinate to move
	 * @param newY y-coordinate to move
	 */
	public void scrollTo(int newX, int newY) {
		x = newX;
		y = newY;
		updateUI();
	}

	/**
	 * Return the current scroll coordinates
	 * @return A point with the current scroll coordinates
	 */
	public Point getScrollPossition()
	{
		return new Point(x,y);
	}

	/**
	 * Sets current zoom
	 * @param newZoom Zoom to be used. 100=100%
	 */
	public void setZoom(int newZoom) throws OperationNotSupportedException
	{
		if(newZoom<1)
			throw new OperationNotSupportedException("Cannot set zoom to 0%");
		zoom = newZoom;
		updateUI();
	}

	/**
	 * Returns current zoom
	 */
	public int getZoom()
	{
		return zoom;
	}

	/**
	 * Updates scroolbar position and visibility
	 */
	private void updateScrolls()
	{
		if(image == null)
			return;

		final int hVisible = getWidth() - vScrollBar.getWidth();
		final int vVisible = getHeight() - hScrollBar.getHeight();
		final int iWidth = image.getWidth() * zoom / 100;
		final int iHeight = image.getHeight() * zoom / 100;

		if(iWidth<getWidth())
			x=0;

		if(iHeight<getHeight())
			y=0;

		if(iWidth + this.x <= hVisible)
		{
			hScrollBar.setVisible(false);
		}
		else
		{
			hScrollBar.setMaximum(iWidth-hVisible);
			hScrollBar.setVisible(true);
		}

		if(iHeight + this.y <= vVisible)
		{
			vScrollBar.setVisible(false);
		}
		else
		{
			vScrollBar.setMaximum(iHeight-vVisible);
			vScrollBar.setVisible(true);
		}
	}

	/**
	 * Occurs when user changes a scrollbar position
	 */
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		this.x = hScrollBar.getValue();
		this.y = vScrollBar.getValue();
		updateUI();
	}

	@Override
	public Color getStrokeColor() {
		return strokeColor;
	}

	@Override
	public void setStrokeColor(Color c) {
		strokeColor = c;

	}

	public BasicStroke getStroke() {
		return stroke;
	}


	public void setStroke(BasicStroke s)
	{
		stroke = s;
	}

	public BufferedImage createToolingLayer()
	{
		toolingLayer = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = toolingLayer.createGraphics();
		g.setPaint(new Color(255,255,255,0));
		g.fillRect(0, 0, toolingLayer.getWidth(), toolingLayer.getHeight());
		g.dispose();
		return toolingLayer;
	}

	public BufferedImage getToolingLayer()
	{
		return toolingLayer;
	}

	public void destroyToolingLayer()
	{
		toolingLayer = null;
	}

	@Override
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public void setFillColor(Color c) {
		fillColor = c;
	}

	public int getShapeMode()
	{
		return shapeMode;
	}

	public void setShapeMode(int s)
	{
		shapeMode = s;
	}
}
