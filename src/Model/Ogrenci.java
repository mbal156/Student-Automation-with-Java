package Model;

import java.sql.Date;

public class Ogrenci extends Kullanici {
	private int ogrenciID;
    private String ad;
    private String soyad;
    private String dogumTarihi;
    private String cinsiyet;
    private String telefon;
    private String eposta;
    private String adres;
	
    public Ogrenci(int kullaniciID, String kullaniciAdi, String sifre,int ogrenciID, String ad, String soyad, String dogumTarihi, String cinsiyet, String telefon,
			String eposta, String adres) {
    	super(kullaniciID, kullaniciAdi, sifre);
		this.ogrenciID = ogrenciID;
		this.ad = ad;
		this.soyad = soyad;
		this.dogumTarihi = dogumTarihi;
		this.cinsiyet = cinsiyet;
		this.telefon = telefon;
		this.eposta = eposta;
		this.adres = adres;
	}
	public Ogrenci() {
		
	}
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
	public String getDogumTarihi() {
		return dogumTarihi;
	}
	public void setDogumTarihi(String date) {
		this.dogumTarihi = date;
	}
	public String getCinsiyet() {
		return cinsiyet;
	}
	public void setCinsiyet(String cinsiyet) {
		this.cinsiyet = cinsiyet;
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
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public int getOgrenciID() {
		return ogrenciID;
	}
	public void setOgrenciID(int ogrenciID) {
		this.ogrenciID = ogrenciID;
	}
	@Override
    public boolean login(String username, String password) {
        // Giriş işlemi başarılı olursa true döner.
        return true;
    }
}
