package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

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
        add(vScrollBar,BorderLayout.EAST);
        vScrollBar.addAdjustmentListener(this);

        hScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
        hScrollBar.setMaximum(0);
        hScrollBar.setMinimum(0);
        hScrollBar.setValue(0);
        add(hScrollBar,BorderLayout.SOUTH);
        hScrollBar.addAdjustmentListener(this);

        fillColor = Color.WHITE;
        strokeColor = Color.BLACK;
        stroke = new BasicStroke();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        updateScrolls();
        Graphics2D g2d = (Graphics2D)g;
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor(Color.gray);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(Color.black);
        if(image != null)
        {
            int w = image.getWidth();
            int h = image.getHeight();
            g2d.drawRect(-x-1,-y-1,zoom*w/100, zoom*h/100);
            g2d.drawImage(image, -x-1,-y-1, zoom*w/100, zoom*h/100 , this);

            if(toolingLayer != null)
            {
                g2d.drawImage(toolingLayer, -x-1,-y-1, zoom*w/100, zoom*h/100 , this);
            }
        }
    }

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

    public void notifyChanged()
    {
        if(history.size()>16)
            history.removeFirst();

        BufferedImage tmp = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tmp.createGraphics();
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

        BufferedImage forward = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = forward.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        fordwardHistory.push(forward);

        BufferedImage tmp = history.pop();
        g = image.createGraphics();
        g.drawImage(tmp,0,0,null);
        g.dispose();
        updateUI();
        dtool.onSelected(this);
    }

    /**
     * Redoes last change in history fordward buffer
     */
    public void redo()
    {
        if(fordwardHistory.size()==0)
            return;

        BufferedImage tmp = new BufferedImage(image.getWidth(),image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = tmp.createGraphics();
        g.drawImage(image,0,0,null);
        g.dispose();
        history.push(tmp);

        tmp = fordwardHistory.pop();
        g = image.createGraphics();
        g.drawImage(tmp,0,0,null);
        g.dispose();
        updateUI();
        dtool.onSelected(this);
    }

    public DrawingTool getDrawingTool()
    {
        return dtool;
    }

    public void setImage(BufferedImage img)
    {
        image = img;
        updateScrolls();
        updateUI();
        notifyChanged();
    }

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

    public void scrollTo(int newX, int newY) {
        x = newX;
        y = newY;
        updateUI();
    }

    public void setZoom(int newZoom){
        zoom = newZoom;
        updateUI();
    }



    private void updateScrolls()
    {
        if(image == null)
            return;

        final int hVisible = getWidth() - vScrollBar.getWidth();
        final int vVisible = getHeight() - hScrollBar.getHeight();
        final int iWidth = image.getWidth() * zoom / 100;
        final int iHeight = image.getHeight() * zoom / 100;

        if(iWidth <= hVisible)
        {
            hScrollBar.setVisible(false);
        }
        else
        {
            hScrollBar.setMaximum(iWidth-hVisible);
            hScrollBar.setVisible(true);
        }

        if(iHeight <= vVisible)
        {
            vScrollBar.setVisible(false);
        }
        else
        {
            vScrollBar.setMaximum(iHeight-vVisible);
            vScrollBar.setVisible(true);
        }
    }

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
