package com.sao.java.paint.dialogs;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Window;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowFocusListener;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Graphics2D;

import java.awt.geom.AffineTransform;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.sao.java.paint.tools.RectangleSelection;
import com.sao.java.paint.ui.TextStyleToolbar;
import com.sao.java.paint.ui.DrawingPanel;

/**
 * Dialog for create text shapes
 */
public class StrokeTextDialog
	extends JDialog
	implements WindowFocusListener, ActionListener
{

	TextStyleToolbar styleToolbar;
	RectangleSelection selectionTool;
	DrawingPanel drawingPanel;
	JTextArea textArea;


	/**
	 * Class constructor
	 * @param parent Parent window for the dialog
	 * @param selection A selecction tool used to put seleccion on drawing panel
	 * @param dp A drawing panel where to put the results
	 */
	public StrokeTextDialog(Window parent, RectangleSelection selection, DrawingPanel dp)
	{
		super(parent);
		setTitle("Insert text");
		setLayout(new BorderLayout());
		setLocationRelativeTo(parent);
		addWindowFocusListener(this);
		styleToolbar = new TextStyleToolbar();
		styleToolbar.addActionListener(this);
		add(styleToolbar,BorderLayout.NORTH);
		addBottomBar();
		textArea = new JTextArea("New\ntext");
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


	/**
	 * Puts controls to apply results or cancel text insertion
	 */
	void addBottomBar()
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

	/**
	 * Updates text image on destination drawing panel
	 */
	void updateImage()
	{
		boolean drawBorder = (drawingPanel.getShapeMode() & DrawingPanel.BORDER) != 0;
		boolean drawFill = (drawingPanel.getShapeMode() & DrawingPanel.FILL) != 0;

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

		//Composes font
		Font f = new Font(styleToolbar.getFontName(), styleToolbar.getStyle(), styleToolbar.getFontSize());

		//Calculates text dimensions
		String[] lines = s.split("\n");

		FontMetrics fm = drawingPanel.getGraphics().getFontMetrics(f);
		int width = 0;
		for(String l:lines)
		{
			final int w = fm.stringWidth(l);
			if(w>width)
				width=w;
		}

		// Draws lines in selection
		int height = fm.getHeight();
		int margin = ( drawingPanel.getShapeMode() & DrawingPanel.BORDER) != 0 ? (int)(drawingPanel.getStroke().getLineWidth() + 3) : 3;

		BufferedImage img = new BufferedImage(width, height*lines.length+margin, BufferedImage.TYPE_INT_ARGB );
		Graphics2D g = img.createGraphics();
		g.setBackground(new Color(255,255,255,0));
		g.clearRect(0, 0, width, height*lines.length+margin);

		final AffineTransform at = g.getTransform();

		for(int i=0; i<lines.length; i++)
		{
			final GlyphVector v = f.createGlyphVector(getFontMetrics(f).getFontRenderContext(), lines[i]);
			final Shape shape = v.getOutline();
			final Rectangle bounds = shape.getBounds();

			g.setTransform(at);

			int x;
			switch(styleToolbar.getAlign())
			{
				case TextStyleToolbar.ALIGN_CENTER:
					x = (width - bounds.width) / 2 - bounds.x;
					break;
				case TextStyleToolbar.ALIGN_RIGHT:
					x=width-bounds.width;
					break;
				case TextStyleToolbar.ALIGN_LEFT:
				default:
					x = -bounds.x;
					break;
			}
			final int y = (height/2+margin)+height*i;
			g.translate(x,y);
			//System.out.println(String.format("Render: '%s' x=%d, y=%d h=%d w=%d",lines[i],x,y,height,bounds.width));

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
			}
		}
		g.dispose();

		selectionTool.paste(drawingPanel, img, true);
	}

	/**
	 * Occurs when the user returns from other window
	 */
	@Override
	public void windowGainedFocus(WindowEvent e) {
		updateImage();

	}

	@Override
	public void windowLostFocus(WindowEvent e) {
		// Does nothing

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		updateImage();
	}

}
