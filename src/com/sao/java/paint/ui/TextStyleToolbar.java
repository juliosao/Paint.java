package com.sao.java.paint.ui;

import java.awt.GraphicsEnvironment;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;

public class TextStyleToolbar
	extends JToolBar
	implements ItemListener, ChangeListener
{
	/* Font face management */
	public static final String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	protected String fontName = fonts[0];
	private JComboBox<String> cmbFont;

	/** Font size management */
	JSpinner spnSize;
	int fontSize=64;

	/* Align management */
	private final JToggleButton btnLeft;
	private final JToggleButton btnRight;
	private final JToggleButton btnCenter;


	public static final int ALIGN_LEFT = 0;
	public static final int ALIGN_CENTER = 1;
	public static final int ALIGN_RIGHT = 2;

	private static final AdaptableIcon LEFT_ICON = new AdaptableIcon(TextStyleToolbar.class.getResource("img/left.png"),32,32);
	private static final AdaptableIcon CENTER_ICON = new AdaptableIcon(TextStyleToolbar.class.getResource("img/center.png"),32,32);
	private static final AdaptableIcon RIGHT_ICON = new AdaptableIcon(TextStyleToolbar.class.getResource("img/right.png"),32,32);

	private String command = "";
	private boolean updating = false;
	private int align = ALIGN_LEFT;


	/* Style management */
	private final JToggleButton btnBold;
	private final JToggleButton btnItalic;

	private boolean bold = false;
	private boolean italic = false;

	private static final AdaptableIcon BOLD_ICON = new AdaptableIcon(TextStyleToolbar.class.getResource("img/bold.png"),32,32);;
	private static final AdaptableIcon ITALIC_ICON = new AdaptableIcon(TextStyleToolbar.class.getResource("img/italic.png"),32,32);;

	/** Action listener management */
	private HashSet<ActionListener> aListeners;


	/**
	 * Control for select align method
	 */
	public TextStyleToolbar()
	{
		Dimension d = new Dimension(32,32);

		// Font face
		add(new JLabel("Font:"));
		cmbFont = new JComboBox<>(fonts);
		cmbFont.addItemListener(this);
		fontName = (String)cmbFont.getSelectedItem();
		add(cmbFont);
		add(new JToolBar.Separator());

		// Font size
		add(new JLabel(" Size:"));
		spnSize = new JSpinner(new SpinnerNumberModel(fontSize, 5, 255, 1));
		spnSize.addChangeListener(this);
		add(spnSize);
		add(new JToolBar.Separator());

		// Font style
		btnBold = new JToggleButton();
		btnBold.setIcon(BOLD_ICON);
		btnBold.addItemListener(this);
		btnBold.setPreferredSize(d);
		add(btnBold);

		btnItalic = new JToggleButton();
		btnItalic.setIcon(ITALIC_ICON);
		btnItalic.addItemListener(this);
		btnItalic.setPreferredSize(d);
		add(btnItalic);
		add(new JToolBar.Separator());

		// Text Align
		btnLeft = new JToggleButton();
		btnLeft.setIcon(LEFT_ICON);
		btnLeft.setSelected(true);
		btnLeft.setPreferredSize(d);
		btnLeft.addItemListener(this);
		add(btnLeft);

		btnCenter = new JToggleButton();
		btnCenter.setIcon(CENTER_ICON);
		btnCenter.addItemListener(this);
		btnCenter.setPreferredSize(d);
		add(btnCenter);

		btnRight = new JToggleButton();
		btnRight.setIcon(RIGHT_ICON);
		btnRight.addItemListener(this);
		btnRight.setPreferredSize(d);
		add(btnRight);

		// Listners
		aListeners = new HashSet<>();
	}

	/**
	 * Occurs when user picks a align
	 */
	@Override
	public void itemStateChanged(ItemEvent e)
	{
		if(updating)
			return;

		updating = true;

		Object src = e.getSource();
		if(src == btnBold)
		{
			bold = btnBold.isSelected();
		}
		else if(src == btnItalic)
		{
			italic = btnItalic.isSelected();
		}
		else if(src == btnLeft)
		{
			btnLeft.setSelected(true);
			btnCenter.setSelected(false);
			btnRight.setSelected(false);
			align = ALIGN_LEFT;
		}
		else if(src == btnCenter)
		{
			btnLeft.setSelected(false);
			btnCenter.setSelected(true);
			btnRight.setSelected(false);
			align = ALIGN_CENTER;
		}
		else if(src == btnRight)
		{
			btnLeft.setSelected(false);
			btnCenter.setSelected(false);
			btnRight.setSelected(true);
			align = ALIGN_RIGHT;
		}
		else if(src == cmbFont)
		{
			fontName = (String)cmbFont.getSelectedItem();
		}

		ActionEvent ae = new ActionEvent(this,ActionEvent.ACTION_PERFORMED,command);
		for(ActionListener l: aListeners)
		{
			l.actionPerformed(ae);
		}

		updating = false;
	}

	@Override
	public void stateChanged(javax.swing.event.ChangeEvent evt) {
		if(updating)
			return;

		updating = true;

		fontSize = (int)spnSize.getValue();

		ActionEvent ae = new ActionEvent(this,ActionEvent.ACTION_PERFORMED,command);
		for(ActionListener l: aListeners)
		{
			l.actionPerformed(ae);
		}

		updating = false;

	}

	/**
	 * Sets the action command to send to the listeners
	 * @param c A string with the action to send
	 */
	public void setActionCommand(String c)
	{
		command = c;
	}

	/**
	 * Gets the action command to send to the listeners
	 * @return A string with the action command to send
	 */
	public String getActionCommand()
	{
		return command;
	}

	/**
	 * Adds a listener to the listeners set
	 * @param l A new listener to add
	 */
	public void addActionListener(ActionListener l)
	{
		aListeners.add(l);
	}

	/**
	 * Remove a listener from the listeners set
	 * @param l The listener to remove
	 */
	public void removeActionListener(ActionListener l)
	{
		aListeners.remove(l);
	}

	/**
	 * Gets the current align for the alignSelector
	 * @return
	 */
	public int getAlign()
	{
		return align;
	}

	/**
	 * Sets the align of the control
	 * @param a The new alingn must be one of ALIGN_LEFT, ALIGN_CENTER or ALIGN_RIGHT
	 */
	public void setAlign(int a)
	{
		align = a;
		updating = true;

		btnLeft.setSelected(a == ALIGN_LEFT);
		btnCenter.setSelected(a == ALIGN_CENTER);
		btnRight.setSelected(a == ALIGN_RIGHT);

		updating = false;
	}

	/**
	 * Returns current font name
	 * @return The selected font name
	 */
	public String getFontName()
	{
		return fontName;
	}

	/**
	 * Gets current font style
	 * @return The selectec font style
	 */
	public int getStyle()
	{
		int style = Font.PLAIN;

		if(bold)
			style |= Font.BOLD;

		if(italic)
			style |= Font.ITALIC;

		return style;
	}

	/**
	 * Returns the current font size
	 * @return The current font size
	 */
	public int getFontSize()
	{
		return fontSize;
	}
}
