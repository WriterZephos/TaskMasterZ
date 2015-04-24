package engine;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean isCompleted = false;
	private String title;
	private String category;
	private int priority;
	private Date dueDate;
	private String notes;

	public Task(String t, String c, int p, Date d, String n){
		
		title = t;
		category = c;
		priority = p;
		dueDate = d;
		notes = n;
		
	}
	
	Task(String t, String c, int p){
		
		title = t;
		category = c;
		priority = p;
		dueDate = null;
		
	}

	public String getSummary(int type){
		switch(type){
			case 0:
				return getDueDateSummary();
			case 1: 
				return getPrioritySummary();
			case 2:
				return getNameSummary();
			case 3:
				return getCategorySummary();
			default:
				return getDueDateSummary();
				
		}
	}
	
	public String getNameSummary(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a" );
		return "  " + title + ((dueDate == null) ? ", Due On: N/A" : ", Due On: " + format.format(dueDate) ) + ", Priority: " + priority + ", " + category + (isCompleted ? ", Completed" : ", Not Completed"); 
	}
	
	public String getCategorySummary(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a" );
		return "  " + title + ", " + category + ((dueDate == null) ?  ", Due On: N/A" : ", Due On: " + format.format(dueDate) ) + ", Priority: " + priority + (isCompleted ? ", Completed" : ", Not Completed"); 
	}
	
	public String getPrioritySummary(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a" );
		return "  " + title + ", Priority: " + priority + ((dueDate == null) ?  ", Due On: N/A" : ", Due On: " + format.format(dueDate) ) + ", " + category + (isCompleted ? ", Completed" : ", Not Completed"); 
	}
	
	public String getDueDateSummary(){
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy 'at' h:mm a" );
		return "  " + title + ((dueDate == null) ?  ", Due On: N/A" : ", Due On: " + format.format(dueDate) ) + ", Priority: " + priority + ", " + category + (isCompleted ? ", Completed" : ", Not Completed"); 
	}
	
	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String text) {
		notes = text;
		
	}

	
}
