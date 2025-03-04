package wiew;

import Helper.*;
import Model.Ogretmen;
import Model.IDataLoader;  // IDataLoader importu
import Model.IMesajGonder;  // IMesajGonder importu

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.awt.event.ActionEvent;

public class OgretmenGuı extends JFrame implements IDataLoader, IMesajGonder {  // IMesajGonder'ı da implement ettik
    private JPanel wrapper;
    private JTable tblNotlar;
    private JTextField fldOgrenciID, fldDersID, fldNot;
    private JButton btnNotEkle, btnNotGuncelle;
    private JTextField fldMesajOgrenciID, fldMesaj;
    private JButton btnMesajGonder;

    private DefaultTableModel mdlNotlar;
    private Object[] rowNotlar;

    private Dbconnect db = new Dbconnect();

    public OgretmenGuı(Ogretmen ogretmen) {
        // Ana panel oluştur
        wrapper = new JPanel();
        getContentPane().add(wrapper);
        wrapper.setLayout(null);

        // Üst Panel
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 0, 128));
        panel.setBounds(0, 0, 800, 56);
        panel.setLayout(new BorderLayout());

        JLabel lblHeader = new JLabel("Öğretmen Paneli");
        lblHeader.setForeground(Color.WHITE);
        lblHeader.setFont(new Font("Arial", Font.BOLD, 24));
        lblHeader.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblHeader, BorderLayout.CENTER);

        // Çıkış Butonu
        JButton btnExit = new JButton("Çıkış");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBackground(new Color(220, 53, 69)); // Kırmızı renk
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        btnExit.addActionListener((ActionEvent e) -> {
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

        panel.add(btnExit, BorderLayout.EAST);
        wrapper.add(panel);

        // Üst kısım: Öğretmen bilgisi
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlHeader.setBounds(0, 66, 786, 23);
        JLabel lblWelcome = new JLabel("Hoş Geldiniz, " + ogretmen.getAd() + " | Uzmanlık Alanı: " + ogretmen.getUzmanlikAlani());
        pnlHeader.add(lblWelcome);
        wrapper.add(pnlHeader);

        // Orta kısım: Tablo ve veriler
        mdlNotlar = new DefaultTableModel();
        Object[] colNotlar = {"Kayıt ID", "Öğrenci ID", "Ders ID", "Not"};
        mdlNotlar.setColumnIdentifiers(colNotlar);
        tblNotlar = new JTable(mdlNotlar);
        JScrollPane scrollPane = new JScrollPane(tblNotlar);
        scrollPane.setBounds(0, 88, 786, 372);
        wrapper.add(scrollPane);

        // Alt kısım: Form ve butonlar
        JPanel pnlBottom = new JPanel();
        pnlBottom.setBounds(0, 460, 786, 103);
        pnlBottom.setLayout(new GridLayout(3, 1, 5, 5));

        // Not ekleme paneli
        JPanel pnlNotEkle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fldOgrenciID = new JTextField(10);
        fldDersID = new JTextField(10);
        fldNot = new JTextField(5);
        btnNotEkle = new JButton("Not Ekle");
        pnlNotEkle.add(new JLabel("Öğrenci ID:"));
        pnlNotEkle.add(fldOgrenciID);
        pnlNotEkle.add(new JLabel("Ders ID:"));
        pnlNotEkle.add(fldDersID);
        pnlNotEkle.add(new JLabel("Not:"));
        pnlNotEkle.add(fldNot);
        pnlNotEkle.add(btnNotEkle);
        pnlBottom.add(pnlNotEkle);

        // Not güncelleme paneli
        JPanel pnlNotGuncelle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        btnNotGuncelle = new JButton("Not Güncelle");
        pnlNotGuncelle.add(new JLabel("Not Güncelleme için yukarıdaki alanları doldurun"));
        pnlNotGuncelle.add(btnNotGuncelle);
        pnlBottom.add(pnlNotGuncelle);

        // Mesaj gönderme paneli
        JPanel pnlMesajGonder = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fldMesaj = new JTextField(20);  // Öğrenci ID'sine gerek yok
        btnMesajGonder = new JButton("Tüm Öğrencilere Mesaj Gönder");
        pnlMesajGonder.add(new JLabel("Mesaj:"));
        pnlMesajGonder.add(fldMesaj);
        pnlMesajGonder.add(btnMesajGonder);
        pnlBottom.add(pnlMesajGonder);

        wrapper.add(pnlBottom);

        // Pencere ayarları
        setTitle("Öğretmen Paneli");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        // Verileri yükle
        loadData();  // loadData() metodunu çağırıyoruz

        // Not ekleme butonu işlemleri
        btnNotEkle.addActionListener(e -> {
            String ogrenciID = fldOgrenciID.getText();
            String dersID = fldDersID.getText();
            String not = fldNot.getText();

            if (ogrenciID.isEmpty() || dersID.isEmpty() || not.isEmpty()) {
                Helper.showMsg("fill");
                return;
            }

            try (Connection con = db.connDb();
                 PreparedStatement ps = con.prepareStatement("INSERT INTO kayitlar (OgrenciID, DersID, Notu) VALUES (?, ?, ?)")) {
                ps.setInt(1, Integer.parseInt(ogrenciID));
                ps.setInt(2, Integer.parseInt(dersID));
                ps.setDouble(3, Double.parseDouble(not));
                ps.executeUpdate();
                Helper.showMsg("success");
                loadNotlar();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Helper.showMsg("error");
            }
        });

        // Not güncelleme butonu işlemleri
        btnNotGuncelle.addActionListener(e -> {
            String ogrenciID = fldOgrenciID.getText();
            String dersID = fldDersID.getText();
            String not = fldNot.getText();

            if (ogrenciID.isEmpty() || dersID.isEmpty() || not.isEmpty()) {
                Helper.showMsg("fill");
                return;
            }

            try (Connection con = db.connDb();
                 PreparedStatement ps = con.prepareStatement("UPDATE kayitlar SET Notu = ? WHERE OgrenciID = ? AND DersID = ?")) {
                ps.setDouble(1, Double.parseDouble(not));
                ps.setInt(2, Integer.parseInt(ogrenciID));
                ps.setInt(3, Integer.parseInt(dersID));
                ps.executeUpdate();
                Helper.showMsg("success");
                loadNotlar();
            } catch (SQLException ex) {
                ex.printStackTrace();
                Helper.showMsg("error");
            }
        });

        // Mesaj gönderme butonu işlemleri
        btnMesajGonder.addActionListener(e -> {
            String mesaj = fldMesaj.getText();

            if (mesaj.isEmpty()) {
                Helper.showMsg("fill"); // Kullanıcı mesajı boş bırakmışsa uyarı
                return;
            }

            sendMessage(mesaj);  // Mesajı göndermek için sendMessage metodunu çağırıyoruz
        });
    }

    // IDataLoader ve IMesajGonder'ı implement ettik, veri yükleme ve mesaj gönderme işlemleri burada olacak
    @Override
    public void loadData() {
        loadNotlar();  // Burada notları yüklüyoruz
    }

    @Override
    public void sendMessage(String mesaj) {
        // Veritabanına mesaj gönderme işlemi (tüm öğrencilere)
        try (Connection con = db.connDb();
             PreparedStatement ps = con.prepareStatement("INSERT INTO mesajlar (OgretmenID, OgrenciID, Mesaj) VALUES (?, NULL, ?)")) {
            ps.setInt(1, 1); // Öğretmen ID'sini ekleyin, 1 örnek olarak
            ps.setString(2, mesaj); // Mesaj içeriğini ekleyin
            ps.executeUpdate();
            Helper.showMsg("success"); // Başarı mesajı
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showMsg("error"); // Hata mesajı
        }
    }

    // Verileri tabloya yükleme
    private void loadNotlar() {
        mdlNotlar.setRowCount(0);
        try (Connection con = db.connDb();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM kayitlar")) {
            while (rs.next()) {
                rowNotlar = new Object[]{rs.getInt("KayitID"), rs.getInt("OgrenciID"), rs.getInt("DersID"), rs.getDouble("Notu")};
                mdlNotlar.addRow(rowNotlar);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Helper.showMsg("error");
        }
    }
}
