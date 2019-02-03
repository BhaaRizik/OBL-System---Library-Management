

/**
 * 
 * In this class the librarian can  register a new subscriber 
 * 
 * 
 * In this method the librarian insert
 *  the new subscriber details and save it the data base 
 *         AddSubscriberMeth
 *         
 *BackMeth, by this method the client return to previous page         
 *         
 * 
 * @author Mahmoud Atarieh
 * 
 * */


package application;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddNewSubscriberController {
	
	


    @FXML
    private TextField NameText;

    @FXML
    private TextField IdText;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField AdressText;

    @FXML
    private TextField NumberSubscriberText;

    @FXML
    private TextField PhoneText;

    @FXML
    private Label ResultLabel;

    @FXML
    private Button BackButton;

    @FXML
    private Button AddSubscriberButton;
    @FXML
    private TextField PasswordText;

    @FXML
    void AddSubscriberMeth(ActionEvent event) {
    	ArrayList<String> NewStudent=new ArrayList<String>();
    	int zero=0;
    	NewStudent.add(NameText.getText());//name
    	NewStudent.add(IdText.getText());//id
    	NewStudent.add(EmailText.getText());   	//email
    	NewStudent.add(PhoneText.getText());    	//phone
    	NewStudent.add(AdressText.getText()); //address
    	NewStudent.add("Active");     //status
    	NewStudent.add(PasswordText.getText());//password
    	NewStudent.add(NumberSubscriberText.getText());//numbersub
    	NewStudent.add("0");
    	NewStudent.add("AddNewSubscriber");////abbout what
    	
    	 boolean result=(boolean)IPController.client.Request(NewStudent);
    	
    	
    	
    	    NameText.setText(null);

    	 IdText.setText(null);

    	     EmailText.setText(null);

    	   AdressText.setText(null);

    	    NumberSubscriberText.setText(null);

    	     PhoneText.setText(null);
    	    PasswordText.setText(null);
    	    
    	    if(result)
    	    MessagesAlert.infoBox(" Subscriber added successfully ", "OBL Library");
    	    else
    	    	MessagesAlert.infoBox(" Adding Subscriber failed ", "OBL Library");
    	    	
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

}
