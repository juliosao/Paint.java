package com.sao.java.paint.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sao.java.paint.tools.ColorPicker;
import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.tools.Ellipse;
import com.sao.java.paint.tools.Line;
import com.sao.java.paint.tools.Pencil;
import com.sao.java.paint.tools.Rectangle;
import com.sao.java.paint.tools.Strokable;
import com.sao.java.paint.tools.StrokeProvider;
import com.sao.java.paint.tools.Fill;

public class ToolBox
	extends JPanel
    implements Coloreable, Strokable
{
	ToolBoxListener lst;
	DrawingTool[] tools;

	public ToolBox(final ToolBoxListener src)
	{
            super();

            tools = new DrawingTool[]{
                    new Pencil(),
                    new Line(),
                    new Rectangle(),
                    new Ellipse(),
                    new Fill(),
					new ColorPicker()
            };

            GridLayout fl = new GridLayout(0,1);
            setLayout(fl);

            for (final DrawingTool t: tools) {            
                    JButton btn = new JButton(t.getDescription());
                    btn.addActionListener(new ActionListener(){
                        public void actionPerformed(ActionEvent e)
                        {
                            src.onToolSelected(t);
                        }
                    });
                    add(btn);
            }

            src.onToolSelected(tools[0]);
	}

    @Override
    public void setColorProvider(ColorProvider cp) 
    {
        for (final DrawingTool t: tools)   
        {
            if(t instanceof Coloreable)   
            {
                ((Coloreable)t).setColorProvider(cp);
            }
        }        
    }

	@Override
	public void setStrokeProvider(StrokeProvider sp) {
		for (final DrawingTool t: tools)   
        {
            if(t instanceof Strokable)   
            {
                ((Strokable)t).setStrokeProvider(sp);
            }
        }  		
	}
}
