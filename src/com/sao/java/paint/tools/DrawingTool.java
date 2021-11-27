package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
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
	abstract public void onMousePressed(BufferedImage image, DrawingMouseEvent me);

	/**
	 * Occurs when mouse is released on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	abstract public void onMouseReleased(BufferedImage image, DrawingMouseEvent me);

	/**
	 * Occurs when mouse is moving while pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	abstract public void onMouseDragged(BufferedImage image, DrawingMouseEvent me);

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
