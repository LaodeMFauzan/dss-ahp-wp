package filkom.skripsi.controller;

import filkom.skripsi.WeightedProduct;
import filkom.skripsi.model.OutputProperties;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class ResultController implements Initializable {

    @FXML
    public TableView<OutputProperties> tb_output = new TableView<>();
    @FXML
    public TableColumn<OutputProperties, Integer> tc_alternative = new TableColumn<>();
    @FXML
    public TableColumn<OutputProperties, Double> tc_vi = new TableColumn<>();
    @FXML
    public TableColumn<OutputProperties, Integer> tc_index = new TableColumn<>();

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
}
