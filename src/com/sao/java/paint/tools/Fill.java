package com.sao.java.paint.tools;

import java.util.Stack;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Point;

public class Fill extends DrawingTool
{
	private static final Icon icon;

	static
	{
		icon = loadIcon("fill");
	}


	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
	dp.notifyChanged();
	Color c = me.button == 1 ? dp.getStrokeColor() : dp.getFillColor();
	standardFloodFill(dp.getImage(),c,me.x,me.y);
	}


	private void standardFloodFill(BufferedImage img, Color strokeColor, int x, int y)
	{
		Stack<Point> pending = new Stack<>();
		int width = img.getWidth();
		int height = img.getHeight();
		int replacedColor = img.getRGB(x, y);
		int newColor = strokeColor.getRGB() | (strokeColor.getAlpha()<<24);

		if(newColor == replacedColor) //Si este caso se da, estamos ante una pérdida de tiempo... (Y una recursion infinita)
			return;

		pending.push(new Point(x,y));

		while(pending.size()>0)
		{
			Point p = pending.pop();

			if(p.x<0 || p.x>=width || p.y<0 || p.y>=height)
				continue;

			int color = img.getRGB(p.x, p.y);
			if(color == replacedColor)
			{
				img.setRGB(p.x,p.y,newColor);
				pending.push(new Point(p.x+1,p.y));
				pending.push(new Point(p.x-1,p.y));
				pending.push(new Point(p.x,p.y+1));
				pending.push(new Point(p.x,p.y-1));
			}
		}
	}

	public String getDescription()
	{
		return Translator.m("Fill");
	}

	@Override
	public Icon getIcon()
	{
		return icon;
	}

}
