package com.sao.java.paint.tools;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImageSelection
	implements Transferable, ClipboardOwner

{
	private final static DataFlavor pngFlavor = new DataFlavor(ImageSelection.class,"image/png");
	private Image image;


	public ImageSelection(Image image)
	{
		this.image = image;
	}

	public BufferedImage getPngImage()
	{
		BufferedImage img =  new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return img;
	}

	public BufferedImage getJpegImage()
	{
		BufferedImage img =  new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_RGB);
		Graphics2D g = img.createGraphics();
		g.setBackground(Color.WHITE);
		g.clearRect(0, 0, img.getWidth(), img.getHeight());
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return img;
	}

	// Returns supported flavors
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return new DataFlavor[] { pngFlavor, DataFlavor.imageFlavor };
	}

	// Returns true if flavor is supported
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return pngFlavor.equals(flavor) || DataFlavor.imageFlavor.equals(flavor);
	}

	// Returns image
	@Override
	public Object getTransferData(DataFlavor flavor)
		throws UnsupportedFlavorException, IOException
	{
		if (DataFlavor.imageFlavor.equals(flavor))
		{
			return getJpegImage();
		}
		else if(pngFlavor.equals(flavor))
		{
			return getPngImage();
		}

		throw new UnsupportedFlavorException(flavor);
	}

	public void copyToClipboard()
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(this, this);
	}

	public static ImageSelection pasteFromClipboard()
	{
		Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if (transferable != null)
		{
			DataFlavor df;

			if(transferable.isDataFlavorSupported(pngFlavor))
			{
				df = pngFlavor;
			}
			if(transferable.isDataFlavorSupported(DataFlavor.imageFlavor))
			{
				df = DataFlavor.imageFlavor;
			}
			else
			{
				return null;
			}

			try
			{
				return new ImageSelection((Image) transferable.getTransferData(df));
			}
			catch (Exception e)
			{
				// handle this as desired
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public void lostOwnership(Clipboard clipboard, Transferable contents) {
		// Do nothing
	}
}