package com.sao.java.paint.dialogs;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Graphics2D;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;

import com.sao.java.paint.tools.RectangleSelection;
import com.sao.java.paint.ui.DrawingPanel;


public class StrokeTextDialog
	extends JDialog
{
	RectangleSelection selectionTool;
	DrawingPanel drawingPanel;
	JTextArea textArea;
	int fontSize = 10;
	boolean italic = false;
	boolean bold = false;
	String fontName;
	Color strokeColor = Color.BLACK;

	public StrokeTextDialog(Window parent, RectangleSelection selection, DrawingPanel dp)
	{
		super(parent);
		setTitle("Insert text");
		setLayout(new BorderLayout());
		addToolbar();
		textArea = new JTextArea("New text");
		textArea.setPreferredSize(new Dimension(320,200));
		add(textArea,BorderLayout.CENTER);
		selectionTool = selection;
		drawingPanel = dp;
		pack();
	}

	private void addToolbar()
	{
		JToolBar jt = new JToolBar();
		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		JLabel jl = new JLabel("Font:") ;
		JComboBox<String> jcb = new JComboBox<>(fonts);
		jcb.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				fontName = (String)jcb.getSelectedItem();
				updateImage();
			}
		});
		fontName = (String)jcb.getSelectedItem();

		jt.add(jcb);

		JToggleButton btnB = new JToggleButton(" B ");
		btnB.setFont(new Font("Serif", Font.BOLD, 14));
		btnB.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				bold = btnB.isSelected();
				updateImage();
			}
		});
		jt.add(btnB);

		JToggleButton btnI = new JToggleButton(" I ");
		btnI.setFont(new Font("Serif", Font.ITALIC, 14));
		btnI.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				italic = btnI.isSelected();
				updateImage();
			}
		});
		jt.add(btnI);

		jl = new JLabel(" Size:");
		jt.add(jl);

		JSpinner spn = new JSpinner(new SpinnerNumberModel(10, 5, 100, 1));
		spn.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				fontSize = (int)spn.getValue();
				updateImage();
			}
		});
		jt.add(spn);
		add(jt,BorderLayout.NORTH);

	}

	void updateImage()
	{
		int style = bold ? Font.BOLD : Font.PLAIN;
		if(italic) style |= Font.ITALIC;
		String s = textArea.getText();

		int lines = 1;
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i) == '\n')
				lines++;
		}

		Font f = new Font(fontName, style, fontSize);
		FontMetrics fm = getGraphics().getFontMetrics(f);
		int height = fm.getHeight() * lines;
		int width = fm.stringWidth(s);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = img.createGraphics();
		g.setBackground(new Color(255,255,255,0));
		g.clearRect(0, 0, width, height);
		g.setColor(Color.BLACK);
		g.drawString(s, 0, 0);
		selectionTool.onSelected(drawingPanel);
		selectionTool.paste(drawingPanel, img);
	}
}
