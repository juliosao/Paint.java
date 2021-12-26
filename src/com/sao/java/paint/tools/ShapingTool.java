package com.sao.java.paint.tools;

import com.sao.java.paint.ui.DrawingPanel;

import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Stroke;
import java.awt.Point;

public abstract class ShapingTool 
    extends DrawingTool{

    protected static final Color TRANSPARENT = new Color(255,255,255,0);
    
    protected Point old = null;
    protected Point current = null;

    /*BufferedImage tooling;*/
    
    protected Color strokeColor;
    protected Color fillColor;
    protected Stroke stroke;
    protected int mode;
    protected BufferedImage tooling;
    protected Graphics2D graphics;

    @Override
    public void onMousePressed(DrawingPanel dp,  DrawingMouseEvent me)
    {
        dp.notifyChanged();
        tooling = dp.createToolingLayer();
        graphics = (Graphics2D)tooling.getGraphics();
        strokeColor = me.button == 1 ? dp.getStrokeColor() : dp.getFillColor();
        fillColor = me.button == 1 ? dp.getFillColor() : dp.getStrokeColor();
        mode = dp.getShapeMode();
        stroke = dp.getStroke();
        old=me.getPoint();
        current=old;
        draw();

    }

    @Override
    public void onMouseReleased(DrawingPanel dp,  DrawingMouseEvent me)
    {
        current =  me.getPoint();
        draw();
        graphics.dispose();
        BufferedImage img = dp.getImage();
        Graphics2D g = img.createGraphics();
        g.drawImage(tooling, 0, 0, null);
        g.dispose();
    }

    @Override
    public void onMouseDragged(DrawingPanel dp,  DrawingMouseEvent me)
    {
        current =  me.getPoint();
        draw();
    }

    protected void clear()
    {
        graphics.setBackground(TRANSPARENT);
        graphics.clearRect(0,0,tooling.getWidth(),tooling.getHeight());
    }

    protected abstract void draw();
}
