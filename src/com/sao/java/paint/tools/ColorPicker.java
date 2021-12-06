package com.sao.java.paint.tools;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class ColorPicker extends ColorDrawingTool {
	Point old = null;
	Graphics2D g;

	@Override
	public void onMousePressed(BufferedImage image, DrawingMouseEvent me) {
		if(colorProvider != null)
			colorProvider.setStrokeColor(new Color(image.getRGB(me.x, me.y)));
	}

	@Override
	public void onMouseReleased(BufferedImage image, DrawingMouseEvent me) {

	}

	@Override
	public void onMouseDragged(BufferedImage image, DrawingMouseEvent me) {

	}

	public String getDescription() {
		return "Color Picker";
	}

}
