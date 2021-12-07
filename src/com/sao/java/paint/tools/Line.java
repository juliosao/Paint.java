package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;

public class Line 
	extends ColorDrawingTool
	implements Strokable
{
    Point old = null;
    BufferedImage backupImage;
    Graphics2D g;
	StrokeProvider strokeProvider;

    @Override
    public void onMousePressed(BufferedImage image, DrawingMouseEvent me)
    {
        backupImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics2D tmpG = (Graphics2D)backupImage.getGraphics();
        tmpG.drawImage(image, 0, 0, null);
        tmpG.dispose();

        g = (Graphics2D)image.getGraphics();
        g.setColor(colorProvider.getStrokeColor());
        if(strokeProvider != null)
			g.setStroke(strokeProvider.getStroke());
        old=me.getPoint();
        g.drawLine(old.x, old.y, old.x, old.y );
    }

    @Override
    public void onMouseReleased(BufferedImage image, DrawingMouseEvent me)
    {
        Point current =  me.getPoint();
        g.drawImage(backupImage, 0, 0, null);
        g.drawLine(old.x, old.y, current.x, current.y);
        g.dispose();
    }

    @Override
    public void onMouseDragged(BufferedImage image, DrawingMouseEvent me)
    {
        Point current =  me.getPoint();
        g.drawImage(backupImage, 0, 0, null);
        g.drawLine(old.x, old.y, current.x, current.y);
    }

    public String getDescription()
    {
        return "Line";
    }

	@Override
	public void setStrokeProvider(StrokeProvider sp) {
		strokeProvider = sp;		
	}

}
