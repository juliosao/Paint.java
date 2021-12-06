package com.sao.java.paint.tools;

import java.util.Stack;

import com.sao.java.paint.divcompat.ColorPalette;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Point;

public class Fill extends ColorDrawingTool
{
        protected Color strokeColor = null;

    @Override
    public void onMousePressed(BufferedImage image, DrawingMouseEvent me)
    {
            strokeColor=colorProvider.getStrokeColor();
    }

    @Override
    public void onMouseReleased(BufferedImage image, DrawingMouseEvent me)
    {
            standardFloodFill(image,me.x,me.y);
    }

    @Override
    public void onMouseDragged(BufferedImage image, DrawingMouseEvent me)
    {

    }

    private void standardFloodFill(BufferedImage img, int x, int y)
    {
            Stack<Point> pending = new Stack<>();
            int width = img.getWidth();
            int height = img.getHeight();
            int replacedColor = img.getRGB(x, y);;
            int newColor = strokeColor.getRGB();

            if(newColor == replacedColor) //Si este caso se da, estamos ante una pérdida de tiempo... (Y una recursion infinita)
                    return;

            pending.push(new Point(x,y));

            while(pending.size()>0)
            {
                    Point p = pending.pop();

                    if(p.x<0 || p.x>=width || p.y<0 || p.y>=height)
                            continue;

                    int color = img.getRGB(p.x, p.y);
                    if(color == replacedColor)
                    {
                            img.setRGB(p.x,p.y,newColor);
                            pending.push(new Point(p.x+1,p.y));
                            pending.push(new Point(p.x-1,p.y));
                            pending.push(new Point(p.x,p.y+1));
                            pending.push(new Point(p.x,p.y-1));
                    }
            }
    }

    public String getDescription()
    {
            return "Fill";
    }

}
