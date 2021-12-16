package com.sao.java.paint.tools;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class ImageSelection
	implements Transferable
{
	private Image image;

	public ImageSelection(Image image)
	{
		this.image = image;
	}

	public BufferedImage getImage()
	{
		BufferedImage img =  new BufferedImage(image.getWidth(null),image.getHeight(null),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = img.createGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		return img;
	}

	// Returns supported flavors
	@Override
	public DataFlavor[] getTransferDataFlavors()
	{
		return new DataFlavor[] { DataFlavor.imageFlavor };
	}

	// Returns true if flavor is supported
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor)
	{
		return DataFlavor.imageFlavor.equals(flavor);
	}

	// Returns image
	@Override
	public Object getTransferData(DataFlavor flavor)
		throws UnsupportedFlavorException, IOException
	{
		if (!DataFlavor.imageFlavor.equals(flavor))
		{
			throw new UnsupportedFlavorException(flavor);
		}
		return image;
	}

	public void copyToClipboard()
	{
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(this, null);
	}

	public static ImageSelection pasteFromClipboard()
	{
		Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
		if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor))
		{
			try
			{
				return new ImageSelection((Image) transferable.getTransferData(DataFlavor.imageFlavor));
			}
			catch (Exception e)
			{
				// handle this as desired
				e.printStackTrace();
			}
		}
		return null;
	}

}