package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import zui.ZComponentFactory;
import engine.Profile;
import engine.ProfileManager;
import engine.TaskList;

public class MainWindow {

	
	boolean ProfileLoaded = false;
	ProfilePanel currentProfile;
	private JFrame frame;
	private JLabel message;
	JMenu taskList;
	static MainWindow window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new MainWindow();
					window.frame.setVisible(true);
					JMenuBar menuBar = new JMenuBar();
					window.frame.setJMenuBar(menuBar);
					JMenu profile = new JMenu("Profile");
					menuBar.add(profile);
					
					JMenuItem changeProfile = new JMenuItem("Change Profile");
					changeProfile.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							window.restart();
						}});
					profile.add(changeProfile);
					
					JMenuItem newProfile = new JMenuItem("New Profile");
					newProfile.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							window.restart();
							window.newProfile();
							
						}});
					profile.add(newProfile);
					
					JMenuItem deleteProfile = new JMenuItem("Delete Profile");
					profile.add(deleteProfile);
					deleteProfile.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							Object[] options = {"Cancel",
				                    "Yes, delete",};
							
							int option = JOptionPane.showOptionDialog(window.getFrame(),
								    "Are you sure you want to delete this profile and all of its Task Lists? ",
								    "Delete Profile: " + ProfileManager.getActiveProfile().getName(),
								    JOptionPane.OK_CANCEL_OPTION,
								    JOptionPane.WARNING_MESSAGE,
								    null,
								    options,
								    options[0]);
								
								if(option != 0){
									ProfileManager.deleteProfile(ProfileManager.getActiveProfile().getName());
									window.restart();
									return;	
								}
							
						}});
					
					window.taskList = new JMenu("Task List");
					window.taskList.setEnabled(false);
					menuBar.add(window.taskList);

					JMenuItem newTaskList = new JMenuItem("New Task List");
					window.taskList.add(newTaskList);
					newTaskList.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String sT = (String)JOptionPane.showInputDialog(
					                window.frame,
					                "New Task List Name: ","Create New Task List",
					                JOptionPane.QUESTION_MESSAGE);
						
							//If a string was returned, say so.
							if ((sT != null) && (sT.length() > 0)) {
								TaskList tl = new TaskList(sT);
								ProfileManager.getActiveProfile().addTaskList(tl);
								window.currentProfile.addTaskList(tl);
							}
							
						}});
					
					JMenuItem deleteTaskList = new JMenuItem("Delete Task List");
					window.taskList.add(deleteTaskList);
					deleteTaskList.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							
							if(ProfileManager.getActiveProfile().getLists().size() <= 1){
								JOptionPane.showMessageDialog(window.frame,
									    "A profile needs at least one Task List. You can't delete the last Task List.",
									    "Delete Task List",
									    JOptionPane.WARNING_MESSAGE);
								return;
								}
							
							Object[] options = {"Cancel",
				                    "Yes, delete",};
							
							int option = JOptionPane.showOptionDialog(window.getFrame(),
								    "Are you sure you want to delete the task Task List: " + ((BasicPanel)window.currentProfile.getSelectedComponent()).getTaskList().getName(),
								    "Delete Task List",
								    JOptionPane.OK_CANCEL_OPTION,
								    JOptionPane.WARNING_MESSAGE,
								    null,
								    options,
								    options[0]);
								
								if(option != 0){
									ProfileManager.getActiveProfile().deleteTaskList(((BasicPanel)window.currentProfile.getSelectedComponent()).getTaskList().getName());
									window.currentProfile.deleteTaskList();
									return;	
								}
							
						}});
					
					JMenuItem renameTaskList = new JMenuItem("Rename Task List");
					window.taskList.add(renameTaskList);
					renameTaskList.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							String sT = (String)JOptionPane.showInputDialog(
					                window.frame,
					                "Enter a new name for Task List: " + ((BasicPanel)window.currentProfile.getSelectedComponent()).getTaskList().getName(),
					                "Rename Task List",
					                JOptionPane.QUESTION_MESSAGE);
						
							//If a string was returned, say so.
							if ((sT != null) && (sT.length() > 0)) {
								ProfileManager.getActiveProfile().renameTaskList(((BasicPanel)window.currentProfile.getSelectedComponent()).getTaskList().getName(), sT);
								window.currentProfile.rename(sT);
							}
							
						}});
					
					JMenu info = new JMenu("Info");
					menuBar.add(info);
					
					JMenuItem about = new JMenuItem("About Task Master Z");
					info.add(about);
					about.addActionListener(new ActionListener(){

						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane.showMessageDialog(window.frame,
								    "Task Master Z"
								    + "\n\nCreated by Bryant Morrill in January of 2015."
								    + "\n\nVersion 1.0"
								    + "\n\nVersion Notes: This application was programmed in about a week, "
								    + "\ntherefore the code is a mess and it is poorly structured."
								    + "\nIf you would like to make improvements you are welcome to as "
								    + "\nlong as you don't change the credit on this dialogue (you can add "
								    + "\nto it, just don't remove any names) or sell or restrict access to "
								    + "\nthe source code in any way. This application was intended for the "
								    + "\nbenefit of other students to help them keep track of homework \n"
								    + "and/ or learn how to code in Java. I know the code is ugly but at "
								    + "\nleast it works. Sorry I didn't really put any comments in the code."
								    + "\nGood luck.",
								    "About Task Master Z",
								    JOptionPane.INFORMATION_MESSAGE);
							
						}});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		//frame.setBounds(100, 100, 1000, 690);
		//frame.setPreferredSize(new Dimension(1000,690));
		frame.setMinimumSize(new Dimension(750,625));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		//frame.pack();
		//currentProfile = new ProfilePanel(ProfileManager.getActiveProfile());
		//frame.getContentPane().add(currentProfile, BorderLayout.CENTER);
		frame.getContentPane().add(createStartScreen(), BorderLayout.CENTER); 
		frame.pack();
	}

	public JPanel createStartScreen(){
		JPanel start = new JPanel();
		start.setPreferredSize(new Dimension(300,300));
		start.setLayout(new BoxLayout(start,BoxLayout.PAGE_AXIS));
		message = ZComponentFactory.createBasicLabel("");
		message.setForeground(Color.RED);
		JComboBox<String> profileBox = new JComboBox<String>(ProfileManager.getProfileList());
		profileBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		profileBox.setPreferredSize(new Dimension(150,30));
		profileBox.setMinimumSize(new Dimension(150,30));
		profileBox.setMaximumSize(new Dimension(150,30));
		profileBox.setSelectedIndex(-1);
		JButton loadProfile = ZComponentFactory.createJButton("Load Profile", 150);
		loadProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
		loadProfile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(profileBox.getSelectedIndex() == -1){
					message.setText("No Profile Selected");
				} else {
					loadProfile(ProfileManager.getProfiles().get(profileBox.getSelectedItem()));
				}
				
				
			}});
		
		JButton newProfile = ZComponentFactory.createJButton("New Profile", 150);
		newProfile.setAlignmentX(Component.CENTER_ALIGNMENT);
		newProfile.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				newProfile();
				
			}});
		
		start.add(Box.createRigidArea(new Dimension(900,160)));
		start.add(message);
		start.add(Box.createRigidArea(new Dimension(900,20)));
		start.add(ZComponentFactory.createBasicLabel("Select Profile"));
		start.add(profileBox);
		start.add(Box.createRigidArea(new Dimension(900,20)));
		start.add(loadProfile);
		start.add(Box.createRigidArea(new Dimension(900,20)));
		start.add(newProfile);
		start.add(Box.createRigidArea(new Dimension(900,250)));
		
		return start;

	}
	
	private void loadProfile(Profile p){
		taskList.setEnabled(true);
		ProfileManager.setActiveProfile(p);
		currentProfile = new ProfilePanel(ProfileManager.getActiveProfile());
		frame.getContentPane().removeAll();
		frame.getContentPane().add(currentProfile, BorderLayout.CENTER);
		frame.pack();
		frame.revalidate();
		frame.repaint();
	}

	public JFrame getFrame() {
		return frame;
	}

	public void restart() {
		taskList.setEnabled(false);
		frame.getContentPane().removeAll();
		frame.getContentPane().add(createStartScreen(), BorderLayout.CENTER);
		frame.pack();
		frame.revalidate();
		frame.repaint();
	}
	
	public void newProfile(){
	
		String s = null;
		String s2 = null;
		Object[] options = {"Try again",
                "Main Menu",};
				
			boolean valid = false;
			
			while(!valid){
				s = (String)JOptionPane.showInputDialog(
		                frame,
		                "New Profile Name: ","Create New Profile",
		                JOptionPane.QUESTION_MESSAGE);
				
				if ((s != null) && (s.length() > 0)) {
				
					if(!ProfileManager.getProfiles().containsKey(s)){
						valid = true;
					} else {
						int option = JOptionPane.showOptionDialog(window.getFrame(),
						    "That Profile already exists.",
						    "Profile Name Taken",
						    JOptionPane.OK_CANCEL_OPTION,
						    JOptionPane.WARNING_MESSAGE,
						    null,
						    options,
						    options[1]);
						
						if(option != 0){
							valid = true;
							window.restart();
							return;	
						}
					}
				} else {
					int option = JOptionPane.showOptionDialog(window.getFrame(),
					    "Invalid entry.",
					    "Invalid entry",
					    JOptionPane.OK_CANCEL_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,
					    options[1]);
					
					if(option != 0){
						valid = true;
						window.restart();
						return;	
					}
				}
			}
				
			boolean done = false;
			
			while(!done){
				
				s2 = (String)JOptionPane.showInputDialog(
		                frame,
		                "New Task List Name: ","Create New Task List",
		                JOptionPane.QUESTION_MESSAGE);
			
				//If a string was returned, say so.
				if ((s2 != null) && (s2.length() > 0)) {
					done = true;
				} else {
					int option = JOptionPane.showOptionDialog(window.getFrame(),
					    "At least one Task List is required to create a new profile. What would you like to do?",
					    "Task List Required",
					    JOptionPane.OK_CANCEL_OPTION,
					    JOptionPane.WARNING_MESSAGE,
					    null,
					    options,
					    options[1]);
					
					if(option != 0){
						done = true;
						window.restart();
						return;	
					}
				}
			}
				
			Profile p = new Profile(s,s2);
			ProfileManager.addProfile(p);
			loadProfile(p);
			}
		
	}
	
	
	
	
