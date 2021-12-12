package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
import com.sao.java.paint.ui.DrawingPanel;

public class Brush extends BrushDrawingTool
{
	BufferedImage image;
	int oldX, oldY;
	int r,g,b;
	private static final int alpha = 255 << 24;

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		image = dp.getImage();
		initBrush((int)dp.getStroke().getLineWidth());
		final int c = dp.getStrokeColor().getRGB();
		r= (c >> 16) & 255;
		g= (c >> 8) & 255;
		b= c & 255;

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
				final double r = (width/2);
				final double oi = r-i;
				final double oj = r-j;
				final double dist = Math.sqrt( oi*oi+oj*oj );

				if(dist>r || x<0 || x>=maxW || y<0 || y>maxH )
				{
					redInk[i][j]=-1;
					greenInk[i][j]=-1;
					blueInk[i][j]=-1;
				}
				else
				{
					redInk[i][j]=1;
					greenInk[i][j]=1;
					blueInk[i][j]=1;
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

	@Override
	protected void apply(int dx, int dy)
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

					final double diff = 1-redInk[i][j];

					final int newR = (int)(cR*diff + redInk[i][j]*r);
					final int newG = (int)(cG*diff + greenInk[i][j]*g);
					final int newB = (int)(cB*diff + blueInk[i][j]*b);
					final int newC = alpha|(newR<<16)|(newG<<8)|newB;
					image.setRGB(x, y, newC );
					redInk[i][j] *= 0.9;
					greenInk[i][j] *= 0.9;
					blueInk[i][j] *= 0.9;
				}
			}
		}
	}



	public String getDescription()
	{
		return "Brush";
	}
}
