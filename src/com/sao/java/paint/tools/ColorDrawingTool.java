/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.tools;

import com.sao.java.paint.ui.ColorListener;
import com.sao.java.paint.ui.ColorProvider;
import java.awt.Color;

/**
 *
 * @author Julio
 */
public abstract class ColorDrawingTool 
        extends DrawingTool
        implements ColorListener
{
    protected Color strokeColor = Color.BLACK;
    private ColorProvider colorProvider = null;
    
    @Override
    public void setStrokeColor(Color c) {
        strokeColor = c;
    }
    
    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
        colorProvider.addColorListener(this);
    }
}
