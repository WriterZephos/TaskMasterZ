package gui;

import java.util.ArrayList;
import javax.swing.JTabbedPane;

import engine.Profile;
import engine.TaskList;

public class ProfilePanel extends JTabbedPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<BasicPanel> allBasics;

	public ProfilePanel(Profile active){
		super(JTabbedPane.TOP);
		allBasics = new ArrayList<BasicPanel>();
		for(TaskList t : active.getLists().values()){
			BasicPanel basic = new BasicPanel(t);
			allBasics.add(basic);
			addTab(t.getName(),basic);
		}
	
	}
	
	public void addTaskList(TaskList tl){
		BasicPanel basic = new BasicPanel(tl);
		allBasics.add(basic);
		addTab(tl.getName(),basic);
		
		revalidate();
		repaint();

	}
	
	public void deleteTaskList(){
		remove(getSelectedIndex());
		revalidate();
		repaint();

	}
	
	public void rename(String s){
		
		setTitleAt(getSelectedIndex(), s);
		
		revalidate();
		repaint();
	}
	
}
