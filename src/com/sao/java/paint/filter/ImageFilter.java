package com.sao.java.paint.filter;

import com.sao.java.paint.ui.DrawingPanel;

public interface ImageFilter {
	final int MODE_R = 1;
	final int MODE_G = 2;
	final int MODE_B = 4;
	final int MODE_A = 8;
	final int MODE_RGBA = 15;

	void applyTo(DrawingPanel i, int mode, int x0, int y0, int x1, int y1);
	String getDescription();
}
