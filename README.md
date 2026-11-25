# Pengujian login Eraspace di Android

Suite Kotlin dan UI Automator menguji Eraspace di emulator Android Studio berdasarkan TestScenario

| ID | Data uji | Pemeriksaan |
| --- | --- | --- |
| 1.1 | Email yang belum terdaftar dan password uji | Kedua kolom terisi, form login tetap terbuka, dan muncul pesan “Email atau No Handphone belum terdaftar”. |
| 1.2 | Akun QA dengan kredensial valid | Kedua kolom terisi, login membuka Home dengan saldo poin dan daftar menu kategori. Tes kemudian memeriksa nama di tab Akun dan tampilan Riwayat Poin di Cek Poin. |

TC 1.2 menutup pengingat verifikasi jika muncul setelah login. Riwayat Poin harus menampilkan transaksi atau pesan “Belum memiliki riwayat poin” untuk akun tanpa transaksi.

## Menjalankan di Device Manager

1. Buka **Tools → Device Manager** di Android Studio. Jalankan **Pixel 2 API 30** atau emulator Android API 30 ke atas dengan Play Store.
2. Pastikan [Eraspace](https://play.google.com/store/apps/details?id=com.eraspace.app) terpasang. Selesaikan onboarding dan siapkan aplikasi dalam keadaan logout. Emulator perlu koneksi internet agar server Eraspace merespons.
3. Untuk menjalankan TC 1.2, isi `eraspace-test.properties` di folder utama proyek. Jika file belum ada, buat dengan format berikut. File ini diabaikan oleh Git.

```properties Valid
eraspaceUsername=
eraspacePassword=
```

Jika nilai mengandung garis miring terbalik (`\`), tulis sebagai `\\`. Jika kedua kolom kosong, TC 1.2 dilewati dan TC 1.1 tetap dapat dijalankan.

4. Jalankan kelas `EraspaceLoginTest` dari Android Studio dengan memilih emulator tersebut. Dari PowerShell, biarkan emulator target sebagai satu-satunya perangkat Android yang terhubung, lalu jalankan perintah berikut.

```powershell
.\gradlew.bat :app:connectedDebugAndroidTest `
  "-Pandroid.testInstrumentationRunnerArguments.class=com.example.testqamobile.eraspace.EraspaceLoginTest"
```

Argumen runner diteruskan ke emulator. Gunakan akun uji khusus dan simpan kredensial di lingkungan lokal. Laporan Gradle tersedia di `app/build/reports/androidTests/connected/debug/index.html`. TC 1.2 berjalan terakhir dan meninggalkan akun dalam keadaan login. Logout sebelum mengulang suite.

## Pemeliharaan

Kode ada di `app/src/androidTest/java/com/example/testqamobile/eraspace`.

- `EraspaceLoginTest.kt` berisi langkah dan data setiap test case.
- `LoginScreen.kt` menyimpan selector form, interaksi, dan pemeriksaan penolakan.
- `HomeScreen.kt` menyimpan selector beranda, tab Akun, dan riwayat poin.
- `EraspaceApp.kt` membuka aplikasi dan menunggu elemen tampil.

Jika Eraspace berubah, gunakan resource ID dari emulator untuk memperbarui screen terkait. Selector dirujuk dari Eraspace 5.33.4 di Pixel 2 API 30.
