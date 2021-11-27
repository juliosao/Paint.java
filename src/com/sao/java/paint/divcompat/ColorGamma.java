/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.divcompat;

import java.awt.Color;

/**
 *
 * @author Julio
 */
public class ColorGamma {
    public static final int NUMCOLORS = 16;
    private Color colors[] = new Color[NUMCOLORS];
    
    public ColorGamma()
    {
        for(int i=0; i<NUMCOLORS; i++)
        {
            colors[i] = new Color(0);
        }
    }
    
    public ColorGamma(Color clrs[])
    {
        for(int i=0; i<NUMCOLORS; i++)
        {
            colors[i] = new Color(clrs[i].getRGB());
        }
    }
    
    public ColorGamma(ColorGamma g)
    {
        for(int i=0; i<NUMCOLORS; i++)
        {
            colors[i] = new Color(g.colors[i].getRGB());
        }
    }
    
    public ColorGamma(Color initialColor, Color finalColor)
    {
        float incR = ((float)finalColor.getRed() - (float)initialColor.getRed()) / (float)(NUMCOLORS - 1);
        float incG = ((float)finalColor.getGreen() - (float)initialColor.getGreen()) / (float)(NUMCOLORS - 1);
        float incB = ((float)finalColor.getBlue() - (float)initialColor.getBlue()) / (float)(NUMCOLORS - 1);
        float incA = ((float)finalColor.getAlpha() - (float)initialColor.getAlpha()) / (float)(NUMCOLORS - 1);        
        
        
        int r = initialColor.getRed();
        int g = initialColor.getGreen();
        int b = initialColor.getBlue();
        int a = initialColor.getAlpha();
                
        
        for(int i=0; i<NUMCOLORS; i++)
        {
            colors[i] = new Color(r+(int)(incR*i),g+(int)(incG*i),b+(int)(incB*i),(int)a+(int)(incA*i));
        }
    }
    
    public Color getColor(int i)
    {
        return colors[i];
    }
    
    public void setColor(int i, Color c)
    {
        colors[i] = new Color(c.getRGB());
    }
}
