package filkom.skripsi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage stage)  {
        try{
            URL url = new File("src/filkom/skripsi/gui/upload_file_layout.fxml").toURL();
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            String image = JavaFXApplication9.class.getResource("src/filkom/skripsi/style/decision-image.png").toExternalForm();
            root.setStyle("-fx-background-image: url('" + image + "'); " +
                    "-fx-background-position: center center; " +
                    "-fx-background-repeat: stretch;");

            stage.setTitle("DS");
            stage.setScene(scene);
            stage.show();

            WeightedProduct weightedProduct = new WeightedProduct();
            weightedProduct.getResultOfAHPWP(new File("data/manualisasi.csv"));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
