package com.sao.java.paint.ui;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class ShapeModeCombo extends JComboBox
	implements ListCellRenderer
{
	private final ImageIcon modes[]= new ImageIcon[]{
		new ImageIcon(getClass().getResource("img/imgBorder.png")),
		new ImageIcon(getClass().getResource("img/imgFill.png")),
		new ImageIcon(getClass().getResource("img/imgBorderFill.png"))
	};

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

	@Override
	public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {

		JLabel result = new JLabel();

		int selectedIndex = ((Integer)value).intValue();
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


	public int getValue()
	{
		return getSelectedIndex()+1;
	}

	public void setValue(int idx)
	{
		setSelectedIndex(idx-1);
	}
}
