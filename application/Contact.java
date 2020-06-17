package application;

import java.io.Serializable;


public class Contact implements IContact,Serializable{

	private String firstName;
	private String lastName;
	private String userName;
	private String email;
	private String password;
	private int day;
	private int month;
	private int year;
	private String gender;

	public Contact(String email) {
		this.email = email;
	}
	public Contact(String firstName, String lastName, String userName, String email, String pass, int day, int month, int year,String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.email = email;
		this.password = pass;
		this.day = day;
		this.month = month;
		this.year = year;
		this.gender=gender;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPass(String password) {
		this.password = password;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setDate(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPass() {
		return this.password;
	}
	
	public String getUser() {
		return this.userName;
	}
	public String getGender() {
		return this.gender;
	}
	public int getDay() {
		return this.day;
	}
	
	public int getMonth() {
		return this.month;
	}
	
	public int getYear() {
		return this.year;
	}
}