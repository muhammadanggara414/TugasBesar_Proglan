# FinancialApp #
FinancialApp adalah aplikasi GUI untuk mengelola transaksi keuangan, dirancang khusus untuk Usaha Mikro, Kecil, dan Menengah (UMKM). Aplikasi ini memungkinkan pengguna untuk menambah, menghapus, dan mengekspor transaksi ke dalam format PDF. Selain itu, aplikasi ini juga menyediakan ringkasan keuangan yang mencakup pemasukan, pengeluaran, dan saldo.

## Fitur Utama ##
- Autentikasi Pengguna: Pengguna harus masuk dengan username dan password untuk mengakses aplikasi.
- Manajemen Transaksi: Pengguna dapat menambah, menghapus, dan mencari transaksi berdasarkan deskripsi.
- Ringkasan Keuangan: Menampilkan total pemasukan, pengeluaran, dan saldo saat ini.
- Ekspor ke PDF: Pengguna dapat mengekspor laporan transaksi ke dalam format PDF dan mengirimkannya melalui email.
- Antarmuka Pengguna yang Ramah: Desain antarmuka yang sederhana dan intuitif untuk memudahkan pengguna.

## Prasyarat ##
Sebelum menjalankan aplikasi ini, pastikan Anda memiliki:
- Java Development Kit (JDK) terinstal di sistem Anda.
- Library iText untuk PDF (tambahkan ke classpath).
- Library JSON (org.json) untuk pengolahan data JSON.
- Library JavaMail untuk pengiriman email.
  
## Instalasi ##
1. Clone Repository:
```
git clone https://github.com/FatihHDR/.git
cd FinancialApp
```
2. Tambahkan Dependensi: Pastikan untuk menambahkan dependensi berikut ke dalam proyek Anda:
- iText
- JSON
- JavaMail
3. Jalankan Aplikasi: Kompilasi dan jalankan aplikasi menggunakan IDE favorit Anda atau melalui command line:
```
javac FinancialApp.java
java FinancialApp
```

## Cara Menggunakan ##
- Masuk: Masukkan username dan password (default: admin / password).
- Tambah Transaksi: Klik tombol "Tambah Transaksi" untuk membuka dialog dan masukkan detail transaksi.
- Hapus Transaksi: Pilih transaksi yang ingin dihapus dan klik tombol "Hapus Transaksi".
- Cari Transaksi: Gunakan kolom pencarian untuk menemukan transaksi berdasarkan deskripsi.
- Ekspor ke PDF: Klik tombol "Ekspor ke PDF" untuk menyimpan laporan transaksi ke dalam file PDF dan mengirimkannya melalui email.
- Keluar: Klik tombol "Keluar" untuk keluar dari aplikasi.
  
## Struktur Kode ##
- FinancialApp: Kelas utama yang mengatur antarmuka pengguna dan logika aplikasi.
- loadTransactions(): Memuat transaksi dari file JSON.
- saveTransactions(): Menyimpan transaksi ke file JSON.
- exportToPDF(): Mengekspor transaksi ke file PDF.
- sendEmail(): Mengirim email dengan lampiran file PDF.
  
## Catatan Keamanan ##
Pastikan untuk mengganti username dan password default sebelum menggunakan aplikasi ini dalam lingkungan produksi.
Jangan menyimpan kredensial email dalam kode sumber. Gunakan metode yang lebih aman untuk mengelola kredensial.

## Lisensi ##
Aplikasi ini dilisensikan di bawah Lisensi MIT.

## Kontribusi ##
Kontribusi sangat diterima! Silakan buka issue atau kirim pull request untuk perbaikan atau fitur baru.

## Kontak ##
Untuk pertanyaan atau saran, silakan hubungi [fatahillah.alt@gmail.com].
