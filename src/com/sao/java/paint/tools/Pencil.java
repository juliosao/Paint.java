package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Graphics2D;
import java.awt.Point;

public class Pencil 
	extends DrawingTool    
{
    Point old = null;
    Graphics2D g;

    @Override
    public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
    {
		BufferedImage image = dp.getImage();
		g = (Graphics2D)image.getGraphics();
		g.setColor(dp.getStrokeColor());
		g.setStroke(dp.getStroke());
		old=me.getPoint();
		g.drawLine(old.x, old.y, old.x, old.y);
    }

    @Override
    public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
    {
	    Point current =  me.getPoint();
	    g.drawLine(old.x, old.y, current.x, current.y);
	    g.dispose();
    }

    @Override
    public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
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
