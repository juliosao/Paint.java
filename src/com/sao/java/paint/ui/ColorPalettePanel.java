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
import java.util.HashSet;
import javax.swing.JPanel;

/**
 * @author Julio
 */
public class ColorPalettePanel 
        extends JPanel
        implements ColorProvider, ColorListener, ActionListener {
    
    int idx = 0;
    ColorButton buttons[] = new ColorButton[ColorPalette.NUMCOLORS];
    HashSet<ColorListener> colorListeners = new HashSet<>();
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
                dispatchStrokeColor();
                break;
            }
        }
    }

    @Override
    public void askForStrokeColor() 
    {        
        if(colorProvider != null)
        {
            colorProvider.askForStrokeColor();
        }        
    }

    @Override
    public void setStrokeColor(Color c) 
    {
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            if(buttons[i].getBackground().equals(c))
            {
                idx = i;
                return;
            }
        }
        dispatchStrokeColor();
    }

    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
    }

    @Override
    public Color getStrokeColor() {
        return buttons[idx].getBackground();
    }

    @Override
    public void dispatchStrokeColor() {
        Color c = buttons[idx].getBackground();
        for(ColorListener colorListener: colorListeners)
        {
            colorListener.setStrokeColor(c);
        }
    }

    @Override
    public void addColorListener(ColorListener cl) {
        colorListeners.add(cl);
    }

    @Override
    public void removeColorListener(ColorListener cl) {
        colorListeners.remove(cl);
    }


    @Override
    public void setPalette(ColorPalette cp) {
        // TODO Auto-generated method stub
        
    }
    
}
