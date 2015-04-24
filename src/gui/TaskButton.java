package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import engine.Task;

public class TaskButton extends JButton{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Task task;
	private BasicPanel basic;
	private int buttonIndex;
	
	TaskButton(Task t, BasicPanel b, TaskPanel p, int i, int summaryType){
		super(t.getSummary(summaryType));
		
		basic = b;
		task = t;
		buttonIndex = i;
		
		setToolTipText(t.getSummary(summaryType));
		
		setAlignmentX(Component.CENTER_ALIGNMENT);
		setMinimumSize(new Dimension(650, 25));
		setMaximumSize(new Dimension(6000, 25));
		setPreferredSize(new Dimension(600,25));
		setVerticalAlignment(JButton.BOTTOM);
		setHorizontalAlignment(JButton.LEFT);
		setFocusPainted(false);
		setBorder(null);
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		setForeground(Color.BLACK);
		setFont(new Font(Font.MONOSPACED,Font.PLAIN,14));
		
		addActionListener(new Listener());
		
		
		
	}
	
	
	
	private class Listener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!(basic.getSelectedTask() == task)){
				basic.setSelectedTask(task, buttonIndex);
			}
			
		}
		
	}
	
	

}
