package com.sao.java.paint.tools;

import com.sao.java.paint.ui.AdaptableIcon;
import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Cursor;
import java.net.URL;

import javax.swing.Icon;

/**
 * Represents a drawing tool
 */
public abstract class DrawingTool {
	/**
	 * Opaque alpha component int RGB
	 */
	public static final int opaque = 255 << 24;

	/**
	 * Occurs when user selects the tool
	 * @param dp Drawing pannel where to paint
	 */
	public void onSelected(DrawingPanel dp){}

	/**
	 * Occurs when user selects another tool and current tool must dump its work
	 * @param dp Drawing pannel where to paint
	 */
	public void onFinished(DrawingPanel dp){}

	/**
	 * Occurs when mouse is pressed on canvas
	 * @param dp Drawing pannel where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMousePressed(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Occurs when mouse is released on canvas
	 * @param dp Drawing pannel where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMouseReleased(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Occurs when mouse is moving while pressed on canvas
	 * @param dp Drawing pannel where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMouseDragged(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Occurs when mouse is moved over the DrawingPanel but no mouse button is pressed
	 * @param dp Drawing pannel where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMouseFlight(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Gets tool description
	 * @return The tool description as string
	 */
	public String getDescription()
	{
		return "DrawingTool";
	}

	public Icon getIcon()
	{
		return null;
	}

	/**
	 * Loads icon from path
	 * @param what name of the icon to load
	 * @return The found icon or null
	 */
	protected static Icon loadIcon(String what)
	{
		URL url = DrawingTool.class.getResource("img/"+what+".png");
		return new AdaptableIcon(url);
	}

	/**
	 * Mouse cursor representing this tool
	 */
	private final static Cursor DEFAULTCURSOR = new Cursor(Cursor.HAND_CURSOR);
	public Cursor getCursor()
	{
		return DEFAULTCURSOR;
	}

	/**
	 * Gets RGB from components
	 * @param a alpha component
	 * @param r red component
	 * @param g green component
	 * @param b blue component
	 * @return The rgb value for the color
	 */
	public static final int rgb(int a,int r, int g, int b)
	{
		if(a>255)
			a = 255;
		if(r>255)
			r = 255;
		if(g>255)
			g = 255;
		if(b>255)
			b = 255;

		if(a<0)
			a = 0;
		if(r<0)
			r = 0;
		if(g<0)
			g = 0;
		if(b<0)
			b = 0;

		return (a<<25) | (r<<16) | (g<<8) | b;
	}
}
