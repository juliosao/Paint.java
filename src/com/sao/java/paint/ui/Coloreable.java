package com.sao.java.paint.ui;

import java.awt.Color;

public interface Coloreable {
    /**
    * Gets actual stroke color
    * @return The actual stroke color
    */
   Color getStrokeColor();

   /**
    * Sets actual stroke color
    * @return The actual stroke color
    */
   void setStrokeColor(Color c);

   /**
    * Gets actual fill color
    * @return The actual fill color
    */
    Color getFillColor();

    /**
     * Sets actual fill color
     */
    void setFillColor(Color c);

}
