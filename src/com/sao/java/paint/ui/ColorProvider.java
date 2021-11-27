/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.ui;

import java.awt.Color;

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
     * Causes color provider ask to parent color providers for color
     */
    void askForStrokeColor();
    
    /**
     * Causes color provider to setup stroke color for all color listeners
     */
    void dispatchStrokeColor();
    
    /**
     * Adds new ColorListener to the set of listeners to manage
     * @param cl The ColorListener to add
     */
    void addColorListener(ColorListener cl);
    
    /**
     * Remove a ColorListener from the set of listeners to manage
     * @param cl The ColorListener to remove
     */
    void removeColorListener(ColorListener cl);
    
}
