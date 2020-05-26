# Change Preventers

[Smell](..) → [Martin Fowler Code Smells](.) → [Change Preventers](#)

Semua smell di dalam grup ini berkaitan dengan code yang mempersulit kita ketika ingin melakukan perubahan atau penambahan fitur.

- [Divergent Change](#divergent-change)
- [Shotgun Surgery](#shotgun-surgery)
- [Parallel Inheritance Hierarchies](#parallel-inheritance-hierarchies)


## Divergent Change

[sourcemaking](https://sourcemaking.com/refactoring/smells/divergent-change) |
[refactoring.guru](https://refactoring.guru/smells/divergent-change) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/divergent_change/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/divergent_change/after)

Martin Fowler:

> Divergent change occurs when one module is often changed in different ways for different reasons. If you look at a module and say, “Well, I will have to change these three functions every time I get a new database; I have to change these four functions every time there is a new financial instrument,” this is an indication of divergent change. The database interaction and financial processing problems are separate contexts, and we can make our programming life better by moving such contexts into separate modules. That way, when we have a change to one context, we only have to understand that one context and ignore the other. We always found this to be important, but now, with our brains shrinking with age, it becomes all the more imperative. Of course, you often discover this only after you’ve added a few databases or financial instruments; context boundaries are usually unclear in the early days of a program and continue to shift as a software system’s capabilities change.

### Penjelasan Smell

![Bagai microcontroller, hampir selalu ada pergantian ketika terdapat add, update, atau remove dalam satu tempat](https://refactoring.guru/images/refactoring/content/smells/divergent-change-01.png "Bagai microcontroller, hampir selalu ada pergantian ketika terdapat add, update, atau remove dalam satu tempat")

> Ada sebuah perusahaan menciptakan **mesin printer** yang bisa scan dokumen. Pada suatu kasus, fungsi scanner ini ingin mereka tambahkan kemampuan agar bisa scan layar HP. Mau tak mau harus ganti komponen printer yaitu *ticker* (pengatur kecepatan scan dan print). Setelah *ticker* diganti ternyata komponen tersebut memperlambat proses print dan harus diganti lagi komponen tersebut dan pita printer. Setelah kedua komponen berganti, tiba-tiba mereka ingin fungsi scanner tersebut difungsikan sebagai mesin fotokopi langsung. Mereka harus memodifikasikan board dengan menambahkan paralelitas scanner dan printer. Namun semua itu telah membuang-buang waktu hingga 3 minggu hanya karena penambahan 2 fitur baru lho!

Hati-hati ketika mempelajari smell ini dari sourcemaking karena bagian *Signs and Symptoms* di sourcemaking berbeda dengan definisi Martin Fowler di bukunya. Kita akan menggunakan definisi sesuai dengan sumbernya.

Seperti di paragraf dari Martin Fowler diatas, smell ini terjadi ketika sebuah class sering berubah untuk alasan yang berbeda-beda. Dengan kata lain, class ini melanggar [Single Responsibility Principle](https://en.wikipedia.org/wiki/Single-responsibility_principle).

Disebut di paragraf diatas: *context boundaries are usually unclear*. Terkadang sulit untuk menentukan apa yang menjadi tanggungjawab class ini atau bukan. Hal ini kembali ke diskusi tim masing-masing ketika code design, atau menunggu gejala benar-benar jelas ketika terbukti benar class ini sering berubah untuk alasan yang berbeda. Pemisahan tanggungjawab paling klasik adalah menggunakan MVC dimana model, view, dan controller dipisah. Bila ingin pemisahan yang lebih detail, Anda juga bisa menggunakan **layered architecture** yang sudah diajarkan di kelas DDD.

Perhatikan class [Rectangle](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/divergent_change/before/Rectangle.java) di package `before`. Disana terdapat field `width` dan `weight`. Terdapat method `area()` dan `perimeter()`. Dan juga terdapat method `print(String style)`. Kita bisa berargumen bahwa class ini memiliki dua tanggungjawab, yaitu mengurus kalkulasi Rectangle dan juga mengatur tampilan Rectangle ke dalam console.

### Penyelesaian

![Pecahkan class tersebut menjadi beberapa bagian](https://refactoring.guru/images/refactoring/content/smells/divergent-change-02.png "Pecahkan class tersebut menjadi beberapa bagian")

Kita pindahkan method `print` di class `Rectangle` ke class yang baru. Karena di `print` juga terdapat smell primitive obsession, sekalian kita buatkan struktur baru menggunakan strategy design pattern. Perhatikan hasil refactor-nya di package `after`.


## Shotgun Surgery

[sourcemaking](https://sourcemaking.com/refactoring/smells/shotgun-surgery) |
[refactoring.guru](https://refactoring.guru/smells/shotgun-surgery) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/shotgun_surgery/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/shotgun_surgery/after)

### Penjelasan Smell

![Sekali berubah, semua feature harus kalian ganti ya!](https://refactoring.guru/images/refactoring/content/smells/shotgun-surgery-01.png "Sekali berubah, semua feature harus kalian ganti ya!")

> Pada suatu hari, terdapat mobil fortuner yang ingin diganti mesin diesel menjadi diesel V8! Ngomongnya gampang.. Saya turunkan mesin kamu.. Saya muatkan mesin diesel V8 dan ternyata mesin diesel tersebut kebesaran! Mau tak mau saya potong frame mobil kamu dan saya harus bongkar dashboard kamu hanya agar mesin tersebut muat ke dalam mobil. Setelah ganti mesin, datang lagi masalah kalo mesin tersebut memerlukan radiator khusus.. ganti lagi radiatornya.. eh enggak dingin AC-nya.. ganti bagian lain dan lainnya lagi. Pokoknya ribet!! 💢

Smell ini terjadi ketika kita ingin mengganti atau menambahkan fitur ke dalam code, kita perlu mengganti bagian code yang tersebar di banyak class lain. Analoginya adalah bila seseorang ditembak dengan senjata api jenis shotgun, luka tembak akan menyebar di banyak tempat.

Perhatikan code di package `before`. Class `PriceService` dan `PriceIncludeTaxService`, di dalamnya terdapat code untuk mengubah price yang access modifier-nya public. Masing-masing fungsi update juga memiliki validasi price masing-masing.

Sekarang, kita mendapatkan permintaan fitur baru dimana setiap kali ada update value pada class `Product`, maka kita melakukan logging menggunakan class [Logger](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/divergent_change/after/Logger.java).

Bila kita tidak segera melakukan refactor, kita perlu selalu melakukan perubahan ke setiap bagian code update value yang tersebar di banyak class. Karena ini hanya contoh, tersebarnya baru di dua class saja. Bayangkan bila tersebar di puluhan class. Selain capek, hal ini juga rawan *human-error* karena bisa ada bagian code yang lupa diganti.

Contoh masalah lainnya: bila cara validasi price diganti, kita juga perlu melakukan shotgun surgery.

### Penyelesaian

![Pecahkan komponen yang menimbulkan masalah beruntun](https://refactoring.guru/images/refactoring/content/smells/shotgun-surgery-02.png "Pecahkan komponen yang menimbulkan masalah beruntun")

Kita ubah access modifier price menjadi private. Code validasi price dipindahkan ke `setPrice`. Lalu fungsi setter ditambahkan logging.

![Alhamdulillah.. sudah saya amankan konflik ini!](https://refactoring.guru/images/refactoring/content/smells/shotgun-surgery-03.png "Alhamdulillah.. sudah saya amankan konflik ini!")

Sekarang, update value price sudah terpusat di dalam fungsi `setPrice`, sehingga bila suatu saat nanti ada perubahan mengenai update value price, kita cukup mengubah 1x saja di satu tempat.


## Parallel Inheritance Hierarchies

[sourcemaking](https://sourcemaking.com/refactoring/smells/parallel-inheritance-hierarchies) |
[refactoring.guru](https://refactoring.guru/smells/parallel-inheritance-hierarchies) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/parallel_inheritance_hierarchies/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/change_preventers/parallel_inheritance_hierarchies/after)

### Penjelasan Smell

![Hierarki maha bucin memang selalu ikut-ikutan terhadap segala hal yang ia senangi](https://refactoring.guru/images/refactoring/content/smells/parallel-inheritance-hierarchies-01.png "Hierarki maha bucin memang selalu ikut-ikutan terhadap segala hal yang ia senangi")

> Anggap ada bodi laptop dan layar tablet. Disaat ada komponen memory di body laptop, layar tablet musti ada memory. Disaat bodi laptop memiliki keyboard, layar tablet juga harus ada keyboard virtual. Dan disaat laptop itu ada komponen baru, layar tablet tersebut musti punya komponen itu juga. Hierarki maha bucin memang selalu ikut-ikutan terhadap segala hal yang ia senangi! 💫💢

Smell ini terjadi ketika ada hirarki inheritance yang selalu ditambahkan bersama-sama.

Bayangkan ada dua atau lebih hirarki parent-children class. Lalu kita perlu menambahkan (extends) satu class baru di salah satu hirarki, hirarki lain ikut-ikutan juga butuh untuk extends class mengikuti hirarki ini. Contoh: Abstract class A di-extends menjadi A1 dan A2. Lalu ada abstract class B di-extends menjadi B1 dan B2. Ketika A di-extends menjadi A3, B juga ikut-ikutan butuh B3.

Untuk menghadapi smell ini, kita perlu mempertimbangkan apakah cocok bila hirarki-hirarki ini digabung saja menjadi satu.

Smell tidak perlu ditangani bila memang hirarki paralel ini disengaja untuk keperluan pemisahan konteks/tanggungjawab. Atau ketika digabung code malah menjadi lebih jorok.

Perhatikan code di package `before`. Hirarki `Shape2D` selalu berkembang berbarengan dengan hirarki `AreaInterface`. Bila ada `Shape2D` yang baru, misalnya `Circle`. maka `AreaInterface` juga akan ikut-ikutan membuat anak baru `CircleAreaCalculator`.

### Penyelesaian

![Gabungkan hierarki bucin ke hierarki pasangannya dengan inheritence dan generalisasi](https://refactoring.guru/images/refactoring/content/smells/parallel-inheritance-hierarchies-02.png "Gabungkan hierarki bucin ke hierarki pasangannya dengan inheritence dan generalisasi")

Kita pindahkan logika perhitungan area agar digabung saja ke dalam `Shape2D`.

---

### Referensi Gambar

Semua gambar referensi mengikuti pictorial gambar pada **Refactoring.guru** dengan tetap mengutamakan link credit pada [sourcemaking.com](https://sourcemaking.com/refactoring/smells/) maupun [refactoring.guru](https://refactoring.guru/smells/)
