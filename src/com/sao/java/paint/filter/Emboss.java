package com.sao.java.paint.filter;

public class Emboss extends ConvolutionFilter {
	public Emboss()
	{
		convolutionMatrix = new double[][]{
			{-2,-1,0},{-1,1,1},{0,1,2}
		};
	}

	@Override
	public String getDescription() {
		return "Emboss";
	}
}
