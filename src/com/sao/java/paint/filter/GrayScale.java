package com.sao.java.paint.filter;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.ui.DrawingPanel;

public class GrayScale
	implements ImageFilter {

	@Override
	public void applyTo(DrawingPanel dp, int mode, int x0, int y0, int x1, int y1)
	{
		BufferedImage image = dp.getImage();
		if(mode==0)
			return;

		int divi = 0;

		if((mode & ImageFilter.MODE_A) != 0)
		{
			divi+=1;
		}
		if((mode & ImageFilter.MODE_R) != 0)
		{
			divi+=1;
		}
		if((mode & ImageFilter.MODE_G) != 0)
		{
			divi+=1;
		}
		if((mode & ImageFilter.MODE_B) != 0)
		{
			divi+=1;
		}

		dp.notifyChanged();
		for(int x=x0; x<x1; x++)
		{
			for(int y=x0; y<y1; y++)
			{
				final int oldColor = image.getRGB(x, y);
				int a = ((oldColor) >> 24) & 255;
				int r = ((oldColor) >> 16) & 255;
				int g = ((oldColor) >> 8) & 255;
				int b = oldColor & 255;
				int nc=0;

				if((mode & ImageFilter.MODE_A) != 0)
				{
					nc += r;
				}
				if((mode & ImageFilter.MODE_R) != 0)
				{
					nc += r;
				}
				if((mode & ImageFilter.MODE_G) != 0)
				{
					nc += g;
				}
				if((mode & ImageFilter.MODE_B) != 0)
				{
					nc += b;
				}

				nc/=divi;

				final int na = ((mode & ImageFilter.MODE_A) != 0) ? nc : a;
				final int nr = ((mode & ImageFilter.MODE_R) != 0) ? nc : r;
				final int ng = ((mode & ImageFilter.MODE_G) != 0) ? nc : g;
				final int nb = ((mode & ImageFilter.MODE_B) != 0) ? nc : b;

				image.setRGB(x,y,DrawingTool.rgb((int)na, (int)nr, (int)ng, (int)nb));
			}
		}

		dp.updateUI();
	}


	public String getDescription()
	{
		return Translator.m("GrayScale");
	}
}
