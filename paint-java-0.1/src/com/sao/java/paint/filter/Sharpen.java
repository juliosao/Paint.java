package com.sao.java.paint.filter;

public class Sharpen extends ConvolutionFilter {
	public Sharpen()
	{
		final double adjust = 1.0/3.0;
		convolutionMatrix = new double[][]{
			{0,-adjust,0},{-adjust,7*adjust,-adjust},{-adjust,0,-adjust}
		};
	}

	@Override
	public String getDescription() {
		return "Sharpen";
	}
}
