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
public class controllerComposeDraft implements Initializable {
	
	public String sorting = null;
	public String filtering = null;
	Mail m;
	File file;
	App apps ;
	
	 @FXML 
		private AnchorPane pane;
	 
	 @FXML 
		private TextField textreceiver;
	 
	 @FXML 
		private TextArea textbody;
	 
	 @FXML 
		private TextField textsub;
	 
	 @FXML 
		private Label labelsender;
	 @FXML 
		private Label warning;
		@FXML 
		private ComboBox<Integer> priority;
	 
	  @FXML
	  private ListView<String> list;
	  
	  @FXML
	  private ListView<String> listreceivers;
	  
	  public void addReceive(ActionEvent event)  {
		  warning.setText("");
		  if(!(textreceiver.getText()==null) && (verify.verifyEmailAddress(textreceiver.getText()))) {
			warning.setText("");
			File file = new File("Accounts\\index.txt");
			doublyLinkedLists accounts = ObjectReaderWriter.readFile(file);
			if(!verify.checkEmail(textreceiver.getText(), accounts)) {
			  if(!(textreceiver.getText().equals(apps.accountUser))) {
			    listreceivers.getItems().add(textreceiver.getText());
			    textreceiver.clear();
			  }else {
				  warning.setText("Can't send to yourself!");
			  }
			}else {
			   warning.setText("Receiver is not in the system");
			}
		  }else {
			  warning.setText("Please enter valid receiver!");
		  }
		}
	  public void deleteReceive(ActionEvent event)  {
		  listreceivers.getItems().removeAll(listreceivers.getSelectionModel().getSelectedItem());
		
		    }
	  
	    public void attach(ActionEvent event)  {
	    	FileChooser filechoosen=new FileChooser();
	    	filechoosen.setTitle("open");
	        File file=filechoosen.showOpenDialog( null);
	    
	    if(file!=null) {
	    list.getItems().add(file.getAbsolutePath());
	            
	    }
	    
	
	    }
	    public void delete(ActionEvent event)  {
			list.getItems().removeAll(list.getSelectionModel().getSelectedItem());
		
		    }
	    public void open(ActionEvent event) throws IOException  {
	    	  File file= new File(list.getSelectionModel().getSelectedItem());
		      Desktop desktop=Desktop.getDesktop();
		      desktop.open(file);
		      
		    }
	    
	    public void attachDone(ActionEvent event)  {
		SinglyLinkedLists path=new SinglyLinkedLists();
		 int k=list.getItems().size();
         for(int i=0;i<k;i++ ) {
        	 path.add(list.getItems().get(i));
         }
	
	    }
	

	
	
	public void receive(App app) {
		this.apps=app;
		file = new File("Accounts\\"+apps.accountUser+"\\Drafts\\index.txt");
		labelsender.setText(app.accountUser);
	}
	
	public void send(ActionEvent event) {
		File file = new File(m.getPath());
		EmailOperations.removeEmail(this.file, m);
		EmailOperations.deleteEmail(file);
		String subject = textsub.getText();
		String body = textbody.getText();
		QueuesLinkedList q = new QueuesLinkedList();
		SinglyLinkedLists att = new SinglyLinkedLists();
		if(listreceivers.getItems().size()!=0) {
		for(int i = 0; i<listreceivers.getItems().size();i++) {
			q.enqueue(listreceivers.getItems().get(i));
		}
		
		for(int i=0; i<list.getItems().size();i++) {
			att.add(list.getItems().get(i));
		}
		EmailOperations.check = true;
		Mail mail = new Mail(apps.accountUser,subject,q,body);
		if(priority.getValue() != null) {
		mail.setPriority(priority.getValue());
		}
		else {
			mail.setPriority(5);
		}
		mail.setAttachments(att);
		EmailOperations.createEmail(mail);
		EmailOperations.sendEmail(mail);
		mail.setStatus("Sent");
	    Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
		window.close();
	}
		else {
			warning.setText("Please enter any receiver!");
		}
		
	}
	
	public void save(ActionEvent event) {
		File file = new File(m.getPath());
		EmailOperations.removeEmail(this.file, m);
		EmailOperations.deleteEmail(file);
		String subject = textsub.getText();
		String body = textbody.getText();
		QueuesLinkedList q = new QueuesLinkedList();
		SinglyLinkedLists att = new SinglyLinkedLists();
		for(int i = 0; i<listreceivers.getItems().size();i++) {
			q.enqueue(listreceivers.getItems().get(i));
		}
		
		for(int i=0; i<list.getItems().size();i++) {
			att.add(list.getItems().get(i));
		}
		EmailOperations.check = false;
		Mail mail = new Mail(apps.accountUser,subject,q,body);
		if(priority.getValue() != null) {
			mail.setPriority(priority.getValue());
		}
		else {
			mail.setPriority(5);
		}
		mail.setAttachments(att);
		EmailOperations.createEmail(mail);
		mail.setStatus("Draft");
		Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
		window.close();
	}


	public void mailInfo(ActionEvent event) {
		String subject = textsub.getText();
		String body = textbody.getText();
		QueuesLinkedList q = new QueuesLinkedList();
		SinglyLinkedLists att = new SinglyLinkedLists();
		if(listreceivers.getItems().size()!=0) {
		for(int i = 0; i<listreceivers.getItems().size();i++) {
			q.enqueue(listreceivers.getItems().get(i));
		}
		
		for(int i=0; i<list.getItems().size();i++) {
			att.add(list.getItems().get(i));
		}
		EmailOperations.check = true;
		Mail mail = new Mail(apps.accountUser,subject,q,body);
		if(priority.getValue() != null) {
		mail.setPriority(priority.getValue());
		}
		else {
			mail.setPriority(5);
		}
		mail.setAttachments(att);
		EmailOperations.createEmail(mail);
		EmailOperations.sendEmail(mail);
		mail.setStatus("Sent");
	    Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
		window.close();
	}
		else {
			warning.setText("Please enter any receiver!");
		}
	}
	public void mailInfoDraft(ActionEvent event) {
		String subject = textsub.getText();
		String body = textbody.getText();
		QueuesLinkedList q = new QueuesLinkedList();
		SinglyLinkedLists att = new SinglyLinkedLists();
		for(int i = 0; i<listreceivers.getItems().size();i++) {
			q.enqueue(listreceivers.getItems().get(i));
		}
		
		for(int i=0; i<list.getItems().size();i++) {
			att.add(list.getItems().get(i));
		}
		EmailOperations.check = false;
		Mail mail = new Mail(apps.accountUser,subject,q,body);
		if(priority.getValue() != null) {
			mail.setPriority(priority.getValue());
		}
		else {
			mail.setPriority(5);
		}
		mail.setAttachments(att);
		EmailOperations.createEmail(mail);
		mail.setStatus("Draft");
			Stage window = (Stage)(((Node) event.getSource()).getScene().getWindow());
			window.close();
			
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
     priority.getItems().add(1);
     priority.getItems().add(2);
     priority.getItems().add(3);
     priority.getItems().add(4);
     priority.getItems().add(5);
	}
	
	public void identify (Mail email) {
		m = email;
		textsub.setText(email.getSubject());
		labelsender.setText(email.getSender());
		for(int i = 0;i<email.getReceiver().size();i++) {
		   String name = (String)email.getReceiver().dequeue();
		   email.getReceiver().enqueue(name);
		   listreceivers.getItems().add(name);
		}
		if(email.getAttachments() != null) {
			for(int i =0;i<email.getAttachments().size();i++) {
				list.getItems().add((String)email.getAttachments().get(i));
			}
		}

		textbody.setText(email.getBody());
		
		
	}
	
}