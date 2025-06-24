# OtoLink 🚗🛵

**OtoLink** adalah aplikasi marketplace mobile berbasis Android yang digunakan untuk jual beli kendaraan bermotor (mobil dan motor) serta sepeda. Aplikasi ini memanfaatkan **Firebase** sebagai backend utama untuk autentikasi, database, dan penyimpanan gambar.

---

## ✨ Fitur Utama

### 🔐 Registrasi
- Pengguna dapat mendaftar menggunakan **email** dan **username**.
- Keduanya memerlukan **password** sebagai kredensial keamanan.

### 🔓 Login
- Pengguna masuk menggunakan email.
- Sistem akan mendeteksi akun yang sudah ada dan langsung mengizinkan akses.

### 📤 Unggah Iklan Penjualan
Penjual dapat mengunggah iklan kendaraan dengan mengisi:
- Merek
- Model
- Tahun
- Harga
- Deskripsi
- Foto kendaraan (disimpan di Firebase Storage)
- Lokasi

### 🔎 Pencarian
Pengguna dapat mencari kendaraan berdasarkan **merek** atau **model** menggunakan fitur pencarian kata kunci.

### 💬 Chat Langsung
Fitur **chat** memungkinkan pembeli dan penjual untuk berkomunikasi langsung dalam aplikasi.

### ❤️ Simpan Kendaraan Favorit
Pengguna dapat menyimpan kendaraan favorit ke akun mereka untuk dilihat kembali di kemudian hari.

---

## 🔧 Teknologi yang Digunakan

- **Android Studio**
- **Kotlin** untuk logika aplikasi
- **Firebase Authentication** – untuk login dan registrasi
- **Firebase Realtime Database / Firestore** – untuk menyimpan data kendaraan dan pengguna
- **Firebase Storage** – untuk menyimpan foto kendaraan
- **Firebase Cloud Messaging (opsional)** – untuk notifikasi chat atau favorit

---

## 👥 Anggota Kelompok
- Ahmad Fikri Hanif (2317051061) Kelas B (Membuat view bagian Start page, login dan registrasi).
