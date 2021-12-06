/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.tools;

import com.sao.java.paint.ui.ColorProvider;
import com.sao.java.paint.ui.Coloreable;

/**
 *
 * @author Julio
 */
public abstract class ColorDrawingTool 
        extends DrawingTool
        implements Coloreable
{
    protected ColorProvider colorProvider = null;
    
    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
    }
}
