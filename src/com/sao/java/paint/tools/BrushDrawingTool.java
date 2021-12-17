package com.sao.java.paint.tools;

public abstract class BrushDrawingTool
	extends DrawingTool
{
	protected double[][] redInk, greenInk, blueInk;
	protected int width;


	/**
	 * Initialices array for color components
	 * @param w Brush width
	 */
	public void initBrush(int w)
	{
		width = w;
		redInk = new double[width][width];
		greenInk = new double[width][width];
		blueInk = new double[width][width];
	}

	/**
	 * Applies tool for a point
	 * @param x x-coordinate
	 * @param y y-coordinate
	 */
	protected abstract void apply(int x, int y);

	/**
	 * Applies tool from one point to another
	 * @param x0 x-coordinate for source point
	 * @param y0 y-coordinate for source point
	 * @param x1 x-coordinate for destination point
	 * @param y1 y-coordinate for destination point
	 */
	protected void apply(int x0, int y0, int x1, int y1 )
	{
		final double diffX = x1 - x0;
		final double diffY = y1 - y0;
		final boolean horizontal = Math.abs(diffX) > Math.abs(diffY);

		if(horizontal)
		{
			if(diffX==0)// If diffX is 0 a endless loop should not to be executed
			{
				apply(x0, y0);
				return;
			}

			final int incX = diffX > 0 ? 1 : -1;
			final double relation = diffY/diffX;
			for(int x=x0; x!=x1; x+=incX)
			{
				int y = (int)(y0 + relation*(x-x0));
				apply(x, y);
			}
		}
		else
		{
			if(diffY==0)// If diffX is 0 a endless loop should not to be executed
			{
				apply(x0, y0);
				return;
			}

			final int incY = diffY > 0 ? 1 : -1;
			final double relation = diffX/diffY;
			for(int y=y0; y!=y1; y+=incY)
			{
				int x = (int)(x0 + relation*(y-y0));
				apply(x, y);
			}
		}
	}
}