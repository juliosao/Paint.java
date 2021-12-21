package com.sao.java.paint.filter;

import com.sao.java.paint.i18n.Translator;

public class Emboss extends ConvolutionFilter {
	public Emboss()
	{
		convolutionMatrix = new double[][]{
			{-2,-1,0},{-1,1,1},{0,1,2}
		};
	}

	@Override
	public String getDescription() {
		return Translator.m("Emboss");
	}
}
