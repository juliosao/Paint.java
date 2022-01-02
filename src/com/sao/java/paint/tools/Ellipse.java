package com.sao.java.paint.tools;

import javax.swing.Icon;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Ellipse
	extends ShapingTool
{

	private static final Icon icon;

	static
	{
		icon = loadIcon("ellipse");
	}


	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
	{
		super.onMousePressed(dp,me);
		draw();
	}

	@Override
	protected void draw()
	{
		int x = old.x < current.x ? old.x : current.x;
		int y = old.y < current.y ? old.y : current.y;
		int w = Math.abs(old.x - current.x);
		int h = Math.abs(old.y - current.y);

		clear();
		if((mode & DrawingPanel.FILL) != 0 )
		{
			graphics.setColor(fillColor);
			graphics.fillOval(x, y, w, h);
		}

		if((mode & DrawingPanel.BORDER) != 0 )
		{
			graphics.setStroke(stroke);
			graphics.setColor(strokeColor);
			graphics.drawOval(x, y, w, h);
		}
	}

	public String getDescription()
	{
		return Translator.m("Ellipse");
	}

	public Icon getIcon()
	{
		return icon;
	}

}
