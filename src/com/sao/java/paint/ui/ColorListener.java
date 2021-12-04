package com.sao.java.paint.ui;

import java.awt.Color;

import com.sao.java.paint.divcompat.ColorPalette;

public interface ColorListener {
	void setStrokeColor(Color c);	
        void setColorProvider(ColorProvider cp);
        void setPalette(ColorPalette cp);
}
