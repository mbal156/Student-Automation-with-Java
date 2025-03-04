package wiew;

import Helper.*;
import Model.Ogrenci;
import Model.Iprofilemanagement;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OgrencıGui extends JFrame implements Iprofilemanagement  {
    private JTextField fldTelefon;
    private JTextField fldEposta;
    private JTextField fldAdres; // Adres alanı
    private JButton btnGuncelle;

    private DefaultTableModel mdlDersProgrami;
    private DefaultTableModel mdlSinavSonuclari;
    private DefaultTableModel mdlMesajlar;

    private JTable tblDersProgrami;
    private JTable tblSinavSonuclari;
    private JTable tblMesajlar;

    private Dbconnect db = new Dbconnect();
    private Ogrenci ogrenci;

    public OgrencıGui(Ogrenci ogr) {
        if (ogr == null) {
            throw new IllegalArgumentException("Ogrenci nesnesi null olamaz!");
        }
        this.ogrenci = ogr;

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Öğrenci Paneli - Hoş Geldiniz, " + ogrenci.getAd());
        setLocationRelativeTo(null);

        // Wrapper Panel
        JPanel wrapper = new JPanel(new BorderLayout());
        setContentPane(wrapper);

        // Üst Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 0, 128)); // Mavi arka plan
        topPanel.setPreferredSize(new Dimension(1000, 70));
        topPanel.setLayout(new BorderLayout());

        JLabel lblBaslik = new JLabel("Öğrenci Paneli", JLabel.CENTER);
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("Arial", Font.BOLD, 24));
        topPanel.add(lblBaslik, BorderLayout.CENTER);

        wrapper.add(topPanel, BorderLayout.NORTH);
        JButton btnExit = new JButton("Çıkış");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBackground(new Color(220, 53, 69)); // Kırmızı renk
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                this,
                "Çıkmak istediğinize emin misiniz?",
                "Çıkış Onayı",
                JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); // Uygulamayı kapat
            }
        });

        topPanel.add(btnExit, BorderLayout.EAST);

        wrapper.add(topPanel, BorderLayout.NORTH);
        
        
        // TabbedPane
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Profil Sekmesi
        JPanel pnlProfil = new JPanel();
        pnlProfil.setLayout(null); // Manuel konumlandırma
        pnlProfil.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblProfil = new JLabel("Profil Bilgileri");
        lblProfil.setFont(new Font("Arial", Font.BOLD, 18));
        lblProfil.setBounds(10, 10, 200, 30);
        pnlProfil.add(lblProfil);

        // Telefon Alanı
        JLabel lblTelefon = new JLabel("Telefon:");
        lblTelefon.setBounds(10, 60, 100, 30);
        pnlProfil.add(lblTelefon);

        fldTelefon = new JTextField();
        fldTelefon.setBounds(120, 60, 200, 30);
        pnlProfil.add(fldTelefon);

        // E-posta Alanı
        JLabel lblEposta = new JLabel("E-posta:");
        lblEposta.setBounds(10, 110, 100, 30);
        pnlProfil.add(lblEposta);

        fldEposta = new JTextField();
        fldEposta.setBounds(120, 110, 200, 30);
        pnlProfil.add(fldEposta);

        // Adres Alanı
        JLabel lblAdres = new JLabel("Adres:");
        lblAdres.setBounds(10, 160, 100, 30);
        pnlProfil.add(lblAdres);

        fldAdres = new JTextField();
        fldAdres.setBounds(120, 160, 200, 30);
        pnlProfil.add(fldAdres);

        // Güncelle Butonu
        btnGuncelle = new JButton("Güncelle");
        btnGuncelle.setBounds(120, 220, 100, 30);
        pnlProfil.add(btnGuncelle);

        tabbedPane.addTab("Profil", pnlProfil);

        // Ders Programı Sekmesi
        mdlDersProgrami = new DefaultTableModel(new String[]{"Ders Adı", "Kredi"}, 0);
        tblDersProgrami = new JTable(mdlDersProgrami);
        tabbedPane.addTab("Ders Programı", new JScrollPane(tblDersProgrami));

        // Sınav Sonuçları Sekmesi
        mdlSinavSonuclari = new DefaultTableModel(new String[]{"Ders Adı", "Kredi", "Not"}, 0);
        tblSinavSonuclari = new JTable(mdlSinavSonuclari);
        tabbedPane.addTab("Sınav Sonuçları", new JScrollPane(tblSinavSonuclari));

        mdlMesajlar = new DefaultTableModel(new String[]{"Gönderen", "Mesaj", "Tarih"}, 0); // Tarih sütunu eklendi
        tblMesajlar = new JTable(mdlMesajlar);
        tabbedPane.addTab("Mesajlar", new JScrollPane(tblMesajlar));

        // Ders Ekleme Sekmesi
        JPanel pnlDersEkle = new JPanel(new BorderLayout());
        DefaultListModel<String> listModel = new DefaultListModel<>();
        JList<String> dersListesi = new JList<>(listModel);
        pnlDersEkle.add(new JScrollPane(dersListesi), BorderLayout.CENTER);

        JButton btnDersEkle = new JButton("Ders Ekle");
        pnlDersEkle.add(btnDersEkle, BorderLayout.SOUTH);
        tabbedPane.addTab("Ders Ekleme", pnlDersEkle);

        // Dersleri Listeye Yükle
        loadDersListesi(listModel);

        btnDersEkle.addActionListener(e -> dersEkle(dersListesi));

        wrapper.add(tabbedPane, BorderLayout.CENTER);

        // Eventler
        btnGuncelle.addActionListener(e -> profilGuncelle());
        setVisible(true);

        // Verileri yükle
        loadProfilBilgileri();
        loadDersProgrami();
        loadSinavSonuclari();
        loadMesajlar();
    }

    private void loadDersListesi(DefaultListModel<String> listModel) {
        String query = "SELECT DersID, DersAdi FROM dersler";
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listModel.addElement(rs.getInt("DersID") + " - " + rs.getString("DersAdi"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void dersEkle(JList<String> dersListesi) {
        String selectedValue = dersListesi.getSelectedValue();
        if (selectedValue != null) {
            try (Connection con = db.connDb();
                 PreparedStatement ps = con.prepareStatement(
                         "INSERT INTO kayitlar (OgrenciID, DersID) VALUES (?, ?)")) {
                int dersID = Integer.parseInt(selectedValue.split(" - ")[0]); // DersID'yi al
                ps.setInt(1, ogrenci.getKullaniciID());
                ps.setInt(2, dersID);
                ps.executeUpdate();
                JOptionPane.showMessageDialog(this, "Ders başarıyla eklendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                loadDersProgrami(); // Ders Programı sekmesini güncelle
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ders eklenirken hata oluştu!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Lütfen bir ders seçin.", "Uyarı", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void loadProfilBilgileri() {
        String query = "SELECT Telefon, Eposta, Adres FROM ogrenciler WHERE OgrenciID = ?";
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ogrenci.getKullaniciID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fldTelefon.setText(rs.getString("Telefon"));
                fldEposta.setText(rs.getString("Eposta"));
                fldAdres.setText(rs.getString("Adres"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void profilGuncelle() {
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(
                     "UPDATE ogrenciler SET Telefon = ?, Eposta = ?, Adres = ? WHERE OgrenciID = ?")) {
            ps.setString(1, fldTelefon.getText());
            ps.setString(2, fldEposta.getText());
            ps.setString(3, fldAdres.getText());
            ps.setInt(4, ogrenci.getKullaniciID());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Profil güncellendi.", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadDersProgrami() {
        mdlDersProgrami.setRowCount(0);
        String query =
                "SELECT DISTINCT d.DersAdi, d.Kredisi " +
                        "FROM kayitlar k " +
                        "INNER JOIN dersler d ON k.DersID = d.DersID " +
                        "WHERE k.OgrenciID = ?";
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ogrenci.getKullaniciID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mdlDersProgrami.addRow(new Object[]{
                        rs.getString("DersAdi"),
                        rs.getInt("Kredisi")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadSinavSonuclari() {
        mdlSinavSonuclari.setRowCount(0);
        String query =
                "SELECT d.DersAdi, d.Kredisi, k.Notu " +
                        "FROM kayitlar k " +
                        "INNER JOIN dersler d ON k.DersID = d.DersID " +
                        "WHERE k.OgrenciID = ?";
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ogrenci.getKullaniciID());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mdlSinavSonuclari.addRow(new Object[]{
                        rs.getString("DersAdi"),
                        rs.getInt("Kredisi"),
                        rs.getDouble("Notu")
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadMesajlar() {
        mdlMesajlar.setRowCount(0); // Tabloyu sıfırla
        String query =
                "SELECT m.Mesaj, m.Tarih, o.Ad, o.Soyad " +
                "FROM mesajlar m " +
                "INNER JOIN ogretmenler o ON m.OgretmenID = o.OgretmenID " + // Gönderen olarak Öğretmen bilgisi
                "WHERE m.OgrenciID = ?  OR m.OgrenciID IS NULL"; // Sadece bu öğrenciye ait mesajları getir
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, ogrenci.getKullaniciID()); // Öğrenci ID'sini gönderiyoruz
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                mdlMesajlar.addRow(new Object[]{
                        rs.getString("Ad") + " " + rs.getString("Soyad"), // Gönderen Öğretmenin Ad ve Soyadı
                        rs.getString("Mesaj"), // Mesaj metni
                        rs.getDate("Tarih") // Mesajın gönderilme tarihi
                });
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
