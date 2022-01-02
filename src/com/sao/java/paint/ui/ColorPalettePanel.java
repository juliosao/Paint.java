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
        implements Coloreable, ActionListener {

    int idx = 0;
    ColorPalette palette;
    ColorButton buttons[] = new ColorButton[ColorPalette.NUMCOLORS];
    ActionListener listener = null;

    public ColorPalettePanel(ColorPalette cp){

        setLayout(new GridLayout(16,16,2,2));
        palette = cp;
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            ColorButton b = new ColorButton(cp.getColor(i));
            b.addActionListener(this);
            buttons[i] = b;
            add(b);
        }
    }

    public void addActionListener(ActionListener l)
    {
        listener = l;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ColorButton src = (ColorButton)e.getSource();
        for(int i = 0; i<ColorPalette.NUMCOLORS; i++)
        {
            if(src == buttons[i])
            {
                idx = i;
                listener.actionPerformed(new ActionEvent(this,ActionEvent.ACTION_PERFORMED,"ColorChanged"));
                return;
            }
        }
    }


    @Override
    public void setStrokeColor(Color c)
    {
        // Does nothing?
    }

    @Override
    public Color getStrokeColor() {
        return buttons[idx].getStrokeColor();
    }

    public ColorPalette getColorPalette() {
        return palette;
    }

    public void setColorPalette(ColorPalette p)
    {
        palette = p;
        for(int i=0; i<ColorPalette.NUMCOLORS; i++)
        {
            buttons[i].setStrokeColor(p.getColor(i));
        }
    }

    @Override
    public Color getFillColor() {
        return null;
    }

    @Override
    public void setFillColor(Color c) {
        //Does nothing
    }


}
