/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import com.sao.java.paint.divcompat.ColorPalette;

import com.sao.java.paint.dialogs.ColorPickerDialog;
import com.sao.java.paint.divcompat.ColorGamma;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box.Filler;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

/**
 *
 * @author julio
 */
public class ColorGammaBar
	extends JPanel
	implements Coloreable
{
	public static final String ACTIONSTROKE = "StrokeColor";
	public static final String ACTIONFILL = "FillColor";
	ColorButton strokeButton;
	ColorButton fillButton;
	ColorButton[] colorButtons;
	ColorPalette palette;
	ActionListener actionListener;

	public ColorGammaBar(ColorPalette p)
	{
		setLayout(new GridLayout(1,ColorGamma.NUMCOLORS+2));

		/**
		 * Stroke Button
		 */
		palette = p;
		strokeButton = new ColorButton(Color.black);
		strokeButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerDialog cpd = new ColorPickerDialog(null, palette);
				final Color c = cpd.askForStrokeColor(strokeButton.getStrokeColor());
				ColorPalette newPalette = cpd.getColorPalette();
				if(newPalette != palette)
					palette = newPalette;

				ColorGamma gamma = palette.getGamma(c);
				for(int i=0; i<ColorGamma.NUMCOLORS; i++)
				{
					colorButtons[i].setStrokeColor(gamma.getColor(i));
				}

				setStrokeColor(c);
			}
		});
		add(strokeButton);


		fillButton = new ColorButton(Color.black);
		fillButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerDialog cpd = new ColorPickerDialog(null, palette);
				final Color c = cpd.askForStrokeColor(fillButton.getStrokeColor());
				ColorPalette newPalette = cpd.getColorPalette();
				if(newPalette != palette)
					palette = newPalette;

				ColorGamma gamma = palette.getGamma(c);
				for(int i=0; i<ColorGamma.NUMCOLORS; i++)
				{
					colorButtons[i].setStrokeColor(gamma.getColor(i));
				}

				setFillColor(c);
			}
		});
		add(fillButton);

		/**
		 * Color gamma buttons
		 */
		Dimension d = new Dimension(10,10);
		add(new Filler(d,d,d));

		ColorGamma gamma = palette.getGamma(Color.black);
		colorButtons = new ColorButton[ColorGamma.NUMCOLORS];

		for(int i=0; i<ColorGamma.NUMCOLORS; i++)
		{
			ColorButton c = new ColorButton(gamma.getColor(i));
			/*
			c.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					setStrokeColor(c.getStrokeColor());
				}
			});
			*/
			c.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if(e.getButton() == 1)
					{
						setStrokeColor(c.getStrokeColor());
					}
					else
					{
						setFillColor(c.getStrokeColor());
					}
				}
			});
			add(c);
			colorButtons[i] = c;
		}
	}

	@Override
	public void setStrokeColor(Color c) {
		strokeButton.setBackground(c);
		if(actionListener != null)
			actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ACTIONSTROKE));
	}

	@Override
	public Color getStrokeColor() {
		return strokeButton.getBackground();
	}

	public void addActionListener(ActionListener l)
	{
		actionListener = l;
	}

	@Override
	public Color getFillColor() {
		return fillButton.getBackground();
	}

	@Override
	public void setFillColor(Color c) {
		fillButton.setBackground(c);
		if(actionListener != null)
			actionListener.actionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, ACTIONFILL));
	}
}
