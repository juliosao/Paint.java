package com.sao.java.paint.divcompat;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;
import javax.naming.OperationNotSupportedException;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.spi.ImageReaderSpi;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;


public class DivMapReader extends ImageReader {
    private static final int alpha = 255 << 24;
    public static final byte[] FPGHDR = new byte[] { 0x66, 0x70, 0x67, 0x1A, 0x0D, 0x0A, 0x00 };
	public static final byte[] MAPHDR = new byte[] { 0x6d, 0x61, 0x70, 0x1A, 0x0D, 0x0A, 0x00 };    
    ColorPalette p;
    BufferedImage image;

    protected DivMapReader(ImageReaderSpi originatingProvider, Object iis) throws IOException
    {
        super(originatingProvider);
    }

    public void setInput(final Object source, final boolean seekForwardOnly,final boolean ignoreMetadata)
    {
        byte version;
        int code;
        short height;
        short width;
        String fileName;
        String description;
        ColorPalette palette;
        int controlPointsNumber;
        LinkedList<Point> controlPoints = new LinkedList<>();
        final ImageInputStream iis;
        byte tmp[] = new byte[16];
        byte header[] = new byte[7];
        try
        {            
            System.out.println("Me piden cargar una imagen!:"+source);
            if (source instanceof ImageInputStream) {
                iis = (ImageInputStream)source;
            }
            else if(source instanceof File){
                iis = new FileImageInputStream((File)source);
            }
            else
            {
                System.out.println("Origen no soportado");
                throw new OperationNotSupportedException("Origen no soportado");
            }

            iis.setByteOrder(ByteOrder.LITTLE_ENDIAN);

            iis.read(header);            
            if (!Arrays.equals(MAPHDR, header) && !Arrays.equals(FPGHDR, header))
                throw new IOException("NOT a MAP file");

            version = iis.readByte();
            System.out.println("Version:"+version);
            width = iis.readShort();
            System.out.println("Width:"+width);
            height = iis.readShort();
            System.out.println("Height:"+height);            
            code = iis.readInt();
            System.out.println("Code:"+code);
            iis.read(tmp);
            fileName = tmp[0]==0 ? "" : new String(tmp,StandardCharsets.US_ASCII);
            System.out.println("FileName:"+fileName);
            iis.read(tmp);
            description = tmp[0]==0 ? "" : new String(tmp,StandardCharsets.US_ASCII);
            System.out.println("Description:"+description);
            palette = new ColorPalette(iis);
            
            iis.skipBytes(576); //paint.java does not support this at this time

            controlPointsNumber = iis.readShort();
            System.out.println("Control Points:"+controlPointsNumber);
            for(int i=0; i<controlPointsNumber; i++)
            {
                controlPoints.add(new Point(iis.readShort(),iis.readShort()));
            }

            image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for(int j=0; j<height; j++)
            {
                for(int i=0; i<width; i++)
                {                
                    image.setRGB(i,j, palette.getRGB(iis.read())|alpha );
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }



    @Override
    public int getNumImages(boolean allowSearch) throws IOException
    {
        return 1;
    }

    @Override
    public int getWidth(int imageIndex) throws IOException {
        return image.getWidth();
    }

    @Override
    public int getHeight(int imageIndex) throws IOException {
        return image.getHeight();
    }

    @Override
    public Iterator<ImageTypeSpecifier> getImageTypes(int imageIndex) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIOMetadata getStreamMetadata() throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IIOMetadata getImageMetadata(int imageIndex) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BufferedImage read(int imageIndex, ImageReadParam param) throws IOException {
        return image;
    }
}
