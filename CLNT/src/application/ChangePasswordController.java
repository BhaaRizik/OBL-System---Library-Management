
/**
 * 
 * In this function the client has the ability to change password
 * 
 * BackToProfileMeth, back to the previous page
 * 
 * SavePassMeth, change an save the password 
 * 
 * run, in this thread the system save changing password in action table
 * 
 * @author Jameel Othman 
 * 
 * */


package application;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ChangePasswordController extends Thread {

	@FXML
    private Button BackToProfile;

    @FXML
    private Button SavePassword;

    @FXML
    private TextField OldPasswordText;

    @FXML
    private TextField NewPasswordText;

    @FXML
    private TextField ConfirmPasswordText;
    
public void run(){  
    	
	ArrayList<String> newAction=new ArrayList<String>();
	newAction.add(LoginController.GlobalSubscriberInformation.get(2));
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy   HH:mm:ss");
	Date date = new Date();    	
	
	newAction.add(dateFormat.format(date));
	newAction.add("Changed the Password");
	newAction.add("add new action");
	IPController.client.Request(newAction);
    	
    	}  
    
    @FXML
    void BackToProfileMeth(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/SubscriberProfile.fxml").openStream());
		Scene scene = new Scene(root);			
		primaryStage.setScene(scene);		
		primaryStage.show();
		
		SubscriberProfileController SPC = (SubscriberProfileController) loader.getController(); 
		SPC.fillsubscriberdetails(LoginController.GlobalSubscriberInformation);

    }

    @FXML
    void SavePassMeth(ActionEvent event) throws IOException {
    	ArrayList<String> UpdatePassword=new ArrayList<String>();
    	UpdatePassword.add(LoginController.GlobalSubscriberInformation.get(2));//Subscriber Id
       
        	UpdatePassword.add(NewPasswordText.getText()); //new password
        	UpdatePassword.add("UpdatePassword");// to switch method
        	
         	
        	String OldPassword=OldPasswordText.getText();
        	String NewPassword=NewPasswordText.getText();
        	String ConfirmPassword=ConfirmPasswordText.getText();
        	if(LoginController.GlobalSubscriberInformation.get(7).equals(OldPassword)&&NewPassword.equals(ConfirmPassword)) {
        		
        		IPController.client.Request(UpdatePassword);
	    		MessagesAlert.infoBox("The Password Changed Successfully!","ORT Braude Library"); 	    		
	    	   this.start();	   //save the new action 		
	    		((Node)event.getSource()).getScene().getWindow().hide();
	    		Stage primaryStage = new Stage();
	    		FXMLLoader loader = new FXMLLoader();
	    		Pane root = loader.load(getClass().getResource("/application/LoginController.fxml").openStream());
	    		Scene scene = new Scene(root);			
	    		primaryStage.setScene(scene);	
	    		
	    		primaryStage.show();
	    		

        	}
        	else 
        	{
        		if(!(LoginController.GlobalSubscriberInformation.get(7).equals(OldPassword)))
        		MessagesAlert.infoBox("Sorry!\n Your old Password doesn't match the password in our system!","ORT Braude Library");
        		if(!(NewPassword.equals(ConfirmPassword)))
        	    		MessagesAlert.infoBox("ERROR: Your password and confirmation password do not match","ORT Braude Library");
        		OldPasswordText.setText("");
        		NewPasswordText.setText("");
        		ConfirmPasswordText.setText("");

        		
        	}
    	


    }

   
  
}
