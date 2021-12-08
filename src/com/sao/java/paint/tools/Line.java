package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.ui.DrawingPanel;
import java.awt.Graphics2D;
import java.awt.Point;

public class Line 
	extends ColorDrawingTool
	implements Strokable
{
    Point old = null;
    BufferedImage backupImage;
    Graphics2D g;
	StrokeProvider strokeProvider;

    @Override
    public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
    {
        BufferedImage image = dp.getImage();
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
    public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
    {
        Point current =  me.getPoint();
        g.drawImage(backupImage, 0, 0, null);
        g.drawLine(old.x, old.y, current.x, current.y);
        g.dispose();
    }

    @Override
    public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
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
