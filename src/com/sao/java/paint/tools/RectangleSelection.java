package com.sao.java.paint.tools;

import java.awt.BasicStroke;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import com.sao.java.paint.ui.DrawingPanel;

public class RectangleSelection
extends DrawingTool
{
	static final BasicStroke selectionStroke = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER );
	static final Color selectionColor = Color.BLUE;
	static final Color selectionBColor = new Color(255,255,255,0);
	Graphics2D g;
	Point old;
	Point current;
	int width;
	int height;
	BufferedImage originalImage;
	BufferedImage pastedImage;

	@Override
	public void onSelected(DrawingPanel dp)
	{
		originalImage = dp.getImage();
		BufferedImage i = dp.createToolingLayer();
		g = i.createGraphics();
		width = i.getWidth();
		height = i.getHeight();
		g.setBackground(selectionBColor);
		g.clearRect(0, 0, width,height);
		g.setStroke(selectionStroke);
		pastedImage = null;
	}

	@Override
	public void onFinished(DrawingPanel dp)
	{
		if(pastedImage != null && originalImage != null)
		{
			int x = old.x < current.x ? old.x : current.x;
			int y = old.y < current.y ? old.y : current.y;
			int w = Math.abs(old.x - current.x);
			int h = Math.abs(old.y - current.y);

			Graphics2D g2 = originalImage.createGraphics();
			g2.drawImage(pastedImage, x, y, w, h, null);
			g2.dispose();
		}
	}

	/**
	 * Occurs when mouse is pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	@Override
	public void onMousePressed(DrawingPanel dp, DrawingMouseEvent me){
		old=me.getPoint();
		current=old;

		g.setBackground(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			g.drawImage(pastedImage, old.x, old.y, 1, 1, null);
		}

		g.setColor(selectionColor);
		g.drawRect(old.x, old.y, 1, 1 );
	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
		current =  me.getPoint();
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);

		g.setBackground(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			g.drawImage(pastedImage, x, y, w, h, null);
		}
		g.setColor(selectionColor);
		g.drawRect(x,y,w,h);
	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
	{
		current =  me.getPoint();
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);
		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			g.drawImage(pastedImage, x, y, w, h, null);
		}
		g.setColor(selectionColor);
		g.drawRect(x,y,w,h);
	}

	/** */
	public BufferedImage copy()
	{
		if(originalImage != null)
		{
			int x = old.x < current.x ? old.x : current.x;
			int y = old.y < current.y ? old.y : current.y;
			int w = Math.abs(old.x - current.x);
			int h = Math.abs(old.y - current.y);

			BufferedImage copyImage = new BufferedImage(w,h,originalImage.getType());
			Graphics2D g2 = copyImage.createGraphics();
			g2.drawImage(originalImage, 0, 0, w, h, x, y, x+w, y+h, null);

			return copyImage;
		}

		return null;

	}

	public BufferedImage cut()
	{
		if(originalImage != null)
		{
			int x = old.x < current.x ? old.x : current.x;
			int y = old.y < current.y ? old.y : current.y;
			int w = Math.abs(old.x - current.x);
			int h = Math.abs(old.y - current.y);

			BufferedImage copyImage = new BufferedImage(w,h,originalImage.getType());
			Graphics2D g2 = copyImage.createGraphics();
			g2.drawImage(originalImage, 0, 0, w, h, x, y, x+w, y+h, null);
			g2.dispose();

			g2 = originalImage.createGraphics();
			g2.setBackground(Color.WHITE);
			g2.clearRect(x,y,w,h);
			g2.dispose();

			return copyImage;
		}
		return null;
	}

	public void paste(DrawingPanel dp, BufferedImage img)
	{
		pastedImage = img;
		old = new Point(0,0);
		current = new Point(img.getWidth(),img.getHeight());
		g.drawImage(img, 0, 0, null);
	}

	public String getDescription()
	{
		return "Selection";
	}

}
