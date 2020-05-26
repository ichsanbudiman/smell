# Dispensables

[Smell](..) → [Martin Fowler Code Smells](.) → [Dispensables](#)

Semua smell di dalam grup ini berkaitan dengan bagian-bagian code kurang berguna dan bisa dibuang.

- [Comments](#comments)
- [Duplicate Code](#duplicate-code)
- [Lazy Class](#lazy-class)
- [Data Class](#data-class)
- [Dead Code](#dead-code)
- [Speculative Generality](#speculative-generality)


## Comments

[sourcemaking](https://sourcemaking.com/refactoring/smells/comments) |
[refactoring.guru](https://refactoring.guru/smells/comments) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/comments/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/comments/after)

### Penjelasan Smell

![Code berisi comment](https://refactoring.guru/images/refactoring/content/smells/comments-01.png "Code penuh sarat akan komentar untuk menutupi kejorokannya")

> Kan aku kasih comment biar programmer bisa mengerti jalan program/algoritma yang ingin saya terapkan. Lalu kenapa disebut sampah? Tanya kenapa?

Nama method atau atribut haruslah sudah self-explain tanpa harus diberi comment. Begitu juga logic flow di dalam fungsi. Bila logic flow perlu dijelaskan, dapat dipertimbangkan code tersebut sebagai bagian dari smell Long Method dan perlu di-extract.

Comment boleh diberikan sebagai dokumentasi. Contohnya untuk bahasa pemrograman Java, digunakan [Javadoc](https://www.tutorialspoint.com/java/java_documentation.htm) menggunakan `/** */` sebagai dokumentasi. Penggunaan `//` untuk single line atau `/* */` untuk multiline dihindari.

```java
/**
* The HelloWorld program implements an application that
* simply displays "Hello World!" to the standard output.
*
* @author  John Doe
* @version 1.0
* @since   2020-03-20
*/
public class HelloWorld { ... }
```

Pada contoh [MenuPrinter.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/comments/before/MenuPrinter.java), terdapat banyak komentar.

Lebih parahnya, terdapat hidden-side-effect pada fungsi `printMenu`. Di komentar diatas menu, tertulis bahwa fungsi ini melakukan print kemudian scan (terjadi [temporal cohesion](https://en.wikipedia.org/wiki/Cohesion_(computer_science))). Sedangkan nama fungsi hanya `printMenu` saja. Tentu saja ini menyesatkan programmer lain yang akan memakai fungsi ini.

### Penyelesaian

![Sikat comment!](https://refactoring.guru/images/refactoring/content/smells/comments-02.png "Sikat dan buang comment dari code!")

Dilakukan [extract method](https://sourcemaking.com/refactoring/extract-method) pada bagian-bagian di dalam fungsi, dan dilakukan [rename method](https://sourcemaking.com/refactoring/rename-method) pada fungsi `printMenu` menjadi `printMenuAndGetInput`.


## Duplicate Code

[sourcemaking](https://sourcemaking.com/refactoring/smells/duplicate-code) |
[refactoring.guru](https://refactoring.guru/smells/duplicate-code) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/duplicate_code/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/duplicate_code/after)

### Penjelasan Smell

![Duplikasi Code](https://sourcemaking.com/images/refactoring-illustrations/duplicate-code-1.png "Code berisikan duplikat")

> Copas.. Copas.. Copas.. Ctrl+C, Ctrl+V.. Pokoknya copas aja lalu edit dikit-dikit! Bodo amat, yang penting jalan! Tapi kan kamu bisa jadikan code yang duplikat jadi 1 method?

Terdapat bagian code yang sama atau mirip.

Kadang kesamaan terlihat jelas sehingga mudah di-refactor. Namun terkadang, harus putar otak untuk membuat beberapa bagian code yang mirip menjadi lebih generic.

Satu hal yang bisa menjadi *rule of thumb* adalah [Rule of Three](https://en.wikipedia.org/wiki/Rule_of_three_(computer_programming)). Bila bagian code sudah di copy-paste tiga kali, ini sudah menjadi lampu merah untuk segera dilakukan refactor.

Pada contoh kasus di [Foo.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/duplicate_code/before/Foo.java) terdapat banyak bagian code yang duplikat di method bar, baz, dan qux.

### Penyelesaian

![Ganti dengan method baru](https://sourcemaking.com/images/refactoring-illustrations/duplicate-code-2.png "Ganti code duplikat dengan method baru")

Terjadi [extract method](https://sourcemaking.com/refactoring/extract-method) di banyak tempat. Selain itu, `for` yang serupa tetapi tidak sama juga diakali dengan cara di-extract namun ditambahi variabel sehingga bisa digunakan di ketiga fungsi tersebut.


## Lazy Class

[sourcemaking](https://sourcemaking.com/refactoring/smells/lazy-class) |
[refactoring.guru](https://refactoring.guru/smells/lazy-class) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/lazy_class/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/lazy_class/after)

### Penjelasan Smell

![Class yang maha pemalas](https://refactoring.guru/images/refactoring/content/smells/lazy-class-01.png "Class yang maha pemalas..? Mana boleh!")

> Lah enak banget ngoding ditinggalin? Cuma buat gambarin lingkaran doang ngapain dibuatkan ini class? Buang aja 💢

Memecah class memang bagus, terlebih bila bertujuan untuk memenuhi SRP. Class yang terlalu gemuk juga sudah dibahas di code smell Large Class.

Namun, class yang terlalu kecil juga tidak baik karena semakin banyak class yang harus dibaca, semakin sulit programmer memahami code suatu produk.

Lazy class adalah kondisi dimana class memiliki fungsi yang minim, ekstrimnya hanya memiliki satu buah fungsi.

Lazy class bisa saja terjadi karena refactoring. Awalnya class ini memiliki fungsi yang banyak. Namun, satu per satu dipindahkan ke class lain karena pertimbangan tertentu.

Pada contoh di [PriceValidator.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/lazy_class/before/PriceValidator.java). Class hanya memiliki satu buah fungsi untuk validasi harga.


### Penyelesaian

![Buang class pemalas](https://refactoring.guru/images/refactoring/content/smells/lazy-class-02.png "Buang class pemalas!")

Fungsi `validate` dipindahkan ke class [Price.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/lazy_class/after/Price.java) kemudian diberi nama yang lebih sesuai untuk class Price: `isPriceValid`. Setelah dipindahkan, class PriceValidator bisa dihapus.


## Data Class

[sourcemaking](https://sourcemaking.com/refactoring/smells/data-class) |
[refactoring.guru](https://refactoring.guru/smells/data-class) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/data_class/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/data_class/after)

### Penjelasan Smell

![Class yang hanya berisikan connector ke CD, Database, dll](https://refactoring.guru/images/refactoring/content/smells/data-class-01.png "Class yang hanya berisikan tampungan data saja")

> Class atau data struct sih? Kok class cuma muat data doang? Kebanyakan belajar data struct kau? Kakek aku juga bisa kali ngoding class isinya data doang! 💿💾💢

Bila class hanya cuma sebagai *dumb data holders*, class bisa dipertimbangkan untuk dibuang dengan cara digabung dengan class lain. *Dumb data holders* berarti class ini hanya memiliki field dan fungsi setter getter saja.

Ketika produk baru awal-awal di-code, sangat wajar bila banyak class yang belum memiliki behavior (baru isi data saja). Namun, bila produk sudah makin berkembang, namun masih ada suatu class yang hanya berisi setter/getter, sudah sinyal kuat untuk dilakukan refactor.

Pada contoh kasus [FullName.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/data_class/before/FullName.java), class hanya berisi setter getter untuk field `firstName` dan `lastName`.

### Penyelesaian

![Pasang method pada Data Class](https://refactoring.guru/images/refactoring/content/smells/data-class-02.png "Pasang method lah.. biar kepakai ini class")

Semua field dari FullName [dipindahkan](https://sourcemaking.com/refactoring/move-method) ke [User.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/data_class/after/User.java). Kemudian class FullName dihapus.

Atau tambahkan method-method untuk melengkapi Data Class jika memerlukan fungsionalitas. Anda juga bisa biarkan smell ini terjadi bila Data Class tersebut difungsikan untuk keperluan Domain-Driven Programming terutama pada level Repository atau Model.


## Dead Code

[sourcemaking](https://sourcemaking.com/refactoring/smells/dead-code) |
[refactoring.guru](https://refactoring.guru/smells/dead-code) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/dead_code/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/dead_code/after)

### Penjelasan Smell

![Code-code yang berguguran karena tidak dipakai](https://refactoring.guru/images/refactoring/content/smells/dead-code-01.png "Code-code yang berguguran karena tidak dipakai")

> **BERITA DUKA**: Inna lillahi wa inna ilaihi raji'un. Telah tersia-siakan code *blah-blah* entah karena tidak dipakai atau dihajar sama code lain. Dead code berdampak mengerikan dan sangat mudah menipu programmer jika tidak teliti lho!

Bagian code yang tidak akan tersentuh ketika sistem dijalankan. Contoh yang paling mudahnya adalah sebagai berikut:

```java
void foo() {
  if(true) {
    return;
  }

  System.out.println("halo");
}
```

`System.out.println("halo");` tidak akan pernah dijalankan karena fungsi `foo` akan selalu `return` duluan.

Bila Anda menggunakan IDE yang bagus, dead code biasanya akan diberi warning. Misalnya di Eclipse, code `System.out.println` diatas akan diberikan warning dengan simbol lampu kuning yang memberitahu line of code ini adalah dead code.

Bahkan untuk beberapa bahasa pemrograman tertentu, contohnya Golang, dead code bukan sekadar warning. Bila ada dead code program tidak bisa di-build.

Terkadang, dead code tidak nampak sejelas itu. Contohnya di class [PriceCalculator.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/dead_code/before/PriceCalculator.java).

Bayangkan awalnya requirement dari client adalah: diskon diberikan 10% bila harga kurang dari 10.000, jika lebih, diskon 20%. Terbentuklah code berikut:

```java
public double calculate(double price, boolean isDiscount){
  double discountPrice = 0;

  if(isDiscount){
    if(price < 10000){
      discountPrice = price * 0.1;
    } else {
      discountPrice = price * 0.2;
    }
  }

  return price - discountPrice;
}
```

Tiba-tiba, requirement dari client berubah. Client meminta diskon diketok rata 15%.

Entah mengapa, programmer tidak menghapus if yang pertama. Ia langsung menambahkan di bagian bawah sebelum return. Secara requirement, ini tidak masalah. Diskon akan selalu 15%.

```java
public double calculate(double price, boolean isDiscount){
  double discountPrice = 0;

  if(isDiscount){
    if(price < 10000){
      discountPrice = price * 0.1;
    } else {
      discountPrice = price * 0.2;
    }
  }

  if(isDiscount)
    discountPrice = price * 0.15;
  return price - discountPrice;
}
```

### Penyelesaian

![Buang dead code dari class!](https://refactoring.guru/images/refactoring/content/smells/dead-code-02.png "Kuburkan! Buang code yang wafat dari class!")

Setelah membaca code di bagian `before`, Anda tentunya menyadari bahwa bagian code ini adalah dead code yang tersembunyi.

```java
if(isDiscount){
  if(price < 10000){
    discountPrice = price * 0.1;
  } else {
    discountPrice = price * 0.2;
  }
}
```

Bagian ini memang dijalankan, namun variabel `discountPrice` akan selalu ditimpa di line of code selanjutnya. Sehingga bagian ini aman untuk dibuang.


## Speculative Generality

[sourcemaking](https://sourcemaking.com/refactoring/smells/speculative-generality) |
[refactoring.guru](https://refactoring.guru/smells/speculative-generality) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/speculative_generality/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/dispensables/speculative_generality/after)

### Penjelasan Smell

![Hierarki yang maha spekulan dan sok ide](https://refactoring.guru/images/refactoring/content/smells/speculative-generality-01.png "Pengennya class ini saya buat kalo misalnya sewaktu-waktu terpakai bisa berguna lho!")

> Pengen banget class ini saya buat kalo misalnya sewaktu-waktu terpakai bisa berguna lho! Lah buat apa kalo tidak terpakai? Kubur lagi? Mending buang tuh imaji dan haluan kau jauh-jauh kalo sekarang masih gak benar! 💤💢🗑️

Class, method, field, atau parameter yang sudah disiapkan walaupun belum dipakai. Bahkan sebenarnya tidak ada dalam requirement.

Biasanya karena programmer sok ide, berspekulasi, bahwa ini akan dibutuhkan. Namun ternyata setelah produk berjalan lama, spekulasinya ini tidak terbukti.

Hal ini tentunya menjadi masalah karena kita membuat code lebih sulit dibaca untuk hal yang tidak perlu.

Salah satu prinsip extreme programming (XP) yang terkenal adalah [You aren't gonna need it (YAGNI)](https://en.wikipedia.org/wiki/You_aren%27t_gonna_need_it). Disarankan oleh XP untuk tidak menambahkan fungsionalitas sampai benar-benar diperlukan.

Contohnya, pada package `before`.

Requirement dari client adalah Price bisa memiliki Currency antara IDR atau USD.

Programmer berspekulasi bahwa IDR dan USD adalah kurs jenis tradisional, nantinya akan ada kurs jenis digital seperti bitcoin. Oleh karena itu, Programmer membuat hirarki seperti di dalam package `before`.

### Penyelesaian

![Buang hierarki kosong dan useless!](https://refactoring.guru/images/refactoring/content/smells/speculative-generality-02.png "Buang imaji dan haluan! Bukan saatnya buat program buat masa depan kalo sekarang aja masih gak benar!")

Dilakukan [Collapse Hierarchy](https://sourcemaking.com/refactoring/collapse-hierarchy). Class `Traditional` dan `Digital` dihapus. class `USD` dan `IDR` menjadi turunan langsung dari class `Currency`.

---

### Referensi Gambar

Semua gambar referensi mengikuti pictorial gambar pada **Refactoring.guru** (kecuali pada Duplicate Code, bersumber dari **sourcemaking**) dengan tetap mengutamakan link credit pada [sourcemaking.com](https://sourcemaking.com/refactoring/smells/) maupun [refactoring.guru](https://refactoring.guru/smells/)
