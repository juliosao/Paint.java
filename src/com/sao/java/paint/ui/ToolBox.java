package com.sao.java.paint.ui;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sao.java.paint.tools.DrawingTool;
import com.sao.java.paint.tools.Ellipse;
import com.sao.java.paint.tools.Line;
import com.sao.java.paint.tools.Pencil;
import com.sao.java.paint.tools.Rectangle;
import com.sao.java.paint.tools.Fill;

public class ToolBox
	extends JPanel
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
                    new Fill()
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
}
