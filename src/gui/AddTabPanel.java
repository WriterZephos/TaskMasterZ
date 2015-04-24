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
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import engine.ProfileManager;
import engine.Task;
import zui.ListTicker;
import zui.NumberTicker;
import zui.ZComponentFactory;

public class AddTabPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	BasicPanel basic;
	
	JLabel message;
	JTextField name;
	JTextField category;
	NumberTicker priority;
	JTextArea notes;
	
	NumberTicker day;
	NumberTicker month;
	NumberTicker year;
	
	NumberTicker hours;
	NumberTicker minutes;
	
	ListTicker am_pm;
	
	JButton submit;
	
	JDatePickerImpl datePicker;

	private JCheckBox useDate;

	
	AddTabPanel(BasicPanel b){
		basic = b;
		initialize();		
	}
	
	
	public void initialize(){
		
		setLayout(new BorderLayout());
		
		JPanel left = new JPanel();
		JPanel right = new JPanel();
		JPanel bottom = new JPanel();
		JPanel top = new JPanel();
		
		
		left.setLayout(new BoxLayout(left, BoxLayout.PAGE_AXIS));
		left.setMinimumSize(new Dimension(300,200));
		left.setPreferredSize(new Dimension(300,200));
		right.setLayout(new BoxLayout(right, BoxLayout.PAGE_AXIS));
		right.setMinimumSize(new Dimension(300,200));
		right.setPreferredSize(new Dimension(300,200));
		bottom.setLayout(new BoxLayout(bottom, BoxLayout.PAGE_AXIS));
		top.setLayout(new BoxLayout(top, BoxLayout.PAGE_AXIS));
		
		message = ZComponentFactory.createBasicLabel("");
		message.setForeground(Color.RED);
		name = ZComponentFactory.createTextField("", 250);
		category = ZComponentFactory.createTextField("", 250);
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
		Calendar cal = Calendar.getInstance();  
		datePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		datePicker.getModel().setSelected(true);
		
		hours = new NumberTicker(12,0,2,true,false,true);
		hours.setAlignmentY(Component.CENTER_ALIGNMENT);
		minutes = new NumberTicker(60,0,2,15,true,true,false);
		minutes.setAlignmentY(Component.CENTER_ALIGNMENT);
		am_pm = new ListTicker(0,"AM","PM");
		am_pm.setAlignmentY(Component.CENTER_ALIGNMENT);
		
		JPanel time = new JPanel();
		time.setLayout(new BoxLayout(time, BoxLayout.LINE_AXIS));
		time.add(hours);
		time.add(Box.createRigidArea(new Dimension(10,30)));
		time.add(minutes);
		time.add(Box.createRigidArea(new Dimension(10,30)));
		time.add(am_pm);
		time.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JScrollPane notesScroll = ZComponentFactory.createScrollPaneTextArea("", 306,250);
		notes = (JTextArea) notesScroll.getViewport().getView();
		
		submit = ZComponentFactory.createJButton("Submit Task", 150);
		submit.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(name.getText().replaceAll("\\s","").isEmpty() || category.getText().replaceAll("\\s","").isEmpty() || (datePicker.getModel().getValue() == null && useDate.isSelected())){
					message.setText("Some required fields are empty.");
					return;
				}
				
				Date date = (Date) datePicker.getModel().getValue();
				Calendar cal = Calendar.getInstance();
				
				if(useDate.isSelected()){	
					cal.setTime(date);  
					cal.set(Calendar.HOUR, (hours.getValue() == 12 ? 0 : hours.getValue()));  
					cal.set(Calendar.MINUTE, minutes.getValue());
					cal.set(Calendar.AM_PM, am_pm.getOption().equals("AM") ? Calendar.AM: Calendar.PM);  
					// Put it back in the Date object  
					date = cal.getTime();	
				} else {
					date = null;
				}
				
				Task newTask = new Task(name.getText(),category.getText(),priority.getValue(),date, notes.getText());
				basic.getTaskList().addTask(newTask);
				basic.refreshList();
				ProfileManager.saveProfileList();
				clear();
				
				//SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a z" );
				//System.out.println(format.format(date));
	
			}});
		
		top.add(Box.createRigidArea(new Dimension(300,10)));
		top.add(message);
		top.add(Box.createRigidArea(new Dimension(300,10)));
		
		left.add(ZComponentFactory.createBasicLabel("Task Name:"));
		left.add(name);
		left.add(Box.createRigidArea(new Dimension(300,20)));
		left.add(ZComponentFactory.createBasicLabel("Category:"));
		left.add(category);
		left.add(Box.createRigidArea(new Dimension(300,20)));
		left.add(ZComponentFactory.createBasicLabel("Priority Level:"));
		left.add(priority);
		left.add(Box.createRigidArea(new Dimension(300,20)));
		left.add(datePanel);
		left.add(datePicker);
		left.add(Box.createRigidArea(new Dimension(300,20)));
		left.add(ZComponentFactory.createBasicLabel("Due Time:"));
		left.add(time);
		
		right.add(ZComponentFactory.createBasicLabel("Task Notes:"));
		right.add(notesScroll);
		
		bottom.add(Box.createRigidArea(new Dimension(300,20)));
		bottom.add(submit);
		bottom.add(Box.createRigidArea(new Dimension(300,20)));
		
		add(top,BorderLayout.NORTH);
		add(left, BorderLayout.WEST);
		add(right, BorderLayout.EAST);
		add(bottom,BorderLayout.SOUTH);
		
	}
	
	public void refresh(){
		revalidate();
		repaint();
	}
	
	public void clear(){
		message.setText("");
		name.setText("");
		category.setText("");
		priority.reset();
		Calendar cal = Calendar.getInstance();
		datePicker.getModel().setDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		datePicker.getModel().setSelected(true);
		hours.reset();
		minutes.reset();
		am_pm.reset();
		notes.setText("");
		refresh();
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
