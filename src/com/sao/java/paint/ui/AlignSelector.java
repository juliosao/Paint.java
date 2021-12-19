package com.sao.java.paint.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class AlignSelector
	extends JPanel
	implements ItemListener
{
	/**
	 * Align to the left
	 */
	public static final int ALIGN_LEFT = 0;

	/**
	 * Align to the center
	 */
	public static final int ALIGN_CENTER = 1;

	/**
	 * Align to the right
	 */
	public static final int ALIGN_RIGHT = 2;

	private String command = "";
	private boolean updating = false;
	private int align;
	private JToggleButton[] selectors;
	private HashSet<ActionListener> aListeners;

	/**
	 * Control for select align method
	 */
	public AlignSelector()
	{
		Dimension d = new Dimension(32,32);
		setLayout(new FlowLayout());

		JToggleButton btnLeft = new JToggleButton();
		btnLeft.setIcon(new ImageIcon(getClass().getResource("img/left.png")) );
		btnLeft.setPreferredSize(d);
		btnLeft.addItemListener(this);
		add(btnLeft);

		JToggleButton btnCenter = new JToggleButton();
		btnCenter.setIcon(new ImageIcon(getClass().getResource("img/center.png")));
		btnCenter.setPreferredSize(d);
		btnCenter.addItemListener(this);
		add(btnCenter);

		JToggleButton btnRight = new JToggleButton();
		btnRight.setIcon(new ImageIcon(getClass().getResource("img/right.png")));
		btnRight.setPreferredSize(d);
		btnRight.addItemListener(this);
		add(btnRight);

		aListeners = new HashSet<>();
		selectors = new JToggleButton[]{btnLeft, btnCenter, btnRight};
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

		JToggleButton source = (JToggleButton)e.getItem();
		for(int i=0; i<selectors.length; i++)
		{
			selectors[i].setSelected(selectors[i]==source);
			align = i;
		}

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
	public String getActionCOmmand()
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

		for(int i=0; i<selectors.length; i++)
		{
			selectors[i].setSelected(i==a);
			align = i;
		}

		updating = false;
	}
}
