package Helper;

import javax.swing.*;

public class Helper {

    public static void showMsg(String str) {
        String msg;
        String title;

        switch (str) {
            case "fill":
                msg = "Lütfen tüm alanları doldurun!";
                title = "Eksik Alan";
                break;
            case "success":
                msg = "İşlem başarıyla tamamlandı!";
                title = "Başarılı";
                break;
            case "error":
                msg = "Bir hata oluştu!";
                title = "Hata";
                break;
            default:
                msg = str;
                title = "Mesaj";
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;

        switch (str) {
            case "sure":
                msg = "Bu işlemi gerçekleştirmek istediğinizden emin misiniz?";
                break;
            default:
                msg = str;
        }

        int res = JOptionPane.showConfirmDialog(null, msg, "Onay", JOptionPane.YES_NO_OPTION);
        return res == JOptionPane.YES_OPTION;
    }
}
