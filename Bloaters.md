# Bloaters

[Smell](.) → [Martin Fowler Code Smells](Fowler) → [Bloaters](#)

Semua smell di dalam grup ini berkaitan dengan bagian code yang terlalu gemuk. Bila terlalu banyak code di dalam suatu class atau method, programmer lain akan kesulitan memahami isi code.

- [Long Method](#long-method)
- [Large Class](#large-class)
- [Primitive Obsession](#primitive-obsession)
- [Long Parameter List](#long-parameter-list)
- [Data Clumps](#data-clumps)


## Long Method

[sourcemaking](https://sourcemaking.com/refactoring/smells/long-method) |
[refactoring.guru](https://refactoring.guru/smells/long-method) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_method/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_method/after)

### Penjelasan

![Gulungan struk yang panjang tiada akhir](https://refactoring.guru/images/refactoring/content/smells/long-method-01.png "Long Method bagaikan gulungan struk yang panjang tiada akhir")

> Long Method bagaikan gulungan struk yang panjang tiada akhir, dikira mau ikut lomba struk terpanjang macam kayak Al\*amart apa? Atau mau nantang Edho Zell buat borong semua barang di Indom\*\*et?

### Penjelasan Smell

Smell ini terjadi ketika ada sebuah method yang terlalu panjang. Method yang terlalu panjang dapat ditentukan dengan beberapa indikator:

- programmer kesulitan memahami method. Perlu membaca dengan detail baris per baris berulang-ulang untuk memahami tujuan code ini
- programmer perlu menuliskan internal comment untuk mengklarifikasi tujuan beberapa line of code
- satu method memiliki banyak tanggungjawab
- terdapat hidden-side-effect di dalam method. Nama method tidak sesuai dengan apa yang dilakukan di dalamnya.
- melebihi N baris. N ini merupakan angka yang ditentukan oleh masing-masing tim. Beda perusahaan atau beda bahasa pemrograman bisa beda angka. Di sourcemaking, dibilang tidak boleh lebih dari 10 baris.

Pada contoh [SemesterMenu.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_method/before/SemesterMenu.java), terdapat method `menu`, `create`, dan `delete` yang melebihi 10 baris.

### Penyelesaian

![Pecahkan method menjadi beberapa bagian](https://refactoring.guru/images/refactoring/content/smells/long-method-02.png "Potong method yang berkepanjangan menjadi beberapa method-method baru")

Dilakukan [Extract Method](https://sourcemaking.com/refactoring/extract-method) pada method `menu`, `create`, dan `delete`.

Lihat hasilnya di package [after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_method/after/SemesterMenu.java).

![Jadilah submethod-submethod yang enak dibaca](https://refactoring.guru/images/refactoring/content/smells/long-method-03.png "Jadilah submethod-submethod yang enak dibaca")

Code di dalam method `menu` diekstrak menjadi `showSemesters()`, `printAndGetMenu()`, dan `showSemester()`.

Untuk method create, delete, dan bagian code input menu. Kita dapat melihat terdapat [duplicate code](https://sourcemaking.com/refactoring/smells/duplicate-code) yang juga membuat method menjadi panjang. Hal ini dapat diatasi dengan mengakali logic input menjadi method `getDateInput`, `getIntInput`, dan `getStringInput`.

### Tambahan

Refactor pada contoh `SemesterMenu` ini belum selesai. Terdapat smell Large Class yang akan diurus [disini](https://github.com/akmalrusli363/smell/large_class).


## Large Class

[sourcemaking](https://sourcemaking.com/refactoring/smells/large-class) |
[refactoring.guru](https://refactoring.guru/smells/large-class) |
[before](https://github.com/mrp130/smell/tree/master/src/fowler/bloaters/large_class/before) |
[after](https://github.com/mrp130/smell/tree/master/src/fowler/bloaters/large_class/after)

### Penjelasan Smell

![Robot multitasking](https://refactoring.guru/images/refactoring/content/smells/large-class-01.png "Large Class bagaikan robot yang terlalu besar, multitasking, dan mengerikan")

> Wih ada inovasi robot multitasking nih dengan memadukan belasan tangannya buat berbagai kerjaan. Dikira Squidward bermutasi dengan Spongebob gara-gara Sandy, tapi ternyata malah bikin panik banyak orang? :scream:

Smell ini terjadi ketika ada sebuah class yang memiliki terlalu banyak method dan/atau line of code.

Code yang mengandung Large Class berpotensi melanggar [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single-responsibility_principle) bila terdapat terlalu banyak jenis tanggungjawab yang dilakukan oleh satu class. Bila Large Class disertai dengan pelanggaran SRP, maka terjadi juga smell Divergent Change.

Pada contoh [SemesterMenu.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/large_class/before/SemesterMenu.java), class ini bukan hanya berurusan dengan menu dan input console untuk CRUD semester saja. Class ini juga pegang kendali untuk pembuatan vector semester. Class ini juga terbebani dengan banyaknya variasi cara input di method `getDateInput`, `getIntInput`, dan `getStringInput`.

### Penyelesaian

![Pecahkan class besar menjadi beberapa class](https://refactoring.guru/images/refactoring/content/smells/large-class-02.png "Potong class yang terlalu besar menjadi beberapa class")

Dilakukan [Extract Class](https://sourcemaking.com/refactoring/extract-class) pada:

- vector Semester dan validasi-validasinya diekstrak ke class baru bernama [Semesters](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/large_class/after/Semesters.java).
- fungsi `getDateInput`, `getIntInput`, dan `getStringInput` dibuat menjadi 3 class yang terpisah. Lalu [Extract Superclass](https://sourcemaking.com/refactoring/extract-superclass) menjadi abstract class Console.

Dilakukan [Move Method](https://sourcemaking.com/refactoring/move-method) pada:

- fungsi `showSemester` menjadi `toString` di class Semester.
- fungsi `showSemesters` menjadi `show` di class Semesters.
- fungsi `isLabelUnique` pindah ke class Semesters.

![Biarkan class-class menjalankan task sesuai kebutuhan](https://refactoring.guru/images/refactoring/content/smells/large-class-03.png "Biarkan class-class pecahan menjalankan task sesuai kebutuhan")

Lihat hasilnya di package [after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/large_class/after/SemesterMenu.java).


## Primitive Obsession

[sourcemaking](https://sourcemaking.com/refactoring/smells/primitive-obsession) |
[refactoring.guru](https://refactoring.guru/smells/primitive-obsession) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/primitive_obsession/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/primitive_obsession/after)

### Penjelasan Smell

![Objek-objek geometri](https://refactoring.guru/images/refactoring/content/smells/primitive-obsession-01.png "Primitive Obsession bagaikan kita masih SD")

> Ini tahun 1987 atau 2020? Ngoding aja cuma pakai `int`, `float`, `char`, sama `boolean` doang buat ngoding. Dikira kita masih hidup di zaman yang susah apa?

Smell ini terjadi ketika ada field atau parameter yang memakai tipe data primitif, padahal behavior-nya lebih daripada tipe data yang ia gunakan.

Hal ini tidak terjadi hanya di tipe data primitif `int`, `float`, dan sebagainya saja. Hal ini juga bisa terjadi di tipe data lain di level abstraksi yang lebih tinggi. Misalnya `String`. Intinya adalah, bila programmer menyimpan data dengan sebuah tipe data, padahal behavior dari data itu lebih dari tipe data yang ia gunakan, berarti sebenarnya harus diganti atau dibuatkan class baru.

Perhatikan contoh [Mahasiswa.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/primitive_obsession/before/Mahasiswa.java). Terdapat field `name` menggunakan String dan tanggal lahir yang dipecah-pecah menjadi integer. Padahal `name` memiliki validasi sesuai dengan regex dalam code, dan tanggal lahir memiliki validasi tanggal harus valid.

Selain itu, terdapat juga field `type` menggunakan String. Padahal value `type` hanya boleh Reguler atau Global. Selain itu, `type` juga digunakan pada [MahasiswaGreeter](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/primitive_obsession/before/MahasiswaGreeter.java) yang di dalam sini terdapat smell switch statement.

### Penyelesaian

![Buat class berisikan variabel primitif](https://refactoring.guru/images/refactoring/content/smells/primitive-obsession-02.png "Susunlah class yang terdirikan atas object-object/variabel primitive")

Dilakukan [Extract Class](https://sourcemaking.com/refactoring/extract-class) pada `name`, dibuatkan class baru bernama [FullName](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/primitive_obsession/after/FullName.java). Kemudian [replace data value with object](https://sourcemaking.com/refactoring/replace-data-value-with-object) pada field `name` di class Mahasiswa.

![Object class teratur, class lebih mudah menangani object](https://refactoring.guru/images/refactoring/content/smells/primitive-obsession-03.png "Object class teratur, class lebih mudah menangani object")

Untuk integer `dayOfBirth`, `monthOfBirth`, dan `yearOfBirth`, kita ganti menggunakan class `java.util.Date` bawaan dari Java.

Untuk logic Greeter, kita gunakan [replace type code with state/strategy](https://sourcemaking.com/refactoring/replace-type-code-with-state-strategy).


## Long Parameter List

[sourcemaking](https://sourcemaking.com/refactoring/smells/long-parameter-list) |
[refactoring.guru](https://refactoring.guru/smells/long-parameter-list) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_parameter_list/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_parameter_list/after)

### Penjelasan Smell

![Penuh sesak pokoknya](https://refactoring.guru/images/refactoring/content/smells/long-parameter-list-01.png "Penuh sesak pokoknya.. Kayak naik kereta deh..")

> Pengen naik angkot berharap kosong malah penuh. Aku pengen skip malah disuruh naik, udah gitu duduk kiri kanan mepet-mepetan pula, udah kayak apa ini angkot? Aku nemuin code ginian rasanya suruh masukkin data-data yang lagi-lagi ribetin projectnya lagi. RIBET!! RIBET!!! :anger:

Smell ini terjadi ketika ada method yang memiliki jumlah parameter yang terlalu banyak. Hal ini membuat code menjadi sulit dibaca dan juga menyebabkan *cognitive overload* pada programmer karena ada beban memori harus mengingat-ingat letak parameter. Lebih berbahayanya lagi jika parameter memiliki tipe data yang sama. Bisa saja tidak sengaja tertukar posisinya.

Perhatikan contoh [WalletNotification.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/bloaters/long_parameter_list/before/WalletNotification.java). Terdapat method `balanceNotif(String name, String email, int amount, int current)`.

### Penyelesaian

![Gunakan method call atau wakilkan sebagai object](https://refactoring.guru/images/refactoring/content/smells/long-parameter-list-02.png "Gunakan method call atau wakilkan sebagai object")

Dilakukan [Preserve Whole Object](https://sourcemaking.com/refactoring/preserve-whole-object) pada method `balanceNotif` sehingga menjadi `balanceNotif(User user, int amount)`. Karena name, email, dan current balance bisa didapatkan langsung dari object user.

```java
public void balanceNotif(User user, int amount) {
  ...
}
```


## Data Clumps

[sourcemaking](https://sourcemaking.com/refactoring/smells/data-clumps) |
[refactoring.guru](https://refactoring.guru/smells/data-clumps) |
[before](https://github.com/mrp130/smell/tree/master/src/fowler/bloaters/data_clumps/before) |
[after](https://github.com/mrp130/smell/tree/master/src/fowler/bloaters/data_clumps/after)

### Penjelasan Smell

![Data Clump joget dangdut indo remix](https://refactoring.guru/images/refactoring/content/smells/data-clumps-01.png "Data Clump joget dangdut indo remix")

> Data Clump itu bagaikan bucin.. Giliran butuh dibujuk semuanya, kalo enggak ya sudah :sob:

Smell ini terjadi ketika ada beberapa field atau parameter method yang selalu dipakai atau dioper bersama-sama. Bila terjadi hal seperti ini, berarti variabel-variabel ini bisa dipertimbangkan untuk digabungkan langsung dalam satu class.

Perhatikan class `Kelas` dan `Semester` di dalam package `before`. Pada kedua class ini, terdapat Date `start` dan `end`.

### Penyelesaian

![Fasilitaskan mereka, buat dia nyaman!](https://refactoring.guru/images/refactoring/content/smells/data-clumps-02.png "Fasilitaskan mereka, buat dia nyaman!")

Karena selalu berbarengan, `start` dan `end` dipertimbangkan untuk digabung dalam class `DateRange`. Agar tidak hanya menjadi smell data class, kita dapat melengkapi class `DateRange` dengan behavior tertentu. Contohnya kita tambahkan validasi `start` tidak boleh melebihi `end`.

---

### Referensi Gambar

Semua gambar referensi mengikuti pictorial gambar pada **Refactoring.guru** dengan tetap mengutamakan link credit pada [sourcemaking.com](https://sourcemaking.com/refactoring/smells/) maupun [refactoring.guru](https://refactoring.guru/smells/)
