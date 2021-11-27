/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.tools;

import java.awt.Point;

/**
 *
 * @author julio
 */
public class DrawingMouseEvent {
    final int x;
    final int y;

    public DrawingMouseEvent(int nx, int ny)
    {
        x = nx;
        y = ny;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Point getPoint()
    {
        return new Point(x,y);
    }
}
