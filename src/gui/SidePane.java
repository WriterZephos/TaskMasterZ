package gui;

import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SidePane extends JTabbedPane{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BasicPanel basic;
	AddTabPanel addTab;
	ManageTabPanel manageTab;
	
	
	SidePane(BasicPanel b){
		
		basic = b;
		
		setPreferredSize(new Dimension(620,525));
		setMinimumSize(new Dimension(620,500));
		setMaximumSize(new Dimension(620,500));
		
		manageTab = new ManageTabPanel(basic);
		addTab = new AddTabPanel(basic);
		
		addTab("Manage Task", manageTab);
		addTab("Add Task", addTab);
		
		addChangeListener(new TabListener());
		
	}
	
	private class TabListener implements ChangeListener{

		@Override
		public void stateChanged(ChangeEvent e) {
			
			int index = getSelectedIndex();
			
			if(index == 1){
				basic.clearSelectedTask();
				manageTab.clear();
			} else {
				addTab.clear();

			}
			
		}
	}

	public ManageTabPanel getManageTab() {
		return manageTab;
	}
	
}
