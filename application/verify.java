package application;



import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.*;

public class verify {

	public static int correctPass(String email, String password, doublyLinkedLists usersAccount) {
		for(int i = 0; i < usersAccount.size(); i++) {
			Contact user = (Contact) usersAccount.get(i);
			String userEmail = user.getEmail();
			String userPass = user.getPass();
			if(email.equals(user.getEmail())) {
				if(password.equals(user.getPass())) {
					return 0;
				}
				else {
					return 2;
				}
			}
		}
		return 1;
	}
	
	public static Boolean checkUserName(String userName, doublyLinkedLists usersAccount) {
		for(int i = 0; i < usersAccount.size(); i++) {
			Contact user = (Contact) usersAccount.get(i);
			if(userName.equals(user.getUser())) {
              return false;
			}
		}
		return true;
	}
	
	public static Boolean checkEmail(String email, doublyLinkedLists usersAccount) {
		if(!verifyEmailAddress(email)) {
			return false;
		}
		for(int i = 0; i < usersAccount.size(); i++) {
			Contact user = (Contact) usersAccount.get(i);
			if(email.equals(user.getEmail())) {
              return false;
			}
		}
		return true;
	}
	
	public static Boolean verifyPass(String passOne, String passTwo) {
		if(passOne.equals(passTwo)) {
			return true;
		}
		return false;
	}
	
	public static Boolean verifyContact(String firstName, String lastName, String userName, String email, String pass) {
		if(firstName.isEmpty()) {
			return false;
		}
		if(lastName.isEmpty()) {
			return false;
		}
		if(userName.isEmpty()) {
			return false;
		}
		if(email.isEmpty()) {
			return false;
		}
		if(pass.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public static Boolean verifyEmailAddress(String email) {
		String regex = "^[A-Za-z0-9_.-]+@mfy.com$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		if(matcher.matches()) {
			return true;
		}
		return false;
	}
	
	public static Boolean verifyLogin(String mail, String pass) {
		if(mail.isEmpty()) {
			return false;
		}
		if(pass.isEmpty()) {
			return false;
		}
		return true;
	}
}