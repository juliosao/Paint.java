/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;


/**
 *
 * @author julio
 */
public class ColorButton
        extends JButton
        implements Coloreable
{
    /**
     * Class constructor
     * @param c Color for the button
     */
    public ColorButton(final Color c)
    {
        super();
        setBackground(c);
        setText("");
        Dimension s = new Dimension(24,24);
        setMinimumSize(s);
        setPreferredSize(s);
        setFocusPainted(false);
    }

    @Override
    public void setStrokeColor(Color c) {
        setBackground(c);
    }


    @Override
    public Color getStrokeColor() {
        return getBackground();
    }

    @Override
	public Color getFillColor() {
		return null;
	}

	@Override
	public void setFillColor(Color c) {
		// Does nothing
	}
}
