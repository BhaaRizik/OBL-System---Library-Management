
/**
 * 
 * In this class the librarian add new book to the library
 * 
 * Check if the book found 
 *      use the function : CheckIfFoundMeth
 *     the librarian can't add book because we already have it 
 *
 *If the book not exist, then 
 *       use the function : AddNewBoKmeth
 *       the fields change to enable status    
 *       the user can enter the details of the new book   
 *       and the book saved in the data base 
 * 
 * ChooseFileMeth, in this method we choose the suitable PDF file to the new book
 * BackMeth : back to the previous page 
 * 
 * @author Jameel Othman
 * 
 * 
 * */








package application;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class AddNewBookController {
	
	public static String PDFString;
	// String PdfString=null;
	
    @FXML
	private Button CheckBookButton;
    
    @FXML
    private TextField BookAthorNameText;

    @FXML
    private TextField BookIdText;

    @FXML
    private TextField BookNameText;

    @FXML
    private TextField BookGenreTtext;

    @FXML
    private TextField BookCopiesText;

    @FXML
    private TextField BookInShelfText;

    @FXML
    private TextField LocationBookText;

    @FXML
    private TextField PrintDateText;

    @FXML
    private TextArea BookDescriptionText;

    @FXML
    private TextField catalogNumberText;

    @FXML
    private TextField purchaseDateText;

    @FXML
    private Label BookFoundedInLibLabel;

    @FXML
    private Label BookNotFoundedLib;
    @FXML
    private Label CheckIfavailablelab;
    @FXML
    private Button AddNewBookButton;
    @FXML
    private Label FileLabel;
    @FXML
    private Button ChooseFileButton;
    
    
    @FXML
    void ChooseFileMeth(ActionEvent event) throws FileNotFoundException {
    	
    /*	FileChooser fc= new FileChooser();
    	fc.getExtensionFilters().addAll(new ExtensionFilter("PDF Files","*.pdf"));
    	File selectedFile=fc.showOpenDialog(null);
    	if(selectedFile!=null)
    	{
    		PdfString=selectedFile.getName();
    		FileLabel.setText(PdfString);
    	System.out.println(PdfString); 
    	}*/
    	    PDFString = new String();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Open Resource File");
			File file = fileChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

			// File file = new File("java1.pdf");
			FileInputStream fis = new FileInputStream(file); // get the file
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum); // no doubt here is 0
					// Writes len bytes from the specified byte array starting at offset off to this
					// byte array output stream.
					System.out.println("read " + readNum + " bytes,");
				}
			} catch (IOException ex) {
			}
			byte[] bytes = bos.toByteArray(); // save it to Byte
			PDFString = Base64.getEncoder().encodeToString(bytes); // change Byte[] to string
    }

    @FXML
    void AddNewBoKmeth(ActionEvent event) throws IOException {
    	
    	ArrayList<String> NewBook=new ArrayList<String>();

    	NewBook.add(BookIdText.getText());
    	NewBook.add(BookNameText.getText());
    	NewBook.add(BookAthorNameText.getText());
    	NewBook.add(BookGenreTtext.getText());
    	NewBook.add(BookDescriptionText.getText());
    	NewBook.add(BookCopiesText.getText());
    	NewBook.add(BookCopiesText.getText());  //books in shelf
    	NewBook.add(LocationBookText.getText());
    	NewBook.add(PrintDateText.getText());
    	NewBook.add(catalogNumberText.getText());
    	NewBook.add(purchaseDateText.getText());
    	NewBook.add(PDFString);//
    	NewBook.add("0");
    	NewBook.add("Add New Book");
    	
    	
    	
    /*	if(!(FileLabel.getText().equals(PdfString)))
    	{
			MessagesAlert.infoBox("Please choose a pdf file", "OBL Library");
			return;

    	}*/
    	
    	
    	IPController.client.Request(NewBook);
    	MessagesAlert.infoBox("The new book is added successfully!", "OBL Library");
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
    void BackMeth(ActionEvent event) throws IOException {
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
    void CheckIfFoundMeth(ActionEvent event) {
    	
ArrayList<String> CheckBookIfFounded=new ArrayList<String>();
    	
    	if(BookIdText.getText().isEmpty())
    	{
        	MessagesAlert.infoBox("Please insert Book id to check!", "OBL Library");
        	return;
    	}

    	
    	CheckBookIfFounded.add(BookIdText.getText());
    	CheckBookIfFounded.add("If the book exists in the library");
    	
    	String Result=(String)IPController.client.Request(CheckBookIfFounded);
    	
    	 System.out.println(Result);
    	if(Result.equals("founded")) {
    		BookNotFoundedLib.setVisible(false);
    		BookFoundedInLibLabel.setVisible(true);
    		
    		CheckIfavailablelab.setVisible(false);
    		
    		 BookAthorNameText.setDisable(true);
     		
    		 BookIdText.setDisable(false);

    	     BookNameText.setDisable(true);

    	    BookGenreTtext.setDisable(true);

    	     BookCopiesText.setDisable(true);


    	     LocationBookText.setDisable(true);

    	     PrintDateText.setDisable(true);

    	    BookDescriptionText.setDisable(true);

    	     catalogNumberText.setDisable(true);

    	   purchaseDateText.setDisable(true);
    	   CheckIfavailablelab.setVisible(false);
    		
    		}
    	else {
    		AddNewBookButton.setVisible(true);
    		BookFoundedInLibLabel.setVisible(false);
    		BookNotFoundedLib.setVisible(true);
    		
    		 BookAthorNameText.setDisable(false);
    		
    		 BookIdText.setDisable(true);

    	     BookNameText.setDisable(false);

    	    BookGenreTtext.setDisable(false);

    	     BookCopiesText.setDisable(false);

    	     LocationBookText.setDisable(false);

    	     PrintDateText.setDisable(false);

    	    BookDescriptionText.setDisable(false);

    	     catalogNumberText.setDisable(false);

    	   purchaseDateText.setDisable(false);
    	   CheckIfavailablelab.setVisible(false);
    		}
    
    	}
    	    	
}


