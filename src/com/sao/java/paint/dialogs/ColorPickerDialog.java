/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.dialogs;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.ui.ColorBuilder;
import com.sao.java.paint.ui.ColorPalettePanel;
import com.sao.java.paint.ui.ColorProvider;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;

/**
 *
 * @author julio
 */
public class ColorPickerDialog 
        extends javax.swing.JDialog
        implements ColorProvider
{
    ColorPalette palette;
    ColorPalettePanel colorPalettePanel;
	Container container;
	ColorBuilder colorBuilder;
	Color currentColor;
	
        
    /**
     * Creates new form ColorPicker
     */
    public ColorPickerDialog(Window parent, ColorPalette p) {
        super(parent); 
        
        palette = p;
              
		container = getContentPane();
		setLayout(new BorderLayout());
        setModal(true);

		addMainPanel();
		addButtonPanel();
		pack();
    }

	@Override
	public Color getStrokeColor() {
		return currentColor;
	}

	@Override
	public void setStrokeColor(Color c) {
		currentColor = c;
		colorBuilder.setStrokeColor(c);		
	}

	@Override
	public void askForStrokeColor() {
		setVisible(true);		
	}

	@Override
	public ColorPalette getColorPalette() {
		return palette;
	}

	public void addButtonPanel()
	{
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JButton btnOk = new JButton("Ok");
		btnOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentColor = colorBuilder.getStrokeColor();
				ColorPickerDialog.this.setVisible(false);				
			}
		});
		jp.add(btnOk);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {				
				ColorPickerDialog.this.setVisible(false);				
			}
		});

		jp.add(btnCancel);
		add(jp,BorderLayout.SOUTH);
	}

	public void addMainPanel()
	{
		JPanel jp = new JPanel();		
		jp.setLayout(new FlowLayout());

		JPanel pnlBuilder = new JPanel();
		colorBuilder = new ColorBuilder();
		pnlBuilder.setBorder(BorderFactory.createTitledBorder("Color Builder"));
		pnlBuilder.add(colorBuilder);
		jp.add(pnlBuilder);
		
		JPanel pnlPalette = new JPanel();
		pnlPalette.setBorder(BorderFactory.createTitledBorder("Palette"));
		pnlPalette.setLayout(new BoxLayout(pnlPalette,BoxLayout.Y_AXIS));

		colorPalettePanel = new ColorPalettePanel(palette);
		colorPalettePanel.setColorProvider(this);
		pnlPalette.add(colorPalettePanel);
		jp.add(pnlPalette);

		JButton btnLoad = new JButton("Load palette...");
		btnLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}

		});
		pnlPalette.add(btnLoad);
		
		add(jp,BorderLayout.CENTER);
	}

    
}
