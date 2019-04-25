package filkom.skripsi;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        WeightedProduct weightedProduct = new WeightedProduct();
        weightedProduct.getResultOfAHPWP(new File("data/manualisasi.csv"));
    }
}
