package engine;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class ProfileManager {

	private static HashMap<String,Profile> profiles;
	private static Profile activeProfile;

	
	static{
		profiles = new HashMap<String,Profile>();
		loadProfileList();
	}

	public static HashMap<String,Profile> getProfiles() {
		return profiles;
	}

	public static void setProfiles(HashMap<String,Profile> profiles) {
		ProfileManager.profiles = profiles;
	}

	public static Profile getActiveProfile() {
		return activeProfile;
	}

	public static void setActiveProfile(Profile activeProfile) {
		ProfileManager.activeProfile = activeProfile;
	}
	
	public static String[] getProfileList(){
		String[] profileList = new String[profiles.size()];
		int i = 0;
		for (Profile p: profiles.values()){
			profileList[i] = p.getName();
			i++;
		}
		return profileList;
	}

	public static void deleteProfile(String s){
		profiles.remove(s);
		saveProfileList();
	}

	public static void addProfile(Profile p) {
		profiles.put(p.getName(), p);
		saveProfileList();
		
	}

	private static void loadProfileList(){
		File f = new File("profileList.ser");
		if(f.exists()){
			HashMap<String,Profile> profileList = deserialize("profileList.ser");
			setProfiles(profileList);
		}
		
	}
	
	public static void saveProfileList(){
		serialize(profiles,"profileList.ser");
	}
	
	
	@SuppressWarnings("unchecked")
	private static HashMap<String,Profile> deserialize(String filePath) {
		
		HashMap<String,Profile> profileList = new HashMap<String,Profile>();
	
		try(ObjectInputStream deserializer = new ObjectInputStream(new FileInputStream(filePath))) {
			
			profileList = (HashMap<String,Profile>) deserializer.readObject();
			
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profileList;
	}
	
	public static void serialize(HashMap<String,Profile> profileList, String filePath){
		
		
		
		try (ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream(filePath))){
			
			serializer.writeObject(profileList);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
