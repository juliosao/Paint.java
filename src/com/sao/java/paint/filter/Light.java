package com.sao.java.paint.filter;

import com.sao.java.paint.i18n.Translator;

public class Light extends ConvolutionFilter {
	public Light()
	{
		convolutionMatrix = new double[][]{
			{0,0,0},{0,1.1,0},{0,0,0}
		};
	}

	@Override
	public String getDescription() {
		return Translator.m("Illuminate");
	}
}
