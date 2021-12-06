/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;

import com.sao.java.paint.divcompat.ColorPalette;


/**
 *
 * @author julio
 */
public class ColorButton 
        extends JButton
        implements ColorProvider, Coloreable
{
    ColorProvider colorProvider = null;

    /**
     * Class constructor
     * @param c Color for the button
     */
    public ColorButton(final Color c)
    {
        super();
        setup(c);        
    }

    
    private void setup(Color c)
    {
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
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
    }

    @Override
    public Color getStrokeColor() {
        return getBackground();
    }

    @Override
    public void askForStrokeColor() {
        if(colorProvider != null)
            colorProvider.askForStrokeColor();
    }


    @Override
    public ColorPalette getColorPalette() {
        return colorProvider.getColorPalette();
    }

    
}
