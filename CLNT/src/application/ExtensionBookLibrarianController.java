/**
 * 
 * 
 * In this class extension is available to the librarian 
 * 
 * if the librarian implements the extension operation , then he/she need to 
 * enter the subscriber ID and check the books that he can extend loaning
 * 
 * ExtendMeth, in this function the librarian extend a book to certain subscriber 
 * 
 * fillLoanDetails, in this function the system show the data front of the client 
 *  
 *  ViewBookSubscriber, in this function the system view the books with subscriber
 *  
 * 
 * @author Jameel Othman
 * */



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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class ExtensionBookLibrarianController {
	 @FXML
	    private TextField SubsccriberIdText;

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
    	if(LoginController.WhoIsThis.equals("Librarian"))
    	{
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/Sample.fxml").openStream());		
		Scene scene = new Scene(root);					
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	else 
    	{
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/application/ManagerProfile.fxml").openStream());		
    		Scene scene = new Scene(root);					
    		primaryStage.setScene(scene);		
    		primaryStage.show();
    	}
    
		

    }

    @FXML
    void ExtendMeth(ActionEvent event) throws ParseException, IOException {//hoon sht3'let
    	
    	
    	
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
    	if(LoginController.WhoIsThis.equals("Librarian"))
    	{
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/Sample.fxml").openStream());		
		Scene scene = new Scene(root);					
		primaryStage.setScene(scene);		
		primaryStage.show();
    	}
    	else 
    	{
    		((Node)event.getSource()).getScene().getWindow().hide();
    		Stage primaryStage = new Stage();
    		FXMLLoader loader = new FXMLLoader();
    		Pane root = loader.load(getClass().getResource("/application/ManagerProfile.fxml").openStream());		
    		Scene scene = new Scene(root);					
    		primaryStage.setScene(scene);		
    		primaryStage.show();
    	}
    
    	
    }
    
    @FXML
    void ViewBookSubscriber(ActionEvent event) {

    	ArrayList<String> ExtensionBook=new ArrayList<String>();
    	ArrayList<ItemInLoan> ItemInLoan1=new ArrayList<ItemInLoan>();
    	ExtensionBook.add(SubsccriberIdText.getText());
    	ExtensionBook.add("Get all the books from item in loan");
    	ItemInLoan1=(ArrayList<ItemInLoan>)IPController.client.Request(ExtensionBook); 
   	
    	
    	if(ItemInLoan1.isEmpty()) {
			MessagesAlert.infoBox("it is no books to extend!", "OBL Library");

    	}else {
    		
    		 BookIdCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("BookID"));
 	        BookNameCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("BookName"));
 	        LoanDateCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("LoanDate"));
 	        returnDateCol.setCellValueFactory(new PropertyValueFactory<ItemInLoan, String>("returnDate"));
 	        ObservableList<ItemInLoan> toShow = FXCollections.observableArrayList();
  	        toShow.addAll(ItemInLoan1);
  	        System.out.println(" => " +toShow);
  	        BookTableView.setItems(toShow);
    }
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

  
}
