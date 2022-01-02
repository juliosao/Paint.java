/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Graphics;
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
    //private static final BasicStroke BORDERSTROKE = new BasicStroke(2, BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER);
    Color filColor;


    /**
     * Class constructor
     * @param c Color for the button
     */
    public ColorButton(final Color c)
    {
        super();
        filColor = c;
        setText("");
        Dimension s = new Dimension(24,24);
        setMinimumSize(s);
        setPreferredSize(s);
        setFocusPainted(false);
    }

    @Override
    public void setStrokeColor(Color c) {
        filColor=c;
        updateUI();
    }

    public void paintComponent(Graphics g){
        final int w = getWidth();
        final int h = getHeight();

        if(filColor.getAlpha() != 255)
        {
            for(int i=0; i<w; i+=10)
            {
                boolean even = (i/10)%2==1;
                for(int j=0; j<h; j+=10)
                {
                    final int rw = i+10>w ? w%10 : 10;
                    final int rh = j+10>h ? h%10 : 10;
                    if(even)
                    {
                        g.setColor(Color.white);
                    }
                    else
                    {
                        g.setColor(Color.LIGHT_GRAY);
                    }
                    g.fillRect(i,j,rw,rh);
                    even=!even;
                }
            }
        }

        g.setColor(filColor);
        g.fillRect(0, 0, w, h);


        g.setColor(Color.black);
        g.drawRect(0,0, w, h);

    }

    @Override
    public Color getStrokeColor() {
        return filColor;
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
