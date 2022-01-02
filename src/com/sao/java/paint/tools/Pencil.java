package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import javax.swing.Icon;

import java.awt.Graphics2D;
import java.awt.Point;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Pencil
	extends DrawingTool
{
	private static final Icon icon;
	Point old = null;
	Graphics2D g;

	static
	{		
		icon = loadIcon("pencil");
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		BufferedImage image = dp.getImage();
		g = (Graphics2D)image.getGraphics();

		g.setColor(me.button==1 ? dp.getStrokeColor() : dp.getFillColor());
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
		return Translator.m("Pencil");
	}

	public Icon getIcon()
	{
		return icon;
	}

}
