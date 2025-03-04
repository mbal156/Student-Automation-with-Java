package Model;

public class Ders {
    private int dersID;
    private String dersAdi;
    private int kredi;
    private int bolumID;

    public Ders(int dersID, String dersAdi, int kredi, int bolumID) {
        this.dersID = dersID;
        this.dersAdi = dersAdi;
        this.kredi = kredi;
        this.bolumID = bolumID;
    }

    public int getDersID() {
        return dersID;
    }

    public void setDersID(int dersID) {
        this.dersID = dersID;
    }

    public String getDersAdi() {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi) {
        this.dersAdi = dersAdi;
    }

    public int getKredi() {
        return kredi;
    }

    public void setKredi(int kredi) {
        this.kredi = kredi;
    }

    public int getBolumID() {
        return bolumID;
    }

    public void setBolumID(int bolumID) {
        this.bolumID = bolumID;
    }
}