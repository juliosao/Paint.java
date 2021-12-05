package com.sao.java.paint.divcompat;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;

public class DivMapReader extends ImageReader {
{
    
}

@Override
public int getNumImages(boolean allowSearch) throws IOException {
    // TODO Auto-generated method stub
    return 1;
}

@Override
public int getWidth(int imageIndex) throws IOException {
    // TODO Auto-generated method stub
    return 0;
}

@Override
public int getHeight(int imageIndex) throws IOException {
    // TODO Auto-generated method stub
    return 0;
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
    // TODO Auto-generated method stub
    return null;
}
