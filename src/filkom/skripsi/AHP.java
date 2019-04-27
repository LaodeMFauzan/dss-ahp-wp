package filkom.skripsi;

import java.io.*;
import java.util.HashMap;


public class AHP {

    private double[][] comparisonMatrix,normalizeMatrix;
    private double[] preferenceVector,matrixMultiplication;
    private double consistencyIndex,lambdaMax,consistencyRatio;

    public AHP() {

    }

    public double[] getPreferenceVector() {
        return preferenceVector;
    }

    public double[][] getComparisonMatrix() {
        return comparisonMatrix;
    }

    public double[][] getNormalizeMatrix() {
        return normalizeMatrix;
    }

    public double[] getMatrixMultiplication() {
        return matrixMultiplication;
    }

    private double[][] getComparisonMatrix(File file) throws IOException {
        String line = "";
        int i = 0;
        String split = ",";
        BufferedReader brSize = new BufferedReader(new FileReader(file));
        int criteria = brSize.readLine().split(split).length;
        this.comparisonMatrix = new double[criteria][criteria];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null && !line.contains("data") ) {
                for(int j = 0; j < criteria; j++){
                    comparisonMatrix[i][j] = Double.parseDouble(line.split(split)[j]);
                    System.out.print(comparisonMatrix[i][j] +" ");
                }
                i++;
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return comparisonMatrix;
    }

    private double[][] normalizeMatrix(){
        double[] sumOfMatrix = new double[comparisonMatrix.length];
        this.normalizeMatrix = new double[comparisonMatrix.length][comparisonMatrix.length];
        sumOfMatrix = computesumOfMatrix(sumOfMatrix);
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
               normalizeMatrix[i][j] = comparisonMatrix[i][j] / sumOfMatrix[j];
            }
        }
        return normalizeMatrix;
    }

    private double[] computesumOfMatrix(double[] sumOfMatrix){
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
                sumOfMatrix[i] += comparisonMatrix[j][i];
            }
        }
        return sumOfMatrix;
    }

    private double[] createPreferenceVector(){
        this.preferenceVector = new double[normalizeMatrix.length];
        for (int i = 0; i < normalizeMatrix.length; i++){
            for(int j = 0; j < normalizeMatrix[i].length; j++){
                preferenceVector[i] += normalizeMatrix[i][j];
            }
            preferenceVector[i] /= normalizeMatrix[i].length;
        }
        return preferenceVector;
    }

    private double[] getMatrixCompTimesVectorPref(){
        this.matrixMultiplication = new double[preferenceVector.length];
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
                matrixMultiplication[i] += comparisonMatrix[i][j]*preferenceVector[j];
            }
        }
        return matrixMultiplication;
    }

    private double getLambdaMax(){
        double result = 0;
        for (int i = 0; i < preferenceVector.length; i++){
            result += matrixMultiplication[i] / preferenceVector[i];
        }
        this.lambdaMax =   result / preferenceVector.length;

        return this.lambdaMax;
    }

    private double getConsistencyIndex(double criteria){
        this.consistencyIndex = (lambdaMax - criteria) / (criteria-1);
        return this.consistencyIndex;
    }

    private double getConsistencyRatio(double criteria, HashMap<Double, Double> ratioIndex){
        this.consistencyRatio = consistencyIndex / ratioIndex.get(criteria);
        return this.consistencyRatio;
    }

    private boolean isConsitent(){
        return (consistencyRatio <= 0.1);
    }

    void useAHPToGetConsistency(File file) throws IOException {
        HashMap<Double,Double> ratioIndex = new HashMap();
        ratioIndex.put(1.0,0.0);
        ratioIndex.put(2.0,0.0);
        ratioIndex.put(3.0,0.58);
        ratioIndex.put(4.0,0.9);
        ratioIndex.put(5.0,1.12);
        ratioIndex.put(6.0,1.24);
        ratioIndex.put(7.0,1.32);
        ratioIndex.put(8.0,1.41);
        ratioIndex.put(9.0,1.45);
        ratioIndex.put(10.0,1.49);

        double criteria = getComparisonMatrix(file).length;
        System.out.println("Kriteria = "+criteria);

        System.out.println("Matriks Perbandingan");
        print2DArray(comparisonMatrix);
        System.out.println("Matriks Normalisasi");
        print2DArray(normalizeMatrix());
        System.out.println("Vektor preferensi");
        print1DArray(createPreferenceVector());
        System.out.println("Perkalian Matrix");
        print1DArray(getMatrixCompTimesVectorPref());
        System.out.println("Lambda max = "+getLambdaMax());
        System.out.println("Consistency Index = "+getConsistencyIndex(criteria));
        System.out.println("Consistency ratio = "+getConsistencyRatio(criteria,ratioIndex));
        System.out.println("Is Consistent = "+isConsitent());
    }

    public void print2DArray(double[][] matrix){
        for (double[] matrix1 : matrix) {
            for (double v : matrix1) {
                System.out.print(v + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print1DArray(double[] arr){
        for (double v : arr) {
            System.out.println(v);
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        AHP ahp = new AHP();
        ahp.useAHPToGetConsistency(new File("data/tes-data-ahp.csv"));
    }
}
