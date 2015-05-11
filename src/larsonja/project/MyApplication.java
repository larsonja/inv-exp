package larsonja.project;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

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
    	
    	java.awt.Label locationLabel = new java.awt.Label();
    	
    	//Making my choice box/drop down menu
    	ChoiceBox<Object> locationBox = new ChoiceBox<Object>();
    	locationBox.setItems(FXCollections.observableArrayList("Brooksbank", "Deep Cove", new Separator(), "All Locations"));
    	locationBox.setTooltip(new Tooltip("Select the location"));
    	
    	final String[] locations = new String[]{ "BB", "DC", "Total"}; //might need to add a space here where the separator is
    	locationBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
    		public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value){
    			locationLabel.setText(locations[new_value.intValue()]);
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
	    				
	    				
	    				String itemName;
	    				double count;
	    				double desiredA; // column 3 for BB, 4 for DC
	    				double desiredB;
	    				Double locationOrders;
	    				
	    				int flag = 0;
	    				List<String> Inventory = new ArrayList<String>(); 
	    				List<String> Ordering = new ArrayList<String>();
	    				String row;
	    				String name;
	    				String location;
	    					    				
	    				location = locationLabel.getText();
	    				
	    				if(location == "BB"){
	    					flag = 3;
	    				} else if (location == "DC") {
	    					flag = 4;
	    				} //still need to add other option for doing total
	    				
	    				
	    				name = inputFileName.getText();
	    					    				
	    				
	    				name = name.concat(".csv"); //add the file extension onto the end of the file name
	    				try{
	    				BufferedReader reader = null;
	    				reader = new BufferedReader(new FileReader(name)); //start reading from my file
	    				
	    				
	    				while((row = reader.readLine()) != null){
	    					if((row.startsWith(",") == false)){ //ignore anything that starts with a comma
	    						Inventory.add(row);
	    					}
	    				}
	    				reader.close();
	    				} catch (IOException r) {
	    					r.printStackTrace();
	    				}
	    				//now all my rows should be in the lines, so use that to examine and operate on
	    				
	    				Iterator<String> iterator = Inventory.iterator();
	    				String tempRow;
	    				
	    				//need to make a new file
	    				Writer writer;
	    				String inventoryOutputName = outputFileName.getText();
	    				inventoryOutputName = inventoryOutputName.concat(".csv");
	    				
	    				try {
	    				    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inventoryOutputName), "utf-8"));
	    				    
	    				    
	    				    while(iterator.hasNext()){
	    						
	    						tempRow = iterator.next();
	    						
	    						if(tempRow.startsWith("//")){
	    							//do nothing to it and just print
	    							Ordering.add(tempRow);
	    							
	    						} else if(tempRow.startsWith(",")){
	    							//actually do nothing at all with the line, it's considered empty
	    						} else {
	    							//parse normally and put into new CSV
	    							String[] parts = tempRow.split(",");
	    							itemName = parts[0];
	    							count = Double.valueOf(parts[2]);
	    							
	    							Item currentItem = new Item(itemName, count, Double.valueOf(parts[3]), Double.valueOf(parts[4]));
	    							/*
	    							 * creating the item here is redundant as we could just do the calculations and it would be simple enough
	    							 * however the goal of this project is to be a functional core of a bigger project that will be build off of this.
	    							 * Hence why we create the object here. otherwise you can just deal with the calculations after this comment
	    							 */
	    							
	    							desiredA = currentItem.getAmountA();
	    							desiredB = currentItem.getAmountB();
	    							//this gives me the amount, not check if it wants the A or B values
	    							if(flag == 3){
	    								locationOrders = desiredA;
	    							} else if (flag == 4) {
	    								locationOrders = desiredB;
	    							} else {
	    								locationOrders = desiredA;
	    							}
	    							
	    							String result = itemName;
	    							result = result.concat(","); //comma for CSV file format
	    							result = result.concat(parts[1]); //the units of measurement
	    							result = result.concat(","); //comma for the CSV 
	    							result = result.concat(locationOrders.toString()); //the actual orders
	    							//my string is now formatted, add to list of strings
	    							
	    							Ordering.add(result); //again a little redundant but will be used in future most likely
	    						}
	    					}
	    				    
	    				    //have Ordering list full of my outputs so need to print that to the file and then we'll be good
	    				    Iterator<String> iteratorB = Ordering.iterator();
	    				    
	    				    while(iteratorB.hasNext()){
	    				    	//print it all to the file
	    				    	String toPrint = iteratorB.next();
	    				    	
	    				    	writer.write(toPrint);
	    				    	writer.write(System.getProperty("line.separator"));
	    				    }//iterates through and prints it into the new file
	    				    
	    				    writer.close(); //done writing so the file closes 
	    				    
	    				} catch (IOException x) {
	    				  x.printStackTrace();
	    				}
	    				
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