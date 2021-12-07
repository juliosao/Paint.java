package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Point;

public class Pencil 
	extends ColorDrawingTool    
	implements Strokable
{
    Point old = null;
    Graphics2D g;
	StrokeProvider strokeProvider;

    @Override
    public void onMousePressed(BufferedImage image, DrawingMouseEvent me)
    {
		g = (Graphics2D)image.getGraphics();
		g.setColor(colorProvider.getStrokeColor());
		if(strokeProvider != null)
			g.setStroke(strokeProvider.getStroke());
		old=me.getPoint();
		g.drawLine(old.x, old.y, old.x, old.y);
    }

    @Override
    public void onMouseReleased(BufferedImage image, DrawingMouseEvent me)
    {
	    Point current =  me.getPoint();
	    g.drawLine(old.x, old.y, current.x, current.y);
	    g.dispose();
    }

    @Override
    public void onMouseDragged(BufferedImage image, DrawingMouseEvent me)
    {
	    Point current =  me.getPoint();
	    g.drawLine(old.x, old.y, current.x, current.y);
	    old = current;
    }

    public String getDescription()
    {
	    return "Pencil";
    }

	@Override
	public void setStrokeProvider(StrokeProvider sp) {
		strokeProvider = sp;
		
	}
}
