package com.sao.java.paint.dialogs;

import java.awt.Rectangle;
import java.awt.Window;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.BorderFactory;

import com.sao.java.paint.filter.ImageFilter;
import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.tools.RectangleSelection;
import com.sao.java.paint.ui.DrawingPanel;

public class FilterDialog
	extends JDialog
{
	DrawingPanel drawingPanel;
	ImageFilter imageFilter;
	JCheckBox chkRed, chkGreen, chkBlue, chkAlpha;

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
		p.setBorder(BorderFactory.createTitledBorder(Translator.m("Channels")));

		JLabel lbl = new JLabel(Translator.m("Red"));
		p.add(lbl);
		chkRed = new JCheckBox();
		chkRed.setSelected(true);
		p.add(chkRed);

		lbl = new JLabel(Translator.m("Green"));
		p.add(lbl);
		chkGreen = new JCheckBox();
		chkGreen.setSelected(true);
		p.add(chkGreen);

		lbl = new JLabel(Translator.m("Blue"));
		p.add(lbl);
		chkBlue = new JCheckBox();
		chkBlue.setSelected(true);
		p.add(chkBlue);

		lbl = new JLabel(Translator.m("Alpha"));
		p.add(lbl);
		chkAlpha = new JCheckBox();
		chkAlpha.setSelected(true);
		p.add(chkAlpha);

		add(p,BorderLayout.CENTER);
	}

	public void addBottomBar()
	{
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		JButton b = new JButton(Translator.m("Apply"));
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int mode = 0;
				if(chkAlpha.isSelected())mode|=ImageFilter.MODE_A;
				if(chkRed.isSelected())mode|=ImageFilter.MODE_R;
				if(chkGreen.isSelected())mode|=ImageFilter.MODE_G;
				if(chkBlue.isSelected())mode|=ImageFilter.MODE_B;
				DrawingTool dt = drawingPanel.getDrawingTool();
				BufferedImage bi = drawingPanel.getImage();
				if(mode != 0)
				{
					if(dt instanceof RectangleSelection)
					{
						RectangleSelection rs = (RectangleSelection)dt;
						Rectangle r = rs.getBounds();
						imageFilter.applyTo(drawingPanel, mode, r.x, r.y, r.x+r.width, r.y+r.height);
					}
					else
						imageFilter.applyTo(drawingPanel, mode, 0, 0, bi.getWidth(), bi.getHeight());
				}
			}
		});
		p.add(b);

		b = new JButton(Translator.m("Exit"));
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
