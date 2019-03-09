package filkom.skripsi;

import java.io.*;

public class WeightedProduct {

    public double[] calculateVectorSi(double[] priorityWeight,double[][] alternativeData){
        double[] result = new double[alternativeData.length];
        for (int i =0; i < alternativeData.length; i++){
            result[i] = 1;
            for (int j = 0; j < alternativeData[i].length; j++){
                result[i] *= Math.pow(alternativeData[i][j],priorityWeight[j]);
            }
        }
        return result;
    }

    public double[] calculateVectorVi(double[] vectorSi){
        double[] result = new double[vectorSi.length];
        double sumOfVectorSi = 0;
        for (int i = 0; i < vectorSi.length; i++){
            sumOfVectorSi += vectorSi[i];
        }
        for (int i = 0; i < result.length; i++){
            result[i] = vectorSi[i] / sumOfVectorSi;
        }
        return result;
    }

    double[] selectionSort(double arr[])
    {
        int n = arr.length;

        // One by one move boundary of unsorted subarray
        for (int i = 0; i < n-1; i++)
        {
            // Find the minimum element in unsorted array
            int min_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] > arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            double temp = arr[min_idx];
            arr[min_idx] = arr[i];
            arr[i] = temp;
        }
        return arr;
    }

    public double[][] readData(File file) throws IOException {
        String line = "";
        int i = 0;
        String split = ",";
        boolean isAlternative = false;
        BufferedReader brSize = new BufferedReader(new FileReader(file));
        int criteriaCount = brSize.readLine().split(split).length;
        int dataCount = 0;
        while ((line = brSize.readLine()) != null ) {
            if (line.contains("data")) {
                isAlternative = true;
                continue;
            }
            if (!isAlternative) {
                continue;
            }
            dataCount++;
        }
        double[][] alternativeMatrix = new double[dataCount][criteriaCount];

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            isAlternative = false;
            while ((line = br.readLine()) != null ) {
                if (line.contains("data")){
                    isAlternative = true;
                    continue;
                }
                if (!isAlternative){
                    continue;
                }
                for(int j = 0; j < criteriaCount; j++){
                    alternativeMatrix[i][j] = Double.parseDouble(line.split(split)[j]);
                    System.out.print(alternativeMatrix[i][j]+" ");
                }
                i++;
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alternativeMatrix;
    }

    public double[] getResultOfAHPWP(File file) throws IOException {
        AHP ahp = new AHP();
        //ahp.useAHPToGetConsistency(file);
        double[][] matrixAlternative = readData(file);
       // ahp.print2DArray(matrixAlternative);
        double[] vectorSi = calculateVectorSi(ahp.preferenceVector(ahp.normalizeMatrix(ahp.createComparisonMatrix(file))),matrixAlternative);
        System.out.println("Vektor Si");
        ahp.print1DArray(vectorSi);
        double[] vectorVi = calculateVectorVi(vectorSi);
        System.out.println("Vektor Vi");
        ahp.print1DArray(vectorVi);
        double[] result = selectionSort(vectorVi);
        System.out.println("final result");
        ahp.print1DArray(result);

        return result;
    }

    public static void main(String[] args) throws IOException {
        WeightedProduct weightedProduct = new WeightedProduct();
        weightedProduct.getResultOfAHPWP(new File("data/tes-data-ahp.csv"));
    }
}
