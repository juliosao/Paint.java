package com.sao.java.paint.ui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Provides a combo for picking the shapes drawing mode
 */
public class ShapeModeCombo extends JComboBox<Integer>
	implements ListCellRenderer<Integer>
{
	/**
	 * Modes available with their icons
	 */
	private final ImageIcon modes[]= new ImageIcon[]{
		new ImageIcon(getClass().getResource("img/imgBorder.png")),
		new ImageIcon(getClass().getResource("img/imgFill.png")),
		new ImageIcon(getClass().getResource("img/imgBorderFill.png"))
	};

	/**
	 * Class constructor
	 */
	public ShapeModeCombo()
	{
		Dimension d = new Dimension(96,34);
		setPreferredSize(d);
		setRenderer(this);
		for( int i=0; i<modes.length; i++)
        {
            this.addItem(i);
        }
	}

	/**
	 * Gets the drawing mode
	 * @return The drawing mode
	 * Mode is a value from see DrawingPanel.BORDER, DrawingPanel.FILL or combination of both
	 */
	public int getValue()
	{
		return getSelectedIndex()+1;
	}

	/**
	 * Sets the drawing mode
	 * @param idx The new drawing mode
	 */
	public void setValue(int idx)
	{
		setSelectedIndex(idx-1);
	}

	/**
	 * Draws items on combo
	 */
	@Override
	public Component getListCellRendererComponent(JList<? extends Integer> list, Integer value, int index,
		boolean isSelected, boolean cellHasFocus) {
		JLabel result = new JLabel();

		int selectedIndex = value.intValue();
		if (isSelected)
		{
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		ImageIcon icon = modes[selectedIndex];

		result.setOpaque(true);
		result.setIcon( icon );
		result.setText("");
		return result;
	}
}
