package com.sao.java.paint.filter;

import com.sao.java.paint.i18n.Translator;

public class Blur extends ConvolutionFilter {
	public Blur()
	{
		final double adjust = 1.0/9.0;
		convolutionMatrix = new double[][]{
			{adjust,adjust,adjust},{adjust,adjust,adjust},{adjust,adjust,adjust}
		};
	}

	@Override
	public String getDescription() {
		return Translator.m("Blur");
	}


}
