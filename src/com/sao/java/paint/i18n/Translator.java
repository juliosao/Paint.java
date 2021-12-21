package com.sao.java.paint.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Translator {
	static ResourceBundle rb;

	static
	{
		try
		{
			rb = ResourceBundle.getBundle("com.sao.java.paint.i18n.translations");
		}
		catch(MissingResourceException mre)
		{
			try
			{
				rb = ResourceBundle.getBundle("com.sao.java.paint.i18n.translations",new Locale("en","US"));
			}
			catch(Exception ex)
			{
				rb = null;
			}
		}
	}

	public static String m(String what)
	{
		try
		{
			return rb.getString(what);
		}
		catch(Exception ex)
		{
			return what;
		}
	}
}
