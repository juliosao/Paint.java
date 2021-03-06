package com.sao.java.paint.dialogs;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javax.swing.SpinnerNumberModel;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.ColorButton;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import java.awt.Window;

/**
 * Dialog to generate new images
 */
public class NewImageDialog
	extends javax.swing.JDialog
{
	public static final int CANCEL = 0;
	public static final int OK = 1;

	int result = CANCEL;
	BufferedImage img;
	JSpinner txtWidth;
	JSpinner txtHeight;
	ColorButton btnColor;

	/**
	 * Class constructor
	 * @param parent Parent window where the dialog is in
	 */
	public NewImageDialog(Window parent)
	{
		super(parent);
		setTitle("New image");
		setModal(true);
		setLocationRelativeTo(parent);
		final ColorPalette palette = new ColorPalette();
		final ColorPickerDialog cp = new ColorPickerDialog(this, palette);

		setLayout(new BorderLayout());
		JPanel jp = new JPanel();
		jp.setBorder(javax.swing.BorderFactory.createTitledBorder(Translator.m("Dimensions")));
		add(jp,BorderLayout.CENTER);
		jp.setLayout(new GridLayout(3,2));
		jp.add(new JLabel(Translator.m("NewWidth")));
		txtWidth = new JSpinner(new SpinnerNumberModel(800, 1, 10240, 1));
		jp.add(txtWidth);
		jp.add(new JLabel(Translator.m("NewHeight")));
		txtHeight = new JSpinner(new SpinnerNumberModel(600, 1, 10240, 1));
		jp.add(txtHeight);
		jp.add(new JLabel(Translator.m("Color")));
		btnColor = new ColorButton(Color.WHITE);
		btnColor.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				cp.setStrokeColor(btnColor.getStrokeColor());
				btnColor.setStrokeColor((cp.askForStrokeColor(Color.WHITE)));
			}
		});
		jp.add(btnColor);

		JPanel rstp = new JPanel();
		rstp.setLayout(new FlowLayout());
		add(rstp,BorderLayout.SOUTH);

		JButton btnOK = new JButton();
		btnOK.setText(Translator.m("Ok"));
		btnOK.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
                img = new BufferedImage((int)txtWidth.getValue(), (int)txtHeight.getValue(), BufferedImage.TYPE_INT_ARGB);
				Graphics2D g = (Graphics2D)img.createGraphics();
				g.setPaint(btnColor.getStrokeColor());
				g.fillRect ( 0, 0, img.getWidth(), img.getHeight() );
				g.dispose();
				result = OK;
				setVisible(false);
            }
		});
		rstp.add(btnOK);

		JButton btnCancel = new JButton();
		btnCancel.setText(Translator.m("Cancel"));
		btnCancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				result = CANCEL;
				setVisible(false);
			}
		});
		rstp.add(btnCancel);

		pack();

	}

	/**
	 * Sets generated image dimensions
	 * @param width width of the new image
	 * @param height height of the new image
	 */
	public void setDimensions(int width, int height)
	{
		txtWidth.setValue(width);
		txtHeight.setValue(height);
	}

	/**
	 * Sets the background color of the new image
	 * @param c The background color of the new image
	 */
	public void setColor(Color c)
	{
		btnColor.setStrokeColor(c);
	}

	/**
	 * Gets the new image generated
	 * @return Gets the new image generated
	 */
	public BufferedImage getImage()
	{
		return img;
	}

	/**
	 * Gets the result action of the dialog
	 * @return OK if user pressed "Ok" button, else CANCEL
	 */
	public int getResult()
	{
		return result;
	}
}
