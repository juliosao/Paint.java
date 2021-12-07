package com.sao.java.paint;

import com.sao.java.paint.ui.DrawingPanel;
import com.sao.java.paint.dialogs.ColorPickerDialog;
import com.sao.java.paint.dialogs.NewImageDialog;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.ui.ToolBox;
import com.sao.java.paint.ui.ToolBoxListener;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.tools.StrokeProvider;
import com.sao.java.paint.tools.BasicStrokeProvider;
import com.sao.java.paint.ui.ColorGammaBar;
import com.sao.java.paint.ui.ColorProvider;
import com.sao.java.paint.ui.Coloreable;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author julio
 */
public class JPaintMainWindow extends JFrame
    implements WindowListener, Coloreable, ToolBoxListener
{
    static FileNameExtensionFilter filters[] = new FileNameExtensionFilter[]{ 
        new FileNameExtensionFilter("All Images", "JPEG","JPG","PNG","BMP"),
        new FileNameExtensionFilter("JPEG Image", "JPEG","JPG"),
        new FileNameExtensionFilter("PNG Image", "PNG"),
        new FileNameExtensionFilter("BMP Image", "BMP"),
    };
	static int windowCount = 0;

    DrawingPanel drawingPanel;
    ToolBox toolbox;
    ColorPickerDialog colorPicker = null;
    ColorGammaBar colorToolbar;
    JMenuBar menuBar;
    JMenu menuFile;
    JSlider zoomer;
    DrawingTool drawingTool;
    Container container;
    ColorProvider colorProvider;
	StrokeProvider strokeProvider = new BasicStrokeProvider();
    File currentFile = null;
    static final String TITLE = "Paint.java v0.1";


    public JPaintMainWindow()
    {
        super();
		windowCount += 1;
        setTitle(TITLE);

        container = getContentPane();
        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        
        colorPicker = new ColorPickerDialog(this, new ColorPalette());                
        
        createDrawingPanel();
        createBotomToolBar();
        createToolBox();
        createMenus();
        
	    setSize(800,600);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }
    
    private void createDrawingPanel()
    {
        drawingPanel=new DrawingPanel();
        drawingPanel.setSize(800,600);    
        //drawingPanel.setImage(createImg(640,480));
        container.add(drawingPanel,BorderLayout.CENTER);
    }

    private void createToolBox()
    {
        toolbox = new ToolBox(this);
        toolbox.setColorProvider(colorToolbar);
		toolbox.setStrokeProvider(strokeProvider);
	    container.add(toolbox,BorderLayout.WEST);
    }
    
    private void createBotomToolBar()
    {
        JPanel pnl = new JPanel();
        pnl.setLayout(new FlowLayout());
        
        colorToolbar = new ColorGammaBar(colorPicker);
        colorToolbar.setColorProvider(colorPicker);        
        pnl.add(colorToolbar);//,BorderLayout.CENTER);
        

		JLabel jlbl = new JLabel("Zoom:");
		pnl.add(jlbl);

		JLabel lblZoom = new JLabel("100 %");

        zoomer = new JSlider(10,1000);
        zoomer.setValue(100);
        zoomer.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                drawingPanel.setZoom(zoomer.getValue());
				lblZoom.setText(""+zoomer.getValue()+" %");
            }
        });
        pnl.add(zoomer);//,BorderLayout.EAST);
		
		pnl.add(lblZoom);
        
        container.add(pnl,BorderLayout.SOUTH);

		jlbl = new JLabel(" - Width:");
		pnl.add(jlbl);
		JLabel lblWidth = new JLabel("1 px");

        JSlider jslWidth = new JSlider(1,100);
        jslWidth.setValue(1);
        jslWidth.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
				if(strokeProvider instanceof BasicStrokeProvider)
				{
                	((BasicStrokeProvider)strokeProvider).setWidth(jslWidth.getValue());
					lblWidth.setText(""+jslWidth.getValue()+" px");
				}
            }
        });

		pnl.add(jslWidth);
        pnl.add(lblWidth);//,BorderLayout.EAST);
    }

	public void setImage(BufferedImage bi)
	{
		drawingPanel.setImage(bi);		
	}
        
    private void createMenus()
    {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        JMenuItem mnu = new JMenuItem("New...");
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
        mnu = new JMenuItem("Open...");
        mnu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent evt) {
                openPicture();
            }
        });
        menuFile.add(mnu);
        
        mnu = new JMenuItem("Save...");
        mnu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                savePicture();
            }
        });
        menuFile.add(mnu);
        
        mnu = new JMenuItem("Save As...");
        mnu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                savePictureAs();
            }
        });
        menuFile.add(mnu);
        mnu = new JMenuItem("Exit");
        mnu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {
                dispatchEvent(new WindowEvent(JPaintMainWindow.this, WindowEvent.WINDOW_CLOSING));
            }
        });
        
        menuFile.add(mnu);

        setJMenuBar(menuBar);
    }

    @Override
    public void windowOpened(WindowEvent we) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent we) {
        switch (JOptionPane.showConfirmDialog(JPaintMainWindow.this, "Do you want to save picture?", 
        "Exit paint.java", JOptionPane.YES_NO_CANCEL_OPTION)) {
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

    @Override
    public void onToolSelected(DrawingTool t) {
        drawingTool = t;
        drawingPanel.setDrawingTool(t);
    }

    @Override
    public void setColorProvider(ColorProvider cp) {
        colorProvider = cp;
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
            JOptionPane.showMessageDialog(JPaintMainWindow.this, "Cannot open file");
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
            else
            {
                f = new File(f.getAbsolutePath()+"."+mode);
            }
            ImageIO.write(drawingPanel.getImage(), mode ,f );
            currentFile = f;
            this.setTitle(TITLE + " - " + currentFile.getName());
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(JPaintMainWindow.this, "Cannot save file");
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
            fileChooser.setDialogTitle("Save image as...");
            fileChooser.setApproveButtonText("Save");
            for(FileNameExtensionFilter filter: filters)
            {
                fileChooser.setFileFilter(filter);
            }
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showSaveDialog(JPaintMainWindow.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                savePicture(fileChooser.getSelectedFile());                
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(JPaintMainWindow.this, "Cannot open file");
        }
    }

}
