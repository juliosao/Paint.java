package com.sao.java.paint.tools;

import java.awt.Point;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Stack;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;


public class ColorSelectionTool
	extends DrawingTool
	implements SelectionTool
{
	static final int SELECTED = 0x320000FF;
	static final int BORDER = 0xFF0000FF;
	static final int TRANSPARENT = 0x00FFFFFF;

	BufferedImage tooling;
	int width;
	int height;
	private static final Icon icon;

	static
	{
		icon = loadIcon("colorSelection");
	}

	public String getDescription()
	{
		return Translator.m("Color_selection");
	}

	public Icon getIcon()
	{
		return icon;
	}

	@Override
	public void onSelected(DrawingPanel dp)
	{
		tooling = dp.createToolingLayer();
		width = tooling.getWidth();
		height = tooling.getHeight();
		selectNone(dp);
	}

	/**
	 * Occurs when mouse is pressed on canvas
	 * @param g Graphics object where to paint
	 * @param me Mouse event with coordinates
	 */
	@Override
	public void onMousePressed(DrawingPanel dp, DrawingMouseEvent me)
	{
		if(me.button == DrawingMouseEvent.RIGHTBUTTON)
		{
			selectNone(dp);
			return;
		}
		select(dp.getImage(), me.x, me.y);
	}

	public void selectNone(DrawingPanel dp)
	{
		for(int x=0; x<width; x++)
		{
			for(int y=0; y<height; y++)
			{
				tooling.setRGB(x,y,TRANSPARENT);
			}
		}
		dp.updateUI();
	}

	private void select(BufferedImage img, int x, int y)
	{
		Stack<Point> pending = new Stack<>();

		int[] imgColors = img.getRGB(0, 0, width, height, null, 0, width);
		int[] tolColors = tooling.getRGB(0, 0, width, height, null, 0, width);

		int selectedColor = imgColors[x+y*width];

		pending.push(new Point(x,y));

		while(pending.size()>0)
		{
			Point p = pending.pop();

			if(p.x<0 || p.x>=width || p.y<0 || p.y>=height)
				continue;

			final int pos = p.x+p.y*width;
			int color = imgColors[pos];
			int tcolor = tolColors[pos];
			if(color == selectedColor && tcolor == TRANSPARENT)
			{
				tolColors[pos] = SELECTED;
				pending.push(new Point(p.x+1,p.y));
				pending.push(new Point(p.x-1,p.y));
				pending.push(new Point(p.x,p.y+1));
				pending.push(new Point(p.x,p.y-1));
			}
		}

		tooling.setRGB(0, 0, width, height, tolColors, 0, width);
	}


	/**
	 * Gets the selected area on a drawing pannel and clears it
	 * @param dp Affected drawing pannel
	 * @return A buffered image with the selected data
	*/
	@Override
	public BufferedImage copy(DrawingPanel dp)
	{
		BufferedImage copy = getSelectedImage(dp,false);
		selectNone(dp);
		return copy;
	}

	/**
	 * Gets the selected area on a drawing pannel and clears it
	 * @param dp Affected drawing pannel
	 * @return A buffered image with the selected data
	 */
	@Override
	public BufferedImage cut(DrawingPanel dp)
	{
		dp.notifyChanged();
		BufferedImage copy = getSelectedImage(dp,true);
		return copy;
	}

	private BufferedImage getSelectedImage(DrawingPanel dp, boolean erase)
	{
		BufferedImage img = dp.getImage();
		BufferedImage result = new BufferedImage(width, height, img.getType());

		int[] imgColors = img.getRGB(0, 0, width, height, null, 0, width);
		int[] tolColors = tooling.getRGB(0, 0, width, height, null, 0, width);
		int[] resColors = result.getRGB(0, 0, width, height, null, 0, width);

		for(int i=0; i<imgColors.length; i++)
		{
			resColors[i] = tolColors[i] == TRANSPARENT ? TRANSPARENT : imgColors[i];
			if(erase)
				imgColors[i] = tolColors[i] == TRANSPARENT ? imgColors[i] : TRANSPARENT;
		}

		result.setRGB(0, 0, width, height, resColors, 0, width);
		if(erase)
		{
			img.setRGB(0, 0, width, height, imgColors, 0, width);
		}

		return result;
	}
}
