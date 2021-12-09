package com.sao.java.paint.tools;

import com.sao.java.paint.ui.Coloreable;
import com.sao.java.paint.ui.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ColorPicker extends DrawingTool {
	Point old = null;
	Graphics2D g;
	Coloreable coloreable;

	public ColorPicker(Coloreable c)
	{
		coloreable = c;
	}

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me) {
		coloreable.setStrokeColor(new Color( dp.getImage().getRGB(me.x, me.y)));
	}

	public String getDescription() {
		return "Color Picker";
	}

}
