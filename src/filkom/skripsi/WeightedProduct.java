package filkom.skripsi;

import java.io.*;

public class WeightedProduct {

    private double[] vectorSi,vectorVi;
    private int[] alternativeIndex;
    private int[] recommendedIndex;

    public double[] getVectorVi() {
        return vectorVi;
    }

    public void setVectorVi(double[] vectorVi) {
        this.vectorVi = vectorVi;
    }

    public int[] getRecommendedIndex() {
        return recommendedIndex;
    }

    public void setRecommendedIndex(int[] recommendedIndex) {
        this.recommendedIndex = recommendedIndex;
    }

    /**
     * @param priorityWeight
     * @param alternativeData
     * @return vector Si
     */
    private double[] calculateVectorSi(double[] priorityWeight, double[][] alternativeData){
        this.vectorSi = new double[alternativeData.length];
        for (int i =0; i < alternativeData.length; i++){
            vectorSi[i] = 1;
            for (int j = 0; j < alternativeData[i].length; j++){
                vectorSi[i] *= Math.pow(alternativeData[i][j],priorityWeight[j]);
            }
        }
        return vectorSi;
    }

    /**
     * @return vector Vi
     */
    private double[] calculateVectorVi(){
        this.vectorVi = new double[vectorSi.length];
        double sumOfVectorSi = 0;
        for (double v : vectorSi) {
            sumOfVectorSi += v;
        }
        for (int i = 0; i < vectorVi.length; i++){
            vectorVi[i] = vectorSi[i] / sumOfVectorSi;
        }
        return vectorVi;
    }

    // Use selection sort to sort the result
    private double[] selectionSort(double[] arr) {
        int n = arr.length;

        for (int i = 0; i < n-1; i++)
        {
            int max_idx = i;
            for (int j = i+1; j < n; j++)
                if (arr[j] > arr[max_idx]){
                    max_idx = j;
                }

            double temp = arr[max_idx];
            arr[max_idx] = arr[i];
            arr[i] = temp;

            int tempAlt = alternativeIndex[max_idx];
            alternativeIndex[max_idx] = alternativeIndex[i];
            alternativeIndex[i] = tempAlt;
        }
        return arr;
    }

    /**
     * @param file
     * @return alternative matrix
     * @throws IOException
     */
    private double[][] readData(File file) throws IOException {
        String line;
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
        alternativeIndex = new int[dataCount];

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
                    alternativeMatrix[i][j] = Double.parseDouble(line.split(",")[j]);
                    System.out.print(alternativeMatrix[i][j]+" ");
                    alternativeIndex[i] = i;
                }
                i++;
                System.out.println();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return alternativeMatrix;
    }

    public void getResultOfAHPWP(File file) throws IOException {
        AHP ahp = new AHP();
        ahp.useAHPToGetConsistency(file);
        System.out.println("Vektor Si");
        ahp.print1DArray(calculateVectorSi(ahp.getPreferenceVector(),readData(file)));
        System.out.println("Vektor Vi");
        ahp.print1DArray(calculateVectorVi());
        double[] result = selectionSort(vectorVi);
        System.out.println("final result");
        ahp.print1DArray(result);
        recommendedIndex = new int[alternativeIndex.length];
        System.out.println("Sequence Of Recommended Candidate : ");
        for (int i = 0; i < alternativeIndex.length; i++) {
            recommendedIndex[i] = alternativeIndex[i] + 1;
            System.out.println("Kandidat " + recommendedIndex[i]);
        }
    }

    public static void main(String[] args)  {

    }
}
