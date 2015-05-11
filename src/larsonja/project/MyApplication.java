package larsonja.project;


import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.beans.value.ChangeListener;



public class MyApplication extends Application {
	
	int finishFlag = 0;
	
	Label mainLabel;
	Button goButton;

	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	mainLabel = new Label("Inventory Master 0.0.1");
    	mainLabel.getStyleClass().add("main_label");
    	
    	//Making my choice box/drop down menu
    	ChoiceBox<Object> locationBox = new ChoiceBox<Object>();
    	locationBox.setItems(FXCollections.observableArrayList("Brooksbank", "Deep Cove", new Separator(), "All Locations"));
    	locationBox.setTooltip(new Tooltip("Select the location"));
    	
    	final String[] locations = new String[]{ "BB", "DC", "Total"}; //might need to add a space here where the separator is
    	locationBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
    		public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value){
    			//set the actual location TODO
    		}
    	});
    	
    	//Defining the inputFileName text field
    	TextField inputFileName = new TextField();
    	inputFileName.setPromptText("Enter the input file name");

    	//Defining the outputFileName text field
    	TextField outputFileName = new TextField();
    	outputFileName.setPromptText("Enter the output file name");
    	
    	Label statusLabel = new Label();
    	
    	goButton = new Button("Go!");
    	goButton.setOnAction(new EventHandler<ActionEvent>(){
    		
    		@Override
    		public void handle(ActionEvent e) {
    			do{
	    			if ((inputFileName.getText() != outputFileName.getText()) && inputFileName.getText() != null && !inputFileName.getText().isEmpty() && outputFileName.getText() != null && !outputFileName.getText().isEmpty()) {
	    				statusLabel.setText("Thank you, generating output now.");
	    				//actually generate output  
	    				statusLabel.setText("Output has been generated.");	
	    				finishFlag = 1;
	    			} else {
	    				statusLabel.setText("You have not filled everything in correctly.");
	    			}
    			} while (finishFlag == 0);
      	   }
    	});
    
    	
    VBox root = new VBox();
    root.getChildren().addAll(mainLabel, locationBox, inputFileName, outputFileName, goButton, statusLabel);
    
    Scene mainScene = new Scene(root, 500, 500);
    primaryStage.setScene(mainScene);
    
    mainScene.getStylesheets().add("larsonja/project/catalogueStyle.css");
     	
   	primaryStage.show();
    }
}