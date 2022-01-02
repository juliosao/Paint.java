package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Erase extends BrushDrawingTool
{
	BufferedImage image;
	int oldX, oldY;
	int color;
	int replace;
	boolean doReplace = false;
	public static final Icon icon;

	static
	{
		icon = loadIcon("eraser");
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		dp.notifyChanged();
		image = dp.getImage();
		initBrush((int)dp.getStroke().getLineWidth());

		color = dp.getFillColor().getRGB();
		replace = dp.getStrokeColor().getRGB();
		doReplace = me.button == DrawingMouseEvent.RIGHTBUTTON;

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
					if(doReplace)
					{
						final int c = image.getRGB(x, y);
						if(c == replace)
							image.setRGB(x, y, color );
					}
					else
						image.setRGB(x, y, color );
				}
			}
		}
	}

	public String getDescription()
	{
		return Translator.m("Eraser");
	}

	public Icon getIcon()
	{
		return icon;
	}
}
