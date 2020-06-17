package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.*;
import eg.edu.alexu.csd.datastructure.queue.cs77_56_91.QueuesLinkedList;

public class ObjectReaderWriter {
	

	static FileOutputStream fos = null;
	static ObjectOutputStream owrite = null;
	static FileInputStream fis = null;
	static ObjectInputStream Oread = null;
	
	public static doublyLinkedLists readFile(File file) {
	  doublyLinkedLists list = new doublyLinkedLists();
	  if(file.length()!=0) {
		try {
		fis = new FileInputStream(file);
		Oread = new ObjectInputStream(fis);
		list = (doublyLinkedLists) Oread.readObject();
		Oread.close();
		fis.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	  }
		return list;
	}
	

	public static void writeObject(File file,Object user) {
		doublyLinkedLists list = new doublyLinkedLists();
		if(file.length()!=0) {
			list = readFile(file);
		}
		list.add(user);
		try {
		fos = new FileOutputStream(file);
		owrite = new ObjectOutputStream(fos);
		owrite.writeObject(list);
		owrite.close();
		fos.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

}