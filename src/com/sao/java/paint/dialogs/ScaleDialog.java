package com.sao.java.paint.dialogs;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

import com.sao.java.paint.ui.DrawingPanel;

/**
 * Dialog to change the image size
 */
public class ScaleDialog
	extends JDialog
	implements ChangeListener
{
	boolean computing = false;
	double oldWidth = 0;
	double oldHeight = 0;
	JSpinner newWidth;
	JSpinner newHeight;
	JCheckBox keepProportions;
	DrawingPanel drawingPanel;

	/**
	 * Class cronstuctor
	 * @param parent Window where the dialog is centered in
	 * @param dp Drawing pannel where to apply changes
	 */
	public ScaleDialog(Window parent, DrawingPanel dp)
	{
		drawingPanel = dp;
		setLocationRelativeTo(parent);
		setup();
	}

	/**
	 * Set-ups ui
	 */
	protected final void setup()
	{
		setTitle("Change image size");
		setLayout(new BorderLayout());
		setModal(true);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(0,2,5,5));
		mainPanel.setBorder(BorderFactory.createTitledBorder("New size"));
		add(mainPanel,BorderLayout.CENTER);

		mainPanel.add(new JLabel("New Width:"));
		newWidth = new JSpinner(new SpinnerNumberModel(800, 1, 10240, 1));
		newWidth.addChangeListener(this);
		mainPanel.add(newWidth);

		mainPanel.add(new JLabel("New Height:"));
		newHeight = new JSpinner(new SpinnerNumberModel(800, 1, 10240, 1));
		newHeight.addChangeListener(this);
		mainPanel.add(newHeight);

		mainPanel.add(new JLabel("Keep proportions"));
		keepProportions = new JCheckBox();
		mainPanel.add(keepProportions);

		JPanel actions = new JPanel();
		actions.setLayout(new FlowLayout());
		add(actions,BorderLayout.SOUTH);

		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				int width = (int)newWidth.getValue();
				int height = (int)newHeight.getValue();
				BufferedImage old = drawingPanel.getImage();
				BufferedImage img = new BufferedImage(width,height,old.getType());
				Graphics2D g = img.createGraphics();
				g.drawImage(old, 0, 0, width, height, null);
				g.dispose();
				drawingPanel.setImage(img);
				setVisible(false);
			}
		});
		actions.add(btnOk);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		actions.add(btnReset);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		actions.add(btnCancel);

		reset();
		keepProportions.setSelected(true);
		pack();
	}

	/**
	 * Ressets dimensions to the current image
	 */
	final void reset()
	{
		BufferedImage img = drawingPanel.getImage();
		newWidth.setValue(img.getWidth());
		newHeight.setValue(img.getHeight());
		oldWidth = img.getWidth();
		oldHeight = img.getHeight();
	}

	/**
	 * Occurs when the user changes a value
	 */
	@Override
	public void stateChanged(javax.swing.event.ChangeEvent evt) {

		if(computing)
			return;

		computing = true;

		if(evt.getSource() == newWidth)
		{
			if(keepProportions.isSelected())
			{
				double tmp = oldHeight/oldWidth*(int)newWidth.getValue();
				if(tmp<1)
					tmp=1;
				newHeight.setValue((int)tmp);
			}
			else
				oldWidth = (int)newWidth.getValue();
		}
		else
		{
			if(keepProportions.isSelected())
			{
				double tmp = oldWidth/oldHeight*(int)newHeight.getValue();
				if(tmp<1)
					tmp=1;
				newHeight.setValue((int)tmp);
			}
			else
				oldHeight= (int)newHeight.getValue();
		}

		computing = false;

	}
}
