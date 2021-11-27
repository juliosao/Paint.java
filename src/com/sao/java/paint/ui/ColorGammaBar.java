/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.divcompat.ColorGamma;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import javax.swing.Box.Filler;
import javax.swing.JPanel;

/**
 *
 * @author julio
 */
public class ColorGammaBar
    extends JPanel
    implements ColorListener, ColorProvider, ActionListener
{
    ColorButton strokeButton;
    ColorButton[] colorButtons;
    ColorPalette palette;
    ColorProvider colorProvider = null;
    HashSet<ColorListener> colorListeners = new HashSet<>();
    
    public ColorGammaBar(ColorPalette p)
    {
        setLayout(new GridLayout(1,ColorGamma.NUMCOLORS+2));
        
        strokeButton = new ColorButton(Color.black);
        strokeButton.addActionListener(this);
        add(strokeButton);
        
        Dimension d = new Dimension(10,10);
		add(new Filler(d,d,d));


        palette = p;
        ColorGamma gamma = palette.getGamma(Color.black);

        colorButtons = new ColorButton[ColorGamma.NUMCOLORS];

        for(int i=0; i<ColorGamma.NUMCOLORS; i++)
        {
            ColorButton c = new ColorButton(gamma.getColor(i));
            c.addColorListener(this);
            add(c);
            colorButtons[i] = c;
        }
    }

    @Override
    public void setStrokeColor(Color c) {
        strokeButton.setBackground(c);
        dispatchStrokeColor();
    }


    @Override
    public void actionPerformed(ActionEvent e) 
    {
       askForStrokeColor();
    }
    
    /**
     * Adds a listener for color events
     * @param c Listener for the color events
     */
    public void addColorListener(ColorListener c)
    {
        colorListeners.add(c);
    }
    
    /**
     * Removes a listener for color events
     * @param c Listener to remove
     */
    public void removeColorListener(ColorListener c)    
    {
        colorListeners.remove(c);
    }
  

    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
        cp.addColorListener(this);
    }
    
    @Override
    public Color getStrokeColor() {
        return strokeButton.getBackground();
    }
    
    @Override
    public void askForStrokeColor() {
        if(colorProvider != null )
            colorProvider.askForStrokeColor();               
    }

    @Override
    public void dispatchStrokeColor() {
        Color c = strokeButton.getBackground();
        for(ColorListener cl: colorListeners)
        {
            cl.setStrokeColor(c);
        }
    }

}
