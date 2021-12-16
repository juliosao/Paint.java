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
    public static final int LEFTBUTTON = 1;
    public static final int RIGHTBUTTON = 3;

    final int x;
    final int y;
    final int button;

    public DrawingMouseEvent(int nx, int ny, int b)
    {
        x = nx;
        y = ny;
        button = b;
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
