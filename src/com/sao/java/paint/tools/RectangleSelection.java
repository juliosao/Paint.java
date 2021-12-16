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
	static final Color selectionColor = new Color(0,0,255,50);
	static final Color selectionBorderColor = Color.BLUE;
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
		g.setColor(selectionBorderColor);
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
		g.setColor(selectionColor);;
		g.fillRect(x, y, w, h);
		g.setColor(selectionBorderColor);
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

		g.setColor(selectionColor);;
		g.fillRect(x, y, w, h);
		g.setColor(selectionBorderColor);
		g.drawRect(x,y,w,h);
	}

	/**
	 * Gets the selected area on a drawing pannel and clears it
	 * @param dp Affected drawing pannel
	 * @return A buffered image with the selected data
	*/
	public BufferedImage copy(DrawingPanel dp)
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

			selectNone(dp);

			return copyImage;
		}

		return null;

	}

	/**
	 * Gets the selected area on a drawing pannel and clears it
	 * @param dp Affected drawing pannel
	 * @return A buffered image with the selected data
	 */
	public BufferedImage cut(DrawingPanel dp)
	{
		if(originalImage != null && old != null )
		{
			dp.notifyChanged();
			int x = old.x < current.x ? old.x : current.x;
			int y = old.y < current.y ? old.y : current.y;
			int w = Math.abs(old.x - current.x);
			int h = Math.abs(old.y - current.y);

			BufferedImage copyImage = new BufferedImage(w,h,originalImage.getType());
			Graphics2D g2 = copyImage.createGraphics();
			g2.drawImage(originalImage, 0, 0, w, h, x, y, x+w, y+h, null);
			g2.dispose();

			g2 = originalImage.createGraphics();
			g2.setBackground(dp.getFillColor());
			g2.clearRect(x,y,w,h);
			g2.dispose();

			selectNone(dp);

			return copyImage;
		}
		return null;
	}

	/**
	 * Puts image on selecction tool
	 * @param dp Affected drawing panel
	 * @param img Image to put
	 */
	public void paste(DrawingPanel dp, BufferedImage img)
	{
		int x,y,w,h;
		dp.notifyChanged();
		pastedImage = img;

		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);

		if(old==null)
		{
			old = new Point(0,0);
			current = new Point(img.getWidth(),img.getHeight());
			x = old.x < current.x ? old.x : current.x;
			y = old.y < current.y ? old.y : current.y;
			w = Math.abs(old.x - current.x);
			h = Math.abs(old.y - current.y);
			g.drawImage(img, 0, 0, null);
		}
		else
		{
			x = old.x < current.x ? old.x : current.x;
			y = old.y < current.y ? old.y : current.y;
			w = Math.abs(old.x - current.x);
			h = Math.abs(old.y - current.y);
			g.drawImage(pastedImage, x, y, w, h, null);
		}

		g.setColor(selectionColor);;
		g.fillRect(x, y, w, h);
		g.setColor(selectionBorderColor);
		g.drawRect(x,y,w,h);
		dp.updateUI();
	}

	/**
	 * Selects complete image on display panel
	 * @param dp Affected drawing panel
	 */
	public void selectAll(DrawingPanel dp)
	{
		old = new Point(0,0);
		current =  new Point(width,height);

		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);
		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			pastedImage = null;
		}
		g.setColor(selectionColor);;
		g.fillRect(x, y, w, h);
		g.setColor(selectionBorderColor);
		g.drawRect(x, y, w, h);
		dp.updateUI();
	}

	/**
	 * Clean selection on a display panel
	 * @param dp Affected drawinf panel
	 */
	public void selectNone(DrawingPanel dp)
	{
		old = null;
		current = null;

		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			pastedImage = null;
		}
		dp.updateUI();
	}

	public String getDescription()
	{
		return "Selection";
	}

}
