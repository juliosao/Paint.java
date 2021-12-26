package com.sao.java.paint;

import com.sao.java.paint.ui.DrawingPanel;
import com.sao.java.paint.ui.ShapeModeCombo;
import com.sao.java.paint.dialogs.AboutDialog;
import com.sao.java.paint.dialogs.CropDialog;
import com.sao.java.paint.dialogs.FilterDialog;
import com.sao.java.paint.dialogs.NewImageDialog;
import com.sao.java.paint.dialogs.ScaleDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.ItemEvent;

import javax.imageio.ImageIO;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JOptionPane;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.filter.ImageFilter;
import com.sao.java.paint.i18n.Translator;
import com.sao.java.paint.ui.ColorGammaBar;
import com.sao.java.paint.tools.*;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Toolkit;

/**
 * @author julio
 * This class is the main window iteself
 */
public class JPaintMainWindow extends JFrame
	implements WindowListener
{
	/**
	 * Allowed file types
	*/
	static final FileNameExtensionFilter filters[] = new FileNameExtensionFilter[]{
		new FileNameExtensionFilter(Translator.m("JPEG_Image"), "JPEG","JPG"),
		new FileNameExtensionFilter(Translator.m("PNG_Image"), "PNG"),
		new FileNameExtensionFilter(Translator.m("BMP_Image"), "BMP"),
		new FileNameExtensionFilter(Translator.m("MAP_Image"), "MAP"),
		new FileNameExtensionFilter(Translator.m("All_Images"), "JPEG","JPG","PNG","BMP","MAP"),
	};
	static int windowCount = 0;
	final static Dimension preferredDimension = new Dimension(1024,768);

	DrawingPanel drawingPanel;
	JToolBar toolbox;
	ColorGammaBar colorToolbar;
	JMenuBar menuBar;
	JMenu menuFile;
	JMenu menuEdit;
	JMenu menuImage;
	JMenu menuFilter;
	JSpinner zoomer;
	Container container;
	File currentFile = null;
	static final String TITLE = "Paint.java v0.2";

	Pencil pencil = new Pencil();
	Line line = new Line();
	Curve curve = new Curve();
	Rectangle rectangle = new Rectangle();
	Ellipse ellipse = new Ellipse();
	Fill fill = new Fill();
	Erase erase = new Erase();
	ColorPicker colorPicker = new ColorPicker();
	Smudge smudge = new Smudge();
	Brush brush = new Brush();
	RectangleSelection rectangleSelection = new RectangleSelection();
	Text text = new Text(this,rectangleSelection);
	Clone clone = new Clone();
	Blur blur = new Blur();
	Sharpen sharpen = new Sharpen();
	Light light = new Light();
	Dark dark = new Dark();
	Airbrush airbrush = new Airbrush();
	DrawingTool[] tools;
	JToggleButton[] buttons;
	JLabel lblCoords;

	/**
	 * Class constructor
	 */
	public JPaintMainWindow()
	{
		super();
		windowCount += 1;
		setTitle(TITLE);

		container = getContentPane();
		BorderLayout bl = new BorderLayout();
		setLayout(bl);

		createDrawingPanel();
		createBotomToolBar();
		createToolBox();
		createMenus();

		setPreferredSize(preferredDimension);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		pack();
	}

	/**
	 * Puts the drawing panel
	 */
	private void createDrawingPanel()
	{
		drawingPanel=new DrawingPanel();
		drawingPanel.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				lblCoords.setText(String.format(" Coords: %4d,%4d", e.getX(),e.getY()));

			}

			@Override
			public void mouseMoved(MouseEvent e) {
				lblCoords.setText(String.format(" Coords: %4d,%4d", e.getX(),e.getY()));
			}

		});
		container.add(drawingPanel,BorderLayout.CENTER);
	}

	/**
	 * Puts the painting tools toolbar
	 */
	private void createToolBox()
	{
		toolbox = new JToolBar("Toolbox");
		toolbox.setLayout(new GridLayout(0,1));
		toolbox.setOrientation(JToggleButton.VERTICAL);
		container.add(toolbox,BorderLayout.WEST);

		tools = new DrawingTool[]{
			pencil, brush, airbrush, erase, line, curve, rectangle, ellipse, text, fill,
			smudge, blur, sharpen, light, dark, clone,
			colorPicker, rectangleSelection
		};
		buttons = new JToggleButton[tools.length];

		int i = 0;
		for(DrawingTool t: tools)
		{
			buttons[i] = new JToggleButton(tools[i].getDescription());
			buttons[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					setDrawingTool(t);
				}
			});
			toolbox.add(buttons[i]);
			i++;
		}

		setDrawingTool(pencil);
	}

	/**
	 * Selects current tool
	 * @param t Tool to select
	 */
	public void setDrawingTool(DrawingTool t)
	{
		drawingPanel.setDrawingTool(t);
		for(int i=0; i<tools.length; i++)
		{
			buttons[i].setSelected( tools[i] == t );
		}
	}

	/**
	 * Puts the control toolbar
	 */
	private void createBotomToolBar()
	{
		JToolBar pnl = new JToolBar(Translator.m("Drawing_control"));
		container.add(pnl,BorderLayout.SOUTH);

		pnl.add(new JLabel(Translator.m("Color:")));

		colorToolbar = new ColorGammaBar(new ColorPalette());
		colorToolbar.setStrokeColor(drawingPanel.getStrokeColor());
		colorToolbar.setFillColor(drawingPanel.getFillColor());
		pnl.add(colorToolbar);//,BorderLayout.CENTER);
		colorToolbar.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getActionCommand() == ColorGammaBar.ACTIONSTROKE)
					drawingPanel.setStrokeColor(colorToolbar.getStrokeColor());
				else
					drawingPanel.setFillColor(colorToolbar.getFillColor());
			}
		});
		colorPicker.setColoreable(colorToolbar);

		pnl.add(new JToolBar.Separator());

		pnl.add(new JLabel(Translator.m("Zoom:")));
		JLabel lblZoom = new JLabel(" 100%");

		zoomer = new JSpinner(new SpinnerNumberModel(100, 5, 10000, 1));
		zoomer.setValue(100);
		zoomer.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				drawingPanel.setZoom((int)zoomer.getValue());
				lblZoom.setText(" "+zoomer.getValue()+"%");
			}
		});
		pnl.add(zoomer);//,BorderLayout.EAST);

		pnl.add(lblZoom);

		pnl.add(new JToolBar.Separator());

		pnl.add(new JLabel(Translator.m("Width:")));
		JLabel lblWidth = new JLabel("1 px");

		JSpinner jslWidth = new JSpinner(new SpinnerNumberModel(1, 1, 256, 1));
		jslWidth.addChangeListener(new javax.swing.event.ChangeListener() {
			@Override
			public void stateChanged(javax.swing.event.ChangeEvent evt) {
				lblWidth.setText(""+jslWidth.getValue()+" px");
				drawingPanel.setStroke(new BasicStroke((int)jslWidth.getValue(),BasicStroke.CAP_BUTT,BasicStroke.JOIN_MITER));
			}
		});
		jslWidth.setValue(5);

		pnl.add(jslWidth);
		pnl.add(lblWidth);//,BorderLayout.EAST);

		pnl.add(new JToolBar.Separator());

		pnl.add(new JLabel(Translator.m("Fill:")));
		ShapeModeCombo cmbFill = new ShapeModeCombo();
		cmbFill.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				drawingPanel.setShapeMode(cmbFill.getValue());
			}
		});
		pnl.add(cmbFill);

		pnl.add(new JToolBar.Separator());
		lblCoords = new JLabel("Coords:0,0");
		pnl.add(lblCoords);
	}

	/**
	 * Sets image to edit
	 * @param bi
	 */
	public void setImage(BufferedImage bi)
	{
		drawingPanel.setImage(bi);
	}

	/**
	 * Create all menus
	 */
	private void createMenus()
	{
		menuBar = new JMenuBar();
		createFileMenu();
		createEditMenu();
		createImageMenu();
		createFilterMenu();
		createHelpMenu();
		setJMenuBar(menuBar);
	}

	/**
	 * Creates file menus
	 */
	private void createFileMenu()
	{
		menuFile = new JMenu("File");

		JMenuItem mnu = new JMenuItem(Translator.m("New"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				NewImageDialog nid = new NewImageDialog(JPaintMainWindow.this);
				nid.setVisible(true);
				if(nid.getResult() == NewImageDialog.OK)
				{
					JPaintMainWindow jpmw = new JPaintMainWindow();
					jpmw.setImage(nid.getImage());
					jpmw.setVisible(true);
				}
			}
		});

		menuFile.add(mnu);
		mnu = new JMenuItem(Translator.m("Open"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent evt) {
				openPicture();
			}
		});
		menuFile.add(mnu);

		mnu = new JMenuItem(Translator.m("Save"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				savePicture();
			}
		});
		menuFile.add(mnu);

		mnu = new JMenuItem(Translator.m("Save_As"));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				savePictureAs();
			}
		});
		menuFile.add(mnu);
		menuFile.add(new JSeparator());

		mnu = new JMenuItem(Translator.m("Exit_paint"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4 ,ActionEvent.ALT_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				dispatchEvent(new WindowEvent(JPaintMainWindow.this, WindowEvent.WINDOW_CLOSING));
			}
		});

		menuFile.add(mnu);
		menuBar.add(menuFile);
	}

	/**
	 * Creates edit menus
	 */
	private void createEditMenu()
	{
		menuEdit = new JMenu(Translator.m("Edit"));

		JMenuItem mnu = new JMenuItem(Translator.m("Undo"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				drawingPanel.undo();
			}
		});
		menuEdit.add(mnu);

		mnu = new JMenuItem(Translator.m("Redo"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				drawingPanel.redo();
			}
		});
		menuEdit.add(mnu);

		menuEdit.add(new JSeparator());

		mnu = new JMenuItem(Translator.m("Cut"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				BufferedImage img = rectangleSelection.cut(drawingPanel);
				ImageSelection imgSel = new ImageSelection(img);
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(imgSel, null);
			}
		});
		menuEdit.add(mnu);

		mnu = new JMenuItem(Translator.m("Copy"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				BufferedImage img = rectangleSelection.copy(drawingPanel);
				ImageSelection imgSel = new ImageSelection(img);
				imgSel.copyToClipboard();
			}
		});
		menuEdit.add(mnu);

		mnu = new JMenuItem(Translator.m("Paste"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				ImageSelection imgSel = ImageSelection.pasteFromClipboard();
				if(imgSel != null)
				{
					setDrawingTool(rectangleSelection);
					rectangleSelection.paste(drawingPanel,imgSel.getPngImage(),false);
				}
			}
		});
		menuEdit.add(mnu);

		mnu = new JMenuItem(Translator.m("Paste_as_new"));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				ImageSelection imgSel = ImageSelection.pasteFromClipboard();
				if(imgSel != null)
				{
					JPaintMainWindow jpmw = new JPaintMainWindow();
					jpmw.setImage(imgSel.getPngImage());
					jpmw.setVisible(true);
				}
			}
		});
		menuEdit.add(mnu);

		menuEdit.add(new JSeparator());

		mnu = new JMenuItem(Translator.m("Select_all"));
		mnu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,ActionEvent.CTRL_MASK));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				setDrawingTool(rectangleSelection);
				rectangleSelection.selectAll(drawingPanel);
			}
		});
		menuEdit.add(mnu);

		mnu = new JMenuItem(Translator.m("Select_none"));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				setDrawingTool(rectangleSelection);
				rectangleSelection.selectNone(drawingPanel);
			}
		});
		menuEdit.add(mnu);

		JCheckBoxMenuItem ts = new JCheckBoxMenuItem(Translator.m("Transparent_selection"));
		ts.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e)
			{
				rectangleSelection.setTransparentSelection(drawingPanel, ts.getState());
			}
		});
		menuEdit.add(ts);

		menuBar.add(menuEdit);
	}

	/**
	 * Creates image menus
	 */
	void createImageMenu()
	{
		menuImage = new JMenu(Translator.m("Image"));
		ActionListener al = new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(e.getActionCommand())
				{
					case "Resize":
						ScaleDialog scd = new ScaleDialog(JPaintMainWindow.this, drawingPanel);
						scd.setVisible(true);
						break;
					case "Crop":
						CropDialog cd = new CropDialog(JPaintMainWindow.this, drawingPanel);
						cd.setVisible(true);
						break;
				}
			}
		};

		JMenuItem mnu = new JMenuItem(Translator.m("Resize"));
		mnu.setActionCommand("Resize");
		mnu.addActionListener(al);
		menuImage.add(mnu);

		mnu = new JMenuItem(Translator.m("Crop/Expand"));
		mnu.setActionCommand("Crop");
		mnu.addActionListener(al);
		menuImage.add(mnu);

		menuBar.add(menuImage);
	}

	/**
	 * Creates filter menu
	 */
	void createFilterMenu()
	{
		menuFilter = new JMenu(Translator.m("Filter"));
		ImageFilter filters[] = new ImageFilter[]{
			new com.sao.java.paint.filter.Blur(),
			new com.sao.java.paint.filter.Sharpen(),
			new com.sao.java.paint.filter.Emboss(),
			new com.sao.java.paint.filter.Highrelief(),
			new com.sao.java.paint.filter.EdgeDetect(),
			new com.sao.java.paint.filter.Light(),
			new com.sao.java.paint.filter.Dark(),
			new com.sao.java.paint.filter.Invert(),
			new com.sao.java.paint.filter.DropColor(),
			new com.sao.java.paint.filter.GrayScale(),
		};

		for(ImageFilter f: filters)
		{
			JMenuItem mnu = new JMenuItem(f.getDescription()+"...");
			mnu.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e)
				{
					FilterDialog fd = new FilterDialog(JPaintMainWindow.this,drawingPanel,f);
					fd.setVisible(true);
				}
			});
			menuFilter.add(mnu);
		}

		menuBar.add(menuFilter);
	}

	/**
	 * Creates help menu
	 */
	void createHelpMenu()
	{
		menuEdit = new JMenu(Translator.m("Help"));
		JMenuItem mnu = new JMenuItem(Translator.m("About"));
		mnu.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				AboutDialog ad = new AboutDialog(JPaintMainWindow.this);
				ad.setVisible(true);
			}
		});
		menuEdit.add(mnu);
		menuBar.add(menuEdit);
	}


	@Override
	public void windowOpened(WindowEvent we) {
		//Not used
	}

	/**
	 * Occurs whe user is clossing the window
	 */
	@Override
	public void windowClosing(WindowEvent we) {
		switch (JOptionPane.showConfirmDialog(JPaintMainWindow.this, Translator.m("save_picture?"),
		Translator.m("Exit_paint"), JOptionPane.YES_NO_CANCEL_OPTION)) {
			case JOptionPane.CANCEL_OPTION:
				return;
			case JOptionPane.YES_OPTION:
				savePicture();
			default:
				setVisible(false);
				windowCount--;
				if(windowCount == 0)
					System.exit(0);
				break;
		}
	}

	/**
	 * Ocuurs when window is closed
	 */
	@Override
	public void windowClosed(WindowEvent we) {
		dispose();
	}

	@Override
	public void windowIconified(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeiconified(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowActivated(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public void windowDeactivated(WindowEvent we) {
		//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	/**
	 * Shows open dile dialog and loads image from selected file
	 */
	public void openPicture()
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			for(FileNameExtensionFilter filter: filters)
			{
				fileChooser.setFileFilter(filter);
			}

			int result = fileChooser.showOpenDialog(JPaintMainWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File selectedFile = fileChooser.getSelectedFile();
				BufferedImage bi = ImageIO.read(selectedFile);
				drawingPanel.setImage(bi);
				currentFile = selectedFile;
				this.setTitle(TITLE + " - " + currentFile.getName());
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(JPaintMainWindow.this, Translator.m("Cannot_open_file"));
		}
	}

	/**
	 * Saves current picture to the desired file
	 * @param f File where to save the current picture
	 */
	public void savePicture(File f)
	{
		try
		{
			int index = f.getName().lastIndexOf('.');
			String mode = "png";
			if (index > 0) {
				mode = f.getName().substring(index + 1).toLowerCase();
			}

			BufferedImage saved = drawingPanel.getImage();
			if(mode == "png")
				ImageIO.write(saved, mode ,f );
			else
			{
				// Transparecny are not allowed
				final int w = saved.getWidth();
				final int h = saved.getHeight();
				final Color c = drawingPanel.getFillColor();

				BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = img.createGraphics();
				g.setBackground( new Color(c.getRed(),c.getGreen(),c.getBlue()) );
				g.clearRect(0, 0, w, h);
				g.drawImage(saved, 0, 0, null);
				g.dispose();
				
				ImageIO.write(img, mode ,f );
			}

			currentFile = f;
			this.setTitle(TITLE + " - " + currentFile.getName());
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(JPaintMainWindow.this, Translator.m("Cannot_save_file"));
		}
	}

	/**
	 * Saves current picture to file. Asking for file if needed
	 */
	public void savePicture()
	{
		if(currentFile != null)
		{
			savePicture(currentFile);
		}
		else
		{
			savePictureAs();
		}
	}

	/**
	 * Shows save as Dialog and saves image in the selected file
	 */
	public void savePictureAs()
	{
		try
		{
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
			fileChooser.setDialogTitle(Translator.m("Save_as"));
			fileChooser.setApproveButtonText(Translator.m("Save"));
			for(FileNameExtensionFilter filter: filters)
			{
				fileChooser.setFileFilter(filter);
			}

			fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
			int result = fileChooser.showSaveDialog(JPaintMainWindow.this);
			if (result == JFileChooser.APPROVE_OPTION) {
				File f = fileChooser.getSelectedFile();

				int index = f.getName().lastIndexOf('.');
				if (index < 0) {
					FileNameExtensionFilter ff = (FileNameExtensionFilter)fileChooser.getFileFilter();
					f = new File(f.getAbsolutePath()+"."+ff.getExtensions()[0] );
				}

				savePicture(f);
			}
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(JPaintMainWindow.this, Translator.m("Cannot_save_file"));
		}
	}

}
