package com.sao.java.paint.tools;

import java.awt.Rectangle;
import java.awt.BasicStroke;
import java.awt.image.BufferedImage;

import javax.swing.Icon;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class RectangleSelection
	extends DrawingTool
	implements SelectionTool
{
	static final int NONE = 0;
	static final int SELECTING=1;
	static final int SELECTED=2;

	static final BasicStroke selectionStroke = new BasicStroke(1, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER );
	static final Color selectionColor = new Color(0,0,255,50);
	static final Color selectionBorderColor = Color.BLUE;
	static final Color selectionBColor = new Color(255,255,255,0);
	static final int TRANSPARENT = 0x00FFFFFF;
	static final int OPAQUE = 0xFF000000;
	boolean transparenSelection;
	Graphics2D g;
	Point old;
	Point current;
	int width;
	int height;
	BufferedImage originalImage;
	BufferedImage pastedImage;
	int status=NONE;
	private static final Icon icon;

	static
	{
		icon = loadIcon("selection");
	}


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
		status = NONE;
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
		old=null;
		current=null;
	}

	/**
	 * Occurs when mouse is pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	@Override
	public void onMousePressed(DrawingPanel dp, DrawingMouseEvent me)
	{
		if(me.button == DrawingMouseEvent.RIGHTBUTTON)
		{
			selectNone(dp);
			return;
		}


		switch(status)
		{
			case NONE:
				updateCoords(me.getPoint());
				draw();
				dp.updateUI();
				status = SELECTING;
				break;
			default:
				updateCoords(me.getPoint());
				draw();
				dp.updateUI();
		}
	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
		if(me.button == DrawingMouseEvent.RIGHTBUTTON)
		{
			if(pastedImage != null && originalImage != null)
			{
				pastedImage = null;
				originalImage = null;
				old = null;
				current = null;
			}
			return;
		}

		updateCoords(me.getPoint());
		draw();
		dp.updateUI();
		status = SELECTED;

	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
	{
		updateCoords(me.getPoint());
		draw();
		dp.updateUI();
	}

	void updateCoords(Point tmp)
	{
		Point a,b,c,d,selected;
		double dist,selDist;

		switch(status)
		{
			case NONE:
				old=current = tmp;
				break;

			case SELECTING:
				current = tmp;
				break;

			case SELECTED:
				a = new Point(old);
				b = new Point(old.x,current.y);
				c = new Point(current);
				d = new Point(current.x, old.y);

				selected = a;
				selDist = Math.sqrt( Math.pow(a.x-tmp.x,2) + Math.pow(a.y-tmp.y,2) );

				dist = Math.sqrt( Math.pow(b.x-tmp.x,2) + Math.pow(b.y-tmp.y,2) );
				if(dist<selDist)
				{
					selDist=dist;
					selected=b;
				}

				dist = Math.sqrt( Math.pow(c.x-tmp.x,2) + Math.pow(c.y-tmp.y,2) );
				if(dist<selDist)
				{
					selDist=dist;
					selected=c;
				}

				dist = Math.sqrt( Math.pow(d.x-tmp.x,2) + Math.pow(d.y-tmp.y,2) );
				if(dist<selDist)
				{
					selected=d;
				}

				if(selected == a)
				{
					old = tmp;
				}
				else if(selected == b)
				{
					old.x = tmp.x;
					current.y = tmp.y;
				}
				else if(selected == c)
				{
					current = tmp;
				}
				else
				{
					current.x = tmp.x;
					old.y = tmp.y;
				}
		}
	}

	/**
	 * Gets the selected area on a drawing pannel and clears it
	 * @param dp Affected drawing pannel
	 * @return A buffered image with the selected data
	*/
	public BufferedImage copy(DrawingPanel dp)
	{
		if(originalImage != null && old !=null)
		{
			BufferedImage copy = getSelectedImage();
			selectNone(dp);
			return copy;
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
			BufferedImage copy = getSelectedImage();
			Graphics2D g2 = originalImage.createGraphics();
			g2.setBackground(dp.getFillColor());
			Rectangle b = getBounds();
			g2.clearRect(b.x,b.y,b.width,b.height);
			g2.dispose();
			selectNone(dp);
			return copy;
		}
		return null;
	}

	/**
	 * Puts image on selecction tool
	 * @param dp Affected drawing panel
	 * @param img Image to put
	 * @param forceTransparency Disables to change alpha to fill and viceversa
	 */
	public void paste(DrawingPanel dp, BufferedImage img, boolean forceTransparency)
	{
		int x,y,w,h,tmpW,tmpH;
		dp.notifyChanged();
		pastedImage = img;

		if(!forceTransparency)
			updateTransparency(dp);

		if(old==null)
		{
			old = dp.getScrollPossition();

			tmpW = pastedImage.getWidth();
			tmpH = pastedImage.getHeight();

			if(tmpW>tmpH)
			{
				w = 100*dp.getWidth()/(2*dp.getZoom());
				h = w * tmpH / tmpW;
			}
			else
			{
				h = 100*dp.getHeight()/(2*dp.getZoom());
				w = h * tmpW / tmpH;
			}

			x = old.x + w/4;
			y = old.y + h/4;

			current = new Point(x+w,y+h);
		}

		draw();
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
		if(pastedImage != null)
		{
			pastedImage = null;
		}

		draw();
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
		status = NONE;

		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			pastedImage = null;
		}
		dp.updateUI();
	}

	public Rectangle getBounds()
	{
		if(old == null)
			return null;

		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);
		return new java.awt.Rectangle(x,y,w,h);
	}

	/**
	 * Sets if selection pixeles of the background color may be transparent
	 */
	public void setTransparentSelection(DrawingPanel dp, boolean v)
	{
		transparenSelection = v;
		updateTransparency(dp);
		if(old!=null)
			dp.updateUI();

	}

	/**
	 * Sets if selection pixeles of the background color may be transparent
	 */
	public boolean getTransparentSelection()
	{
		return transparenSelection;
	}

	protected void draw()
	{
		if(old==null)
			return;

		final int x = old.x < current.x ? old.x : current.x;
		final int y = old.y < current.y ? old.y : current.y;
		final int w = Math.abs(old.x - current.x);
		final int h = Math.abs(old.y - current.y);

		g.setColor(selectionBColor);
		g.clearRect(0, 0, width,height);
		if(pastedImage != null)
		{
			g.drawImage(pastedImage, x, y, w, h, null);
		}
		g.setColor(selectionColor);;
		g.fillRect(x, y, w, h);
		g.setColor(selectionBorderColor);
		g.drawRect(x, y, w, h);
	}

	/**
	 * Updates pasted image with the trasparency hits
	 * @param dp Drawing panel where to render
	 */
	private void updateTransparency(DrawingPanel dp, BufferedImage img)
	{
		if(img == null)
			return;

		final int W = img.getWidth();
		final int H = img.getHeight();
		int color;
		int background = dp.getFillColor().getRGB();

		for(int x=0; x<W; x++)
		{
			for(int y=0; y<H; y++)
			{
				color = img.getRGB(x,y);
				if(transparenSelection && color==background)
					img.setRGB(x, y, color & TRANSPARENT);
				else if(transparenSelection==false && (color & OPAQUE) == 0)
					img.setRGB(x, y, color | OPAQUE);
			}
		}
	}

	/**
	 * Updates pasted image with the trasparency hits
	 * @param dp Drawing panel where to render
	 */
	private void updateTransparency(DrawingPanel dp)
	{
		if(pastedImage == null)
			return;

		updateTransparency(dp, pastedImage);
	}

	/**
	 * Gets selected image
	 * @return The currently selected image
	 */
	public BufferedImage getSelectedImage()
	{
		if(old == null)
			return null;

		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);

		BufferedImage copyImage = new BufferedImage(w,h,originalImage.getType());
		Graphics2D g2 = copyImage.createGraphics();
		g2.drawImage(originalImage, 0, 0, w, h, x, y, x+w, y+h, null);
		return copyImage;
	}

	public String getDescription()
	{
		return Translator.m("Selection");
	}

	public Icon getIcon()
	{
		return icon;
	}

}
