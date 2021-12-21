package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Graphics2D;
import java.awt.Point;

public class Ellipse
	extends DrawingTool
{
	Point old = null;
	Point current = null;
	BufferedImage backupImage;
	Graphics2D g;

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		BufferedImage image = dp.getImage();
		g = image.createGraphics();
		backupImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		Graphics2D tmpG = (Graphics2D)backupImage.getGraphics();
		tmpG.drawImage(image, 0, 0, null);
		tmpG.dispose();

		old=me.getPoint();
		current=old;
	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
		current =  me.getPoint();
		draw(dp);
	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
	{
		current =  me.getPoint();
		draw(dp);
	}

	public void draw(DrawingPanel dp)
	{
		g.drawImage(backupImage, 0, 0, null);
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);

		int mode = dp.getShapeMode();

		if((mode & DrawingPanel.FILL) != 0 )
		{
			g.setColor(dp.getFillColor());
			g.fillOval(x,y,w,h);
		}

		if((mode & DrawingPanel.BORDER) != 0 )
		{
			g.setStroke(dp.getStroke());
			g.setColor(dp.getStrokeColor());
			g.drawOval(x,y,w,h);
		}
	}

	public String getDescription()
	{
		return Translator.m("Ellipse");
	}

}
