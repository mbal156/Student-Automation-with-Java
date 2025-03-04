package Model;


public class Admin extends Kullanici {
   

    public Admin(int kullaniciID, String kullaniciAdi, String sifre) {
        super(kullaniciID, kullaniciAdi, sifre);
    }

    public Admin() {
        super();
    }
    @Override
    public boolean login(String username, String password) {
        // Giriş işlemi başarılı olursa true döner.
        return true;
    }
}
