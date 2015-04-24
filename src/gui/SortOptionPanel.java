package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import engine.Task;
import zui.ZComponentFactory;

public class SortOptionPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	BasicPanel basic;
	JRadioButton sortName;
	JRadioButton sortCategory;
	JRadioButton sortPriority;
	JRadioButton sortDueDate;
	
	int orderNumber = 0;
	
	JCheckBox reverseOrder;
	JCheckBox showCompleted;
	
	public Comparator<Task> priorityOrder = new Comparator<Task>(){

		@Override
		public int compare(Task o1, Task o2) {
			return o2.getPriority() - o1.getPriority();
	}};
		
	public Comparator<Task> nameOrder = new Comparator<Task>(){

		@Override
		public int compare(Task o1, Task o2) {
			return o1.getTitle().compareToIgnoreCase(o2.getTitle());
	}};
			
	public Comparator<Task> categoryOrder = new Comparator<Task>(){

		@Override
		public int compare(Task o1, Task o2) {
			return o1.getCategory().compareToIgnoreCase(o2.getCategory());	
	}};	
	
	public Comparator<Task> dueDateOrder = new Comparator<Task>(){

		@Override
		public int compare(Task o1, Task o2) {
			if(o1.getDueDate() == null && o2.getDueDate() == null){
				return 0;
			} else if (o1.getDueDate() == null && !(o2.getDueDate() == null)){
				return 1;
			} else if (!(o1.getDueDate() == null) && o2.getDueDate() == null){
				return -1;
			}
			return o1.getDueDate().compareTo(o2.getDueDate());	
	}};	
	
	
	public SortOptionPanel(BasicPanel b){
		
		basic = b;
		initialize();
	}
	
	private void initialize(){
		
		setLayout(new BorderLayout());
		
		JPanel sortPanel = new JPanel();
		sortPanel.setLayout(new GridLayout(2,4,0,0));
		
		sortName = new JRadioButton("Name");
		sortName.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				orderNumber = 2;
				sortList();
			}});
		sortCategory = new JRadioButton("Category");
		sortCategory.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				orderNumber = 3;
				sortList();
			}});
		sortPriority = new JRadioButton("Priority");
		sortPriority.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				orderNumber = 1;
				sortList();
			}});
		sortDueDate = new JRadioButton("DueDate");
		sortDueDate.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				orderNumber = 0;
				sortList();
			}});
		sortDueDate.setSelected(true);
		
		ButtonGroup group = new ButtonGroup();
		group.add(sortName);
		group.add(sortCategory);
		group.add(sortPriority);
		group.add(sortDueDate);
		
		reverseOrder = new JCheckBox("Reverse Order");
		reverseOrder.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sortList();
			}});
		showCompleted = new JCheckBox("Show Completed");
		showCompleted.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				sortList();
			}});
		
		sortPanel.add(ZComponentFactory.createBasicLabel("Sort by: "));
		sortPanel.add(sortDueDate);
		sortPanel.add(sortPriority);
		sortPanel.add(reverseOrder);
		sortPanel.add(Box.createRigidArea(new Dimension(10,01)));
		sortPanel.add(sortName);
		sortPanel.add(sortCategory);
		sortPanel.add(showCompleted);
		
		sortList();
		
		add(sortPanel, BorderLayout.WEST);
		
		
	}
	
	public void sortList(){
		
		Comparator<Task> order = dueDateOrder;
		
		switch(orderNumber){
		
		case 0:
			order = dueDateOrder;
			break;
		case 1:
			order = priorityOrder;
			break;
		case 2:
			order = nameOrder;
			break;
		case 3:
			order = categoryOrder;
			break;
		}
		
		if(reverseOrder.isSelected()){
			order = Collections.reverseOrder(order);
		}
		
		basic.getTaskList().sort(order);
		basic.refreshList(showCompleted.isSelected(),orderNumber);
	}
	
	

}
