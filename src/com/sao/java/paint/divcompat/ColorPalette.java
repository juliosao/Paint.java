/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.divcompat;

import java.io.InputStream;
import java.awt.Color;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.stream.ImageInputStream;

/**
 * @author julio
 *         Represents a Color Palete from original DIV GAMES STUDIO
 */
public class ColorPalette {
	public static final int NUMCOLORS = 256;
	public static final int MID = 8;
	public static final int THIRD = 8;

	public static final byte[] PALHDR = new byte[] { 0x70, 0x61, 0x6c, 0x1A, 0x0D, 0x0A, 0x00 };
	public static final byte[] FPGHDR = new byte[] { 0x66, 0x70, 0x67, 0x1A, 0x0D, 0x0A, 0x00 };
	public static final byte[] MAPHDR = new byte[] { 0x6d, 0x61, 0x70, 0x1A, 0x0D, 0x0A, 0x00 };
	private static final int PALSKIP = 0;
	private static final int FPGSKIP = 0;
	private static final int MAPSKIP = 40;
	Color colors[];
	int version;

	/**
	 * Creates a new palette with standard colors
	 */
	public ColorPalette() {
		colors = new Color[NUMCOLORS];
		setGamma(0, new ColorGamma(Color.black, Color.white));

		setGamma(ColorGamma.NUMCOLORS, new ColorGamma(Color.black, Color.red));
		setGamma(ColorGamma.NUMCOLORS * 13, new ColorGamma(Color.red, Color.white));

		setGamma(ColorGamma.NUMCOLORS * 2, new ColorGamma(Color.green, Color.black));
		setGamma(ColorGamma.NUMCOLORS * 14, new ColorGamma(Color.green, Color.white));

		setGamma(ColorGamma.NUMCOLORS * 3, new ColorGamma(Color.black, Color.blue));
		setGamma(ColorGamma.NUMCOLORS * 15, new ColorGamma(Color.blue, Color.white));

		setGamma(ColorGamma.NUMCOLORS * 4, new ColorGamma(Color.red, Color.yellow));
		setGamma(ColorGamma.NUMCOLORS * 5, new ColorGamma(Color.yellow, Color.green));

		setGamma(ColorGamma.NUMCOLORS * 6, new ColorGamma(Color.green, Color.cyan));
		setGamma(ColorGamma.NUMCOLORS * 7, new ColorGamma(Color.cyan, Color.blue));

		setGamma(ColorGamma.NUMCOLORS * 8, new ColorGamma(Color.blue, Color.magenta));
		setGamma(ColorGamma.NUMCOLORS * 9, new ColorGamma(Color.magenta, Color.red));

		setGamma(ColorGamma.NUMCOLORS * 10, new ColorGamma(Color.red, Color.cyan));
		setGamma(ColorGamma.NUMCOLORS * 11, new ColorGamma(Color.blue, Color.yellow));
		setGamma(ColorGamma.NUMCOLORS * 12, new ColorGamma(Color.green, Color.magenta));
	}

	/**
	 * Reads a palette from a stream
	 *
	 * @param sr Stream with the palete
	 * @throws IOException
	 *
	 *                     The palette in the stream must be a DIV GAMES STUDIO PAL
	 *                     MAP, or FPG file.
	 */
	public ColorPalette(InputStream sr)
			throws IOException {
		byte header[] = new byte[7];
		int skip;

		sr.read(header);
		if (Arrays.equals(PALHDR, header))
			skip = PALSKIP;
		else if (Arrays.equals(MAPHDR, header))
			skip = MAPSKIP;
		else if (!Arrays.equals(FPGHDR, header))
			skip = FPGSKIP;
		else
			throw new IOException("NOT a palette file");

		version = sr.read(); // read lee un unico byte pero lo devuelve como int
		sr.skip(skip);

		byte color[] = new byte[3];
		colors = new Color[NUMCOLORS];

		for (int i = 0; i < NUMCOLORS; i++) {
			sr.read(color);
			colors[i] = new Color(
					255 * (int) color[0] / 64,
					255 * (int) color[1] / 64,
					255 * (int) color[2] / 64);
		}
	}

	/**
	 * Reads a palette from a stream
	 *
	 * @param sr Stream with the palete
	 * @throws IOException
	 *
	 * The palette in the stream must be a DIV GAMES STUDIO PAL
	 * MAP, or FPG file, this constructor is indeed to read palette
	 * from a graphic format sequentially, preserving stream for image
	 * read. Use ColorPalette(InputStream sr) if you only want to
	 * read the palette data.
	 */
	public ColorPalette(ImageInputStream sr)
			throws IOException {

		byte color[] = new byte[3];
		colors = new Color[NUMCOLORS];

		for (int i = 0; i < NUMCOLORS; i++) {
			sr.read(color);
			colors[i] = new Color(
					255 * (int) color[0] / 64,
					255 * (int) color[1] / 64,
					255 * (int) color[2] / 64);
		}
	}


	/**
	 * Gets gamma from selected color
	 *
	 * @param c
	 * @return
	 */
	public ColorGamma getGamma(Color c) {
		int idx = 0;
		int bestDiff = Integer.MAX_VALUE;
		int diff;

		for (int i = 0; i < NUMCOLORS; i++) {
			diff = Math.abs(c.getRed() - colors[i].getRed()) +
					Math.abs(c.getGreen() - colors[i].getGreen()) +
					Math.abs(c.getBlue() - colors[i].getBlue());
			if (diff < bestDiff) {
				bestDiff = diff;
				idx = i;
			}
		}

		idx -= idx % ColorGamma.NUMCOLORS;
		return new ColorGamma(Arrays.copyOfRange(colors, idx, idx + ColorGamma.NUMCOLORS));
	}

	/**
	 * Returns gamam from color #idx
	 *
	 * @param idx Color for gamma
	 * @return The fidnded gamma
	 */
	public ColorGamma getGamma(int idx) {
		idx -= idx % ColorGamma.NUMCOLORS;
		return new ColorGamma(Arrays.copyOfRange(colors, idx, idx + ColorGamma.NUMCOLORS));
	}

	/**
	 * Puts a gamma into pal data
	 *
	 * @param idx Where to put the gamma colors
	 * @param cg  Gamma to put
	 */
	public void setGamma(int idx, ColorGamma cg) {
		for (int i = 0; idx + i < NUMCOLORS && i < ColorGamma.NUMCOLORS; i++) {
			colors[idx + i] = new Color(cg.getColor(i).getRGB());
		}
	}

	/**
	 * Returns color at index
	 *
	 * @param idx Index where the color is placed (0-255)
	 * @return Finded color
	 */
	public Color getColor(int idx) {
		return colors[idx];
	}

	/**
	 * Puts a color into the palette
	 *
	 * @param idx Index where to put the color (0-255)
	 * @param c   Color to put
	 */
	public void setColor(int idx, Color c) {
		colors[idx] = new Color(c.getRGB());
	}

	/**
	 * Returns color RGB components
	 * @param idx index of the color to get
	 * @return The RGB components of the color as int
	 */
	public int getRGB(int idx){
		return colors[idx].getRGB();
	}

}
