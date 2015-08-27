package larsonja.project;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class InventoryApp extends Application {
	
	int finishFlag = 0;
	
	Label mainLabel;
	Button goButton;

	public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
    	
    	//will start removing stuff from here and supplementing it into the catalogue class so it will be more just UI on this side
    	
    	mainLabel = new Label("Inventory Master 0.0.3");
    	mainLabel.getStyleClass().add("main_label");
    	
    	java.awt.Label locationLabel = new java.awt.Label();
    	
    	//Making my choice box/drop down menu
    	ChoiceBox<Object> locationBox = new ChoiceBox<Object>();
    	locationBox.setItems(FXCollections.observableArrayList("Brooksbank", "Deep Cove", new Separator(), "All Locations"));
    	locationBox.setTooltip(new Tooltip("Select the location"));
    	
    	final String[] locations = new String[]{ "BB", "DC", "Total"}; //might need to add a space here where the separator is
    	locationBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>(){
    		public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value){
    			//set label so i can easily just take it later, wont be rendered onto screen
    			locationLabel.setText(locations[new_value.intValue()]);
    		}
    	});
    
    	//Defining the inputFileName text field
    	TextField inputFileName = new TextField();
    	inputFileName.setPromptText("Enter the input file name");

    	//Defining the outputFileName text field
    	TextField outputFileName = new TextField();
    	outputFileName.setPromptText("Enter a name for the output file");
    	
    	Label statusLabel = new Label();
    	
    	goButton = new Button("Go!");
    	goButton.setOnAction(new EventHandler<ActionEvent>(){
    		
    		@Override
    		public void handle(ActionEvent e) {
    			do{
	    			if ((inputFileName.getText() != outputFileName.getText()) && inputFileName.getText() != null && !inputFileName.getText().isEmpty() && outputFileName.getText() != null && !outputFileName.getText().isEmpty()) {
	    				statusLabel.setText("Thank you, generating output now.");
	    				
	    				//variable creation
	    				String itemName;
	    				double count = 0;
	    				double desiredA;
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
	    				} //still need to add others on this variable to be selected later
	    				
	    				//get file name and open 
	    				name = ".";
	    				name = name.concat("\\");
	    				name = name.concat(inputFileName.getText()); 
	    				String tempName = "tempOutputFile"; //now is .\inputFileName
	    				
	    				//now convert excel into csv 
	    				convertExcelCsv(name, tempName);
	    				
	    				name = ".\\tempOutputFile.csv"; //rename it as my temp output file name
	    				
	    				try{
	    				BufferedReader reader = null;
	    				reader = new BufferedReader(new FileReader(name));
	    				
	    				//read file out and ignore any blank lines
	    				while((row = reader.readLine()) != null){
	    					if((row.startsWith(",") == false)){ 
	    						Inventory.add(row);
	    					}
	    				}
	    				reader.close();
	    				} catch (IOException r) {
	    					r.printStackTrace();
	    				}
	    				
	    				Iterator<String> iterator = Inventory.iterator();
	    				String tempRow;
	    				
	    				//Open new file to start writing to
	    				Writer writer;
	    				String inventoryOutputName = ".";
	    				inventoryOutputName = inventoryOutputName.concat("\\");
	    				inventoryOutputName = inventoryOutputName.concat(outputFileName.getText());
	    				inventoryOutputName = inventoryOutputName.concat(".csv");
	    				
	    				try {
	    				    writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inventoryOutputName), "utf-8"));
	    				    
	    				    while(iterator.hasNext()){ 
	    				    	
	    						tempRow = iterator.next();
	    						
	    						//decide what to do with each line
	    						if(tempRow.startsWith("//")){ 
	    							//do nothing to it and just print
	    							Ordering.add(tempRow);
	    							
	    						} else if(tempRow.startsWith(",")){
	    							//actually do nothing at all with the line, it's considered empty
	    						} else {
	    							//parse normally and put into new CSV
	    							String[] parts = tempRow.split(",");
	    							
	    							
	    							//is too long, so go back from end
	    							int index = parts.length;
	    							itemName = "";
	    							
	    							index = index - 4; //starts at 0, and 3 back from end should give right index for count
	    							if(index != 0){
	    								itemName.concat("\"");
		    							for( int i = 0; i < index ; i++){
		    								itemName = itemName.concat(parts[i]);
		    							}
		    							itemName.concat("\"");
	    							} else {
	    								itemName = parts[index];
	    							}
	    							
	    							if(parts[index] != null){ //gets count
	    								
	    								count = Double.valueOf(parts[index + 1]);
	    							} else {
	    								count = 0;
	    								//TODO add a pop up window to ask for the number of an item
	    								
	    							}
	    							    							
	    							double desA = Double.valueOf(parts[index + 2]);
	    							double desB = Double.valueOf(parts[index + 3]);

	    							Item currentItem = new Item(itemName, count, desA, desB);
	    							
	    							/*
	    							 * TODO do something with the item and streamline the next segment of code
	    							 * 
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
	    							
	    							if(locationOrders != 0){ //TODO remove this later then we store data
		    							//format my final line to be used by putting in all the information in CSV format
		    							String result = itemName;
		    							result = result.concat(",");
		    							result = result.concat(parts[1]);
		    							result = result.concat(",");
		    							result = result.concat(locationOrders.toString());
	    							
	    								Ordering.add(result); //again a little redundant but will be used in future most likely
	    							}
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
	    				tempName = tempName.concat(".csv");
	    				File tempFile = new File(tempName);
	    				tempFile.delete();
	    				
	    			} else {
	    				statusLabel.setText("You have not filled everything in correctly.");
	    			}
    			} while (finishFlag == 0);
      	   }
    	});
    	
    	File tempFile = new File("tempOutputFile");
    	tempFile.delete();
    	    
	    //data type representing the window
	    VBox root = new VBox();
	    root.getChildren().addAll(mainLabel, locationBox, inputFileName, outputFileName, goButton, statusLabel);
	    
	    //size my screen
	    Scene mainScene = new Scene(root, 500, 500);
	    primaryStage.setScene(mainScene);
	    
	    //add my css styling
	    mainScene.getStylesheets().add("larsonja/project/catalogueStyle.css");
	     	
	   	primaryStage.show(); //show the final screen
    }
    
    
    void convertExcelCsv(String name, String outputName){
    	name = name.concat(".xlsx"); 
    	
    	
    	StringBuffer dataBuffer = new StringBuffer();
		outputName = outputName.concat(".csv");
    
	    try{
			FileOutputStream outputStream = new FileOutputStream(outputName); 
			
			FileInputStream inputStream = new FileInputStream(name);
			
			XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
			XSSFSheet workSheet = workBook.getSheetAt(0);
			
			Row currentRow;
			
			Cell currentCell;
			
			//row major ordering
			Iterator<Row> rowIterator = workSheet.iterator();
			while(rowIterator.hasNext()){
				currentRow = rowIterator.next();
				
				Iterator<Cell> cellIterator = currentRow.iterator();
				while(cellIterator.hasNext()){
					currentCell = cellIterator.next();
					dataBuffer.append(currentCell + ",");
				}
				dataBuffer.append(System.getProperty("line.separator"));
			}
			outputStream.write(dataBuffer.toString().getBytes()); //actually writes my file
			outputStream.close();
			workBook.close(); 
		} catch (Exception m){
			m.printStackTrace();
		}
    }
}