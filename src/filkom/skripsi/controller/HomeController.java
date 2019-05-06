package filkom.skripsi.controller;

import filkom.skripsi.WeightedProduct;
import filkom.skripsi.model.FileProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

public class HomeController {

    @FXML
    public Button btn_compute;

    @FXML
    public Button btn_open_file;

    @FXML
    public AnchorPane ap_root;

    @FXML
    public TableView<FileProperties> tb_file_input = new TableView<>();
    @FXML
    public TableColumn<FileProperties, String> tc_file_name = new TableColumn<>();
    @FXML
    public TableColumn<FileProperties, Integer> tc_criteria = new TableColumn<>();

    private File inputFile;

    private ObservableList<FileProperties> fileProperties;

    private int numOfCriteria;

    @FXML
    public void handleClick(MouseEvent mouseEvent) {
        if (mouseEvent.getSource() == btn_compute) {
            computeAHP_WP();
        } else if (mouseEvent.getSource() == btn_open_file) {
            openFile();
        }
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv", "*.csv"));
        fileChooser.setTitle("Choose the input file");
        inputFile = fileChooser.showOpenDialog(ap_root.getScene().getWindow());
        setTableFile();
    }

    private void setTableFile() {
        tc_file_name.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        tc_criteria.setCellValueFactory(new PropertyValueFactory<>("criteria"));
        if (inputFile != null ) {
            countCriteriaMatrix();
            String[] fileName = inputFile.getAbsolutePath().replaceAll(Pattern.quote("\\"), "\\\\").split("\\\\");
            fillFileProperties(fileName);
        }
        tb_file_input.setItems(fileProperties);
    }

    private void computeAHP_WP() {
        WeightedProduct weightedProduct = new WeightedProduct();
        try {
            if (inputFile != null) {
                weightedProduct.getResultOfAHPWP(inputFile);
            } else {
                showAlertBox();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlertBox() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input file not found");
        alert.setHeaderText(null);
        alert.setContentText("Please input file");
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alert.getDialogPane().setMinWidth(Region.USE_PREF_SIZE);
        alert.setResizable(true);

        alert.showAndWait();
    }

    private void fillFileProperties(String[] fileName){
        if(fileProperties == null){
            fileProperties = FXCollections.observableArrayList(new FileProperties(fileName[fileName.length - 1], numOfCriteria));
        } else {
            fileProperties.add(new FileProperties(fileName[fileName.length - 1], numOfCriteria));
        }
    }

    private void countCriteriaMatrix() {
        numOfCriteria = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("data"))
                    break;
                numOfCriteria++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
