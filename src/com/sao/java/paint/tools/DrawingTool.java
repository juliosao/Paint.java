package com.sao.java.paint.tools;

import com.sao.java.paint.ui.DrawingPanel;

import java.awt.Cursor;

/**
 * Represents a drawing tool
 */
public abstract class DrawingTool {

	/**
	 * Occurs when mouse is pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMousePressed(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Occurs when mouse is released on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMouseReleased(DrawingPanel dp, DrawingMouseEvent me){}

	/**
	 * Occurs when mouse is moving while pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	public void onMouseDragged(DrawingPanel dp, DrawingMouseEvent me){}

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
