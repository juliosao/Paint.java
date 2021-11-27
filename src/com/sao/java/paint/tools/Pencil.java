package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;

public class Pencil extends ColorDrawingTool    
{
    Point old = null;
    Graphics2D g;

    @Override
    public void onMousePressed(BufferedImage image, DrawingMouseEvent me)
    {
		g = (Graphics2D)image.getGraphics();
		g.setColor(strokeColor);
		g.setStroke(new BasicStroke(3));
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
}
