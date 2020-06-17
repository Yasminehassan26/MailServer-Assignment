package application;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

public  class person {
    private  SimpleStringProperty Subject;
    private  SimpleStringProperty Sender;
    private SimpleStringProperty Date;
	private CheckBox Action;

    person(String name, String email, String date) {
        this.Subject = new SimpleStringProperty(name);
        this.Sender = new SimpleStringProperty(email);
        this.Date = new SimpleStringProperty(date);
		this.Action=new CheckBox();

    }

    public String getSubject() {
        return Subject.get();
    }
    public void setSubject(String name) {
        Subject.set(name);
    }
        
    public String getSender() {
        return Sender.get();
    }
    public void setSender(String email) {
        Sender.set(email);
    }
    
    public String getDate() {
        return Date.get();
    }
    public void setDate(String date) {
       Date.set(date);
    }
     public CheckBox getAction() {
	        return Action;
	    }
	    public void setAction(CheckBox selection) {
	       Action =selection;
	    }    
	 
	
}
