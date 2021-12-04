package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.divcompat.ColorPalette;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.BasicStroke;

public class Ellipse 
	extends ColorDrawingTool
{
	Point old = null;	
	BufferedImage backupImage;
	Graphics2D g;

	@Override
	public void onMousePressed(BufferedImage image, DrawingMouseEvent me)
	{
		backupImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics2D tmpG = (Graphics2D)backupImage.getGraphics();
		tmpG.drawImage(image, 0, 0, null);
		tmpG.dispose();

		g = (Graphics2D)image.getGraphics();
		g.setColor(strokeColor);
		g.setStroke(new BasicStroke(3));
		old=me.getPoint();
		g.drawOval(old.x, old.y, 0, 0 );
	}

	@Override
	public void onMouseReleased(BufferedImage image, DrawingMouseEvent me)
	{
		Point current =  me.getPoint();
		g.drawImage(backupImage, 0, 0, null);
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);
		g.drawOval(x,y,w,h);
		g.dispose();
	}

	@Override
	public void onMouseDragged(BufferedImage image, DrawingMouseEvent me)
	{
		Point current =  me.getPoint();
		g.drawImage(backupImage, 0, 0, null);
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);
		g.drawOval(x,y,w,h);
	}


	public String getDescription()
	{
		return "Ellipse";
	}

	@Override
	public void setPalette(ColorPalette cp) {
		// TODO Auto-generated method stub
		
	}

}
