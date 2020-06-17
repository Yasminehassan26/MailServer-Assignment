package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.*;
import eg.edu.alexu.csd.datastructure.queue.cs77_56_91.QueuesLinkedList;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class EmailOperations {
	
	public static boolean check ;
	
	static FileInputStream fis = null;
	static InputStreamReader isr = null;
	static BufferedReader bufferRead = null;
	static FileOutputStream fos = null;
	static OutputStreamWriter osw = null;
	static BufferedWriter bufferWriter = null;
	static ObjectOutputStream owrite = null;

	
	
	public static Boolean checkReciever(File file,QueuesLinkedList receivers) {
		doublyLinkedLists accounts = ObjectReaderWriter.readFile(file);
		for(int i = 0; i < receivers.size(); i++) {
			String receiver = (String) receivers.dequeue();
			receivers.enqueue(receiver);
			Boolean flag = false;
			for(int j = 0;j<accounts.size();j++) {
				Contact user = (Contact)accounts.get(j);
				if(user.getEmail().equals(receiver)) {
					flag = true;
				}
			}
			if(!flag) {
				return false;
			}
		}
		return true;
	}
	
	public static void moveEmail(Folder email,Folder des) {
	  try {
		Path path =  Paths.get(des.getPath()+"\\"+email.getName());
		Files.move(email.file.toPath(), path, StandardCopyOption.REPLACE_EXISTING);
		String newPath = des.getPath();
		email.setPath(newPath+"\\"+email.getName());
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	}
	
	public static void deleteEmail(File mailToBeDeleted) {
		File[] allContents = mailToBeDeleted.listFiles();
	    if (allContents != null) {
	        for (File file : allContents) {
	            deleteEmail(file);
	        }
	    }
	    mailToBeDeleted.delete();
	}

	
	public static String sendOrDraft(Boolean check) {
		if(check) {
			return "Sent";
		}
		return "Drafts";
	}
	
	public static void attachments(SinglyLinkedLists attachments,Mail mail) {
      if(attachments != null) {
	    try {
	      for(int i=0;i<attachments.size();i++) {
		    File copy = new File(mail.getPath()+"\\Attachments\\index.txt");
		    copy.createNewFile();
		    ObjectReaderWriter.writeObject(copy, (String)attachments.get(i));
	      }
	    }
	    catch(Exception e) {
		  e.printStackTrace();
	    }
      }
	}
	
	public static void createEmail(Mail email) {
		Date d = new Date();
		email.setDate(d);
		String check = sendOrDraft(EmailOperations.check);
		int serial = EmailOperations.readSerialNum();
		email.setSerialNum(serial);
		EmailOperations.writeSerialNum(serial+1,App.serialNum);
		Folder mail = new Folder(Integer.toString(serial),"Accounts\\"+email.getSender()+"\\"+check);
		String path = "Accounts\\"+email.getSender()+"\\"+check+"\\"+serial;
		email.setPath(path);
		writeMail(mail.index, email);
		File index = new File("Accounts\\"+email.getSender()+"\\"+check+"\\index.txt");
		writeMail(index, email);
		File bodyEmail = new File("Accounts\\"+email.getSender()+"\\"+check+"\\"+Integer.toString(serial)+"\\Body.txt");
		File attachment = new File("Accounts\\"+email.getSender()+"\\"+check+"\\"+Integer.toString(serial)+"\\Attachments");
		try {
			bodyEmail.createNewFile();
			writeBody(bodyEmail,email.getBody());
			attachment.mkdir();
			attachments(email.getAttachments(),email);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void sendEmail(Mail email) {
		QueuesLinkedList receivers = email.getReceiver();
		Date d = new Date();
		email.setDate(d);
		for(int i = 0; i<receivers.size();i++) {
			String user = (String)receivers.dequeue();
			receivers.enqueue(user);
			QueuesLinkedList q = new QueuesLinkedList();
			q.enqueue(user);
			int serial = EmailOperations.readSerialNum();
			EmailOperations.writeSerialNum(serial+1,App.serialNum);
			Mail sent = new Mail(email.getSender(),email.getSubject(),q,email.getBody());
			sent.setSerialNum(serial);
			sent.setAttachments(email.getAttachments());
			sent.setPath("Accounts\\"+user+"\\Inbox\\"+serial);
			Date d2 = new Date();
			sent.setDate(d2);
			File inboxIndex = new File("Accounts\\"+user+"\\Inbox\\Index.txt");
			Folder sentEmail = new Folder(Integer.toString(serial),"Accounts\\"+user+"\\Inbox");
			File indexEmail = new File("Accounts\\"+user+"\\Inbox\\"+Integer.toString(serial)+"\\Index.txt");
			File bodyEmail = new File("Accounts\\"+user+"\\Inbox\\"+Integer.toString(serial)+"\\Body.txt");
			File attachment = new File("Accounts\\"+user+"\\Inbox\\"+Integer.toString(serial)+"\\Attachments");
			File source = new File(email.getPath()+"\\Attachments");
			EmailOperations.copyDirectory(source, attachment);
			try {
				bodyEmail.createNewFile();
				writeBody(bodyEmail,email.getBody());
				attachment.mkdir();
				attachments(email.getAttachments(),sent);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			writeMail(indexEmail,sent);
			writeMail(inboxIndex, sent);
		}
	}
	

	public static void removeFolder(File file,String name) {
		doublyLinkedLists folders = ObjectReaderWriter.readFile(file);
		Boolean check = true;
		int i;
		for( i = 0; check && i < folders.size();i++) {
			String folderName = (String) folders.get(i);
			if(folderName.equals(name)) {
				check = false;
			}
		}
		folders.remove(i-1);
		try {
		  fos = new FileOutputStream(file);
		  owrite = new ObjectOutputStream(fos);
		  owrite.writeObject(folders);
		  owrite.close();
		  fos.close();
		}
		catch(Exception e) {
			e.getStackTrace();
		}
	}
	
	public static void writeMail(File file,Mail user) {
		ObjectReaderWriter.writeObject(file, user);
	}
	
	public static void removeEmail(File file,Mail email) {
		doublyLinkedLists emails = ObjectReaderWriter.readFile(file);
		Boolean check = true;
		int i;
		for( i = 0; check && i < emails.size();i++) {
			Mail userEmail = (Mail) emails.get(i);
			if(userEmail.getSerialNum() == email.getSerialNum()) {
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
	
	public static void writeBody(File file, String email) {
	  try {
		fos = new FileOutputStream(file);
		osw = new OutputStreamWriter(fos);
		bufferWriter = new BufferedWriter(osw);
		bufferWriter.write(email);
		bufferWriter.close();
		osw.close();
		fos.close();
	  }
	  catch(Exception e) {
		  e.printStackTrace();
		  System.out.println("wrong body");
	  }
	}
	
	public static void copyFile(File original, File copy) {
		try {
		   fis = new FileInputStream(original);
		   fos = new FileOutputStream(copy);
		   int aByte;
		   while((aByte = fis.read()) != -1) {
			  fos.write(aByte);
		   }
		   fos.close();
		   fis.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void copyDirectory(File sourceFolder, File destinationFolder) {
	  try {
	        if (sourceFolder.isDirectory()) 
	        {
	            if (!destinationFolder.exists()) 
	            {
	                destinationFolder.mkdir();
	            }
	             
	            
	            String files[] = sourceFolder.list();
	             
	            for (String file : files) 
	            {
	                File srcFile = new File(sourceFolder, file);
	                File destFile = new File(destinationFolder, file);
	                 
	                copyDirectory(srcFile, destFile);
	            }
	        }
	        else
	        {
	            Files.copy(sourceFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.REPLACE_EXISTING);
	        }
			
	  }
	  catch(Exception e) {
		e.printStackTrace();
	  }
	}
	
	public static void writeSerialNum(int num,File file) {
	  try {
		fos = new FileOutputStream(file);
		fos.write(num);
		fos.close();
	  }
	  catch(Exception e) {
		  e.printStackTrace();
	  }
	}
	
	public static int readSerialNum() {
		int num = 0;
		  try {
			fis = new FileInputStream(App.serialNum);
			num = fis.read();
			fis.close();
		  }
		  catch(Exception e) {
			  e.printStackTrace();
		  }
		  return num;
		}
	
  }