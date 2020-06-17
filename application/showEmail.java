package application;
import javafx.event.ActionEvent;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.SinglyLinkedLists;
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
public class showEmail{
	

	@FXML 
	private Label labelsub;
	
	@FXML 
	private Label labelsender;
	
	@FXML 
	private ListView<String> listReceiver;
	@FXML 
	private ListView<String> listAttach;
	@FXML 
	private TextArea message;
	
	
	
public void identify (Mail email) {
	labelsub.setText(email.getSubject());
	labelsender.setText(email.getSender());
	for(int i = 0;i<email.getReceiver().size();i++) {
	   String name = (String)email.getReceiver().dequeue();
	   email.getReceiver().enqueue(name);
	   listReceiver.getItems().add(name);
	   message.setEditable(false);
	}
	if(email.getAttachments() != null) {
		for(int i =0;i<email.getAttachments().size();i++) {
			listAttach.getItems().add((String)email.getAttachments().get(i));
		}
	}

	message.setText(email.getBody());
	message.setEditable(false);
	
	
}
public void open(ActionEvent event) throws IOException   {
	File file= new File(listAttach.getSelectionModel().getSelectedItem());
      Desktop desktop=Desktop.getDesktop();
      desktop.open(file);
      
    }

}