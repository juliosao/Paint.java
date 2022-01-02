package com.sao.java.paint.filter;

import java.awt.image.BufferedImage;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class SwapChannels
	implements ImageFilter {

	@Override
	public void applyTo(DrawingPanel dp, int mode, int x0, int y0, int x1, int y1) {
		int swapA=-1, swapB=-1;
		int offsetA=-1, offsetB=-1;


		BufferedImage image = dp.getImage();
		if(mode==0)
			return;

		if((mode & ImageFilter.MODE_A) != 0)
		{
			offsetA = 24;
		}
		if((mode & ImageFilter.MODE_R) != 0)
		{
			if(offsetA == -1)
			{
				offsetA = 16;
			}
			else
			{
				offsetB = 16;
			}
		}
		if((mode & ImageFilter.MODE_G) != 0)
		{
			if(offsetA == -1)
			{
				offsetA = 8;
			}
			else
			{
				offsetB = 8;
			}
		}
		if((mode & ImageFilter.MODE_B) != 0)
		{
			if(offsetA == -1)
			{
				offsetA = 0;
			}
			else
			{
				offsetB = 0;
			}
		}

		dp.notifyChanged();
		for(int x=x0; x<x1; x++)
		{
			for(int y=x0; y<y1; y++)
			{
				int color = image.getRGB(x, y);
				swapA = color & (255 << offsetA);
				swapB = color & (255 << offsetB);

				color ^= swapA;
				color ^= swapB;

				color |= swapA >> offsetA << offsetB;
				color |= swapB >> offsetB << offsetA;

				image.setRGB(x,y,color);
			}
		}

		dp.updateUI();

	}

	@Override
	public String getDescription() {
		return Translator.m("Swap_Channels");
	}

}
