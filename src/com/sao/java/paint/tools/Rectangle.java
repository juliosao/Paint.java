package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Graphics2D;
import java.awt.Point;


public class Rectangle
	extends ShapingTool
{
	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		super.onMousePressed(dp,me);		
		draw();
	}

	@Override
	protected void draw()
	{
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);

		clear();
		if((mode & DrawingPanel.FILL) != 0 )
		{
			graphics.setColor(fillColor);
			graphics.fillRect(x,y,w,h);
		}

		if((mode & DrawingPanel.BORDER) != 0 )
		{
			graphics.setStroke(stroke);
			graphics.setColor(strokeColor);
			graphics.drawRect(x, y, w, h);
		}
	}

	

	public String getDescription()
	{
		return Translator.m("Rectangle");
	}

}
