package gui;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import engine.Task;
import engine.TaskList;
import zui.ZComponentFactory;

public class BasicPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JScrollPane scrollPane;
		TaskPanel listPanel;
	
	SidePane tabbedPane;
	
	TaskList taskList;
	public Task SelectedTask;
	private SortOptionPanel topSortPanel;
	
	BasicPanel(TaskList tl){
		
		setLayout(new BorderLayout());
		
		
		taskList = tl;
		listPanel = new TaskPanel(taskList.getTasks(), this);
		scrollPane = ZComponentFactory.createScrollPane(150, 450, listPanel);
		tabbedPane = new SidePane(this);
		topSortPanel = new SortOptionPanel(this);
		
		JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,scrollPane,tabbedPane);
		split.setResizeWeight(1.0);

		
		add(topSortPanel,BorderLayout.NORTH);
		add(split, BorderLayout.CENTER);
		
	}

	public Task getSelectedTask() {
		return SelectedTask;
	}

	public void setSelectedTask(Task selectedTask, int buttonIndex) {
		SelectedTask = selectedTask;
		listPanel.highlight(buttonIndex);
		tabbedPane.getManageTab().refresh();
	}
	
	public void clearSelectedTask(){
		SelectedTask = null;
		listPanel.resetHighlight();
		
	}

	public TaskList getTaskList() {
		return taskList;
	}
	
	public void refreshList(){
		clearSelectedTask();
		topSortPanel.sortList();
	}
	
	public void refreshList(boolean b, int sumType){
		clearSelectedTask();
		listPanel.refresh(b,sumType);
	}
}
