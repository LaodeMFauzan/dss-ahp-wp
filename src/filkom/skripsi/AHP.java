package filkom.skripsi;

import java.io.*;
import java.util.HashMap;


public class AHP {

    public AHP() {

    }

    public double[][] createComparisonMatrix(File file) throws IOException {
        String line = "";
        int i = 0;
        String split = ",";
        BufferedReader brSize = new BufferedReader(new FileReader(file));
        int criteria = brSize.readLine().split(split).length;
        double[][] comparisonMatrix = new double[criteria][criteria];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            while ((line = br.readLine()) != null ) {
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

    public double[][] normalizeMatrix(double[][] comparisonMatrix){
        double[] sumOfMatrix = new double[comparisonMatrix.length];
        double[][] normalizeMatrix = new double[comparisonMatrix.length][comparisonMatrix.length];
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
                sumOfMatrix[i] += comparisonMatrix[j][i];
            }
        }
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
               normalizeMatrix[i][j] = comparisonMatrix[i][j] / sumOfMatrix[j];
            }
        }
        return normalizeMatrix;
    }

    public double[] preferenceVector(double[][] normalizeMatrix){
        double[] prefereneVectors = new double[normalizeMatrix.length];
        for (int i = 0; i < normalizeMatrix.length; i++){
            for(int j = 0; j < normalizeMatrix[i].length; j++){
                prefereneVectors[i] += normalizeMatrix[i][j];
            }
            prefereneVectors[i] /= normalizeMatrix[i].length;
        }
        return prefereneVectors;
    }

    public double[] getMatrixCompTimesVectorPref(double[][] comparisonMatrix, double[] preferenceVector){
        double[] result = new double[preferenceVector.length];
        for (int i = 0; i < comparisonMatrix.length; i++){
            for(int j = 0; j < comparisonMatrix[i].length; j++){
                result[i] += comparisonMatrix[i][j]*preferenceVector[j];
            }
        }
        return result;
    }

    public double getLambdaMax(double[] preferenceVector,double[] matrixMultiplication){
        double result = 0;
        for (int i = 0; i < preferenceVector.length; i++){
            result += matrixMultiplication[i] / preferenceVector[i];
        }
        return  result / preferenceVector.length;
    }

    public double getConsistencyIndex(double lambdaMax,double criteria){
        return (lambdaMax - criteria) / (criteria-1);
    }

    public double getConsistencyRatio(double consistencyIndex,double criteria, HashMap<Double,Double> ratioIndex){
        return consistencyIndex / ratioIndex.get(criteria);
    }

    public boolean isConsitent(double consistencyRatio){
        return (consistencyRatio <= 0.1);
    }

    public void useAHPToGetConsistency(File file) throws IOException {
        HashMap ratioIndex = new HashMap();
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

        double[][] comparisonMatrix = createComparisonMatrix(file);
        double criteria = Double.valueOf(comparisonMatrix.length);
        System.out.println("Kriteria = "+criteria);

        double[][] matrixNormalization = normalizeMatrix(comparisonMatrix);
        double[] preferenceVector = preferenceVector(matrixNormalization);
        double[] matrixMultiplication = getMatrixCompTimesVectorPref(comparisonMatrix,preferenceVector);
        double lambdaMax = getLambdaMax(preferenceVector,matrixMultiplication);
        double consistencyIndex = getConsistencyIndex(lambdaMax,criteria);
        double consistencyRatio = getConsistencyRatio(consistencyIndex,criteria,ratioIndex);
        boolean isConsistent = isConsitent(consistencyRatio);

        System.out.println("Matriks Perbandingan");
        print2DArray(comparisonMatrix);
        System.out.println("Matriks Normalisasi");
        print2DArray(matrixNormalization);
        System.out.println("Vektor preferensi");
        print1DArray(preferenceVector);
        System.out.println("Perkalian Matrix");
        print1DArray(matrixMultiplication);
        System.out.println("Lambda max = "+lambdaMax);
        System.out.println("Consistency Index = "+consistencyIndex);
        System.out.println("Consistency ratio = "+consistencyRatio);
        System.out.println("Is Consistent = "+isConsistent);
    }

    public void print2DArray(double[][] matrix){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void print1DArray(double[] arr){
        for (int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
        System.out.println();
    }

    public static void main(String[] args) throws IOException {
        AHP ahp = new AHP();
        ahp.useAHPToGetConsistency(new File("data/tes-data-ahp.csv"));
    }
}
