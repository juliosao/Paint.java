/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author julio
 */
public class JPaint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JPaintMainWindow jpmw = new JPaintMainWindow();
		BufferedImage image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)image.createGraphics();
		g.setPaint(Color.WHITE);
		g.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
		g.dispose();
		jpmw.setImage(image);					
        jpmw.setVisible(true);
    }

}
