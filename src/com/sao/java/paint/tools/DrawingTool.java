package com.sao.java.paint.tools;

import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Cursor;

/**
 * Represents a drawing tool
 */
public abstract class DrawingTool {
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
	 * Gets tool description
	 * @return The tool description as string
	 */
	public String getDescription()
	{
		return "DrawingTool";
	}

	/**
	 * Mouse cursor representing this tool
	 */
	private final static Cursor DEFAULTCURSOR = new Cursor(Cursor.HAND_CURSOR);
	public Cursor getCursor()
	{
		return DEFAULTCURSOR;
	}
}
