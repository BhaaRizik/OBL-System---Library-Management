
/**
 * 
 * In this class we get the possibility to extend the book 
 * 
 * ExtendMeth, in this function the system do the extension 
 * 
 * `when the subscriber pressed "Extension a book"
 * the system check the possibility of extension to the books that he have 
 * 
 *  
 *  fillLoanDetails, in this function the system show the data front of the client 
 *  
 *  NewAction, insert the extension operation to the data base
 *  
 *  
 *  
 *  
 *  
 * **/


package application;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BookExtensionController {

    @FXML
    private Button ExtendButton;

    @FXML
    private Button BackButton;

    @FXML
    private TableView<ItemInLoan> BookTableView;

    @FXML
    private TableColumn<ItemInLoan, String> BookIdCol;

    @FXML
    private TableColumn<ItemInLoan, String> BookNameCol;

    @FXML
    private TableColumn<ItemInLoan, String> LoanDateCol;

    @FXML
    private TableColumn<ItemInLoan, String> returnDateCol;

    @FXML
    void BackButtonMeth(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/SubscriberProfile.fxml").openStream());		
		Scene scene = new Scene(root);					
		primaryStage.setScene(scene);		 
		primaryStage.show();
	
		System.out.println(LoginController.GlobalSubscriberInformation);
		SubscriberProfileController SPC = (SubscriberProfileController) loader.getController(); 
		SPC.fillsubscriberdetails(LoginController.GlobalSubscriberInformation);

    }

    @FXML
    void ExtendMeth(ActionEvent event) throws ParseException, IOException {//hoon sht3'let
    	if(LoginController.GlobalSubscriberInformation.get(6).equals("Frozen")) {
    		MessagesAlert.infoBox("sorry Your Account is frozened \n please contact the manager!", "OBL Library");
				return;
    		
    	}
    	else {
    	ItemInLoan itemInLoan=BookTableView.getSelectionModel().getSelectedItem();
    	
    	
    	if(itemInLoan!=null) {
    	String dateStr=itemInLoan.getReturnDate();
    	String BookId=itemInLoan.getBookID();
    	
    
    
    	
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    	Date date = formatter.parse(dateStr);
    	Calendar calender = Calendar.getInstance();
    	calender.setTime(date);
    	calender.add(Calendar.DATE, 7);
    	Date newDate=calender.getTime();//new date       
    	String newDateExtension = formatter.format(newDate);//convert the new date to string
    	
    	 
    	ArrayList <String> ExtensionReturnDate=new ArrayList <String>();   	
    	ExtensionReturnDate.add(BookId);// id of book
    	ExtensionReturnDate.add(newDateExtension);// new date to extentsio
    	
    	
    	ExtensionReturnDate.add("Extension Return Date of book");
   	
    	newDateExtension=(String)IPController.client.Request(ExtensionReturnDate);
    	itemInLoan.setReturnDate(newDateExtension);
    
    	}else
    		 
 		   if(itemInLoan==null)
 		   {
 				MessagesAlert.infoBox("First,please choose a book from the table!", "OBL Library");
 				return;
 		   }
    	}
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/SubscriberProfile.fxml").openStream());		
		Scene scene = new Scene(root);					
		primaryStage.setScene(scene);		 
		primaryStage.show();
		SubscriberProfileController SPC = (SubscriberProfileController) loader.getController();
        SPC.fillsubscriberdetails(LoginController.GlobalSubscriberInformation);
        NewAction();
		
    }
    
    
	void fillLoanDetails(ArrayList<ItemInLoan> ItemLn) {
    	   	
    	    BookIdCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("BookID"));
	        BookNameCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("BookName"));
	        LoanDateCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("LoanDate"));
	        returnDateCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("returnDate"));
	        ObservableList<ItemInLoan> toShow = FXCollections.observableArrayList();
 	        toShow.addAll(ItemLn);
 	        System.out.println(" => " +toShow);
 	        BookTableView.setItems(toShow); 
    	
    }
    public void NewAction() {

    	ArrayList<String> newAction=new ArrayList<String>();
    	newAction.add(LoginController.GlobalSubscriberInformation.get(2));
    	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
    	Date date = new Date();    	
    	
    	newAction.add(dateFormat.format(date));
    	newAction.add("Extension Book");
    	newAction.add("add new action");
    	IPController.client.Request(newAction);
    	
    	
    }

}
