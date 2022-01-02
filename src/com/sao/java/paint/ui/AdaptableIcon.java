package com.sao.java.paint.ui;

import java.net.URL;

import javax.swing.Icon;
import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 * Clas for drawing icons that changes sice with parent
 */
public class AdaptableIcon implements Icon{
	protected static final BufferedImage FAILED;
	BufferedImage image;
	int minWidth;
	int minHeight;

	/**
	 * Icon to use when resource load is failed
	 */
	static
	{
		FAILED = new BufferedImage(32,32,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = FAILED.createGraphics();
		BasicStroke s = new BasicStroke(4);
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, 32, 32);
		g.setColor(Color.RED);
		g.setStroke(s);
		g.drawLine(5, 5, 27, 27);
		g.drawLine(5, 27, 27, 5);
		g.dispose();
	}

	/**
	 * Class constructor
	 * @param url Url from wich the icon is loaded
	 */
	public AdaptableIcon(URL url)
	{
		try
		{
			minWidth=0;
			minHeight=0;
			image = ImageIO.read(url);
		}
		catch(Exception ex)
		{
			image = FAILED;
		}
	}

	/**
	 * Class constructor
	 * @param url Url from wich the icon is loaded
	 */
	public AdaptableIcon(URL url, int minWidth, int minHeight)
	{
		try
		{
			this.minWidth=minWidth;
			this.minHeight=minHeight;
			image = ImageIO.read(url);
		}
		catch(Exception ex)
		{
			image = FAILED;
		}
	}

	/**
	 * Paints the icon on component
	 * @param component Component where the icon is painted
	 * @param graphics Graphics object to draw the icon
	 * @param x X-Coordinate where to draw the icon
	 * @param y Y-Coordinate where to draw the icon
	 */
	public void paintIcon(Component component, Graphics graphics, int x, int y)
	{

		int w = minWidth <=0 ? Math.min(component.getWidth(),image.getWidth()) : minWidth;
		int h = minHeight <= 0 ? Math.min(component.getHeight(),image.getHeight()) : minHeight;

		if(h<w)
		{
			w = h * image.getWidth() / image.getHeight();
		}
		else
		{
			h = w * image.getHeight() / image.getWidth();
		}

		x = minWidth <=0 ? x-w/2 : x;
		y = minHeight <= 0 ? y-h/2 : y;

		graphics.drawImage(image, x, y, w, h, null);
	}

	/**
	 * Returns the icon width
	 * The returned value is always 0 because we need to left component to be free to be resized
	 */
	public int getIconWidth()
	{
		return minWidth;
	}

	/**
	 * Returns the icon height
	 * The returned value is always 0 because we need to left component to be free to be resized
	 */
	public int getIconHeight()
	{
		return minHeight;
	}
}
