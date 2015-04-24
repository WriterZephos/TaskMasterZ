package engine;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TaskList implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Task> tasks;
	
	public TaskList(String n){
		name = n;
		tasks = new ArrayList<Task>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Task> getTasks() {
		return tasks;
	}

	public void setTasks(ArrayList<Task> tasks) {
		this.tasks = tasks;
	}

	public void addTask(Task newTask) {
		tasks.add(newTask);
		ProfileManager.saveProfileList();
		
	}
	
	public void deleteTask(Task newTask) {
		tasks.remove(newTask);
		ProfileManager.saveProfileList();
		
	}

	public void sort(Comparator<Task> order) {
		Collections.sort(tasks, order);
	}
	
	
}
