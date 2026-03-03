## Reflection 1

### 1. Clean Code Principles
Dalam pengerjaan fitur Edit dan Delete Product, saya telah menerapkan beberapa prinsip Clean Code, diantaranya:

* **Meaningful Names:** Saya menggunakan nama variabel dan method yang deskriptif dan jelas tujuannya. Contohnya, penggunaan nama `deleteProduct(String id)` dan `editProduct(Product product)` pada Repository mendeskripsikan jelas tentang apa yang dilakukan method tersebut.
* **Single Responsibility Principle (SRP):** Saya memisahkan fungsi antar layer dengan jelas.
    * `ProductController`: Hanya bertugas menerima HTTP request dan mengarahkan ke view yang tepat.
    * `ProductService`: Menangani logika bisnis.
    * `ProductRepository`: Hanya fokus pada manipulasi data di List product.
* **Functions:** Setiap function yang saya buat fokus melakukan satu hal saja. Misalnya, function `findById` hanya bertugas mencari produk, tidak melakukan modifikasi apapun.

### 2. Secure Coding Practices
Dalam mengerjakan tugas ini, ada beberapa praktik Secure Coding yang telah saya diterapkan:
* **Penggunaan UUID:** Saya menggunakan UUID untuk `productId` bukan hanya *sequential integer* (1, 2, 3, ...). Hal ini meningkatkan keamanan karena ID produk menjadi sulit ditebak oleh pihak luar, sehingga meminimalisir risiko pada data.
* **HTTP Methods yang Tepat:** Saya menggunakan method `POST` untuk operasi yang mengubah data (Create, Edit, Delete) agar lebih aman dibandingkan menggunakan `GET` yang parameternya terlihat di URL.

### 3. Mistakes & Improvement
Setelah mengevaluasi kode, saya menemukan beberapa hal yang bisa diperbaiki:
* **Efisiensi Pencarian Data:** Saat ini, method edit dan delete di Repository masih menggunakan iterasi looping pada ArrayList untuk mencari produk berdasarkan ID. Ini memiliki kompleksitas waktu O(N).
    * *Perbaikan:* Sebaiknya ubah struktur data penyimpanan dari ArrayList menjadi `HashMap<String, Product>`. Dengan HashMap, pencarian produk berdasarkan ID bisa dilakukan dengan kompleksitas O(1), yang jauh lebih cepat dan efisien.
* **Validasi Input:** Saat ini belum ada validasi ketat untuk input form. Pengguna bisa saja memasukkan quantity negatif atau nama produk kosong.
    * *Perbaikan:* Menambahkan validasi baik di sisi client (HTML required) maupun server (Spring Validation `@NotNull`, `@Min(0)`) untuk menjaga integritas data.

---

## Reflection 2

### 1. Unit Testing & Code Coverage
Setelah menulis unit test, saya merasa lebih percaya diri dengan kualitas kode saya karena test tersebut membantu memastikan bahwa logika program berjalan sesuai harapan.

Jumlah test harus cukup untuk mencakup:
* **Positive Case:** Skenario normal ketika input benar.
* **Negative Case:** Skenario ketika input salah atau data tidak ditemukan.
* **Edge Case:** Skenario batas (misalnya input kosong, nilai maksimum/minimum).

Mengenai *Code Coverage*:
Memiliki **100% Code Coverage tidak menjamin kode bebas dari bug atau error**.
* Code coverage hanya mengukur persentase baris kode yang dieksekusi saat test berjalan.
* Code coverage tidak menjamin kebenaran logika bisnis. Contohnya, kita bisa saja menulis fungsi penjumlahan `add(a, b)` yang salah mengimplementasikan `return a - b;`. Jika kita hanya mengetes dengan `add(0, 0)`, coverage akan 100% dan test pass, tapi logika sebenarnya salah.
* Code coverage mungkin melewatkan *edge cases* yang tidak terpikirkan saat penulisan test.

### 2. Code Quality in Functional Test
Jika saya membuat functional test suite baru untuk memverifikasi jumlah item dalam product list dengan cara menyalin (copy-paste) prosedur setup dan variabel instance yang sama dari test suite sebelumnya, hal tersebut akan **menurunkan kualitas kode**.

Masalah *clean code* yang terjadi adalah **Code Duplication** dan pelanggaran prinsip **DRY (Don't Repeat Yourself)**.

* **Alasan:** Menduplikasi kode setup (seperti inisialisasi `baseUrl`, port, driver, dll) di banyak file test membuat kode sulit dipelihara (*maintainability* rendah). Jika suatu saat ada perubahan konfigurasi (misalnya cara setup port berubah), kita harus mengubahnya di semua file satu per satu.
* **Saran Perbaikan:**
  Kita bisa membuat sebuah **Base Test Class** (misalnya `BaseFunctionalTest`).
    1.  Pindahkan semua variabel konfigurasi umum (`serverPort`, `testBaseUrl`, `baseUrl`) dan method setup (`@BeforeEach`) ke dalam `BaseFunctionalTest`.
    2.  Buat class test lainnya (seperti `CreateProductFunctionalTest` atau test suite baru tersebut) mewarisi (`extends`) class `BaseFunctionalTest`.

  Dengan cara ini, setup hanya ditulis satu kali dan bisa digunakan kembali oleh semua functional test, menjadikan kode lebih bersih dan mudah dikelola.

## Reflection 2

### 1. Code Quality Issues Fixed
   Dalam pengerjaan modul ini, saya melakukan pembersihan kode berdasarkan temuan code smells dari dashboard SonarCloud untuk meningkatkan kualitas aplikasi. Salah satu masalah yang saya perbaiki adalah keberadaan empty methods pada kelas pengujian yang dianggap tidak memiliki tujuan fungsional dan hanya mengotori kode. Strategi perbaikan yang saya terapkan adalah menghapus metode setup yang kosong atau menambahkan logika asersi yang bermakna agar setiap bagian kode memiliki peran yang jelas. Selain itu, saya juga menghapus beberapa unused imports dan merapikan kembali struktur kode agar lebih ringkas dan mudah dipelihara di masa depan. Masalah kritis lain yang saya tangani adalah perbaikan naming convention pada file HTML agar konsisten menggunakan huruf kecil, karena lingkungan Linux pada CI/CD sangat sensitif terhadap perbedaan huruf kapital. Melalui serangkaian perbaikan ini, kode saya kini memiliki tingkat maintainability yang jauh lebih baik dan berhasil melewati standar Quality Gate yang ditetapkan.

### 2. CI/CD Workflows Assessment
   Berdasarkan implementasi yang telah dilakukan, saya yakin bahwa alur kerja saat ini telah sepenuhnya memenuhi definisi Continuous Integration (CI) dan Continuous Deployment (CD). Setiap kali saya melakukan push kode ke repositori, GitHub Actions secara otomatis menjalankan rangkaian unit tes dan analisis statis melalui SonarCloud untuk memvalidasi kualitas kode secara instan. Proses otomatisasi ini memastikan bahwa hanya kode yang benar-benar stabil dan bersih yang dapat digabungkan ke dalam branch utama aplikasi. Di sisi lain, integrasi dengan PaaS seperti Koyeb yang menggunakan Dockerfile memastikan bahwa aplikasi saya selalu ter-deploy dalam versi terbaru secara otomatis setelah lolos tahap pengujian. Hal ini menghilangkan proses manual yang rentan terhadap kesalahan manusia serta mempercepat waktu perilisan fitur baru ke lingkungan produksi. Secara keseluruhan, kombinasi alat-alat ini telah menciptakan pipa pengembangan yang efisien, transparan, dan memiliki keandalan tinggi

## Reflection 3

### 1. SOLID Principles Applied to the Project
Dalam pengerjaan proyek ini, saya telah menerapkan kelima prinsip SOLID untuk meningkatkan kualitas, struktur, dan maintainability kode. Berikut adalah penjelasannya:

* **Single Responsibility Principle (SRP):** Saya memisahkan `CarController` yang sebelumnya menyatu di dalam file `ProductController.java` menjadi filenya sendiri (`CarController.java`). Dengan ini, setiap controller memiliki satu tanggung jawab tunggal, `ProductController` hanya mengurus routing produk, dan `CarController` hanya mengurus routing mobil.
* **Open-Closed Principle (OCP):** Saya memodifikasi metode `update` pada `CarRepository` dan `ProductRepository`. Tidak boleh melakukan *set* atribut satu per satu (`setName`, `setQuantity`, dll.), saya memperbarui data dengan langsung mengganti objek lama dengan objek baru di dalam `List`. Sehingga, jika sewaktu-waktu model ditambahkan atribut baru, saya tidak perlu lagi membongkar dan memodifikasi kode di dalam *repository*.
* **Liskov Substitution Principle (LSP):** Saya menghapus relasi *inheritance* berupa `extends ProductController` pada `CarController`. Menghapus relasi ini memastikan tidak ada pewarisan rute yang tidak relevan yang bisa merusak perilaku aplikasi jika `CarController` diperlakukan sebagai `ProductController`.
* **Interface Segregation Principle (ISP):** Saya memastikan bahwa antarmuka (`CarService` dan `ProductService`) hanya berisi metode-metode pokok (`create`, `findAll`, `findById`, `update`, `delete`) yang memang diimplementasikan sepenuhnya oleh kelas konkritnya.
* **Dependency Inversion Principle (DIP):** Saya mengubah `ProductRepository` dan `CarRepository` yang awalnya berupa kelas konkrit menjadi sebuah *Interface*, lalu memindahkan logikanya ke dalam kelas implementasi (`ProductRepositoryImpl` dan `CarRepositoryImpl`). Dengan demikian, modul tingkat tinggi kini bergantung pada abstraksi (*interface*), bukan pada implementasi spesifik.

### 2. Advantages of Applying SOLID Principles
Menerapkan prinsip SOLID memberikan beberapa keuntungan besar pada proyek saya:

* **Maintainability (Mudah Dipelihara):** Berkat **SRP**, kode menjadi jauh lebih rapi. Jika ada bug pada fitur mobil, saya tahu persis bahwa saya hanya perlu mencari masalahnya di `CarController` atau `CarRepository`, tanpa takut memengaruhi fitur produk secara tidak sengaja.
* **Extensibility & Flexibility (Mudah Dikembangkan):** Dengan penerapan **DIP** dan **OCP**, aplikasi menjadi sangat fleksibel. Contohnya, saat ini data disimpan menggunakan memori sementara (`List`). Jika esok hari saya ingin menyimpan data ke database asli (seperti PostgreSQL), saya hanya perlu membuat kelas baru (misalnya `CarRepositoryPostgresImpl`) yang mengimplementasikan antarmuka `CarRepository` tanpa perlu mengubah kode di `CarService` atau `CarController` sama sekali.
* **Readability (Mudah Dibaca):** Memecah kode menjadi antarmuka dan implementasi yang lebih kecil menghindari terciptanya "God Object" (satu kelas raksasa yang mengatur segalanya), sehingga kode lebih mudah dipahami oleh developer lain.

### 3. Disadvantages of Not Applying SOLID Principles
Jika prinsip SOLID diabaikan, proyek akan menghadapi beberapa kerugian signifikan:

* **Tight Coupling (Ketergantungan yang Kuat):** Contoh dari pelanggaran **DIP** di awal proyek saya adalah `ProductServiceImpl` yang bergantung langsung pada kelas konkrit `ProductRepository`. Jika saya tidak memisahkannya menjadi *interface*, setiap kali ada perubahan cara penyimpanan data, saya terpaksa harus membongkar dan menulis ulang logika di level *Service* juga. Hal ini membuat aplikasi kaku (*rigid*).
* **Fragility (Kode Rapuh dan Rentan Error):** Contoh pelanggaran **LSP** dan **SRP** sebelumnya di mana `CarController extends ProductController`. Jika saya tidak memperbaikinya, setiap kali saya memodifikasi alur routing di `ProductController`, routing milik `CarController` bisa ikut berubah atau rusak tanpa disadari.
* **Sulit untuk Diuji (Hard to Test):** Tanpa adanya pemisahan *interface* (**DIP**), melakukan *Mocking* saat membuat *Unit Test* akan menjadi sangat sulit. Kita akan terpaksa melakukan pengujian pada implementasi database aslinya, bukan sekadar mengetes logika bisnisnya secara terisolasi.