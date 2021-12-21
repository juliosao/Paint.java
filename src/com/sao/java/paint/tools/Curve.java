package com.sao.java.paint.tools;

import java.awt.Graphics2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Curve
	extends DrawingTool
{

	public final int STARTPOINT = 0;
	public final int ENDPOINT = 1;
	public final int CONTROLPOINT = 2;

	int status = STARTPOINT;
	Point2D.Double start;
	Point2D.Double end;
	Point2D.Double control;
	BufferedImage backupImage;
	Graphics2D g;

	@Override
	public void onSelected(DrawingPanel dp)
	{
		status = STARTPOINT;
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();

		switch(status)
		{
			case STARTPOINT:
				BufferedImage image = dp.getImage();
				g = image.createGraphics();
				g.setStroke(dp.getStroke());
				g.setColor(me.button == 1 ? dp.getStrokeColor() : dp.getFillColor());

				backupImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
				Graphics2D tmpG = (Graphics2D)backupImage.getGraphics();
				tmpG.drawImage(image, 0, 0, null);
				tmpG.dispose();

				start = new Point2D.Double(me.x,me.y);
				end = start;
				control = start;
				draw(dp);
				status = ENDPOINT;
				break;
			case ENDPOINT:
				end = new Point2D.Double(me.x,me.y);
				g.setStroke(dp.getStroke());
				g.setColor(me.button == 1 ? dp.getStrokeColor() : dp.getFillColor());
				draw(dp);
				status = CONTROLPOINT;
				break;
			case CONTROLPOINT:
				control = new Point2D.Double(me.x,me.y);
				g.setStroke(dp.getStroke());
				g.setColor(me.button == 1 ? dp.getStrokeColor() : dp.getFillColor());
				draw(dp);
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
				draw(dp);
				break;
			case CONTROLPOINT:
				control = new Point2D.Double(me.x,me.y);
				draw(dp);
				break;
		};

	}

	public void draw(DrawingPanel dp)
	{
		switch(status)
		{
			case ENDPOINT:
				g.drawImage(backupImage, 0, 0, null);
				g.drawLine((int)start.x, (int)start.y, (int)end.x, (int)end.y);
				break;
			case CONTROLPOINT:
				g.drawImage(backupImage, 0, 0, null);
				QuadCurve2D q = new QuadCurve2D.Double();
				q.setCurve(start, control, end);
				g.draw(q);
				break;
		};


	}

	public String getDescription()
	{
		return Translator.m("Curve");
	}

}
