package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import engine.Task;

public class TaskPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BasicPanel basic;
	ArrayList<Task> taskList;
	ArrayList<TaskButton> buttonList;
	boolean showCompleted = false;
	int summaryType = 0;
	
	TaskButton highlighted;
	int selectedButtonIndex; 
	

	
	TaskPanel(ArrayList<Task> tskLst, BasicPanel b){
		taskList = tskLst;
		basic = b;

		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(600,500));
		setOpaque(false);

		buttonList = new ArrayList<TaskButton>();		
		add(createContents(),BorderLayout.CENTER);
		
	}
	
	private JPanel createContents(){
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		panel.setOpaque(false);
		int buttonIndex = 0;
		for(int i = 0; i < taskList.size() ; i++){
			if(!showCompleted && taskList.get(i).isCompleted()){
				
			} else {
				Calendar c = Calendar.getInstance();  
				Task t = taskList.get(i);
				TaskButton b = new TaskButton(t, basic, this, buttonIndex, summaryType);
				b.setAlignmentX(Component.CENTER_ALIGNMENT);
				if (t.isCompleted()){
					b.setForeground(Color.GREEN.darker());
				} else if ((t.getDueDate() != null) && t.getDueDate().compareTo(c.getTime())<= 0){
						b.setForeground(Color.RED);
				 } else if (t.getPriority() == 5){
					b.setForeground(Color.decode("#0000FF"));
				} else if (t.getPriority() == 4){
					b.setForeground(Color.decode("#3333CC"));
				} else if (t.getPriority() == 3){
					b.setForeground(Color.decode("#6600CC"));
				} else if (t.getPriority() == 2){
					b.setForeground(Color.decode("#660066"));
				} else if (t.getPriority() == 1){
					b.setForeground(Color.decode("#993333"));
				}
				
				panel.add(b);
				buttonList.add(b);
				buttonIndex++;
			}	
		}
		return panel;
	}
	
	protected void highlight(int i){
		if(highlighted == null){
			highlighted = buttonList.get(i);
			buttonList.get(i).setBackground(Color.LIGHT_GRAY);
			buttonList.get(i).setOpaque(true);
		} else {
			highlighted.setBackground(Color.RED);
			highlighted.setOpaque(false);
			highlighted = buttonList.get(i);
			buttonList.get(i).setBackground(Color.LIGHT_GRAY);
			buttonList.get(i).setOpaque(true);
		}
		
		selectedButtonIndex = i;
		revalidate();
		repaint();
	}
	
	protected void resetHighlight(){
		if(highlighted != null){
			highlighted.setBackground(Color.BLUE);
			highlighted.setOpaque(false);
		}
		
		selectedButtonIndex = -1;
		revalidate();
		repaint();
	}
	

	public void refresh() {
		removeAll();
		buttonList = new ArrayList<TaskButton>();
		add(createContents());
		//resetHighlight();
		revalidate();
		repaint();
	}
	
	public void refresh(boolean c, int sumType) {
		showCompleted = c;
		summaryType = sumType;
		removeAll();
		buttonList = new ArrayList<TaskButton>();
		add(createContents());
		//resetHighlight();
		revalidate();
		repaint();
	}
	
	
	
}
