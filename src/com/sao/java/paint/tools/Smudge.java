package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Smudge extends BrushDrawingTool {
	BufferedImage image;
	int oldX, oldY;


	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		image = dp.getImage();
		initBrush((int)dp.getStroke().getLineWidth());

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
					final int newR = (int)(cR*0.6 + redInk[i][j]*0.4);
					final int newG = (int)(cG*0.6 + greenInk[i][j]*0.4);
					final int newB = (int)(cB*0.6 + blueInk[i][j]*0.4);
					final int newC = opaque|(newR<<16)|(newG<<8)|newB;
					image.setRGB(x, y, newC );
					redInk[i][j] = redInk[i][j]*0.6 + cR*0.4;
					greenInk[i][j] = greenInk[i][j]*0.6 + cG*0.4;
					blueInk[i][j] = blueInk[i][j]*0.6 + cB*0.4;
				}
			}
		}
	}



	public String getDescription()
	{
		return Translator.m("Smudge");
	}

}
