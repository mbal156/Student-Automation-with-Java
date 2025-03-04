package Model;

public class Kayit {
    private int kayitID;
    private Ogrenci ogrenci;
    private Ders ders;
    private double notu;

    public Kayit(int kayitID, Ogrenci ogrenci, Ders ders, double notu) {
        this.kayitID = kayitID;
        this.ogrenci = ogrenci;
        this.ders = ders;
        this.notu = notu;
    }

    // Getter ve Setter Metodları
    public int getKayitID() {
        return kayitID;
    }

    public void setKayitID(int kayitID) {
        this.kayitID = kayitID;
    }

    public Ogrenci getOgrenci() {
        return ogrenci;
    }

    public void setOgrenci(Ogrenci ogrenci) {
        this.ogrenci = ogrenci;
    }

    public Ders getDers() {
        return ders;
    }

    public void setDers(Ders ders) {
        this.ders = ders;
    }

    public double getNotu() {
        return notu;
    }

    public void setNotu(double notu) {
        this.notu = notu;
    }

    
}

