package engine;

import java.io.Serializable;
import java.util.HashMap;

public class Profile implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private HashMap<String,TaskList> lists;
	
	public Profile(String n, String tl){	
		name = n;
		lists = new HashMap<String,TaskList>();
		lists.put(tl,new TaskList(tl));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public HashMap<String,TaskList> getLists() {
		return lists;
	}

	public void setLists(HashMap<String,TaskList> lists) {
		this.lists = lists;
	}
	
	public void addTaskList(TaskList tl){
		lists.put(tl.getName(),tl);
		ProfileManager.saveProfileList();
	}

	public void deleteTaskList(String name2) {
		lists.remove(name2);
		ProfileManager.saveProfileList();
		
	}
	
	public void renameTaskList(String s, String s2){
		lists.get(s).setName(s2);
		TaskList tl = lists.get(s);
		lists.remove(s);
		lists.put(s2, tl);
		ProfileManager.saveProfileList();
		
	}
	
	
}
