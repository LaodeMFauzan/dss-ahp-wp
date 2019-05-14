package filkom.skripsi.controller;

import filkom.skripsi.WeightedProduct;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;

public class ResultController {

    @FXML
    public TableView tb_output = new TableView<>();

   private WeightedProduct weightedProduct = new WeightedProduct();

    public ResultController() {
       this.weightedProduct = HomeController.getWeightedProduct();
       if (weightedProduct != null){
           
       }
    }
}
