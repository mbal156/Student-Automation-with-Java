package Model;

public abstract class  Kullanici {
    private int kullaniciID;
    private String kullaniciAdi;
    private String sifre;

    // Constructor
    public Kullanici(int kullaniciID, String kullaniciAdi, String sifre) {
        this.kullaniciID = kullaniciID;
        this.kullaniciAdi = kullaniciAdi;
        this.sifre = sifre;
    }
    public Kullanici() {}
    
    // Getter ve Setter'lar
    public int getKullaniciID() {
        return kullaniciID;
    }

    public void setKullaniciID(int kullaniciID) {
        this.kullaniciID = kullaniciID;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }

    public void setKullaniciAdi(String kullaniciAdi) {
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
 // Ortak metot
    public abstract boolean login(String username, String password);

}
