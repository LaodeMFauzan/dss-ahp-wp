package filkom.skripsi.controller;

import filkom.skripsi.WeightedProduct;
import filkom.skripsi.model.FileProperties;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;

public class HomeController {

    @FXML
    public Button btn_compute;

    @FXML
    public Button btn_open_file;

    @FXML
    public AnchorPane ap_root;

    @FXML
    public TableView<FileProperties> tb_file_input;
    @FXML
    public TableColumn<FileProperties, String> tc_file_name;
    @FXML
    public TableColumn<FileProperties, String> tc_criteria;

    private File inputFile;

    private ObservableList<FileProperties> fileProperties;

    @FXML
    public void handleClick(MouseEvent mouseEvent)  {
        if(mouseEvent.getSource() == btn_compute){
           computeAHP_WP();
        } else if(mouseEvent.getSource() == btn_open_file){
            openFile();
        }
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("csv","*.csv"));
        fileChooser.setTitle("Choose the input file");
        inputFile = fileChooser.showOpenDialog(ap_root.getScene().getWindow());
        setTableFile();
    }

    private void setTableFile(){
        tc_file_name.setCellValueFactory(new PropertyValueFactory<>("fileName"));
        tc_criteria.setCellValueFactory(new PropertyValueFactory<>("criteria"));
        if (inputFile!= null){
            //tb_file_input.setItems();
        }
    }

    private void computeAHP_WP(){
        WeightedProduct weightedProduct = new WeightedProduct();
        try {
            if(inputFile != null){
                weightedProduct.getResultOfAHPWP(inputFile);
            } else {
                showAlertBox();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlertBox() {
    }
}
