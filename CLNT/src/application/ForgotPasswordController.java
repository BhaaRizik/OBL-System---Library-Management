
/**
 * 
 * In this function if the client forget his password 
 * he can restore it by this operation
 * 
 * sendNewPasswordTorUser,
 *          check the details of the student
 *          reset password in the data base
 *          send email to client with the new password  
 *         
 * 
 * buildPassword, in this function the system create a random password
 * 
 * @author Mahmoud Atarieh
 * 
 * */


package application;

import java.io.IOException;
import java.util.ArrayList;

import LaonBoundary.LaonPermissionBoundary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.security.SecureRandom;

public class ForgotPasswordController {
	 @FXML
	    private PasswordField NewPasswordTextArea;

    @FXML
    private AnchorPane NewPasswordPanel;

    @FXML
    private Label ForgotPasswordTitleLable;

    @FXML
    private Label SecondSentence;

    @FXML
    private Label UserNameLabel;

    @FXML
    private Label IdLabel;

    @FXML
    private TextField UserNameTextArea;

    @FXML
    private TextField IdTextArea;

    @FXML
    private Button SaveButton;

    @FXML
    private Label NewPasswordLabel;

    @FXML
    private TextField NeaPasswordTextArea;

    @FXML
    private Button BackButton;
    @FXML
    private Label incorr;

    @FXML
    private Label tryagain;

    @FXML
    void BackButtonMeth(ActionEvent event) throws IOException {
    	((Node)event.getSource()).getScene().getWindow().hide();
		Stage primaryStage = new Stage();
		FXMLLoader loader = new FXMLLoader();
		Pane root = loader.load(getClass().getResource("/application/LoginController.fxml").openStream());
		
		Scene scene = new Scene(root);			
		
		primaryStage.setScene(scene);		
		primaryStage.show();
    }

    @FXML
    void sendNewPasswordTorUser(ActionEvent event) {
         
    	String userName=UserNameTextArea.getText();
    	String ID = IdTextArea.getText();
    	LaonPermissionBoundary LPB=new LaonPermissionBoundary();
    	
    	String newPassword=buildPassword();
    	
    	ArrayList<String> msg = new ArrayList<String>();
        ArrayList<String>  result = new ArrayList<String>(); 
        
        incorr.setVisible (false);
		tryagain.setVisible (false);
        
    	msg.add(userName);
    	msg.add(ID);
    	msg.add(newPassword);
    	msg.add("temporality");
    	
    	System.out.println(msg);
    	// result, is the arrayList that content new password
    	result = (ArrayList<String>)IPController.client.Request(msg);
        System.out.println(result);
    	if(result.get(0).equals("newpassword"))
    	{
    		incorr.setVisible (true);
    		tryagain.setVisible (true);
    		incorr.setText("In few minutes you will get an email ");
    		tryagain.setText("with new PASSWORD.");
    		LPB.sendEmail("New password","\nYour new password is :       "+result.get(2)+"\n\nYou can change it by updating your password  ",result.get(1));
    		
    	}
    	else {
    		//incorr.setVisible (true);
    		//tryagain.setVisible (true);
    		if(result.get(0).equals("fieldEmpty"))
    		     MessagesAlert.infoBox("No info. entered\n Please enter information ","Reset password not available");
    		else {
    			 MessagesAlert.infoBox("Username or ID error\n Please check information ","Wrong Info.");
    		}

    	}
    	
    }
    
    
    
    private String buildPassword() {

       	ArrayList<Integer> l = new ArrayList<>();
    	 StringBuilder sb=new StringBuilder();
    	   String str;
    	   int randInt;
        //Add ASCII numbers of characters commonly acceptable in passwords
        for (int i = 48; i < 58; i++) { //0-9
            l.add(i);
        }
        for (int i = 65; i < 91; i++) { //A-Z
            l.add(i);
        }
        for (int i = 97; i < 123; i++) {
            l.add(i);
        }
      

        /*Randomise over the ASCII numbers and append respective character
          values into a StringBuilder*/
        for (int i = 0; i < 10; i++) {
            randInt = l.get(new SecureRandom().nextInt(62));
            sb.append((char) randInt);
        }

        str = sb.toString();
        return str;  
    }

  
}


