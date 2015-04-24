package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import engine.ProfileManager;
import engine.Task;
import zui.ListTicker;
import zui.NumberTicker;
import zui.ZComponentFactory;

public class ManageTabPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BasicPanel basic;
	
	SummaryPanel summaryPanel;
	JLabel message;
	JTextField name;
	JTextField category;
	NumberTicker priority;
	JCheckBox completed;
	JTextArea notes;
	
	NumberTicker day;
	NumberTicker month;
	NumberTicker year;
	
	NumberTicker hours;
	NumberTicker minutes;
	
	ListTicker am_pm;
	
	JButton update;
	JButton delete;
	
	JDatePickerImpl datePicker;

	private JCheckBox useDate;
	
	ManageTabPanel(BasicPanel b){
		
		basic = b;
		initialize();
	}

	private void initialize() {
		
		setLayout(new BorderLayout());
		
		JPanel right = new JPanel();
		right.setPreferredSize(new Dimension(300,300));
		right.setMinimumSize(new Dimension(300,300));
		JPanel bottom = new JPanel();
		JPanel top = new JPanel();
		JPanel leftEdit = new JPanel();
		summaryPanel = new SummaryPanel(basic);
		JTabbedPane leftTabbedPane = new JTabbedPane(JTabbedPane.TOP);
		leftTabbedPane.setPreferredSize(new Dimension(300,300));
		leftTabbedPane.setMinimumSize(new Dimension(300,300));

		
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
		leftEdit.setLayout(new BoxLayout(leftEdit,BoxLayout.PAGE_AXIS));
		
		message = ZComponentFactory.createBasicLabel("");
		message.setForeground(Color.RED);
		
		name = ZComponentFactory.createTextField("", 200);
		category = ZComponentFactory.createTextField("", 200);
		priority = new NumberTicker(6,-1,true,false,false);
		
		useDate = new JCheckBox();
		useDate.setText("Use Due Date/ Time");
		useDate.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JLabel dateLabel = ZComponentFactory.createBasicLabel("Due Date:");
		dateLabel.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.LINE_AXIS));
		datePanel.add(dateLabel);
		datePanel.add(Box.createRigidArea(new Dimension(10,30)));
		datePanel.add(useDate);
		datePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePickerPanel = new JDatePanelImpl(model,p);
		datePicker = new JDatePickerImpl(datePickerPanel,new DateLabelFormatter());
		datePicker.setMinimumSize(new Dimension(200,30));
		datePicker.setPreferredSize(new Dimension(200,30));
		datePicker.setMaximumSize(new Dimension(200,30));
		datePicker.setAlignmentX(Component.CENTER_ALIGNMENT);
		hours = new NumberTicker(12,0,2,true,false,true);
		hours.setAlignmentY(Component.CENTER_ALIGNMENT);
		minutes = new NumberTicker(60,0,2,15,true,true,false);
		minutes.setAlignmentY(Component.CENTER_ALIGNMENT);
		am_pm = new ListTicker(0,"AM","PM");
		am_pm.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JPanel time = new JPanel();
		time.setLayout(new BoxLayout(time, BoxLayout.LINE_AXIS));
		time.add(hours);
		time.add(minutes);
		time.add(am_pm);
		time.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JScrollPane notesScroll = ZComponentFactory.createScrollPaneTextArea("", 306,250);
		notes = (JTextArea) notesScroll.getViewport().getView();
		completed = new JCheckBox();
		completed.setAlignmentX(CENTER_ALIGNMENT);
		completed.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(basic.getSelectedTask() == null){
					completed.setSelected(false);
					return;
				}
				
				if(completed.isSelected()){
					completed.setText("Completed");
				} else {
					completed.setText("Not Completed");
				}
				
			}});
		
		update = ZComponentFactory.createJButton("Update Task", 150);
		update.setAlignmentY(Component.CENTER_ALIGNMENT);
		update.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(basic.getSelectedTask() == null){
					clear();
					message.setText("No task selected.");	
					return;
				}
				
				if(name.getText() == null || category.getText() == null || (datePicker.getModel().getValue() == null && useDate.isSelected())){
					message.setText("Some required fields are empty.");	
					return;
				}
				
				Date date = (Date) datePicker.getModel().getValue();
				
				if(useDate.isSelected()){
					Calendar cal = Calendar.getInstance();  
					cal.setTime(date);  
				  
					// Set time fields to zero  
					cal.set(Calendar.HOUR, (hours.getValue() == 12 ? 0 : hours.getValue()));  
					cal.set(Calendar.MINUTE, minutes.getValue());
					cal.set(Calendar.AM_PM, am_pm.getOption().equals("AM") ? Calendar.AM: Calendar.PM);
				  
					// Put it back in the Date object  
					date = cal.getTime();
				} else {
					date = null;
				}
				
				Task t = basic.getSelectedTask();
				t.setTitle(name.getText());
				t.setCategory(category.getText());
				t.setPriority(priority.getValue());
				t.setDueDate(date);
				t.setNotes(notes.getText());
				t.setCompleted(completed.isSelected() ? true : false);
				
				basic.refreshList();
				message.setText("");
				summaryPanel.refresh();
				ProfileManager.saveProfileList();
				refresh();
	
			}});
		
		delete = ZComponentFactory.createJButton("Delete Task", 150);
		delete.setAlignmentY(Component.CENTER_ALIGNMENT);
		delete.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(basic.getSelectedTask() == null){
					clear();
					message.setText("No task selected.");	
					return;
				}
				
				Object[] options = {"Cancel",
	                    "Yes, delete",};
				
				int option = JOptionPane.showOptionDialog(basic.getTopLevelAncestor(),
					    "Are you sure you want to delete this Task?",
					    "Delete Task: " + basic.getSelectedTask().getTitle(),
					    JOptionPane.OK_CANCEL_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,
					    options[0]);
					
					if(option != 0){
						basic.getTaskList().deleteTask(basic.getSelectedTask());
						basic.clearSelectedTask();
						basic.refreshList();
						message.setText("");
						summaryPanel.refresh();
				
						refresh();
					}
				
					ProfileManager.saveProfileList();
				
			}});
		
		JPanel buttons = new JPanel();
		buttons.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.LINE_AXIS));
		buttons.add(update);
		buttons.add(Box.createRigidArea(new Dimension(20,30)));
		buttons.add(delete);
		
		top.add(Box.createRigidArea(new Dimension(300,10)));
		top.add(message);
		top.add(Box.createRigidArea(new Dimension(300,10)));
		
		right.add(completed);
		right.add(Box.createRigidArea(new Dimension(300,10)));
		right.add(ZComponentFactory.createBasicLabel("Task Notes:"));
		right.add(notesScroll);
		
		bottom.add(Box.createRigidArea(new Dimension(300,20)));
		bottom.add(buttons);
		bottom.add(Box.createRigidArea(new Dimension(300,20)));
		
		leftEdit.add(Box.createRigidArea(new Dimension(300,20)));
		leftEdit.add(ZComponentFactory.createBasicLabel("Task Name:"));
		leftEdit.add(name);
		leftEdit.add(Box.createRigidArea(new Dimension(300,10)));
		leftEdit.add(ZComponentFactory.createBasicLabel("Category:"));
		leftEdit.add(category);
		leftEdit.add(Box.createRigidArea(new Dimension(300,10)));
		leftEdit.add(ZComponentFactory.createBasicLabel("Priority Level:"));
		leftEdit.add(priority);
		leftEdit.add(Box.createRigidArea(new Dimension(300,10)));
		leftEdit.add(datePanel);
		leftEdit.add(datePicker);
		leftEdit.add(Box.createRigidArea(new Dimension(300,10)));
		leftEdit.add(ZComponentFactory.createBasicLabel("Due Time:"));
		leftEdit.add(time);
		
		leftTabbedPane.addTab("Summary", summaryPanel);
		leftTabbedPane.addTab("Edit", leftEdit);
		
		
		add(top,BorderLayout.NORTH);
		add(right, BorderLayout.EAST);
		add(bottom,BorderLayout.SOUTH);
		add(leftTabbedPane, BorderLayout.WEST);
		
	}
	
	public void refresh(){
		if(basic.getSelectedTask() == null) {
			clear();
			return;
		}
		Task t = basic.getSelectedTask();
		notes.setText(t.getNotes());
		name.setText(t.getTitle());
		category.setText(t.getCategory());
		priority.setCount(t.getPriority());
		if (t.getDueDate() == null){
			datePicker.getModel().setValue(null);
			useDate.setSelected(false);
		} else { 
			useDate.setSelected(true);
			Date d = t.getDueDate();
			Calendar cal = Calendar.getInstance();  
			cal.setTime(d);  
			datePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
			datePicker.getModel().setSelected(true);
			int hour = cal.get(Calendar.HOUR);
			hours.setCount(hour == 0 ? 12 : hour);
			minutes.setCount(cal.get(Calendar.MINUTE));
			int ampm = cal.get(Calendar.AM_PM);
			am_pm.setCount(ampm == Calendar.AM ? 0 : 1);
		}
		completed.setText(t.isCompleted() ? "Completed" : "Not Completed");	
		completed.setSelected(completed.isSelected() ? true : false);
		
		summaryPanel.refresh();
		
		revalidate();
		repaint();
	}
	
	
	public void clear(){
		
		message.setText("");
		name.setText("");
		category.setText("");
		priority.reset();
		datePicker.getModel().setValue(null);
		hours.reset();
		minutes.reset();
		am_pm.reset();
		notes.setText("");
		completed.setSelected(false);
		completed.setText("");
		summaryPanel.clear();
		useDate.setSelected(false);
		revalidate();
		repaint();
		
	}
	
	public SummaryPanel getViewPanel(){
		return summaryPanel;
	}
	
	private class DateLabelFormatter extends AbstractFormatter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return format.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return format.format(cal.getTime());
	        }
	        return "";
	    }
	}
	

}
