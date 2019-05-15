package filkom.skripsi.model;

public class OutputProperties {
    private int recommendedIndex;
    private double vektorVi;
    private int index;

    public OutputProperties(int index, int recommendedIndex, double vektorVi) {
        this.index = index;
        this.recommendedIndex = recommendedIndex;
        this.vektorVi = vektorVi;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getRecommendedIndex() {
        return recommendedIndex;
    }

    public void setRecommendedIndex(int recommendedIndex) {
        this.recommendedIndex = recommendedIndex;
    }

    public double getVektorVi() {
        return vektorVi;
    }

    public void setVektorVi(double vektorVi) {
        this.vektorVi = vektorVi;
    }
}
