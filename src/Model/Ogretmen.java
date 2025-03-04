package Model;

public class Ogretmen extends Kullanici {
    private String ad;
    private String soyad;
    private String uzmanlikAlani;
    private String telefon;
    private String eposta;

    public Ogretmen(int kullaniciID, String kullaniciAdi, String sifre, String ad, String soyad, String uzmanlikAlani, String telefon, String eposta) {
        super(kullaniciID, kullaniciAdi, sifre);
        this.ad = ad;
        this.soyad = soyad;
        this.uzmanlikAlani = uzmanlikAlani;
        this.telefon = telefon;
        this.eposta = eposta;
    }

    public Ogretmen() {
    	
    }
    
    
    // Getter ve Setter Metodları
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getUzmanlikAlani() {
        return uzmanlikAlani;
    }

    public void setUzmanlikAlani(String uzmanlikAlani) {
        this.uzmanlikAlani = uzmanlikAlani;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEposta() {
        return eposta;
    }

    public void setEposta(String eposta) {
        this.eposta = eposta;
    }
    @Override
    public boolean login(String username, String password) {
       
        // Giriş işlemi başarılı olursa true döner.
        return true;
    }
}
