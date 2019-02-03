/**
 * 
 *  The librarian can see the information of the subscriber 
 *  and update it 
 *  
 *  
 * EditMaeth, 
 * edit and update the details of the subscriber 
 * and change the fields from disable to enable
 * 
 * SaveMeth,
 * Save the data that we changed
 * 
 * fillsubscriberdetails, view the details of the subscriber
 * 
 * AddOptionsToComboBox, change the status of the subscriber 
 * 
 * @author Jameel Othman 
 * 
 * 
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
public class InformationSubscriberlibrarianController {
	String StatusSusbcriber=null;

	
    @FXML
    private TextField NameText;

    @FXML
    private TextField AddressText;

    @FXML
    private TextField EmailText;

    @FXML
    private TextField IdSubscriberText;

    @FXML
    private TextField SubscriberNumber;

    @FXML
    private TextField PhoneNumberText;

    @FXML
    private Button EditButtonLabel;

    @FXML
    private Button SaveButtonlabel;

    @FXML
    private ComboBox<String> SubscriberStatusCombox;

    @FXML
    void BackMth(ActionEvent event) throws IOException {
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
    void EditMaeth(ActionEvent event) {
    
    	  NameText.setDisable(false);
		  IdSubscriberText.setDisable(false);
		  EmailText.setDisable(false);
		  PhoneNumberText.setDisable(false);
		  AddressText.setDisable(false);
		  if(LoginController.WhoIsThis.equals("Librarian"))
	         SubscriberStatusCombox.setDisable(true);
		  else 
		     SubscriberStatusCombox.setDisable(false);
		    
		     
		  
		  SubscriberNumber.setDisable(false);
		  SaveButtonlabel.setVisible(true);
		  EditButtonLabel.setVisible(false);
		  
		  
		  
		
			
    }

    @FXML
    void SaveMeth(ActionEvent event) throws IOException {
    	  ArrayList<String>UpdateDataSubscriber=new  ArrayList<String>();
    	  UpdateDataSubscriber.add( NameText.getText());
    	  UpdateDataSubscriber.add( IdSubscriberText.getText());
    	  UpdateDataSubscriber.add( EmailText.getText());
    	  UpdateDataSubscriber.add( PhoneNumberText.getText());
    	  UpdateDataSubscriber.add( AddressText.getText());
    	  UpdateDataSubscriber.add( SubscriberStatusCombox.getSelectionModel().getSelectedItem());
    	  UpdateDataSubscriber.add( SubscriberNumber.getText());
    	  UpdateDataSubscriber.add("Librarian Or Manager Update Subscriber Information");
    	  IPController.client.Request(UpdateDataSubscriber);  
    	  SaveButtonlabel.setVisible(false);
		  EditButtonLabel.setVisible(true);
		  NameText.setDisable(true);
		  IdSubscriberText.setDisable(true);
		  EmailText.setDisable(true);
		  PhoneNumberText.setDisable(true);
		  AddressText.setDisable(true);
		  SubscriberStatusCombox.setDisable(true);
		  SubscriberNumber.setDisable(true);
    	 
    }

	public void fillsubscriberdetails(ArrayList<String> infoSubscriber) {
		  NameText.setText(infoSubscriber.get(1));
		  IdSubscriberText.setText(infoSubscriber.get(2));
		  EmailText.setText(infoSubscriber.get(3));
		  PhoneNumberText.setText(infoSubscriber.get(4));
		  AddressText.setText(infoSubscriber.get(5));
		  SubscriberStatusCombox.setPromptText(infoSubscriber.get(6));
		  SubscriberNumber.setText(infoSubscriber.get(7));

		    
	}
    
	public void AddOptionsToComboBox()
    {
		SubscriberStatusCombox.getItems().addAll("Active","Frozen","Lock");
    }
}
