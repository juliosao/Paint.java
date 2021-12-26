package com.sao.java.paint.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ColorBuilder extends JPanel
	implements Coloreable, ChangeListener  {

	ColorButton btnResult;
	JLabel lblR, lblG, lblB, lblA;
	JSlider sldR, sldG, sldB, sldA;

	public ColorBuilder()
	{
		super();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JPanel pnlComponents = new JPanel();
		pnlComponents.setBorder(BorderFactory.createTitledBorder("RGB"));
		pnlComponents.setLayout(new BoxLayout(pnlComponents,BoxLayout.X_AXIS));

		JPanel pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel("R");
		pnl.add(lbl);
		lblR = new JLabel("255");
		pnl.add(lblR);
		sldR = new JSlider(JSlider.VERTICAL, 0, 255, 0);
		sldR.addChangeListener(this);
		pnl.add(sldR);
		pnlComponents.add(pnl);

		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.Y_AXIS));
		lbl = new JLabel("G");
		pnl.add(lbl);
		lblG = new JLabel("255");
		pnl.add(lblG);
		sldG = new JSlider(JSlider.VERTICAL, 0, 255, 0);
		sldG.addChangeListener(this);
		pnl.add(sldG);
		pnlComponents.add(pnl);

		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.Y_AXIS));
		lbl = new JLabel("B");
		pnl.add(lbl);
		lblB = new JLabel("255");
		pnl.add(lblB);
		sldB = new JSlider(JSlider.VERTICAL, 0, 255, 0);
		sldB.addChangeListener(this);
		pnl.add(sldB);
		pnlComponents.add(pnl);

		pnl = new JPanel();
		pnl.setLayout(new BoxLayout(pnl,BoxLayout.Y_AXIS));
		lbl = new JLabel("A");
		pnl.add(lbl);
		lblA = new JLabel("255");
		pnl.add(lblA);
		sldA = new JSlider(JSlider.VERTICAL, 0, 255, 0);
		sldA.addChangeListener(this);
		pnl.add(sldA);
		pnlComponents.add(pnl);

		add(pnlComponents);

		JPanel pnlResult = new JPanel();
		pnlResult.setBorder(BorderFactory.createTitledBorder("Result"));
		btnResult = new ColorButton(Color.BLACK);
		btnResult.setPreferredSize(new Dimension(64,64));
		pnlResult.add(btnResult);
		add(pnlResult);
	}

	@Override
	public Color getStrokeColor() {
		return btnResult.getStrokeColor();
	}

	@Override
	public void setStrokeColor(Color c) {
		final int a = c.getAlpha();
		final int r = c.getRed();
		final int g = c.getGreen();
		final int b = c.getBlue();

		sldR.setValue(r);
		sldG.setValue(g);
		sldB.setValue(b);
		sldA.setValue(a);

		lblR.setText(String.format("%03d", r));
		lblG.setText(String.format("%03d", g));
		lblB.setText(String.format("%03d", b));
		lblA.setText(String.format("%03d", a));

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		btnResult.setStrokeColor(new Color(sldR.getValue(), sldG.getValue(), sldB.getValue(),sldA.getValue()));
	}

	@Override
	public Color getFillColor() {
		return null;
	}

	@Override
	public void setFillColor(Color c) {
		// Does nothing
	}

}
