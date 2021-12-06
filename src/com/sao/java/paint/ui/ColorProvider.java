/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Color;

import com.sao.java.paint.divcompat.ColorPalette;

/**
 *
 * @author Julio
 */
public interface ColorProvider {
    /**
     * Gets actual stroke color
     * @return The actual stroke color
     */
    Color getStrokeColor();

    /**
     * Sets actual stroke color
     * @return The actual stroke color
     */
    void setStrokeColor(Color c);
    
    /**
     * Causes color provider ask to parent color providers for color
     */
    void askForStrokeColor();
    
    /**
     * Returns current color palette
     * @return Current color palette
     */
    ColorPalette getColorPalette();

}
