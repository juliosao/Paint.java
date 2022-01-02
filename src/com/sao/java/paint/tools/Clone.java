package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Clone extends BrushDrawingTool {
	public static final int INIT = 0;
	public static final int PICKED = 1;
	public static final int APPLY = 2;

	BufferedImage image;
	int oldX, oldY, originX, originY;
	private int status = INIT;

	private static final Icon icon;

	static
	{
		icon = loadIcon("clone");
	}


	public void onSelected(DrawingPanel dp)
	{
		status = INIT;
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		image = dp.getImage();


		switch(status)
		{
			case INIT:
				initBrush((int)dp.getStroke().getLineWidth());
				final int maxW = image.getWidth();
				final int maxH = image.getHeight();

				originX = me.getX() - width/2;
				originY = me.getY() - width/2;

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
							redInk[i][j]= 1;
							greenInk[i][j]= 1;
							blueInk[i][j]= 1;
						}
					}
				}
				status = PICKED;
				break;

			case PICKED:
				oldX = me.getX();
				oldY = me.getY();
				apply(oldX,oldY);
				status = APPLY;
		}

	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
	{
		if(status == APPLY)
		{
			final int x = me.getX();
			final int y = me.getY();
			apply(oldX, oldY, x, y);
		}
	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
	{
		if(status == APPLY)
		{
			final int x = me.getX();
			final int y = me.getY();
			apply(oldX, oldY, x, y);
		}
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

				if( !(x<0 || x>=maxW || y<0 || y>=maxH || redInk[i][j]==-1 || (originX+i<0)
					|| (originX+i>=maxW) || (originY+j<0) || (originY+j>=maxH)))
				{
					// Pixel to clone
					final int c = image.getRGB(originX+i, originY+j);
					image.setRGB(x, y, c );
				}
			}
		}

		originX += dx-oldX;
		originY += dy-oldY;
		oldX = dx;
		oldY = dy;
	}



	public String getDescription()
	{
		return Translator.m("Clone");
	}

	public Icon getIcon()
	{
		return icon;
	}

}
