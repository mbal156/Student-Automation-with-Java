package Model;

public class Bolum {
    private int bolumID;
    private String bolumAdi;

    public Bolum(int bolumID, String bolumAdi) {
        this.bolumID = bolumID;
        this.bolumAdi = bolumAdi;
    }

    public int getBolumID() {
        return bolumID;
    }

    public void setBolumID(int bolumID) {
        this.bolumID = bolumID;
    }

    public String getBolumAdi() {
        return bolumAdi;
    }

    public void setBolumAdi(String bolumAdi) {
        this.bolumAdi = bolumAdi;
    }
}