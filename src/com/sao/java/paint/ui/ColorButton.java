/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashSet;
import javax.swing.JButton;


/**
 *
 * @author julio
 */
public class ColorButton 
        extends JButton
        implements ColorProvider, ColorListener
{
    ColorProvider colorProvider = null;
    HashSet<ColorListener> colorListeners = new HashSet<>();

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
        addActionListener((ActionEvent e) -> {
            ColorButton.this.dispatchStrokeColor();
        });
    }
  
    
    /**
     * Adds a listener for color events
     * @param c Listener for the color events
     */
    @Override
    public void addColorListener(ColorListener c)
    {        
        colorListeners.add(c);
    }
    
    /**
     * Removes a listener for color events
     * @param c Listener to remove
     */
    @Override
    public void removeColorListener(ColorListener c)    
    {
        colorListeners.remove(c);
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
    public void dispatchStrokeColor() {
        Color color = getBackground();
        for(ColorListener cp: colorListeners)
        {
            cp.setStrokeColor(color);
        }
    }
    
}
