package com.sao.java.paint.tools;

import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.Coloreable;
import com.sao.java.paint.ui.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.Icon;

public class ColorPicker extends DrawingTool {
	Point old = null;
	Graphics2D g;
	Coloreable coloreable = null;

	private static final Icon icon;

	static
	{
		icon = loadIcon("colorPick");
	}


	public void setColoreable(Coloreable c)
	{
		coloreable = c;
	}

	public Coloreable getColoreable()
	{
		return coloreable;
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me) {
		int color = dp.getImage().getRGB(me.x, me.y);

		if(me.button == 1)
			coloreable.setStrokeColor(new Color(color,true));
		else
			coloreable.setFillColor(new Color(color,true));
	}

	public String getDescription() {
		return Translator.m("ColorPicker");
	}

	public Icon getIcon()
	{
		return icon;
	}
}
