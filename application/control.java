package application;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class control  {
	
	public static App app = new App();
	
public	String verifiedEmail;
@FXML
private Label lblstatus;
@FXML
private TextField txtUserName;
@FXML
private TextField txPassword;
@FXML
private Label lblstatus1;
@FXML
private Label lblstatus2;
@FXML
private TextField txtUserName1;
@FXML
private TextField txPassword1;
@FXML
private TextField txtmail;
@FXML
private TextField txRePass;

@FXML
private TextField txtfirstName;
@FXML 
private TextField txtlastName;
@FXML 
private DatePicker date;

@FXML 
private ComboBox<String> gender;

@FXML 
private Label userlabel;
@FXML 
private Label firstName;
@FXML 
private Label Lastname;
@FXML 
private Label email;
@FXML 
private Label datelabel;
@FXML 
private Label genderlabel;



ObservableList<String> list=FXCollections.observableArrayList("Female","Male");



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void login (ActionEvent event) {
  ObjectReaderWriter object=new ObjectReaderWriter();
  App app=new App();

  doublyLinkedLists	 users =  object.readFile(app.index);

  verify Verify = new verify();
if(Verify.verifyLogin(txtUserName.getText(),txPassword.getText())){
if(app.signin(txtUserName.getText(),txPassword.getText()))
{   verifiedEmail=txtUserName.getText();
	app.accountUser = txtUserName.getText();
	controllerInbox.receive(app);
	inbox(event);
}
	
else {

int n=	Verify.correctPass(txtUserName.getText(), txPassword.getText(), users);
	if(n==2) {
		lblstatus.setText("wrong password ! try again");
	}
	else if(n==1) {
		lblstatus.setText("Email not found ! try again");

	} }}
else {
	lblstatus.setText("Please fill all the boxes");
}

}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void signUp1 (ActionEvent event ) {	
	scenes test=new scenes();
	test.SignUp(event);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void signUp (ActionEvent event ) {
	
     ObjectReaderWriter object=new ObjectReaderWriter();
     App app=new App();
     doublyLinkedLists users =  object.readFile(app.index);
     verify Verify = new verify();

     if((!(gender.getValue()==null))&&(!(date.getValue() == null))&& Verify.verifyContact(txtfirstName.getText(), txtlastName.getText(),txtUserName1.getText(),txtmail.getText(), txPassword1.getText())) {
     Contact contact=new Contact(txtfirstName.getText(), txtlastName.getText(),txtUserName1.getText(),txtmail.getText()+"@mfy.com", txPassword1.getText(),date.getValue().getDayOfMonth(),date.getValue().getMonthValue(), date.getValue().getYear(),gender.getValue());

    	 if(Verify.verifyPass( txPassword1.getText(), txRePass.getText())) {
    		 if( app.signup(contact)) {
    			 verifiedEmail=txtmail.getText()+"@mfy.com";
    			 app.accountUser = txtmail.getText()+"@mfy.com";
    			 controllerInbox.receive(app);
    			 inbox(event);
    	 }
    		 else {
    		  if(!Verify.verifyEmailAddress(txtmail.getText()+"@mfy.com")) {
    			  lblstatus1.setText("Wrong Email Format");
	    			lblstatus2.setText("enter another one");
    		  }
    		  else if(!Verify.checkEmail(txtmail.getText()+"@mfy.com", users)) {
				 lblstatus1.setText("Email already exist,");
	    			lblstatus2.setText("enter another one");
			 }
    			  else if(!Verify.checkUserName(txtUserName1.getText(), users)) {
    	    			lblstatus1.setText("Username already exist,");
    	    			lblstatus2.setText("enter another one");
    			 }
    			 
    		 }
    	 }
    	 else {
    		 lblstatus2.setText("");
    			lblstatus1.setText("Verify the password");

    	 }
	} else {
		    lblstatus2.setText("");
 			lblstatus1.setText("Please fill all the boxes");

    	 }
    	
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
public void inbox (ActionEvent event ) {
	try {
		Parent  root = FXMLLoader.load(getClass().getResource("/application/inbox.fxml"));
		scenes test=new scenes();
		test.inbox(event);
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void profile() {
	File file=new File("Accounts\\index.txt");
	doublyLinkedLists list=ObjectReaderWriter.readFile(file);
	Contact contact = null;
	for(int i=0;i<list.size();i++) {
		 contact=(Contact)list.get(i);
		if (contact.getEmail().equals(verifiedEmail)) {
			break;
		}
	}
    firstName.setText(contact.getFirstName());
	Lastname.setText(contact.getLastName());
	userlabel.setText(contact.getUser());
	genderlabel.setText(contact.getGender());
	datelabel.setText(""+contact.getDay()+"/"+contact.getMonth()+"/"+contact.getYear()+".");
	email.setText(contact.getEmail()) ;
}
public void backMain(ActionEvent event ) {
	scenes test=new scenes();
	test.backMain(event);
}



}