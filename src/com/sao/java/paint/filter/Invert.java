package com.sao.java.paint.filter;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.ui.DrawingPanel;

public class Invert
	implements ImageFilter {

	@Override
	public void applyTo(DrawingPanel dp, int mode, int x0, int y0, int x1, int y1)
	{
		BufferedImage image = dp.getImage();

		dp.notifyChanged();
		for(int x=x0; x<x1; x++)
		{
			for(int y=y0; y<y1; y++)
			{
				final int oldColor = image.getRGB(x, y);
				int a = ((oldColor) >> 24) & 255;
				int r = ((oldColor) >> 16) & 255;
				int g = ((oldColor) >> 8) & 255;
				int b = oldColor & 255;

				if((mode & ImageFilter.MODE_R) != 0)
				{
					a = 255-a;
				}
				if((mode & ImageFilter.MODE_R) != 0)
				{
					r = 255-r;
				}
				if((mode & ImageFilter.MODE_G) != 0)
				{
					g = 255-g;
				}
				if((mode & ImageFilter.MODE_B) != 0)
				{
					b = 255-b;
				}

				image.setRGB(x,y,DrawingTool.rgb((int)a, (int)r, (int)g, (int)b));
			}
		}

		dp.updateUI();
	}


	public String getDescription()
	{
		return Translator.m("InvertColors");
	}
}
