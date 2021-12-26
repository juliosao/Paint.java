package com.sao.java.paint.tools;

import com.sao.java.paint.i18n.Translator;


public class Line
	extends ShapingTool
{   
    @Override
    protected void draw()
    {
        //graphics.setColor(TRANSPARENT);        
        clear();
        graphics.setColor(strokeColor);
		graphics.setStroke(stroke);
        graphics.drawLine(old.x, old.y, current.x, current.y);
    }

    public String getDescription()
    {
        return Translator.m("Line");
    }

}
