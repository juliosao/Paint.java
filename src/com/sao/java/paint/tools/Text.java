package com.sao.java.paint.tools;

import java.awt.Window;

import com.sao.java.paint.dialogs.StrokeTextDialog;
import com.sao.java.paint.ui.DrawingPanel;

public class Text extends DrawingTool{
	Window parent;
	RectangleSelection rectangleSelection;

	public Text(Window p, RectangleSelection rs)
	{
		parent = p;
		rectangleSelection = rs;
	}

	@Override
	public void onSelected(DrawingPanel dp)
	{
		StrokeTextDialog dialog = new StrokeTextDialog(parent,rectangleSelection,dp);
		dialog.setVisible(true);
	}

	public String getDescription()
	{
		return "Text";
	}
}
