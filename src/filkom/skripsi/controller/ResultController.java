package filkom.skripsi.controller;

import filkom.skripsi.WeightedProduct;
import filkom.skripsi.model.OutputProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    public AnchorPane ap_root_result;

    @FXML
    public TableView<OutputProperties> tb_output = new TableView<>();
    @FXML
    public TableColumn<OutputProperties, Integer> tc_alternative = new TableColumn<>();
    @FXML
    public TableColumn<OutputProperties, Double> tc_vi = new TableColumn<>();
    @FXML
    public TableColumn<OutputProperties, Integer> tc_index = new TableColumn<>();

    @FXML
    public Button btn_back;

    public ResultController() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WeightedProduct weightedProduct = HomeController.getWeightedProduct();
        tc_alternative.setCellValueFactory(new PropertyValueFactory<>("recommendedIndex"));
        tc_vi.setCellValueFactory(new PropertyValueFactory<>("vektorVi"));
        tc_index.setCellValueFactory(new PropertyValueFactory<>("index"));
        ObservableList<OutputProperties> outputProperties = FXCollections.observableArrayList();

        if (weightedProduct != null){
            for(int i = 0; i < weightedProduct.getRecommendedIndex().length; i++){
                int sequenceNum = i+1;
                outputProperties.add(new OutputProperties(sequenceNum, weightedProduct.getRecommendedIndex()[i], weightedProduct.getVectorVi()[i]));
            }
            tb_output.setItems(outputProperties);
        }
    }

    @FXML
    public void handleClick(MouseEvent mouseEvent){
        if (mouseEvent.getSource() == btn_back){
            goToHome();
        }
    }

    /**
     * go to home page
     */
    private void goToHome() {
        ScreenController screenController = new ScreenController(ap_root_result.getScene());
        URL url ;
        try {
            url = new File("src/filkom/skripsi/gui/upload_file_layout.fxml").toURL();
            screenController.addScreen("Home", FXMLLoader.load(url));
            screenController.activate("Home");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
