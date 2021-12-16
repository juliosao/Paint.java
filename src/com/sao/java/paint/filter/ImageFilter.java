package com.sao.java.paint.filter;

import com.sao.java.paint.ui.DrawingPanel;

public interface ImageFilter {
	final int MODE_R = 1;
	final int MODE_G = 2;
	final int MODE_B = 4;
	final int MODE_RGB = 7;

	void applyTo(DrawingPanel i, int mode);
	String getDescription();
}
