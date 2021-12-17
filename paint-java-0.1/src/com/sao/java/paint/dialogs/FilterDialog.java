package com.sao.java.paint.dialogs;

import java.awt.Window;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import com.sao.java.paint.filter.ImageFilter;
import com.sao.java.paint.ui.DrawingPanel;

public class FilterDialog
	extends JDialog
{
	DrawingPanel drawingPanel;
	ImageFilter imageFilter;
	JCheckBox chkRed, chkGreen, chkBlue;

	public FilterDialog(Window parent, DrawingPanel dp, ImageFilter f)
	{
		super(parent);
		setTitle(f.getDescription());
		drawingPanel = dp;
		imageFilter = f;
		setupUI();
	}

	public void setupUI()
	{
		setLayout(new BorderLayout());
		addMainControls();
		addBottomBar();
		pack();
	}

	public void addMainControls()
	{
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(0,2));
		p.setBorder(BorderFactory.createTitledBorder("Channels"));

		JLabel lbl = new JLabel("Red");
		p.add(lbl);
		chkRed = new JCheckBox();
		chkRed.setSelected(true);
		p.add(chkRed);

		lbl = new JLabel("Green");
		p.add(lbl);
		chkGreen = new JCheckBox();
		chkGreen.setSelected(true);
		p.add(chkGreen);

		lbl = new JLabel("Blue");
		p.add(lbl);
		chkBlue = new JCheckBox();
		chkBlue.setSelected(true);
		p.add(chkBlue);

		add(p,BorderLayout.CENTER);
	}

	public void addBottomBar()
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		JButton b = new JButton("Apply");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int mode = 0;
				if(chkRed.isSelected())mode|=ImageFilter.MODE_R;
				if(chkGreen.isSelected())mode|=ImageFilter.MODE_G;
				if(chkBlue.isSelected())mode|=ImageFilter.MODE_B;
				if(mode != 0)imageFilter.applyTo(drawingPanel, mode);
			}
		});
		p.add(b);

		b = new JButton("Exit");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		p.add(b);

		add(p,BorderLayout.SOUTH);
	}
}
