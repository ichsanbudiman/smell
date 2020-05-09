
Semua smell di dalam grup ini berkaitan dengan penerapan prinsip OOP yang kurang tepat.

- [Switch Statements](#switch-statements)
- [Temporary Field](#temporary-field)
- [Refused Bequest](#refused-bequest)
- [Alternative Classes with Different Interfaces](#alternative-classes-with-different-interfaces)

## Switch Statements

[sourcemaking](https://sourcemaking.com/refactoring/smells/switch-statements) |
[refactoring.guru](https://refactoring.guru/smells/switch-statements) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/after)

### Penjelasan Smell

![Depedencies of the object through a controller](https://refactoring.guru/images/refactoring/content/smells/switch-statements-01.png "Semuanya bergantung pada 1 controller, menggantung dengan kabel")

> Apa gunanya mati-matian ngoding switch-case kalo ujung-ujungnya dianggap jorok? Kan aku kan udah menciptakan inovasi istimewa biar programmer tinggal oper-operan ke method lain lalu kenapa enggan dengan diri anda?

Dalam kasus smell tersebut terdapat pemakaian switch atau if-else untuk menentukan alur operasi.

Apalagi hal ini memang dianggap pro-kontra terutama jika anda ingin melakukan tindakan antar object ataupun sekedar oper-operan method. Namun biasanya penggunaan switch-case ini diharamkan jika anda melakukan hal ini untuk deklarasi atau kasus yang melibatkan inheritance.

Tidak semua switch atau if-else itu berbahaya. Perlu dipertimbangkan apakah akan terjadi violasi terhadap OCP ([Open Closed Principle](https://en.wikipedia.org/wiki/Open%E2%80%93closed_principle)).

Lihat class [ShapePrinter.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/before/ShapePrinter.java) dan [CharNeededCounter.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/before/CharNeededCounter.java).

```java
if(shape.equalsIgnoreCase("square")){
  ...
} else if(shape.equalsIgnoreCase("triangle")){
  ...
} else {
  ...
}
```

Kedua class tersebut akan melanggar OCP. Bayangkan bila ada tipe `Shape` baru yang perlu dibuat, tentu saja akan bertambah lagi `if` di masing-masing ShapePrinter dan CharNeededCounter.

Misal bertambah logic shape `Circle`. Violasi OCP terjadi di 2 class tersebut.
```java
if(shape.equalsIgnoreCase("square")){
  ...
} else if(shape.equalsIgnoreCase("triangle")){
  ...
} else if(shape.equalsIgnoreCase("circle")){
  ...
} else {
  ...
}
```

Di dalam contoh ini, if-else square dan triangle ada di 2 class. Pada kondisi nyata bila hal ini dibiarkan terjadi, if-else square dan triangle akan terus beranak-pinak bila ada kebutuhan logic lain.

Semakin sedikit kita menggunakan if-else di dalam code, maka akan semakin baik. Bahkan ada orang yang membuat campaign untuk ini: [Anti-IF Campaign](https://francescocirillo.com/pages/anti-if-campaign).

### Penyelesaian

![Control the object](https://refactoring.guru/images/refactoring/content/smells/switch-statements-02.png "Lebih teratur, manfaatkan polymorphism atau factory untuk mengendalikan object")

Untuk contoh kasus ini, kita melakukan tahapan berikut:

1. [Replace Type Code with Subclasses](https://sourcemaking.com/refactoring/replace-type-code-with-subclasses)

Kita memiliki dua type code. `square` dan `triangle`. Oleh karena itu, kita buat class [Shape.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/after/Shape.java) sebagai abstract class yang memiliki fungsi `charNeeded` dan `print`, lalu [Triangle.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/after/Triangle.java) dan [Square.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/after/Square.java) meng-extends class `Shape`.

2. [Replace Conditional with Polymorphism](https://sourcemaking.com/refactoring/replace-conditional-with-polymorphism)

Setelah class `Square` dan `Triangle` sudah terbentuk. Logic print dari `ShapePrinter` dan logic menghitung karakter dari `CharNeededCounter` dipindahkan ke masing-masing class.

Dengan begini, bila ada jenis baru, misalkan `Circle`, kita tinggal extends dari class `Shape`.

### Tambahan

#### Revisi Martin Fowler

Switch Statements adalah code smell yang dibuat Fowler di buku edisi pertamanya. Di buku edisi kedua, beliau meniadakan code smell ini. Beliau membuat smell baru bernama **Repeated Switches**. Beliau ingin lebih menekankan if-else yang perlu diberantas adalah if-else yang berstruktur sama/mirip dan sering muncul di beberapa tempat. Contohnya di contoh kasus ini if-else square dan triangle muncul dua kali.

#### ShapeFactory

User tetap akan meng-input string melalui console. Oleh karena itu, kita perlu menyiapkan sebuah class [Factory](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/switch_statements/after/ShapeFactory) untuk membuat class Shape dari string yang diinput.

Harusnya Anda menyadari bahwa terjadi violasi OCP disini. Bila class `Circle` dibuat, maka if di Factory bertambah. Hal ini dimaklumi karena OCP hanya dilanggar satu kali saja di dalam Factory (tidak akan dilanggar lagi di tempat lain) dan memang terpaksa dilakukan karena input dari user adalah string. Ibaratkan Factory disini berperan sebagai anti-corruption layer.

Di beberapa bahasa pemrograman, ada cara spesifik untuk mengakali menghilangkan if-else pada class Factory, contohnya untuk Java: https://www.javacodegeeks.com/2014/10/factory-without-if-else.html.


## Temporary Field

[sourcemaking](https://sourcemaking.com/refactoring/smells/temporary-field) |
[refactoring.guru](https://refactoring.guru/smells/temporary-field) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/temporary_field/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/temporary_field/after)

### Penjelasan Smell

![The place where laziness happens](https://refactoring.guru/images/refactoring/content/smells/temporary-field-01.png "Dimana method memakai variabel, disitulah mereka berada")

> Kalo ngoding di C (atau bahasa Procedural lainnya) kan kadang perlu buat variabel global scope, lalu kalo misalnya aku coba terapin ginian di OOP kenapa dibilang jorok? Ya kan ngoding di OOP dan Procedural beda mas! OOP ngoding per-satuan class dan object, kalo procedural ngoding per kit/procedure atau hal-hal yang diperlukan/diproses disitu.

**Perlu diingatkan ngoding di Object-oriented Programming tidaklah sama dengan Procedural Programming!** Dalam kasus ini terdapat field yang bukan bagian dari data class yang bersangkutan. Field ini hanya dipakai sementara oleh beberapa fungsi sehingga ketika fungsi tersebut selesai dijalankan, field ini tidak pernah lagi digunakan.

SOLID Principle yang perlu dicek untuk smell ini adalah SRP ([Single-responsibility principle](https://en.wikipedia.org/wiki/Single-responsibility_principle)). Kita perlu mempertimbangkan apakah field yang bersangkutan adalah field yang cocok menempati class tersebut.

Lihat class [BojekDriver.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/temporary_field/before/BojekDriver.java)
```java
private int f;
private int g;
private int h;

Vector<Location> shortestPath(){
  Vector<Location> paths = new Vector<>();

  //...
  //complex A* algorithm code. using f, g, h variable
  //...

  return paths;
}
```

Disini dimisalkan ada driver yang perlu melakukan pencarian rute terdekat. Salah satu algoritma yang dapat digunakan untuk mencari rute adalah [A*](https://en.wikipedia.org/wiki/A*_search_algorithm). Seperti yang sudah Anda pelajari di matakuliah AI semester lalu, A* membutuhkan beberapa hal untuk beroperasi, misalnya adalah variabel f, g, h.

Di dalam class ini, bayangkan ketiga variabel itu akan digunakan di fungsi `shortestPath` dan juga di fungsi-fungsi private lainnya yang adalah hasil extract fungsi `shortestPath` (diekstrak agar tidak menimbulkan code smell **Long Method**).

BojekDriver seharusnya hanya memperdulikan data miliknya. Variabel f, g, dan h hanyalah temporary field untuk keperluan algoritma A*.

### Penyelesaian

![Do an activity](https://refactoring.guru/images/refactoring/content/smells/temporary-field-02.png "Ajak variabel tersebut untuk menjalankan kesenangannya (halusnya pisah class)")

Untuk contoh kasus ini, kita melakukan [Extract Class](https://sourcemaking.com/refactoring/extract-class)

![Each class has activities](https://refactoring.guru/images/refactoring/content/smells/temporary-field-03.png "Class kini tidak ada variabel pemalas, setiap variabel kini berada pada aktivitas yang diinginkan")

Semua fungsi berkaitan dengan A* dan semua variabelnya kita usir ke class baru [AStar.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/temporary_field/after/AStar.java).

Di class [BojekDriver](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/temporary_field/after/BojekDriver.java), fungsi `shortestPath` tetap ada, namun didelegasi ke class AStar.

```java
Vector<Location> shortestPath(){
  return new AStar().shortestPath(current, destination);
}
```


## Refused Bequest

[sourcemaking](https://sourcemaking.com/refactoring/smells/refused-bequest) |
[refactoring.guru](https://refactoring.guru/smells/refused-bequest) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequest/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequest/after) |
[contoh alternatif](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequest_2/)

### Penjelasan Smell

![Equity problems](https://refactoring.guru/images/refactoring/content/smells/refused-bequest-01.png "Anjing sama apa dengan Kursi? Itulah mengapa setiap object memiliki karakteristik yang berbeda")

> Aku pernah diminta untuk implement suatu abstract class namun saya rada-rada benci sama fitur yang enggan class ini pakai makanya saya `return null` aja atau lempar exception berupa `UnsupportedOperationException` karena dianggap useless bagi class itu. Lah kalo misalnya code saya dianggap jorok, lalu diapakan itu class yang saya implement dari abstract class?

Refused bequest arti harafiahnya adalah "menolak warisan". Dalam smell ini, sebuah class turunan tidak memakai seluruh method hasil extendsnya. Hal ini mengarah ke violasi LSP dan/atau ISP.

Dalam contoh kasus, terdapat class [Stack.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequestbefore/Stack.java) yang melakukan extends terhadap `java.util.Vector`. Untuk pembahasan alternatif yaitu mengenai inheritance Rectangle terhadap Square, dapat dilihat melalui [link ini](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequest_2/).

Di dalam class [Stack](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequestbefore/Stack.java), terdapat fungsi standar sebuah stack LIFO. yaitu: pop, push, dan peek.

```java
public void push(E data) {
  this.add(data);
}

public void pop() {
  this.removeElementAt(this.size()-1);
}

public E peek() {
  return this.elementAt(this.size()-1);
}
```

Namun, terdapat satu masalah. java.util.Vector memiliki banyak fungsi yang memungkinkan class melakukan manipulasi data di dalam array (misalnya bisa hapus data menggunakan indeks). Tentu saja ini melanggar prinsip LIFO (Last-In First-Out).

![Wrong inheritancy of animal and chair](https://refactoring.guru/images/refactoring/content/smells/refused-bequest-02.png "Ada penolakan inheritance dari kursi terhadap hewan")

Oleh karena itu, di class Stack, diakali dengan cara melakukan override pada masing-masing fungsi java.util.Vector yang tidak ingin digunakan, dan kita menghilangkan kinerjanya dengan cara menghapus pemanggilan super.

Sebelumnya seperti ini:
```java
@Override
public synchronized E remove(int index) {
  return super.remove(index);
}
```

Return super kita ubah menjadi return null. Sehingga remove by index tidak terjadi.

```java
/*
* you cannot remove by index, use pop instead
*/
@Override
public synchronized E remove(int index) {
  return null;
}
```

Hal ini menjadi code smell **Refused Bequest**, karena class Stack menolak warisan dari class Vector.

### Penyelesaian

![Change inheritance into delegation](https://refactoring.guru/images/refactoring/content/smells/refused-bequest-03.png "Pindahkan arah hierarki dan gantikan inheritence dengan delegasi!")

Untuk contoh kasus ini, kita melakukan [Replace Inheritance with Delegation](https://sourcemaking.com/refactoring/replace-inheritance-with-delegation).

Hubungan `is-a` tidak cocok untuk Stack dan Vector. Kita ubah hubungannya menjadi hubungan `has-a`.

class [Stack](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/refused_bequestafter/Stack.java) menyimpan java.util.Vector sebagai field-nya. Pop, push, dan peek dilakukan dengan Vector ini.

```java
public class Stack<E> {
	private Vector<E> vector = new Vector<>();

	public void push(E data) {
		vector.add(data);
	}

	public void pop() {
		vector.removeElementAt(vector.size()-1);
	}

	public E peek() {
		return vector.elementAt(vector.size()-1);
	}
}
```

### Tambahan

#### java.util.Stack

Java sudah memiliki class Stack-nya sendiri di package `java.util`. Class Stack disini dibuat sendiri hanya untuk keperluan studi kasus. FYI, java.util.Stack adalah hasil inherit dari java.util.Vector. Anda dapat menghapus data di tengah-tengah Stack layaknya menggunakan Vector.


## Alternative Classes with Different Interfaces

[sourcemaking](https://sourcemaking.com/refactoring/smells/alternative-classes-with-different-interfaces) |
[refactoring.guru](https://refactoring.guru/smells/alternative-classes-with-different-interfaces) |
[before](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/before) |
[after](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/after)

### Penjelasan Smell

![Class with diffrent perspectives](https://refactoring.guru/images/refactoring/content/smells/alternative-classes-with-different-interfaces-01.png "Beda perspektif, ada yang bilang mobil mainan, pickup, dan mobil sedan padahal semuanya sama kok")

> Menebak smell dengan class yang sama namun beda interface atau abstract bisa jadi menandakan bahwa class tersebut diciptakan dengan kemiripan namun seharusnya bisa diciptakan inheritance. Entah developernya lupa kalo bisa dibuatin abstract/interface atau dia jangan-jangan copas lalu ganti nama methodnya?

Ada beberapa class yang memiliki fungsi yang sama, namun tidak datang dari interface atau abstract class.

Kesamaan fungsi yang dimaksud adalah fungsi memiliki tujuan yang sama. Namun bisa saja memiliki nama yang berbeda, atau bisa juga memiliki parameter yang serupa namun beda urutan, atau ada parameter yang satuan, ada yang berupa objek ([Preserve whole object](https://sourcemaking.com/refactoring/preserve-whole-object)).

Hal ini bisa terjadi bisa karena class-class tersebut dikerjakan oleh programmer yang berbeda. Sehingga antar programmer tidak sadar ada yang bisa dibuatkan interface diantara code mereka berdua.

Atau bisa karena tidak mengikuti prinsip DIP ([Dependency Inversion Principle](https://en.wikipedia.org/wiki/Dependency_inversion_principle)).

Contohnya, class [Ghost.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/before/Ghost.java) memiliki fungsi `paint` yang bertujuan menggambar Ghost dari spritesheet yang tersedia.
```java
public class Ghost {
	public void paint(Graphics2D g){
		//draw Ghost pixel from spritesheet
	}
}
```

Di sisi lain, class [PacMan.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/before/PacMan.java) memiliki fungsi `draw` yang bertujuan sama. Menggambar PacMan dari spritesheet yang tersedia.

```java
public class PacMan {
	public void draw(Graphics2D g){
		//draw PacMan pixel from spritesheet
	}
}
```

### Penyelesaian

![Fix with inheritance](https://refactoring.guru/images/refactoring/content/smells/alternative-classes-with-different-interfaces-02.png "Be flexible dengan inheritance! Atau jika hanya perlu 1 class, pakailah 1 class saja!")

Untuk contoh kasus ini, kita melakukan [Extract Superclass](https://sourcemaking.com/refactoring/extract-superclass). Kita membuat interface [Drawable.java](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/after/Drawable.java) yang akan di-implement oleh kedua class.

```java
public interface Drawable {
	public void draw(Graphics2D g);
}
```

Pada class PacMan sebelumnya, nama fungsi adalah `draw`, sedangkan pada Ghost nama fungsi adalah `paint`. Dipilih salah satu dari kedua sinonim tersebut yang akan dipakai. Misal diputuskan `draw` yang dipakai sebagai nama method di interface `Drawable`, maka dilakukan [Rename Method](https://sourcemaking.com/refactoring/rename-method) pada fungsi `paint` di [Ghost](https://github.com/akmalrusli363/smell/tree/master/src/fowler/oo_abusers/alt_classes_with_dif_interfaces/after/Ghost.java).

---

### Referensi Gambar

Semua gambar referensi mengikuti pictorial gambar pada **Refactoring.guru** dengan tetap mengutamakan link credit pada [sourcemaking.com](https://sourcemaking.com/refactoring/smells/) maupun [refactoring.guru](https://refactoring.guru/smells/)
