package com.sao.java.paint.tools;


import java.awt.Stroke;
import java.awt.BasicStroke;

public class BasicStrokeProvider 
	implements StrokeProvider
{
	float width = 1;

	public void setWidth(float w){
		width = w;
	}

	public float getWidth(){
		return width;
	}

	@Override
	public Stroke getStroke() {
		return new BasicStroke(width);
	}

}
