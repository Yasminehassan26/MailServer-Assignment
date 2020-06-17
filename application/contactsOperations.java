package application;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;

public class contactsOperations {
	
	static FileOutputStream fos = null;
	static ObjectOutputStream owrite = null;
	
	
	public static void writeContact(File file,Contact user) {
		ObjectReaderWriter.writeObject(file, user);
	}
	
	public static void deleteContact(File file,Contact user) {
		doublyLinkedLists emails = ObjectReaderWriter.readFile(file);
		Boolean check = true;
		int i;
		for( i = 0; check;i++) {
			Contact userContact = (Contact) emails.get(i);
			if(userContact.getEmail().equals(user.getEmail())) {
				check = false;
			}
		}
		emails.remove(i-1);
		try {
		  fos = new FileOutputStream(file);
		  owrite = new ObjectOutputStream(fos);
		  owrite.writeObject(emails);
		  owrite.close();
		  fos.close();
		}
		catch(Exception e) {
			e.getStackTrace();
		}
	}

}
