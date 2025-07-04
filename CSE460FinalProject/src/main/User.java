package main;

import java.util.ArrayList;

public class User {
	
	private int ID;
	private String username;
	private String role;
	private String password;
	private ArrayList<Integer> orders;
	
	public User(int iD, String username, String role, String password) {
		ID = iD;
		this.username = username;
		this.role = role;
		this.password = password;
	}

	public int getID() {
		return ID;
	}

	public String getUsername() {
		return username;
	}

	public String getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}
}
