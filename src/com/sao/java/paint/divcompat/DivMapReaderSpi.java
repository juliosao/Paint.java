package com.sao.java.paint.divcompat;

import java.io.IOException;
import java.util.Locale;
import javax.imageio.ImageReader;
import javax.imageio.spi.ImageReaderSpi;
import javax.imageio.stream.ImageInputStream;

public class DivMapReaderSpi extends ImageReaderSpi {
    static final String vendorName = "SAO";
    static final String version = "0.1";
    static final String readerClassName =
            "com.sao.java.paint.divcompat.MapReader";
    static final String[] names = { "DIV Map file (8bit)" };
    static final String[] suffixes = { "map" };
    static final String[] MIMETypes = {
            "image/x-div-map" };
    static final String[] writerSpiNames = {
        "com.sao.java.paint.divcompat.MapWriter" };

    // Metadata formats, more information below
    static final boolean supportsStandardStreamMetadataFormat = false;
    static final String nativeStreamMetadataFormatName = null;
    static final String nativeStreamMetadataFormatClassName = null;
    static final String[] extraStreamMetadataFormatNames = null;
    static final String[] extraStreamMetadataFormatClassNames = null;
    static final boolean supportsStandardImageMetadataFormat = false;
    static final String nativeImageMetadataFormatName = null;
    static final String nativeImageMetadataFormatClassName = null;
    static final String[] extraImageMetadataFormatNames = null;
    static final String[] extraImageMetadataFormatClassNames = null;
    static final byte[] MAPHDR = new byte[]{0x6d, 0x61, 0x70, 0x1A, 0x0D, 0x0A, 0x00};

    @Override
    public boolean canDecodeInput(Object source) throws IOException {
        if (!(source instanceof ImageInputStream)) {
            return false;
        }

        ImageInputStream stream = (ImageInputStream)source;
        byte[] b = new byte[7];
        try {
                stream.mark();
                stream.readFully(b);
                stream.reset();
        } catch (IOException e) {
                return false;
        }

        for(int i=0; i<MAPHDR.length; i++)
        {
            if(MAPHDR[i] != b[i])
                return false;
        }

        return true;
    }

    @Override
    public ImageReader createReaderInstance(Object extension) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getDescription(Locale locale) {
        return "DIV MAP File (8bit)";
    }
    
}
