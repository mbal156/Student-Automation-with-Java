# Student Automation with Java

## Student Automation

<ins>Student Automation</ins>, veri tabanı entegrasyonuyla birçok işlemi gerçekleştiren bir öğrenci otomasyon sistemidir. Kullanıcı yönetimi, ders yönetimi gibi işlemleri destekler.

## Proje Yapısı

Bu proje oluşturulurken 3 ayrı package kullanılmıştır:

1) **Helper Package:**  
   - Veri tabanı bağlantısını sağlar.  
   - Hata ve bilgi mesajlarını düzenli göstermek için yardımcı bir sınıf içerir.

2) **Model Package:**  
   - Uygulamadaki kullanıcıların sınıflarını içerir.

3) **View Package:**  
   - Kullanıcı arayüzünü "Java GUI" ile oluşturur.  
   - Üç ayrı pencerede kullanıcı panelleri tasarlanmıştır.  
   - Kullanıcı giriş ekranından, hangi tür kullanıcı olarak giriş yapıldığına bağlı olarak ilgili panele yönlendirme yapılır.

      ![Ekran görüntüsü 2025-03-06 090122](https://github.com/user-attachments/assets/ac9ba372-f65f-4445-991f-766681fe77a6)
      
---

## 📌 Özellikler

### 🧑‍🎓 Öğrenciler:
- Profil görüntüleme 

- Ders programını görüntüleme  
- Sınav sonuçlarını görüntüleme  
- Gelen mesajları görüntüleme  
- Ders alma  
  
![Öğrenci Paneli](https://github.com/user-attachments/assets/578bd2a4-3661-4e98-988b-212cf7f7c9bf)

### 🧑‍🏫 Öğretmenler:
- Öğrencileri görüntüleme  
- Sınav notlarını girme ve güncelleme  
- Mesaj gönderme  

![Ekran görüntüsü 2025-03-08 124755](https://github.com/user-attachments/assets/7f6344c4-ff3f-4254-b573-4cc3f4e52002)

---

### 👨‍💻 Admin:
- Kullanıcı yönetimi
- Ders yönetimi

![Ekran görüntüsü 2025-03-08 125146](https://github.com/user-attachments/assets/31a9836c-4fb8-4d73-bc07-27230aa19dec)

### Kullanılan Teknolojiler 
- **Java** - Uygulamanın temel programlama dili  
- **Java Swing (GUI)** - Masaüstü arayüzü oluşturmak için  
- **MySQL / MariaDB** - Veri tabanı yönetimi için  
- **JDBC (Java Database Connectivity)** - Java ile veri tabanı bağlantısı için  
- **HeidiSQL** - MariaDB/MySQL yönetimi için kullanılan istemci aracı 

---
### Kullanım Rehberi 
- Öncelikle proje klonlama işlemi tamamlayıp sonrasında herhangi bir IDE de  **wiew package** altındaki **Logınguı** çalıştırmalısınız .Bu işlemlerden sonra karşınıza bir giriş ekranı çıkacak bu ekrandan bir 
  kullanıcı girerek panele giriş yapabilirsiniz.\
- **Örnek Kullanıcılar** için admin altındaki fotoğrafa bakabilirsiniz, örnek olarakta birkaçını aşağı ekledim.
- Kullanıcı Adı:"ali123" ,Şifre :"123456"  Öğrenci.
- Kullanıcı Adı:"fatma_ay" ,Şifre :"456789" Öğretmen.
- Kullanıcı Adı:"admin" ,Şifre :"admin123" Admin.
  
