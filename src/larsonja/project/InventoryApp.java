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

  int    finishFlag = 0;
  Label  mainLabel;
  Button goButton;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {

    mainLabel = new Label("Inventory Master 0.0.4");
    mainLabel.getStyleClass().add("main_label");

    java.awt.Label locationLabel = new java.awt.Label();

    // Making my choice box/drop down menu
    ChoiceBox<Object> locationBox = new ChoiceBox<Object>();
    locationBox.setItems(FXCollections.observableArrayList("Brooksbank", "Deep Cove"));
    locationBox.setTooltip(new Tooltip("Select the location"));

    final String[] locations = new String[] { "BB", "DC" };

    locationBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov, Number value, Number new_value) {
        // set label so i can easily just take it later, wont be rendered
        // onto screen
        locationLabel.setText(locations[new_value.intValue()]);
      }
    });

    // Defining the inputFileName text field
    TextField inputFileName = new TextField();
    inputFileName.setPromptText("Enter the input file name");

    // Defining the outputFileName text field
    TextField outputFileName = new TextField();
    outputFileName.setPromptText("Enter a name for the output file");

    Label statusLabel = new Label();

    goButton = new Button("Go!");
    goButton.setOnAction(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent e) { // TODO rewrite this entire next
                                          // section to remove the temp file
                                          // stuff
        do {
          if (checkFileNames(inputFileName.getText(), outputFileName.getText())) {
            statusLabel.setText("Generating output now.");

            String itemName = "";
            double count = 0;
            Double locationOrders = new Double(0);

            List<String> Inventory = new ArrayList<String>();
            List<String> Ordering = new ArrayList<String>();
            String name = setNameInRoot(inputFileName.getText(), "");
            String location = locationLabel.getText();
            int flag = setFlagFromLocation(location);

            String tempName = "tempOutputFile";

            convertExcelCsv(name, tempName);

            name = ".\\tempOutputFile.csv";

            readOutFileNoBlanks(name, Inventory);

            Iterator<String> iterator = Inventory.iterator();
            String tempRow;

            // Open new file to start writing to
            Writer writer;
            String inventoryOutputName = setNameInRoot(outputFileName.getText(), ".csv");

            try {
              writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(inventoryOutputName), "utf-8"));

              while (iterator.hasNext()) {

                tempRow = iterator.next();

                if (tempRow.startsWith("//")) { // Header Line
                  Ordering.add(tempRow);
                } else if (tempRow.startsWith(",")) { // Empty Line
                  // empty
                } else { // Normal Line
                  parseItemIntoList(tempRow, itemName, count, locationOrders, Ordering, flag);

                }
              }

              moveListToWriter(Ordering, writer);
              writer.close();

            } catch (IOException x) {
              x.printStackTrace();
            }

            statusLabel.setText("Output has been generated.");
            finishFlag = 1;

          } else {
            statusLabel.setText("Error in text fields");
          }
        } while (finishFlag != 1);
        
        File tempFile = new File("tempOutputFile.csv");
        tempFile.delete();
        
        finishFlag = 0;
      }
    });


    // data type representing the window
    VBox root = new VBox();
    root.getChildren().addAll(mainLabel, locationBox, inputFileName, outputFileName, goButton, statusLabel);

    // size my screen
    Scene mainScene = new Scene(root, 500, 500);
    primaryStage.setScene(mainScene);

    // add my css styling
    mainScene.getStylesheets().add("larsonja/project/catalogueStyle.css");

    primaryStage.show(); // show the final screen
  }

  void convertExcelCsv(String name, String outputName) {
    name = name.concat(".xlsx");

    StringBuffer dataBuffer = new StringBuffer();
    outputName = outputName.concat(".csv");

    try {
      FileOutputStream outputStream = new FileOutputStream(outputName);
      FileInputStream inputStream = new FileInputStream(name);

      runExcelCsvConversion(dataBuffer, inputStream, outputStream);

      outputStream.close();
    } catch (Exception m) {
      m.printStackTrace();
    }
  }

  private boolean checkFileNames(String inName, String outName) {
    if (inName != outName && inName != null) {
      if (!inName.isEmpty() && outName != null && !outName.isEmpty()) {
        return true;
      }
    }
    return false;
  }

  private int setFlagFromLocation(String location) {
    if (location == "BB") {
      return 3;
    } else if (location == "DC") {
      return 4;
    }
    return 0;
  }

  private String setNameInRoot(String name, String extension) {
    String r = ".";
    r = r.concat("\\");
    r = r.concat(name);
    r = r.concat(extension);
    return r;
  }

  private boolean readOutFileNoBlanks(String fileName, List<String> Inventory) {
    try {
      BufferedReader reader = null;
      String row;
      reader = new BufferedReader(new FileReader(fileName));

      // read file out and ignore any blank lines
      while ((row = reader.readLine()) != null) {
        if (finishFlag == 0) {
          row = ",".concat(row);
          finishFlag = 2;
        }
        if ((row.startsWith(",") == false)) {
          Inventory.add(row);
        }
      }
      reader.close();
    } catch (IOException r) {
      r.printStackTrace();
    }
    return true;
  }

  private void parseItemIntoList(String tempRow, String itemName, double count, Double locationOrders, List<String> Ordering, int flag) {

    String[] parts = tempRow.split(",");

    // is too long, so go back from end
    int index = parts.length - 4;

    itemName = getItemName(index, parts);
    count = getItemCount(index, parts);

    double desA = Double.valueOf(parts[index + 2]);
    double desB = Double.valueOf(parts[index + 3]);

    Item currentItem = new Item(itemName, count, desA, desB);

    locationOrders = getItemOrders(currentItem, flag);

    if (locationOrders != 0) {
      String result = setResultingRowString(itemName, parts[parts.length - 4], locationOrders.toString());
      Ordering.add(result);
    }
  }

  private String getItemName(int index, String[] parts) {
    String s = "";
    if (index != 0) {
      s.concat("\"");
      for (int i = 0; i < index; i++) {
        s = s.concat(parts[i]);
      }
      s.concat("\"");
    } else {
      s = parts[index];
    }
    return s;
  }

  private double getItemCount(int index, String[] parts) {
    if (parts[index] != null) {
      return Double.valueOf(parts[index + 1]);
    } else {
      return 0;
      // TODO add a pop up window to ask for the number of an item
    }
  }

  private Double getItemOrders(Item curItem, int flag) {
    if (flag == 4) {
      return curItem.getAmountB();
    } else {
      return curItem.getAmountA();
    }
  }

  private String setResultingRowString(String itemName, String unit, String orders) {
    String result = itemName;
    result = result.concat(",");
    result = result.concat(unit);
    result = result.concat(",");
    return result.concat(orders);
  }

  private boolean moveListToWriter(List<String> list, Writer writer) throws IOException {
    Iterator<String> iteratorB = list.iterator();

    while (iteratorB.hasNext()) {
      // print it all to the file
      String toPrint = iteratorB.next();

      writer.write(toPrint);
      writer.write(System.getProperty("line.separator"));
    }// iterates through and prints it into the new file
    return true;
  }

  private void runExcelCsvConversion(StringBuffer dataBuffer, FileInputStream inputStream, FileOutputStream outputStream) throws IOException {

    XSSFWorkbook workBook = new XSSFWorkbook(inputStream);
    XSSFSheet workSheet = workBook.getSheetAt(0);

    Row currentRow;
    Cell currentCell;

    Iterator<Row> rowIterator = workSheet.iterator();
    while (rowIterator.hasNext()) {
      currentRow = rowIterator.next();

      Iterator<Cell> cellIterator = currentRow.iterator();
      while (cellIterator.hasNext()) {
        currentCell = cellIterator.next();
        dataBuffer.append(currentCell + ",");
      }
      dataBuffer.append(System.getProperty("line.separator"));
    }
    outputStream.write(dataBuffer.toString().getBytes());
    workBook.close();
  }

}