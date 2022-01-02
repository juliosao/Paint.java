package com.sao.java.paint.tools;

import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Curve
	extends ShapingTool
{

	public final int STARTPOINT = 0;
	public final int ENDPOINT = 1;
	public final int CONTROLPOINT = 2;
	private static final Icon icon;

	int status = STARTPOINT;
	Point2D.Double start;
	Point2D.Double end;
	Point2D.Double control;

	static
	{
		icon = loadIcon("curve");
	}

	@Override
	public void onSelected(DrawingPanel dp)
	{
		status = STARTPOINT;
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		super.onMousePressed(dp, me);

		switch(status)
		{
			case STARTPOINT:

				start = new Point2D.Double(me.x,me.y);
				end = start;
				control = start;
				draw();
				status = ENDPOINT;
				break;
			case ENDPOINT:
				end = new Point2D.Double(me.x,me.y);
				draw();
				status = CONTROLPOINT;
				break;
			case CONTROLPOINT:
				control = new Point2D.Double(me.x,me.y);
				draw();
				put(dp);
				status = STARTPOINT;
				break;
		};
    }

	@Override
	public void onMouseFlight(DrawingPanel dp, DrawingMouseEvent me)
	{
		switch(status)
		{
			case ENDPOINT:
				end = new Point2D.Double(me.x,me.y);
				draw();
				break;
			case CONTROLPOINT:
				control = new Point2D.Double(me.x,me.y);
				draw();
				break;
		};

	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
    {
        draw();
    }

	@Override
	public void draw()
	{
		graphics.setStroke(stroke);
		graphics.setColor(strokeColor);

		clear();

		switch(status)
		{
			case ENDPOINT:
				graphics.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
				break;
			case CONTROLPOINT:
				QuadCurve2D q = new QuadCurve2D.Double();
				q.setCurve(start, control, end);
				graphics.draw(q);
				break;
		};
	}

	public String getDescription()
	{
		return Translator.m("Curve");
	}

	public Icon getIcon()
	{
		return icon;
	}

}
