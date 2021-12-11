package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
import com.sao.java.paint.ui.DrawingPanel;

public class Smudge extends DrawingTool {
	BufferedImage image;
	double[][] redInk, greenInk, blueInk;
	int oldX, oldY, width;
	private static final int alpha = 255 << 24;

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		image = dp.getImage();
		width = (int)dp.getStroke().getLineWidth();
		redInk = new double[width][width];
		greenInk = new double[width][width];
		blueInk = new double[width][width];

		oldX = me.getX();
		oldY = me.getY();
		final int maxW = image.getWidth();
		final int maxH = image.getHeight();

		for(int i=0; i<width; i++)
		{
			final int x = oldX-width/2+i;
			for(int j=0; j<width; j++)
			{
				final int y = oldY-width/2+j;

				if(x<0 || x>=maxW || y<0 || y>maxH)
				{
					redInk[i][j]=-1;
					greenInk[i][j]=-1;
					blueInk[i][j]=-1;
				}
				else
				{
					final int c = image.getRGB(x, y);
					redInk[i][j]= (c >> 16) & 255;
					greenInk[i][j]= (c >> 8) & 255;
					blueInk[i][j]= c & 255;
				}
			}
		}
	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
		final int x = me.getX();
		final int y = me.getY();
		apply(oldX, oldY, x, y);
		oldX = x;
		oldY = y;
	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
	{
		final int x = me.getX();
		final int y = me.getY();
		apply(oldX, oldY, x, y);
		oldX = x;
		oldY = y;
	}

	private void apply(int dx, int dy)
	{
		final int maxW = image.getWidth();
		final int maxH = image.getHeight();

		for(int i=0; i<width; i++)
		{
			final int x = dx-width/2+i;
			for(int j=0; j<width; j++)
			{
				final int y = dy-width/2+j;

				if( !(x<0 || x>=maxW || y<0 || y>=maxH || redInk[i][j]==-1))
				{
					final int c = image.getRGB(x, y);
					final int cR= (c >> 16) & 255;
					final int cG= (c >> 8) & 255;
					final int cB= c & 255;
					final int newR = (int)(cR*0.6 + redInk[i][j]*0.4);
					final int newG = (int)(cG*0.6 + greenInk[i][j]*0.4);
					final int newB = (int)(cB*0.6 + blueInk[i][j]*0.4);
					final int newC = alpha|(newR<<16)|(newG<<8)|newB;
					image.setRGB(x, y, newC );
					redInk[i][j] = redInk[i][j]*0.6 + cR*0.4;
					greenInk[i][j] = greenInk[i][j]*0.6 + cG*0.4;
					blueInk[i][j] = blueInk[i][j]*0.6 + cB*0.4;
				}
			}
		}
	}

	private void apply(int x0, int y0, int x1, int y1 )
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

	public String getDescription()
	{
		return "Smudge";
	}

}
