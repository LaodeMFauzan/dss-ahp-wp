package filkom.skripsi.model;

public class FileProperties {
    private String fileName;
    private int criteria;

    public FileProperties(String fileName, int criteria) {
        this.fileName = fileName;
        this.criteria = criteria;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getCriteria() {
        return criteria;
    }

    public void setCriteria(int criteria) {
        this.criteria = criteria;
    }
}
