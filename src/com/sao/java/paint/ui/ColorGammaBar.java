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
import javax.swing.Box.Filler;
import javax.swing.JPanel;

/**
 *
 * @author julio
 */
public class ColorGammaBar
    extends JPanel
    implements ColorProvider, Coloreable
{
    ColorButton strokeButton;
    ColorButton[] colorButtons;
    ColorPalette palette;
    ColorProvider colorProvider = null;
        
    public ColorGammaBar(ColorProvider p)
    {
        colorProvider = p;
        setLayout(new GridLayout(1,ColorGamma.NUMCOLORS+2));
        
        /**
         * Stroke Button
         */
        strokeButton = new ColorButton(Color.black);
        strokeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                askForStrokeColor(); 
            }
        });
        add(strokeButton);
        
        /**
         * Color gamma buttons
         */
        Dimension d = new Dimension(10,10);
		add(new Filler(d,d,d));        
        ColorGamma gamma = colorProvider.getColorPalette().getGamma(Color.black);
        colorButtons = new ColorButton[ColorGamma.NUMCOLORS];

        for(int i=0; i<ColorGamma.NUMCOLORS; i++)
        {
            ColorButton c = new ColorButton(gamma.getColor(i));
            c.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    setStrokeColor(c.getStrokeColor());
                }
            });
            add(c);
            colorButtons[i] = c;
        }
    }

    @Override
    public void setStrokeColor(Color c) {
        strokeButton.setBackground(c);
        if(colorProvider != null)
        {
            ColorGamma gamma = colorProvider.getColorPalette().getGamma(c);
            for(int i=0; i<ColorGamma.NUMCOLORS; i++)
            {
                colorButtons[i].setStrokeColor(gamma.getColor(i));
            }
        }
    }
    
    @Override
    public Color getStrokeColor() {
        return strokeButton.getBackground();
    }
    
    @Override
    public void askForStrokeColor() {
        if(colorProvider != null )
        {
			colorProvider.setStrokeColor(getStrokeColor());
            colorProvider.askForStrokeColor(); 
            setStrokeColor(colorProvider.getStrokeColor());
        }

    }

    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
    }
    
    @Override
    public ColorPalette getColorPalette() {
        return colorProvider.getColorPalette();
    }
}
