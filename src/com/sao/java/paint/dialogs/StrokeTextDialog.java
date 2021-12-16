package com.sao.java.paint.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Graphics2D;


import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sao.java.paint.tools.RectangleSelection;
import com.sao.java.paint.ui.DrawingPanel;


public class StrokeTextDialog
	extends JDialog
	implements FocusListener
{
	RectangleSelection selectionTool;
	DrawingPanel drawingPanel;
	JTextArea textArea;
	int fontSize = 34;
	boolean italic = false;
	boolean bold = false;
	boolean drawBorder = true;
	boolean drawFill = true;
	String fontName;
	Color strokeColor = Color.BLACK;

	public StrokeTextDialog(Window parent, RectangleSelection selection, DrawingPanel dp)
	{
		super(parent);
		setTitle("Insert text");
		setLayout(new BorderLayout());
		addToolbar();
		addRightBar();
		textArea = new JTextArea("New text");
		textArea.setPreferredSize(new Dimension(320,200));
		textArea.getDocument().addDocumentListener( new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				updateImage();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				updateImage();

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				updateImage();
			}

		});

		add(textArea,BorderLayout.CENTER);
		selectionTool = selection;
		drawingPanel = dp;
		pack();
	}

	@Override
	public void setVisible(boolean v)
	{
		super.setVisible(v);
		if(v==true)
		{
			updateImage();
		}
	}

	private void addToolbar()
	{
		JToolBar jt = new JToolBar();
		jt.setFloatable(false);
		add(jt,BorderLayout.NORTH);

		String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		JLabel jl = new JLabel("Font:") ;
		JComboBox<String> jcb = new JComboBox<>(fonts);
		jcb.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				fontName = (String)jcb.getSelectedItem();
				updateImage();

			}
		});
		fontName = (String)jcb.getSelectedItem();

		jt.add(jcb);

		jl = new JLabel(" Size:");
		jt.add(jl);

		JSpinner spn = new JSpinner(new SpinnerNumberModel(32, 5, 100, 1));
		spn.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				fontSize = (int)spn.getValue();
				updateImage();
			}
		});
		jt.add(spn);

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

		JToggleButton btnBorder = new JToggleButton("Border");
		btnBorder.setFont(new Font("Serif", Font.PLAIN, 14));
		btnBorder.setSelected(true);
		btnBorder.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				drawBorder = btnBorder.isSelected();
				updateImage();
			}
		});
		jt.add(btnBorder);

		JToggleButton btnFill = new JToggleButton("Fill");
		btnFill.setFont(new Font("Serif", Font.PLAIN, 14));
		btnFill.setSelected(true);
		btnFill.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				drawFill = btnFill.isSelected();
				updateImage();
			}
		});
		jt.add(btnFill);

	}

	void addRightBar()
	{
		JPanel jp = new JPanel();
		//jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
		jp.setLayout(new FlowLayout());
		add(jp,BorderLayout.SOUTH);

		JButton btnApply = new JButton("Apply");
		btnApply.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				drawingPanel.setDrawingTool(selectionTool); //This makes the changes to be dumped to the image
			}
		});
		jp.add(btnApply);

		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				selectionTool.selectNone(drawingPanel);
				setVisible(false);
			}
		});
		jp.add(btnExit);


	}


	void updateImage()
	{

		if(drawingPanel.getDrawingTool() != selectionTool)
		{
			drawingPanel.setDrawingTool(selectionTool);
			selectionTool.selectNone(drawingPanel);
		}

		String s = textArea.getText();
		if(s.length() == 0)
		{
			s=" ";
		}

		int lines = 1;
		for(int i=0; i<s.length(); i++)
		{
			if(s.charAt(i) == '\n')
				lines++;
		}

		int style = bold ? Font.BOLD : Font.PLAIN;
		if(italic) style |= Font.ITALIC;

		Font f = new Font(fontName, style, fontSize);
		FontMetrics fm = drawingPanel.getGraphics().getFontMetrics(f);
		GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), s);
    	Shape shape = v.getOutline();

		Rectangle bounds = shape.getBounds();
		int height = fm.getHeight() * lines;
		int width = fm.stringWidth(s);

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g = img.createGraphics();
		g.translate(
            (width - bounds.width) / 2 - bounds.x,
            (height - bounds.height) / 2 - bounds.y
    	);

		g.setBackground(new Color(255,255,255,0));
		g.clearRect(0, 0, width, height);

		if(drawFill)
		{
			g.setColor(drawingPanel.getFillColor());
			g.fill(shape);
		}

		if(drawBorder)
		{
			g.setColor(drawingPanel.getStrokeColor());
			g.setStroke(drawingPanel.getStroke());
			g.draw(shape);
			g.dispose();
		}

		selectionTool.paste(drawingPanel, img);
	}

	@Override
	public void focusGained(FocusEvent e) {
		updateImage();

	}

	@Override
	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub

	}

}
