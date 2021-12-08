/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import com.sao.java.paint.divcompat.ColorPalette;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 * @author Julio
 */
public class ColorPalettePanel 
        extends JPanel
        implements ColorProvider, Coloreable, ActionListener {
    
    int idx = 0;
    ColorButton buttons[] = new ColorButton[ColorPalette.NUMCOLORS];
    ColorProvider colorProvider = null;
    
    
    public ColorPalettePanel(ColorPalette cp){
        setLayout(new GridLayout(16,16,2,2));
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            ColorButton b = new ColorButton(cp.getColor(i));
            b.addActionListener(this);
            buttons[i] = b;
            add(b);
        }
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        ColorButton src = (ColorButton)e.getSource();
        for(int i = 0; i<ColorPalette.NUMCOLORS; i++)
        {
            if(src == buttons[i])
            {
                idx = i;
                colorProvider.setStrokeColor(buttons[i].getStrokeColor());
                return;
            }
        }
    }

    @Override
    public void askForStrokeColor() 
    {
    }

    @Override
    public void setStrokeColor(Color c) 
    {
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            if(buttons[i].getBackground().equals(c))
            {
                idx = i;
                colorProvider.setStrokeColor(c);
                return;
            }
        }
    }

    @Override
    public Color getStrokeColor() {
        return buttons[idx].getBackground();
    }

    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
    }
    
    @Override
    public ColorPalette getColorPalette() {
        return colorProvider.getColorPalette();
    }

    public void setColorPalette(ColorPalette p)
    {
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            buttons[i].setStrokeColor(p.getColor(i));            
        }
    }
}
