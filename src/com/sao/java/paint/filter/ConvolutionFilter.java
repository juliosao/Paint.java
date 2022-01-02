package com.sao.java.paint.filter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.ui.DrawingPanel;

public abstract class ConvolutionFilter
	implements ImageFilter
{
	protected double convolutionMatrix[][];

	public void applyTo(DrawingPanel dp, int mode, int x0, int y0, int x1, int y1)
	{
		int mi = convolutionMatrix.length;
		int mj = convolutionMatrix.length;

		BufferedImage image = dp.getImage();
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage result = new BufferedImage(width,height,image.getType());

		dp.notifyChanged();
		for(int x=x0; x<x1; x++)
		{
			for(int y=y0; y<y1; y++)
			{
				final int oldColor = image.getRGB(x, y);
				final int a = ((oldColor) >> 24) & 255;
				final int r = ((oldColor) >> 16) & 255;
				final int g = ((oldColor) >> 8) & 255;
				final int b = oldColor & 255;

				double na = ((mode & ImageFilter.MODE_R) != 0) ? 0 : a;
				double nr = ((mode & ImageFilter.MODE_R) != 0) ? 0 : r;
				double ng = ((mode & ImageFilter.MODE_G) != 0) ? 0 : g;
				double nb = ((mode & ImageFilter.MODE_B) != 0) ? 0 : b;

				for(int i=0; i<mi; i++)
				{
					final int fx = x+i-mi/2;
					if(fx<0 || fx>=width)
						continue;

					for(int j=0; j<mj; j++)
					{

						final int fy = y+j-mj/2;
						if(fy<0 || fy>=height)
							continue;

						final int c = image.getRGB(fx, fy);
						if((mode & ImageFilter.MODE_R) != 0)
						{
							na += (double)((c >> 24 ) & 255) * convolutionMatrix[i][j];
						}
						if((mode & ImageFilter.MODE_R) != 0)
						{
							nr += (double)((c >> 16 ) & 255) * convolutionMatrix[i][j];
						}
						if((mode & ImageFilter.MODE_G) != 0)
						{
							ng += (double)((c >> 8 ) & 255) * convolutionMatrix[i][j];
						}
						if((mode & ImageFilter.MODE_B) != 0)
						{
							nb += (double)(c & 255) * convolutionMatrix[i][j];
						}
					}
				}


				result.setRGB(x,y,DrawingTool.rgb((int)na, (int)nr, (int)ng, (int)nb));
			}
		}

		Graphics2D g = image.createGraphics();
		g.drawImage(result, 0, 0, null);
		g.dispose();
		dp.updateUI();
	}
}
