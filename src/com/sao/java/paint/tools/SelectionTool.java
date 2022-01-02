package com.sao.java.paint.tools;

import com.sao.java.paint.ui.DrawingPanel;
import java.awt.image.BufferedImage;

public interface SelectionTool {
	BufferedImage cut(DrawingPanel dp);
	BufferedImage copy(DrawingPanel dp);
}
