package com.sao.java.paint.filter;

public class EdgeDetect extends ConvolutionFilter {
	public EdgeDetect()
	{
		convolutionMatrix = new double[][]{
			{0,1,0},{1,-4,1},{0,1,0}
		};
	}

	@Override
	public String getDescription() {
		return "Edge detect";
	}
}
