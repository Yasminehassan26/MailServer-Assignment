package application;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.URL;
import java.util.ResourceBundle;

import eg.edu.alexu.csd.datastructure.linkedList.cs77_cs84.doublyLinkedLists;
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

public class controllerDraft implements Initializable {

	public String sorting = null;
	public String filtering = null;
	Folder inbox ;
	static App apps ;

	@FXML 
	private TableView<person> tableD;
	@FXML 
	private TextField search;
	@FXML 
	private TextField folderName;
	@FXML 
	private TableColumn<person,String> Subject;
	@FXML 
	private TableColumn<person,String> Sender;
	@FXML 
	private TableColumn<person,String> Date;
	@FXML 
	private TableColumn<person,String> Action;
	@FXML 
	private Label label1;
	@FXML 
	private ListView<String> listBox;
	@FXML 
	private ComboBox<String> sort;
	@FXML 
	private ComboBox<String> Filter;
	@FXML 
	private Label page;
	ObservableList<person> list;
	@FXML 
	private Label userLabel;
	@FXML 
	private Label labelDelete;
	@FXML 
	private Label labelMove;
	@FXML 
	private ListView<String> listMove;
   
   
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		userLabel.setText(apps.accountUser);
		labelDelete.setText("");
		userLabel.setText(apps.accountUser);
		folderName.setPromptText("enter the folder name");
		folderName.setEditable(true);

		if(apps.pageNumber == 0) {
			apps.pageNumber = 1;
		}
		String pageNum=""+apps.pageNumber+"";
		page.setText(pageNum);
		ObservableList<person>	received = getData();
	Subject.setCellValueFactory( new PropertyValueFactory<person,String>("Subject"));
	Sender.setCellValueFactory( new PropertyValueFactory<person,String>("Sender"));
	Date.setCellValueFactory( new PropertyValueFactory<person,String>("Date"));
	Action.setCellValueFactory( new PropertyValueFactory<person,String>("Action"));

	tableD.setOnMouseClicked(e -> {
		if(!tableD.getItems().isEmpty()) {
			show();
		  }
	});
	
			
        tableD.setItems(received);
	      
        listBox.getItems().addAll("Inbox","Sent","Draft","Trash");
    	File file = new File("Accounts\\"+apps.accountUser+"\\ExtraFolders.txt");
    	if(file.exists()) {
    		doublyLinkedLists folders = ObjectReaderWriter.readFile(file);
    		for(int i =0;i<folders.size();i++) {
    			listBox.getItems().add((String)folders.get(i));
    			listMove.getItems().add((String)folders.get(i));
    		}
    	}


	}
//////////////////////////////////////////////////////////////////////////////////////
	/**
	 * add the mails to the table
	 * @return
	 */
	public ObservableList<person> getData(){
		inbox = new Folder("Accounts\\"+apps.accountUser+"\\Drafts");
		list = FXCollections.observableArrayList();
		Sort s = new Sort();
		s.setTypeOfSort(sorting);
		Filter f = new Filter(filtering);
		apps.setViewingOptions(inbox, f, s);
		if(apps.filteredList != null && apps.filteredList.size()!= 0) {
		  Mail[] mails = (Mail[]) apps.listEmails(apps.pageNumber);
		  if(mails != null) {
		    for(int i = 0; i < mails.length ;i++) {
		      Mail email = mails[i];
		      list.add(new person(email.getSubject(),email.getSender(),email.getDate().toString()));
		    }
		  }
		}
		return list;
	}
	
	/**
	 * function that delete selected mails from the table
	 */
	ObservableList<person> sent;
	@FXML
	public void deleteSelected(ActionEvent event ) {
		apps.folder = new Folder("Accounts\\"+apps.accountUser+"\\Drafts");
		ObservableList<person>	 Deleted =  FXCollections.observableArrayList();
		doublyLinkedLists deletedEmails = new doublyLinkedLists();
		for(int i=0;i<list.size();i++) {
			if(list.get(i).getAction().isSelected()) {	
				int indexOfMail = ((apps.pageNumber*10)-10)+i;
				Mail m = (Mail) apps.filteredList.get(indexOfMail);
				deletedEmails.add(m);
				Deleted.add(list.get(i));			
			}
		}
		if(deletedEmails.isEmpty()) {
			labelDelete.setText("No Mails Selected!");
		}
		else {
		labelDelete.setText("");
		list.removeAll(Deleted);
        apps.deleteEmails(deletedEmails);
		}
	}
	
	public void Move(ActionEvent event) {
		if(listMove.getSelectionModel().getSelectedItem()==null) {
			labelMove.setText("Select Folder Please");
		}
		else {
			labelMove.setText("");
			Folder folder = new Folder("Accounts\\"+apps.accountUser+"\\"+listMove.getSelectionModel().getSelectedItem());
			ObservableList<person>	 Deleted =  FXCollections.observableArrayList();
			doublyLinkedLists movedEmails = new doublyLinkedLists();
			for(int i=0;i<list.size();i++) {
				if(list.get(i).getAction().isSelected()) {	
					int indexOfMail = ((apps.pageNumber*10)-10)+i;
					Mail m = (Mail) apps.filteredList.get(indexOfMail);
					movedEmails.add(m);
					Deleted.add(list.get(i));			
			    }
		    }
			if(movedEmails.isEmpty()) {
				labelMove.setText("No Mails Selected!");
			}
			else {
			labelMove.setText("");
			list.removeAll(Deleted);
	        apps.moveEmails(movedEmails, folder);
			}
		}
	}
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * function that show the selected email
	 */
	public void show() {

		Parent root;
	
		try {
			FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/compose2.fxml"));
			int number = tableD.getSelectionModel().getSelectedIndex();
			int indexOfMail = ((apps.pageNumber*10)-10)+number;
			Mail m = (Mail) apps.filteredList.get(indexOfMail);
			root=loader.load();
        	controllerComposeDraft c=loader.getController();     	
            c.receive(apps);
            c.identify(m);
        	Scene scene = new Scene(root);
            Stage stage=new Stage();
            stage.setTitle("Compose");
      		stage.setScene(scene);
 		   stage.setResizable(false);
 	
 	    	stage.show();	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
///////////////////////////////////////////////////////////////////
	/**
	 * @param event Function that add a new folder
	 */
	public void addFolder (ActionEvent event ) {
		String add=folderName.getText();
		if(!add.isEmpty()) {
		String path = "Accounts\\"+apps.accountUser;
		Folder newFolder = new Folder(add,path);
		File file = new File(path+"\\ExtraFolders.txt");
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		ObjectReaderWriter.writeObject(file, add);
		folderName.clear();
		listBox.getItems().add(add);
	}
	}
	/**
	 * function that delete selected folder
	 * @param event
	 */
	public void deleteFolder (ActionEvent event) {
		String name=listBox.getSelectionModel().getSelectedItem();
        if(name!="Inbox" && name!="Sent" &&name!="Trash" &&name!="Draft") {
        String folderToDelete = listBox.getSelectionModel().getSelectedItem();
        File file = new File("Accounts\\"+apps.accountUser+"\\"+folderToDelete);
        File file2 = new File("Accounts\\"+apps.accountUser+"\\ExtraFolders.txt");
		listBox.getItems().removeAll(listBox.getSelectionModel().getSelectedItem());
		EmailOperations.deleteEmail(file);
		EmailOperations.removeFolder(file2, folderToDelete);
	}
	}
	/**
	 * function that organize the folders
	 * @param event
	 * @throws IOException 
	 */
	public void work(ActionEvent event)  {

	String name=listBox.getSelectionModel().getSelectedItem();
	if(name=="Inbox") {
		try {
			apps.pageNumber = 1;
			apps.sorting = "Date";
			apps.filtering = null;
			apps.searchAbout = null;
			Parent  root = FXMLLoader.load(getClass().getResource("/application/inbox.fxml"));
			scenes test=new scenes();
			test.inbox(event);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	else if(name=="Sent") {
		apps.pageNumber = 1;
		apps.sorting = "Date";
		apps.filtering = null;
		apps.searchAbout = null;
		controllerSent.receive(apps);
		scenes test=new scenes();
		test.sent(event);
	}
	else if(name=="Trash") {
		apps.pageNumber = 1;
		apps.sorting = "Date";
		apps.filtering = null;
		apps.searchAbout = null;
		controllerTrash.receive(apps);
		scenes test=new scenes();
		test.Trash(event);
	}
	else if(name=="Draft") {
		FXMLLoader  loader=new FXMLLoader(getClass().getResource("/application/draft.fxml"));
		apps.pageNumber = 1;
		apps.sorting = "Date";
		apps.filtering = null;
		apps.searchAbout = null;
		   controllerDraft.receive(apps);
		 scenes test=new scenes();
			test.Draft(event);
		}
	else {
		Parent root;
		FXMLLoader  loader=new FXMLLoader(getClass().getResource("/application/folder.fxml"));
		apps.pageNumber = 1;
		apps.sorting = "Date";
		apps.filtering = null;
		apps.searchAbout = null;
		controllerFolder.receive(apps);
		apps.extraFolderName =  name;
		scenes test=new scenes();
	    test.folder2(event);
        
        
		
	}
	
	}
/////////////////////////////////////////////////////////////////	
	public void SortCombo(ActionEvent event) {
		   apps.sorting = sort.getValue();
		}
			
			
		public void FilterCombo(ActionEvent event) {
		  apps.filtering = Filter.getValue();
		}
			
		public void okay(ActionEvent event) {
			apps.sorting = sort.getValue();
			apps.filtering = Filter.getValue();
			apps.searchAbout = search.getText();
			inbox(event);
		} 


////////////////////////////////////////////////
	
		
	public void switchPagesNext (ActionEvent event ) {
		apps.pageNumber++;
		inbox(event);
	}	
	public void switchPagesPrev (ActionEvent event ) {
	   if(apps.pageNumber > 1) {
		 apps.pageNumber--;
	   }
	   inbox(event);
	}
	
	
	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

public void inbox (ActionEvent event ) {
	FXMLLoader  loader=new FXMLLoader(getClass().getResource("/application/trash.fxml"));
	 controllerDraft.receive(apps);
	 scenes test=new scenes();
	test.Draft(event);
}
public void backMain(ActionEvent event ) {
	scenes test=new scenes();
	test.backMain(event);
}

public void Compose (ActionEvent event ) {
	Parent root;
	try {
		FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/composeDraft.fxml"));
		root = loader.load();
		controllerComposeDraft c=loader.getController();
		
	     c.receive(apps);
	   
				Scene scene = new Scene(root);
		           Stage stage=new Stage();
		           stage.setTitle("My Mail");
		     		stage.setScene(scene);
				   stage.setResizable(false);
			
				stage.show();	
	    
	} catch (IOException e) {
		e.printStackTrace();
	}

}
public static void receive(App app) {
	apps=app;
}

public void Contacts (ActionEvent event ) {
	Parent root;
	try {
	FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/contacts.fxml"));
	root = loader.load();
	showContact c=loader.getController();
	
   c.receive(apps);
   
			   Scene scene = new Scene(root);
	           Stage stage=new Stage();
	           stage.setTitle("My Contacts");
	     	   stage.setScene(scene);
			   stage.setResizable(false);
			   stage.show();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
public void profile (ActionEvent event ) {
	Parent root;
	try {
	FXMLLoader loader=new FXMLLoader(getClass().getResource("/application/profile.fxml"));
	root = loader.load();
	control c=loader.getController();		
    c.profile();
			   Scene scene = new Scene(root);
	           Stage stage=new Stage();
	           stage.setTitle("My Profile");
	     	   stage.setScene(scene);
			   stage.setResizable(false);
			   stage.show();
	} catch (IOException e) {
		e.printStackTrace();
	}
}
}