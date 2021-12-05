package com.sao.java.paint;

import com.sao.java.paint.ui.DrawingPanel;
import com.sao.java.paint.dialogs.ColorPickerDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import com.sao.java.paint.ui.ColorListener;
import com.sao.java.paint.divcompat.ColorPalette;
import com.sao.java.paint.ui.ToolBox;
import com.sao.java.paint.ui.ToolBoxListener;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.ui.ColorGammaBar;
import com.sao.java.paint.ui.ColorProvider;
import java.awt.Graphics2D;
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
    implements WindowListener, ToolBoxListener, ColorListener
{
    static FileNameExtensionFilter filters[] = new FileNameExtensionFilter[]{ 
        new FileNameExtensionFilter("All Images", "JPEG","JPG","PNG","BMP"),
        new FileNameExtensionFilter("JPEG Image", "JPEG","JPG"),
        new FileNameExtensionFilter("PNG Image", "PNG"),
        new FileNameExtensionFilter("BMP Image", "BMP"),
    };
    DrawingPanel drawingPanel;
    ToolBox toolbox;
    ColorPickerDialog colorPicker = null;
    ColorGammaBar colorToolbar;
    JMenuBar menuBar;
    JMenu menuFile;
    JSlider zoomer;
    DrawingTool drawingTool;
    ColorPalette palette = new ColorPalette();
    File currentFile = null;
    static final String TITLE = "Paint.java v0.1";


    public JPaintMainWindow()
    {
        super();

        setTitle(TITLE);

        Container c = getContentPane();
        BorderLayout bl = new BorderLayout();
        setLayout(bl);
        
        palette = new ColorPalette();
        colorPicker = new ColorPickerDialog(this, palette);                
        
        drawingPanel=new DrawingPanel();
        drawingPanel.setSize(800,600);    
        drawingPanel.setImage(createImg(640,480));
        c.add(drawingPanel,BorderLayout.CENTER);
        
        c.add(createBotomToolBar(),BorderLayout.SOUTH);
        
        toolbox = new ToolBox(this);
	    c.add(toolbox,BorderLayout.WEST);               

        createMenus();
        
	    setSize(800,600);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }
    
    
    private JPanel createBotomToolBar()
    {
        JPanel container = new JPanel();
        container.setLayout(new BorderLayout());
        
        colorToolbar = new ColorGammaBar(palette);
        colorToolbar.setColorProvider(colorPicker);        
        container.add(colorToolbar,BorderLayout.CENTER);
        colorToolbar.addColorListener(this);
        colorToolbar.dispatchStrokeColor();
        
        zoomer = new JSlider(10,1000);
        zoomer.setValue(100);
        zoomer.addChangeListener(new javax.swing.event.ChangeListener() {
            @Override
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                drawingPanel.setZoom(zoomer.getValue());
            }
        });
        container.add(zoomer,BorderLayout.EAST);
        
        return container;
    }
    
    
    protected BufferedImage createImg(int height, int width)
    {
        BufferedImage image = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D)image.createGraphics();
        g.setPaint(Color.WHITE);
        g.fillRect ( 0, 0, image.getWidth(), image.getHeight() );
	    g.dispose();
        return image;
    }
        
    private void createMenus()
    {
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        menuBar.add(menuFile);

        JMenuItem mnu = new JMenuItem("New...");
        menuFile.add(mnu);
        mnu = new JMenuItem("Open...");
        mnu.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openPicture();
            }
        });
        menuFile.add(mnu);
        
        mnu = new JMenuItem("Save...");
        mnu.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePicture();
            }
        });
        menuFile.add(mnu);
        
        mnu = new JMenuItem("Save As...");
        mnu.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                savePictureAs();
            }
        });
        menuFile.add(mnu);
        mnu = new JMenuItem("Exit");
        mnu.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
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
                System.exit(0);
                break;
        }
    }

    @Override
    public void windowClosed(WindowEvent we) {

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
        if(drawingTool instanceof ColorListener)
        {
            ((ColorListener)drawingTool).setStrokeColor(colorToolbar.getStrokeColor());
        }
    }

    @Override
    public void setStrokeColor(Color c) {
        if(drawingTool instanceof ColorListener)
        {
            ((ColorListener)drawingTool).setStrokeColor(c);
        }
    }

    @Override
    public void setColorProvider(ColorProvider cp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
            int result = fileChooser.showOpenDialog(JPaintMainWindow.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                savePicture(fileChooser.getSelectedFile());                
            }
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(JPaintMainWindow.this, "Cannot open file");
        }
    }


    @Override
    public void setPalette(ColorPalette cp) {
        // TODO Auto-generated method stub
        
    }
}
