package com.sao.java.paint.filter;

import com.sao.java.paint.i18n.Translator;

public class Dark extends ConvolutionFilter {
	public Dark()
	{
		convolutionMatrix = new double[][]{
			{0,0,0},{0,0.9,0},{0,0,0}
		};
	}

	@Override
	public String getDescription() {
		return Translator.m("Darken");
	}
}
