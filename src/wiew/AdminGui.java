package wiew;

import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import Helper.Dbconnect;
import Model.IDataLoader;  // IDataLoader interface'ini import ediyoruz
import Model.Admin;
public class AdminGui extends JFrame implements IDataLoader {
    private JTable userTable, courseTable;
    private DefaultTableModel userModel, courseModel;
    private Connection conn;

    public AdminGui(Admin admin) {
        // Dbconnect sınıfını kullanarak bağlantı alıyoruz
        Dbconnect db = new Dbconnect();
        this.conn = db.connDb();
        if (conn == null) {
            JOptionPane.showMessageDialog(this, "Veritabanına bağlanılamadı!", "Hata", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        setTitle("Admin Yönetim Paneli");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Üst Panel
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(0, 0, 128));
        topPanel.setLayout(new BorderLayout());
        topPanel.setPreferredSize(new Dimension(getWidth(), 50));

        JLabel lblTitle = new JLabel("Admin Yönetim Paneli", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        topPanel.add(lblTitle, BorderLayout.CENTER);

        JButton btnExit = new JButton("Çıkış");
        btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
        btnExit.setForeground(Color.WHITE);
        btnExit.setBackground(new Color(220, 53, 69)); // Kırmızı renk
        btnExit.setFocusPainted(false);
        btnExit.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        topPanel.add(btnExit, BorderLayout.EAST);

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

        getContentPane().add(topPanel, BorderLayout.NORTH);

        // TabbedPane ile bölümlere ayırma
        JTabbedPane tabbedPane = new JTabbedPane();

        // Kullanıcı Yönetimi
        JPanel userPanel = createUserPanel();
        tabbedPane.addTab("Kullanıcı Yönetimi", userPanel);

        // Ders Yönetimi
        JPanel coursePanel = createCoursePanel();
        tabbedPane.addTab("Ders Yönetimi", coursePanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
    }

    @Override
    public void loadData() {
        loadUsers();
        loadCourses();
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();

        // Kullanıcı tablosu
        userModel = new DefaultTableModel(new String[]{"Kullanıcı ID", "Kullanıcı Adı", "Şifre", "Rol"}, 0);
        userTable = new JTable(userModel);
        loadUsers();
        panel.setLayout(null);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(0, 0, 781, 505);
        panel.add(scrollPane);

        // Kullanıcı ekleme/silme paneli
        JPanel actionPanel = new JPanel();
        actionPanel.setBounds(0, 505, 781, 31);
        JTextField txtUsername = new JTextField(10);
        JTextField txtPassword = new JTextField(10);
        JTextField txtRole = new JTextField(10);
        JButton btnAddUser = new JButton("Ekle");
        JButton btnDeleteUser = new JButton("Sil");

        actionPanel.add(new JLabel("Kullanıcı Adı:"));
        actionPanel.add(txtUsername);
        actionPanel.add(new JLabel("Şifre:"));
        actionPanel.add(txtPassword);
        actionPanel.add(new JLabel("Rol:"));
        actionPanel.add(txtRole);
        actionPanel.add(btnAddUser);
        actionPanel.add(btnDeleteUser);

        panel.add(actionPanel);

        // Kullanıcı ekleme
        btnAddUser.addActionListener(e -> {
            String username = txtUsername.getText();
            String password = txtPassword.getText();
            String role = txtRole.getText();
            addUser(username, password, role);
            loadUsers();
        });

        // Kullanıcı silme
        btnDeleteUser.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow != -1) {
                int userId = (int) userModel.getValueAt(selectedRow, 0);
                deleteUser(userId);
                loadUsers();
            }
        });

        return panel;
    }

    private JPanel createCoursePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Ders tablosu
        courseModel = new DefaultTableModel(new String[]{"Ders ID", "Ders Adı", "Kredi", "Bölüm ID"}, 0);
        courseTable = new JTable(courseModel);
        loadCourses();

        JScrollPane scrollPane = new JScrollPane(courseTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Ders ekleme/silme paneli
        JPanel actionPanel = new JPanel();
        JTextField txtCourseName = new JTextField(10);
        JTextField txtCredit = new JTextField(5);
        JTextField txtDepartmentId = new JTextField(5);
        JButton btnAddCourse = new JButton("Ekle");
        JButton btnDeleteCourse = new JButton("Sil");

        actionPanel.add(new JLabel("Ders Adı:"));
        actionPanel.add(txtCourseName);
        actionPanel.add(new JLabel("Kredi:"));
        actionPanel.add(txtCredit);
        actionPanel.add(new JLabel("Bölüm ID:"));
        actionPanel.add(txtDepartmentId);
        actionPanel.add(btnAddCourse);
        actionPanel.add(btnDeleteCourse);

        panel.add(actionPanel, BorderLayout.SOUTH);

        // Ders ekleme
        btnAddCourse.addActionListener(e -> {
            String courseName = txtCourseName.getText();
            int credit = Integer.parseInt(txtCredit.getText());
            int departmentId = Integer.parseInt(txtDepartmentId.getText());
            addCourse(courseName, credit, departmentId);
            loadCourses();
        });

        // Ders silme
        btnDeleteCourse.addActionListener(e -> {
            int selectedRow = courseTable.getSelectedRow();
            if (selectedRow != -1) {
                int courseId = (int) courseModel.getValueAt(selectedRow, 0);
                deleteCourse(courseId);
                loadCourses();
            }
        });

        return panel;
    }

    private void loadUsers() {
        userModel.setRowCount(0);
        try {
            String query = "SELECT * FROM kullanıcılar";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                userModel.addRow(new Object[]{rs.getInt("KullaniciID"), rs.getString("KullaniciAdi"), rs.getString("Sifre"), rs.getString("Rol")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addUser(String username, String password, String role) {
        try {
            String query = "INSERT INTO kullanıcılar (KullaniciAdi, Sifre, Rol) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role);
            ps.executeUpdate();

            // Yeni kullanıcı ID'sini al
            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                int newUserId = generatedKeys.getInt(1);

                if (role.equalsIgnoreCase("ogrenci")) {
                    String addStudentQuery = "INSERT INTO ogrenciler (OgrenciID, Ad, Soyad) VALUES (?, ?, ?)";
                    PreparedStatement psStudent = conn.prepareStatement(addStudentQuery);
                    psStudent.setInt(1, newUserId);
                    psStudent.setString(2, "Ad Girilmedi");
                    psStudent.setString(3, "Soyad Girilmedi");
                    psStudent.executeUpdate();

                } else if (role.equalsIgnoreCase("ogretmen")) {
                    String addTeacherQuery = "INSERT INTO ogretmenler (OgretmenID, Ad, Soyad) VALUES (?, ?, ?)";
                    PreparedStatement psTeacher = conn.prepareStatement(addTeacherQuery);
                    psTeacher.setInt(1, newUserId);
                    psTeacher.setString(2, "Ad Girilmedi");
                    psTeacher.setString(3, "Soyad Girilmedi");
                    psTeacher.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(int userId) {
        try {
            String query = "DELETE FROM kullanıcılar WHERE KullaniciID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadCourses() {
        courseModel.setRowCount(0);
        try {
            String query = "SELECT * FROM dersler";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                courseModel.addRow(new Object[]{rs.getInt("DersID"), rs.getString("DersAdi"), rs.getInt("Kredisi"), rs.getInt("BolumID")});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addCourse(String courseName, int credit, int departmentId) {
        try {
            String query = "INSERT INTO dersler (DersAdi, Kredisi, BolumID) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, courseName);
            ps.setInt(2, credit);
            ps.setInt(3, departmentId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCourse(int courseId) {
        try {
            String query = "DELETE FROM dersler WHERE DersID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, courseId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
