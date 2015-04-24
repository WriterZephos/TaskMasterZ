package gui;

import java.awt.Dimension;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import engine.Task;
import zui.ZComponentFactory;

public class SummaryPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BasicPanel basic;
	JLabel name;
	JLabel category;
	JLabel priority;
	JLabel date;
	JLabel completed;

	
	SummaryPanel(BasicPanel b){
		
		basic = b;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		name = ZComponentFactory.createBasicLabel("");
		category = ZComponentFactory.createBasicLabel("");
		priority = ZComponentFactory.createBasicLabel("");
		date = ZComponentFactory.createBasicLabel("");
		completed = ZComponentFactory.createBasicLabel("");
		
		add(Box.createRigidArea(new Dimension(200,20)));
		add(name);
		add(Box.createRigidArea(new Dimension(200,20)));
		add(category);
		add(Box.createRigidArea(new Dimension(200,20)));
		add(priority);
		add(Box.createRigidArea(new Dimension(200,20)));
		add(date);
		add(Box.createRigidArea(new Dimension(200,20)));
		add(completed);
		
	}
	
	public void refresh(){
		if(basic.getSelectedTask() == null) {
			clear();
			return;
		}
		Task t = basic.getSelectedTask();
		name.setText(t.getTitle());
		category.setText(t.getCategory());
		priority.setText("" + t.getPriority());
		if (t.getDueDate() == null){
			date.setText("No Due Date");
		} else { 
			Date d = t.getDueDate();
			SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a");
			date.setText(format.format(d));
		}
		completed.setText(t.isCompleted() ? "Completed" : "Not Completed");	
		revalidate();
		repaint();
	}	
	
	public void clear(){
		name.setText("");
		category.setText("");
		priority.setText("");
		date.setText("");
		completed.setText("");
	}
}
