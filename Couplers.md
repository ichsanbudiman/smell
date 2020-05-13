# Couplers

[Smell](.) → [Martin Fowler Code Smells](Fowler) → [Couplers](#)

Semua smell di dalam grup ini berkaitan dengan coupling yang tinggi.

Seperti yang telah Anda pelajari di matakuliah Program Design Methods di semester sebelumnya, code yang baik memiliki coupling yang dibuat serendah mungkin antar modulnya, dan memiliki cohesion yang dibuat setinggi mungkin di dalam modulnya. Untuk review, silakan baca [link ini](https://www.geeksforgeeks.org/software-engineering-coupling-and-cohesion/).

- [Feature Envy](#feature-envy)
- [Inappropriate Intimacy](#inappropriate-intimacy)
- [Message Chains](#message-chains)
- [Middle Man](#middle-man)


## Feature Envy

[sourcemaking](https://sourcemaking.com/refactoring/smells/feature-envy) |
[refactoring.guru](https://refactoring.guru/smells/feature-envy) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/feature_envy/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/feature_envy/after)

### Penjelasan Smell

![Class ngintip tetangga sebelah](https://refactoring.guru/images/refactoring/content/smells/feature-envy-01.png "Class ngintip tetangga sebelah")

> Setiap harinya, Yasugi selalu mengunjungi rumah Kabuto yang berada di sebelahnya karena sepeda motor yang dia miliki ia parkirkan di rumah tersebut. Namun pada kenyataannya, Yasugi juga sering mengintip adik Kabuto main PS4 karena dia senang main bola. Eh digampar si Yasugi! Ya siapa suruh ngintip-ngintip adek mereka main game?

Smell ini terjadi bila ada sebuah method yang lebih sering mengakses data class lain ketimbang class sendiri. Class sendiri pun menjadi 'cemburu'. Bila hal ini terjadi, harus dipikirkan bagaimana cara mengusir method ini ke class lain tersebut.

Pada contoh [Lecturer.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/before/Lecturer.java), terdapat method `isScored` yang hanya mengakses data examiner di class Exam.

```java
public boolean isScored(Exam exam) {
  return exam.getExaminer() != null;
}
```

### Penyelesaian

![Usir tukang pengintip!](https://refactoring.guru/images/refactoring/content/smells/feature-envy-02.png "Usit tukang pengintip!")

Dilakukan [Move Method](https://sourcemaking.com/refactoring/move-method) pada fungsi `isScored`. Perhatikan di package `after`, isScored dipindahkan ke class Exam.

Selain itu, code fragment ini pun dipindahkan juga ke fungsi `setScore` di class Exam.

```java
if(this.isScored()) {
    throw new IllegalArgumentException("exam already scored");
  }
```

### Tambahan

Di bukunya, Martin Fowler menyatakan bahwa ada beberapa kondisi class atau method yang sengaja dirancang untuk hanya consume data di class lain. Contoh yang paling umum terjadi adalah penggunaan design pattern Strategy atau Visitor. Design pattern ini masuk ke dalam Gang of Four Design Pattern, akan kita pelajari di semester depan.


## Inappropriate Intimacy

[sourcemaking](https://sourcemaking.com/refactoring/smells/inappropriate-intimacy) |
[refactoring.guru](https://refactoring.guru/smells/inappropriate-intimacy) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/inappropriate_intimacy/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/inappropriate_intimacy/after)

### Penjelasan Smell

![Method digotong oleh beberapa class](https://refactoring.guru/images/refactoring/content/smells/inappropriate-intimacy-01.png "Method digotong oleh beberapa class")

> Ceritanya ada grup chat yang terdiri dari 7 orang. Ketujuh orang ini memiliki dustanya masing-masing mulai dari maling sendal, SMS mama minta duit, anak tawuran, hingga koleksi wikwik. Namun gara-gara programmer mengekspos profile user, eh kebongkar dusta-dustanya mereka ber-tujuh! :sweat_smile:

Smell ini terjadi karena hubungan antar class yang terlalu intim, menyebabkan class yang satu mengeksploitasi internal field dan/atau method di class lain.

Hal ini berbahaya karena class yang internal logic-nya diakses bisa mendapat perlakuan yang tidak terduga.

Pada constructor di [Product.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/inappropriate_intimacy/before/Product.java), class Product mengetahui ada fungsi `addTag` di Catalog dan menambahkan tag-nya sendiri ke Catalog.

```java
public Product(String name, double price, Catalog catalog) {
  ...
  this.catalog.addTag(this, Catalog.NEWCOMER_TAG);
}
```

Hal ini tentu berbahaya karena artinya, class Product bisa menambahkan tag apapun tanpa seizin Catalog.

### Penyelesaian

![Pencarkan method ke beberapa class](https://refactoring.guru/images/refactoring/content/smells/inappropriate-intimacy-02.png "Pencarkan method ke beberapa class")

Catalog dan Product punya hubungan bidirectional yang tidak perlu, maka dilakukan [Change Bidirectional Association to Unidirectional](https://sourcemaking.com/refactoring/change-bidirectional-association-to-unidirectional). Dengan cara menghapus field `catalog` di dalam `Product`.

Semua method di class [Catalog](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/inappropriate_intimacy/after/Catalog.java) yang berhubungan dengan manipulasi tag access modifier-nya diubah menjadi private.


## Message Chains

[sourcemaking](https://sourcemaking.com/refactoring/smells/message-chains) |
[refactoring.guru](https://refactoring.guru/smells/message-chains) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/message_chains/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/message_chains/after)

### Penjelasan Smell

![Message communication chaining in-betweens](https://refactoring.guru/images/refactoring/content/smells/message-chains-01.png "Perpindahan informasi menggunakan komunikasi antar orang")

> Pada zaman dulu sebelum adanya pos Merpati, manusia mengandalkan teriakan untuk mengirimkan informasi kepada orang yang diinginkan. Eh tapi kan zaman dulu! Kenapa tidak pakai internet aja? Kan lebih cepat dan efisien.

Smell ini terjadi ketika ingin mengakses sebuah method, perlu dilakukan pemanggilan dari hasil return method lainnya sehingga membentuk rantai: `obj.a().b().c()`.

Perhatikan [DistanceTest.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/message_chains/before/DistanceTest.java), terdapat message chaining ketika ingin mengakses latitude dan longitude.

```java
...
driver.getCurrentPosition().getLatitude();
...
```

### Penyelesaian

![Berikan mereka communicator](https://refactoring.guru/images/refactoring/content/smells/message-chains-02.png "Berikan mereka communicator")

Dilakukan [Hide Delegate](https://sourcemaking.com/refactoring/hide-delegate). Perhatikan class [BojekDriver](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/message_chains/after/BojekDriver.java) dan [Destination](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/message_chains/after/Destination.java), telah ditambahkan fungsi `latitude()` dan `longitude()` yang sebenarnya melakukan delegasi chaining yang dilakukan di package before. Hal ini dilakukan agar class client tidak merasakan adanya chaining, disembunyikan di dalam sini.

```java
public double latitude() {
  return this.getCurrentPosition().getLatitude();
}

public double longitude() {
  return this.getCurrentPosition().getLongitude();
}
```

Alhasil, seperti yang bisa dilihat di [DistanceTest.java](after/DistanceTest.java) di package after, message chain sudah tidak ada.

### Tambahan

![Classes can communicate remotely](https://refactoring.guru/images/refactoring/content/smells/message-chains-03.png "Class dapat berkomunikasi remote dengan rekannya")

Jangan terlalu agresif mengurusi message chain karena bisa menyebabkan smell [Middle Man](#middle-man).

Pertimbangkan mengurusi message chain bila terjadi di banyak tempat atau kebetulan ada class yang behavior-nya cocok untuk menampung method delegasi.


## Middle Man

[sourcemaking](https://sourcemaking.com/refactoring/smells/middle-man) |
[refactoring.guru](https://refactoring.guru/smells/middle-man) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/middle_man/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/middle_man/after)

### Penjelasan Smell

![Wanita dikawal preman](https://refactoring.guru/images/refactoring/content/smells/middle-man-01.png "Akses ke class lain harus melalui perantara")

> Pada beberapa kasus, para lelaki harus membayarkan tip kepada 'preman' sebelum mereka bisa bertemu dengan wanita yang mereka inginkan. Lah kan adik aku, kenapa aku malah dipalak juga? Kan kita cuma beda jenis kelamin mengapa kita kena palak?

Middle Man adalah class yang isinya hanya delegasi saja, tidak ada behavior lain selain delegasi ke class lain.

Perhatikan [LinkedList.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/middle_man/before/LinkedList.java) dan [ShoppingCart.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/couplers/middle_man/before/ShoppingCart.java). `ShoppingCart` melakukan `add` pada `LinkedList`. Namun ternyata di dalam `LinkedList`, dia hanya delegasi ke `java.util.Vector`.


### Penyelesaian

![Yay, bisa ketemu idolaku](https://refactoring.guru/images/refactoring/content/smells/middle-man-02.png "Class dapat berkomunikasi dengan rekan secara langsung")

Hapus si Middle Man, yaitu `LinkedList`. Sekarang di package after, class `ShoppingCart` langsung mengakses `java.util.Vector`.

### Tambahan

Ada beberapa design pattern yang memang dirancang untuk delegasi, seperti: Adapter, Proxy, Bridge, Facade atau Mediator. Namun dalam design pattern ini bukan sekadar delegasi mentah-mentah, ada sedikit fitur di dalamnya. Design pattern ini masuk ke dalam Gang of Four Design Pattern, akan kita pelajari di semester depan.

---

### Referensi Gambar

Semua gambar referensi mengikuti pictorial gambar pada **Refactoring.guru** dengan tetap mengutamakan link credit pada [sourcemaking.com](https://sourcemaking.com/refactoring/smells/) maupun [refactoring.guru](https://refactoring.guru/smells/)
