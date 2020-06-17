package application;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.SinglyLinkedLists;
import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;
import eg.edu.alexu.csd.datastructure.queue.cs77_56_91.QueuesLinkedList;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
public class showContact  {
	App apps ;
	 @FXML 
		private TextField textreceiver;
	 @FXML
	  private ListView<String> list;
	 @FXML
	  private Label checkContact;
	 @FXML
	  private Label checkContact1;
		
		
	public void receive(App app) {
		apps=app;
		apps.contact = new File("Accounts\\"+apps.accountUser+"\\Contacts.txt");
		 doublyLinkedLists contacts = ObjectReaderWriter.readFile(apps.contact);
		  if(contacts!=null) {
			  for(int i =0;i<contacts.size();i++) {
				  Contact user = (Contact)contacts.get(i);
				  list.getItems().add(user.getEmail());	
			  }
		  }
	}
	public void myProfile(Contact contact) {
		
		
		
	}

	
	  
	  public void addReceive(ActionEvent event)  {
		  if(verify.verifyEmailAddress(textreceiver.getText())) {
			checkContact.setText("");
			checkContact1.setText("");
			list.getItems().add(textreceiver.getText());
			Contact user = new Contact(textreceiver.getText());
			contactsOperations.writeContact(apps.contact, user);
			textreceiver.clear();
		  }else {
			  checkContact.setText("Invalid EMAIL ADDRESS!");
			  checkContact1.setText("Please enter a valid one.");
		  }
			}
	  public void deleteReceive(ActionEvent event)  {
		  if(!list.getItems().isEmpty()) {
		  Contact user = new Contact(list.getSelectionModel().getSelectedItem());
		  list.getItems().removeAll(list.getSelectionModel().getSelectedItem());
		  contactsOperations.deleteContact(apps.contact, user);}
	  }
	 
}