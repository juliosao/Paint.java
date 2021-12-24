package com.sao.java.paint.tools;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Blur extends BrushDrawingTool {
	BufferedImage image;
	BufferedImage backImage;
	int oldX, oldY;
	double adjust = 1.0/9.0;
	double convolution[][] = new double[][]{
		{adjust,adjust,adjust},{adjust,adjust,adjust},{adjust,adjust,adjust}
	};

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

		backImage = new BufferedImage(maxW,maxH,image.getType());
		Graphics2D g = backImage.createGraphics();
		g.drawImage(image, 0, 0, null);

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
					alphaInk[i][j]=-1;
					redInk[i][j]=-1;
					greenInk[i][j]=-1;
					blueInk[i][j]=-1;
				}
				else
				{
					alphaInk[i][j]=1;
					redInk[i][j]= 1;
					greenInk[i][j]= 1;
					blueInk[i][j]= 1;
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

				if( x<0 || x>=maxW || y<0 || y>=maxH || redInk[i][j]==-1)
					continue;

				double newA = 0;
				double newR = 0;
				double newG = 0;
				double newB = 0;
				for(int ax=0; ax<3; ax++)
				{
					final int tmpX = x-1+ax;
					if( tmpX<0 || tmpX>=maxW)
						continue;

					for(int ay = 0; ay<3; ay++)
					{
						final int tmpY = y-1+ay;
						if(y-1+ay<0 || y-1+ay>=maxH)
							continue;

						final int newRGB = backImage.getRGB(tmpX,tmpY);
						newA += (double)((newRGB>>24) & 255) * convolution[ax][ay];
						newR += (double)((newRGB>>16) & 255) * convolution[ax][ay];
						newG += (double)((newRGB>>8) & 255) * convolution[ax][ay];
						newB += (double)(newRGB & 255) * convolution[ax][ay];
					}
				}

				image.setRGB(x, y, rgb((int)newA,(int)newR,(int)newG,(int)newB));

			}
		}
	}



	public String getDescription()
	{
		return Translator.m("Blur");
	}

}
