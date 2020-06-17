package application;

import java.io.File;
import java.nio.file.Path;

public class Folder implements IFolder {

	private String name;
	private String path;
	
	File file;
	File index;
	
	public Folder(String path) {
		this.path = path;
		file = new File(this.path);
		index = new File(this.path+"\\Index.txt");
		 try {
			if(!file.exists()) {
			  file.mkdir();
			  index.createNewFile();
			}
		 }
	     catch(Exception e){
	       e.printStackTrace();
		 }
	}
	
	public Folder(String name,String path){
		this.name = name;
		this.path = path+"\\"+name;
		file = new File(this.path);
		index = new File(this.path+"\\Index.txt");
		 try {
			if(!file.exists()) {
			  file.mkdir();
			  index.createNewFile();
			}
		 }
	     catch(Exception e){
	       e.printStackTrace();
		 }
	}


	public void setPath(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return this.path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}