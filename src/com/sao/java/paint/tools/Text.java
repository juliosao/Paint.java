package com.sao.java.paint.tools;

import java.awt.Window;

import javax.swing.Icon;

import com.sao.java.paint.dialogs.StrokeTextDialog;
import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.DrawingPanel;

public class Text extends DrawingTool{
	Window parent;
	RectangleSelection rectangleSelection;

	private static final Icon icon;

	static
	{
		icon = loadIcon("text");
	}


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
		return Translator.m("Text");
	}

	public Icon getIcon()
	{
		return icon;
	}
}
