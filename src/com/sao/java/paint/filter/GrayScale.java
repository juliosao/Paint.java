package com.sao.java.paint.filter;

import java.awt.image.BufferedImage;
import com.sao.java.paint.ui.DrawingPanel;

public class GrayScale
	implements ImageFilter {

	@Override
	public void applyTo(DrawingPanel dp, int mode)
	{
		BufferedImage image = dp.getImage();
		if(mode==0)
			return;

		int divi = 0;

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
		for(int x=0; x<image.getWidth(); x++)
		{
			for(int y=0; y<image.getHeight(); y++)
			{
				final int oldColor = image.getRGB(x, y);
				int a = ((oldColor) >> 24) & 255;
				int r = ((oldColor) >> 16) & 255;
				int g = ((oldColor) >> 8) & 255;
				int b = oldColor & 255;
				int nc = 0;

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

				if(r > 255)r=255;
				if(g > 255)g=255;
				if(b > 255)b=255;
				if(r < 0)r=0;
				if(g < 0)g=0;
				if(b < 0)b=0;

				image.setRGB(x,y, a<<24 | nc<<16 | nc<<8 | nc);
			}
		}

		dp.updateUI();
	}


	public String getDescription()
	{
		return "Gray Scale";
	}
}
