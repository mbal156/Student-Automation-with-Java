package wiew;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import Helper.*;
import Model.*;

public class LoginGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField user;
    private JPasswordField passw;
    private Dbconnect conn = new Dbconnect();

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginGui frame = new LoginGui();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public LoginGui() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 981, 515);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 128));
        panel.setBounds(10, 10, 951, 65);
        contentPane.add(panel);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBounds(307, 108, 274, 346);
        contentPane.add(panel_1);
        panel_1.setLayout(null);

        // Kullanıcı adı alanı
        user = new JTextField();
        user.setHorizontalAlignment(SwingConstants.LEFT);
        user.setFont(new Font("Tahoma", Font.PLAIN, 17));
        user.setToolTipText("Kullanıcı Adı");
        user.setBounds(71, 107, 152, 38);
        panel_1.add(user);
        user.setColumns(10);

        // Placeholder için KeyListener
        addKeyListenerPlaceholder(user, "Kullanıcı Adı");

        // Şifre alanı
        passw = new JPasswordField();
        passw.setToolTipText("Şifre");
        passw.setFont(new Font("Tahoma", Font.PLAIN, 10));
        passw.setBounds(71, 169, 152, 32);
        panel_1.add(passw);

        // Placeholder için KeyListener
        addKeyListenerPlaceholder(passw, "Şifre");

        JButton enter = new JButton("Giriş");
        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = user.getText();
                String password = new String(passw.getPassword());
                if (username.equals("Kullanıcı adı girin") || password.equals("Şifre") || username.isEmpty() || password.isEmpty()) {
                    Helper.showMsg("fill");
                } else {
                    try {
                        Connection con = conn.connDb();
                        Statement st1 = con.createStatement();
                        Statement st2 = con.createStatement();

                        ResultSet rs = st1.executeQuery("SELECT * FROM kullanıcılar");
                        boolean loginBasarili = false;

                        while (rs.next()) {
                            if (username.equals(rs.getString("KullaniciAdi")) && password.equals(rs.getString("Sifre"))) {
                                loginBasarili = true;
                                String rol = rs.getString("Rol");

                                Kullanici kullanici = null;

                                if ("ogrenci".equals(rol)) {
                                    kullanici = new Ogrenci();
                                    ResultSet rs2 = st2.executeQuery("SELECT * FROM ogrenciler WHERE OgrenciID = " + rs.getInt("KullaniciID"));
                                    if (rs2.next()) {
                                        Ogrenci ogr = (Ogrenci) kullanici;
                                        ogr.setKullaniciAdi(rs.getString("KullaniciAdi"));
                                        ogr.setKullaniciID(rs.getInt("KullaniciID"));
                                        ogr.setOgrenciID(rs2.getInt("OgrenciID"));
                                        ogr.setAd(rs2.getString("Ad"));
                                        ogr.setSoyad(rs2.getString("Soyad"));
                                        ogr.setCinsiyet(rs2.getString("Cinsiyet"));
                                        ogr.setDogumTarihi(rs2.getString("DogumTarihi"));
                                        ogr.setTelefon(rs2.getString("Telefon"));
                                        ogr.setEposta(rs2.getString("Eposta"));

                                        try {
                                            ogr.setAdres(rs2.getString("Adres"));
                                        } catch (SQLException ex) {
                                            ogr.setAdres("Adres bilgisi mevcut değil");
                                        }

                                        System.out.println("Öğrenci Girişi Başarılı. Ad: " + ogr.getAd() + ", Soyad: " + ogr.getSoyad());
                                        OgrencıGui ogg = new OgrencıGui(ogr);
                                        ogg.setVisible(true);
                                        dispose();
                                    }
                                } else if ("ogretmen".equals(rol)) {
                                    kullanici = new Ogretmen();
                                    ResultSet rs3 = st2.executeQuery("SELECT * FROM ogretmenler WHERE OgretmenID = " + rs.getInt("KullaniciID"));
                                    if (rs3.next()) {
                                        Ogretmen ogretmen = (Ogretmen) kullanici;
                                        ogretmen.setKullaniciID(rs3.getInt("OgretmenID"));
                                        ogretmen.setAd(rs3.getString("Ad"));
                                        ogretmen.setSoyad(rs3.getString("Soyad"));
                                        ogretmen.setUzmanlikAlani(rs3.getString("UzmanlikAlani"));
                                        ogretmen.setTelefon(rs3.getString("Telefon"));
                                        ogretmen.setEposta(rs3.getString("Eposta"));
                                        System.out.println("Öğretmen Girişi Başarılı. Ad: " + ogretmen.getAd() + ", Uzmanlık Alanı: " + ogretmen.getUzmanlikAlani());
                                        OgretmenGuı Org = new OgretmenGuı(ogretmen);
                                        Org.setVisible(true);
                                        dispose();
                                    }
                                } else if ("admin".equals(rol)) {
                                    kullanici = new Admin();
                                    Admin admin = (Admin) kullanici;
                                    admin.setKullaniciID(rs.getInt("KullaniciID"));
                                    admin.setKullaniciAdi(rs.getString("KullaniciAdi"));
                                    admin.setSifre(rs.getString("Sifre"));
                                    System.out.println("Admin Girişi Başarılı. Kullanıcı Adı: " + admin.getKullaniciAdi());
                                    AdminGui adg = new AdminGui(admin);
                                    adg.setVisible(true);
                                    dispose();
                                }
                            }
                        }

                        if (!loginBasarili) {
                            Helper.showMsg("Kullanıcı adı veya şifre hatalı!");
                        }

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        enter.setForeground(new Color(255, 255, 255));
        enter.setBackground(new Color(0, 0, 128));
        enter.setFont(new Font("Tahoma", Font.PLAIN, 17));
        enter.setBounds(96, 220, 104, 38);
        panel_1.add(enter);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setBounds(88, 0, 115, 89);
        panel_1.add(lblNewLabel);
        lblNewLabel.setIcon(new ImageIcon(LoginGui.class.getResource("iu_small_80.png")));

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(33, 107, 33, 38);
        panel_1.add(lblNewLabel_1);
        lblNewLabel_1.setIcon(new ImageIcon(LoginGui.class.getResource("person (1).png")));

        JLabel lblNewLabel_2 = new JLabel("");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_2.setBounds(33, 155, 33, 62);
        panel_1.add(lblNewLabel_2);
        lblNewLabel_2.setIcon(new ImageIcon(LoginGui.class.getResource("password.png")));
    }

    /**
     * Placeholder ekleyen yardımcı metod (KeyListener ile)
     */
    private void addKeyListenerPlaceholder(JTextField field, String placeholder) {
        field.setText(placeholder);
        field.setForeground(Color.GRAY);

        field.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Placeholder varsa, klavyeden ilk karakter girildiğinde sil ve rengi değiştir
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
        });

        field.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                // Eğer alan boşsa, placeholder'ı geri getir
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(Color.GRAY);
                }
            }
        });
    }
}
