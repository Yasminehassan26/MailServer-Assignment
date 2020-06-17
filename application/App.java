package application;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.*;

public class App implements IApp {
	
	public static  doublyLinkedLists filteredList;
	public String path;
	public String accountUser;
	public Folder folder ;
	int pageNumber ;
	public String sorting ;
	public String filtering ;
	public String searchAbout;
	public static String extraFolderName;
	public File contact;

	File account = new File("Accounts");
	File index = new File("Accounts\\index.txt");
	public static File serialNum = new File("Accounts\\serialNumber.txt");
	doublyLinkedLists usersAccount = new doublyLinkedLists(); 
	App(){
	  try {
		if(!account.exists()) {
			if(account.mkdir()) {
				index.createNewFile();
				serialNum.createNewFile();
				EmailOperations.writeSerialNum(0,serialNum);
				System.out.println("Done accounts");
			}
		}
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	}
	
	@Override
	public boolean signin(String email, String password) {
		usersAccount = ObjectReaderWriter.readFile(index);
        int correct = verify.correctPass(email, password, usersAccount);
        if(correct == 0) {
        	return true;
        }
		return false;
	}
	
	@Override
	public boolean signup(IContact contact) {
		Contact newContact = (Contact) contact;
		usersAccount = ObjectReaderWriter.readFile(index);
		if((!verify.checkEmail(newContact.getEmail(), usersAccount)) || (!verify.checkUserName(newContact.getUser(), usersAccount))) {
			return false;
		}
		contactsOperations.writeContact(index, newContact);
		File newAcc = new File("Accounts\\"+newContact.getEmail());
		try {
		newAcc.mkdir();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		String path = "Accounts\\"+newContact.getEmail();
		Folder inbox = new Folder("Inbox",path);
		Folder draft = new Folder("Drafts",path);
		Folder sent = new Folder("Sent",path);
		Folder trash = new Folder("Trash",path);
		File contacts = new File("Accounts\\"+newContact.getEmail()+"\\Contacts.txt");
		try {
		contacts.createNewFile();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void setViewingOptions(IFolder folder, IFilter filter, ISort sort) {
	  Folder newFolder = (Folder)folder;
	  Filter newFilter = (Filter)filter;
	  Sort newSort = (Sort)sort;
	  doublyLinkedLists list = ObjectReaderWriter.readFile(newFolder.index);
	  if(list.size() != 0) {
	    if((newFilter.typeOfSearch == null) && (newSort.getTypeOfSort() == null)) {
		  newSort.setTypeOfSort("Date");
		  newSort.iterativeQsort(list);
		  filteredList = list;
	    }
	    else if (newSort.getTypeOfSort().equals("Priority") && (newFilter.typeOfSearch == null)) {
		  filteredList = newSort.sortPriority(list);
	    }
	    else if (newSort.getTypeOfSort().equals("Priority") && (newFilter.typeOfSearch != null)) {
		  doublyLinkedLists list1 = newSort.sortPriority(list);
		  filteredList = newFilter.BinSearch(list1, searchAbout);
	    }
	    else if (newFilter.typeOfSearch == null) {
		  newSort.setTypeOfSort(newSort.getTypeOfSort());
		  newSort.iterativeQsort(list);
		  filteredList = list;
	    }
	    else if(newSort.getTypeOfSort() == null) {
		  newSort.setTypeOfSort("Date");
		  newSort.iterativeQsort(list);
		  filteredList = newFilter.BinSearch(list, searchAbout);
	    }
	    else {
		  newSort.setTypeOfSort(newSort.getTypeOfSort());
		  newSort.iterativeQsort(list);
		  filteredList =  newFilter.BinSearch(list, searchAbout);
	    }
	   }
	  else {
		  filteredList = list;
	  }
	}
	
	@Override
	public IMail[] listEmails(int page) {
	  doublyLinkedLists list = null;
	  doublyLinkedLists subList = new doublyLinkedLists();
	  Mail[] emails = null;
	 
	  int first = (page*10) - 10;
	  int last = (page*10) - 1;

	 
		  list = filteredList;
		  if(last >= list.size()) {
			  last = list.size() - 1;
		  }
	   if(first >= list.size()) {
		  return null;
	  }
		  subList = (doublyLinkedLists) list.sublist(first, last);
		  emails = new Mail[subList.size()];
		  for(int i = 0; i < subList.size(); i++) {
			  emails[i] = (Mail) subList.get(i);
		  }
		return emails;
	}
	
	@Override
	public void deleteEmails(ILinkedList mails) {
		doublyLinkedLists emails = (doublyLinkedLists)mails;
		Folder trash = new Folder("Accounts\\"+accountUser+"\\Trash");
		moveEmails(mails,trash);		
	}
	
	@Override
	public void moveEmails(ILinkedList mails, IFolder des) {
		doublyLinkedLists emails = (doublyLinkedLists)mails;
		Folder destination = (Folder)des;
		for(int i = 0; i < emails.size(); i++) {
		  Mail mail = (Mail) emails.get(i);
		  Folder folder1 = new Folder(mail.getPath());
		  folder1.setName(Integer.toString(mail.getSerialNum()));
		  EmailOperations.moveEmail(folder1, destination);
		  EmailOperations.writeMail(destination.index, mail);
		  EmailOperations.removeEmail(folder.index, mail);
		}
	}
	
	@Override
	public boolean compose(IMail email) {
		Mail mail = (Mail) email;
		File index = new File("Accounts\\index.txt");
		if(EmailOperations.checkReciever(index, mail.getReceiver())) {
			EmailOperations.check = true;
			EmailOperations.createEmail(mail);
			EmailOperations.sendEmail(mail);
		}
		return false;
	}
	
	
	
	
}