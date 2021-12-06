/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.dialogs;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.ui.ColorPalettePanel;
import com.sao.java.paint.ui.ColorProvider;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Window;

/**
 *
 * @author julio
 */
public class ColorPickerDialog 
        extends javax.swing.JDialog
        implements ColorProvider
{
    ColorPalette palette;
    ColorPalettePanel cpp;
        
    /**
     * Creates new form ColorPicker
     */
    public ColorPickerDialog(Window parent, ColorPalette p) {
        super(parent);
        initComponents();       
        
        palette = p;
        cpp = new ColorPalettePanel(palette);
        pnlPalette.setMaximumSize(new Dimension(256,256));
        pnlPalette.add(cpp,BorderLayout.CENTER);
        cpp.setColorProvider(this);
        cpp.setVisible(true);
        setModal(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCancel1 = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        pnlResult = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        sldRed = new javax.swing.JSlider();
        jLabel2 = new javax.swing.JLabel();
        sldGreen = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        sldBlue = new javax.swing.JSlider();
        jLabel4 = new javax.swing.JLabel();
        sldAlpha = new javax.swing.JSlider();
        pnlPalette = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Palette Editor");

        btnCancel1.setText("Ok");
        btnCancel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancel1ActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        pnlResult.setBorder(javax.swing.BorderFactory.createTitledBorder("Selected"));

        javax.swing.GroupLayout pnlResultLayout = new javax.swing.GroupLayout(pnlResult);
        pnlResult.setLayout(pnlResultLayout);
        pnlResultLayout.setHorizontalGroup(
            pnlResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlResultLayout.setVerticalGroup(
            pnlResultLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 104, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Color Components\n"));

        jLabel1.setText("Red");

        sldRed.setMaximum(255);
        sldRed.setOrientation(javax.swing.JSlider.VERTICAL);
        sldRed.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldRedgenerateColor(evt);
            }
        });

        jLabel2.setText("Green");

        sldGreen.setMaximum(255);
        sldGreen.setOrientation(javax.swing.JSlider.VERTICAL);
        sldGreen.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldGreengenerateColor(evt);
            }
        });

        jLabel3.setText("Blue");

        sldBlue.setMaximum(255);
        sldBlue.setOrientation(javax.swing.JSlider.VERTICAL);
        sldBlue.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldBluegenerateColor(evt);
            }
        });

        jLabel4.setText("Alpha");

        sldAlpha.setMaximum(255);
        sldAlpha.setOrientation(javax.swing.JSlider.VERTICAL);
        sldAlpha.setValue(255);
        sldAlpha.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sldAlphagenerateColor(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sldRed, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(sldGreen, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sldBlue, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(sldAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {sldAlpha, sldBlue, sldGreen, sldRed});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sldRed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sldGreen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sldBlue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sldAlpha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pnlPalette.setBorder(javax.swing.BorderFactory.createTitledBorder("Palete\n"));
        pnlPalette.setMaximumSize(new java.awt.Dimension(512, 512));
        pnlPalette.setMinimumSize(new java.awt.Dimension(512, 512));
        pnlPalette.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlPalette, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlPalette, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(btnCancel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(pnlResult, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancel1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnCancel1ActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        setVisible(false);
    }//GEN-LAST:event_btnCancelActionPerformed

    private void sldRedgenerateColor(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldRedgenerateColor
        int red = sldRed.getValue();
        int green = sldGreen.getValue();
        int blue = sldBlue.getValue();
        int alpha = sldAlpha.getValue();

        pnlResult.setBackground(new Color(red,green,blue,alpha));
    }//GEN-LAST:event_sldRedgenerateColor

    private void sldGreengenerateColor(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldGreengenerateColor
        int red = sldRed.getValue();
        int green = sldGreen.getValue();
        int blue = sldBlue.getValue();
        int alpha = sldAlpha.getValue();

        pnlResult.setBackground(new Color(red,green,blue,alpha));
    }//GEN-LAST:event_sldGreengenerateColor

    private void sldBluegenerateColor(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldBluegenerateColor
        int red = sldRed.getValue();
        int green = sldGreen.getValue();
        int blue = sldBlue.getValue();
        int alpha = sldAlpha.getValue();

        pnlResult.setBackground(new Color(red,green,blue,alpha));
    }//GEN-LAST:event_sldBluegenerateColor

    private void sldAlphagenerateColor(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sldAlphagenerateColor
        int red = sldRed.getValue();
        int green = sldGreen.getValue();
        int blue = sldBlue.getValue();
        int alpha = sldAlpha.getValue();

        pnlResult.setBackground(new Color(red,green,blue,alpha));
    }//GEN-LAST:event_sldAlphagenerateColor

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlPalette;
    private javax.swing.JPanel pnlResult;
    private javax.swing.JSlider sldAlpha;
    private javax.swing.JSlider sldBlue;
    private javax.swing.JSlider sldGreen;
    private javax.swing.JSlider sldRed;
    // End of variables declaration//GEN-END:variables


    @Override
    public void setStrokeColor(Color c) {
        sldRed.setValue(c.getRed());
        sldGreen.setValue(c.getGreen());
        sldBlue.setValue(c.getBlue());
        sldAlpha.setValue(c.getAlpha());
        pnlResult.setBackground(c);
    }

    @Override
    public Color getStrokeColor() {
        return pnlResult.getBackground();
    }    

    @Override
    public void askForStrokeColor() {
        setVisible(true);
    }


    @Override
    public ColorPalette getColorPalette() {
        return palette;
    }
}
