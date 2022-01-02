/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sao.java.paint.dialogs;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.ColorBuilder;
import com.sao.java.paint.ui.ColorPalettePanel;
import com.sao.java.paint.ui.Coloreable;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.Color;
import java.awt.Window;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author julio
 */
public class ColorPickerDialog
        extends javax.swing.JDialog
        implements Coloreable
{
    ColorPalette palette;
    ColorPalettePanel colorPalettePanel;
	Container container;
	ColorBuilder colorBuilder;
	Color currentColor;

	static FileNameExtensionFilter filters[] = new FileNameExtensionFilter[]{
        new FileNameExtensionFilter("DIVPaletteFiles", "PAL")
    };

    /**
     * Creates new form ColorPicker
     */
    public ColorPickerDialog(Window parent, ColorPalette p) {
        super(parent);

        palette = p;

		container = getContentPane();
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
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

	public Color askForStrokeColor(Color defColor)
	{
		setStrokeColor(defColor);
		setVisible(true);
		return currentColor;
	}

	public ColorPalette getColorPalette() {
		return palette;
	}

	public void addButtonPanel()
	{
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());
		JButton btnOk = new JButton(Translator.m("Ok"));
		btnOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				currentColor = colorBuilder.getStrokeColor();
				ColorPickerDialog.this.setVisible(false);
			}
		});
		jp.add(btnOk);

		JButton btnCancel = new JButton(Translator.m("Cancel"));
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				ColorPickerDialog.this.setVisible(false);
			}
		});

		jp.add(btnCancel);
		add(jp,BorderLayout.SOUTH);
	}

	public <JPaintMainWindow> void addMainPanel()
	{
		JPanel jp = new JPanel();
		jp.setLayout(new FlowLayout());

		JPanel pnlBuilder = new JPanel();
		colorBuilder = new ColorBuilder();
		pnlBuilder.setBorder(BorderFactory.createTitledBorder(Translator.m("ColorBuilder")));
		pnlBuilder.add(colorBuilder);
		jp.add(pnlBuilder);

		JPanel pnlPalette = new JPanel();
		pnlPalette.setBorder(BorderFactory.createTitledBorder(Translator.m("Palette")));
		pnlPalette.setLayout(new BoxLayout(pnlPalette,BoxLayout.Y_AXIS));

		colorPalettePanel = new ColorPalettePanel(palette);
		colorPalettePanel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setStrokeColor(colorPalettePanel.getStrokeColor());
			}
		});
		pnlPalette.add(colorPalettePanel);
		jp.add(pnlPalette);

		JButton btnLoad = new JButton(Translator.m("LoadPalette"));
		btnLoad.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try
				{
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
					for(FileNameExtensionFilter filter: filters)
					{
						fileChooser.setFileFilter(filter);
					}

					int result = fileChooser.showOpenDialog(ColorPickerDialog.this);
					if (result == JFileChooser.APPROVE_OPTION) {
						File selectedFile = fileChooser.getSelectedFile();
						ColorPalette p = new ColorPalette(new FileInputStream(selectedFile) );
						palette = p;
						colorPalettePanel.setColorPalette(p);

					}
				}
				catch(Exception ex)
				{
					JOptionPane.showMessageDialog(ColorPickerDialog.this, Translator.m("Cannot_open_file"));
				}
			}

		});
		pnlPalette.add(btnLoad);

		add(jp,BorderLayout.CENTER);
	}

	@Override
	public Color getFillColor() {
		// Does nothing
		return null;
	}

	@Override
	public void setFillColor(Color c) {
		// Does nothing

	}

}
