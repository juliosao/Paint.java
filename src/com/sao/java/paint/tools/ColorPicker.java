package com.sao.java.paint.tools;


import com.sao.java.paint.ui.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ColorPicker extends ColorDrawingTool {
	Point old = null;
	Graphics2D g;

	@Override
	public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me) {
		if(colorProvider != null)
			colorProvider.setStrokeColor(new Color( dp.getImage().getRGB(me.x, me.y)));
	}

	@Override
	public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me) {

	}

	@Override
	public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me) {

	}

	public String getDescription() {
		return "Color Picker";
	}

}
