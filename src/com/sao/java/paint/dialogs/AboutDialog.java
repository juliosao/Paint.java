package com.sao.java.paint.dialogs;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class AboutDialog extends JDialog{
	public AboutDialog(Window parent)
	{
		super(parent);
		setTitle("About paint.java");
		setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS));
		JLabel l = new JLabel("Paint.java");
		l.setAlignmentX(CENTER_ALIGNMENT);
		add(l);

		l = new JLabel("Version 0.1");
		l.setAlignmentX(CENTER_ALIGNMENT);
		add(l);

		l = new JLabel("Copyright (c) 2021 Julio A. García López");
		l.setAlignmentX(CENTER_ALIGNMENT);
		add(l);

		JButton b = new JButton("Ok");
		b.setAlignmentX(CENTER_ALIGNMENT);
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				AboutDialog.this.setVisible(false);
				AboutDialog.this.dispose();;
			}
		});
		add(b);
		pack();
	}
}
