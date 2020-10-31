---
description: "Klasifikasi smell berdasarkan literatur dari Martin Fowler yang mencakup 5 jenis smell: Bloaters, Object-Orientation Abusers, Change Preventers, Dispensables, dan Couplers"
---

# Martin Fowler Code Smells

[Smell](..) → [Martin Fowler Code Smells](#)

Pada literatur Martin Fowler, kasus smell ini mencakup permasalahan mendasar mengenai code, class, relasi antar class, dan penerapan prinsip OOP yang kurang tepat yang mempersulit programmer dalam menghadapi perubahan.

Untuk pembagian materi, BINUS menggunakan pengelompokan group smell yang telah dibuat oleh Martin Fowler yang telah diadaptasi secara interaktif melalui [sourcemaking](https://sourcemaking.com/refactoring) dan [refactoring.guru](https://refactoring.guru/refactoring/smells/).

Semua contoh dalam [repository ini](https://github.com/akmalrusli363/smell/tree/master/src/fowler/) dibagi dalam dua package. `before` dan `after`. Sesuai dengan namanya, `before` adalah contoh code yang memiliki code smell yang akan dibahas, `after` adalah hasil setelah di-refactor.

### Penjelasan Smell

- [Bloaters](Bloaters)
- [Object-Orientation Abusers](OO-Abusers)
- [Change Preventers](Change-Preventers)
- [Dispensables](Dispensables)
- [Couplers](Couplers)

## Referensi

- Fowler, Martin. Refactoring: improving the design of existing code. Addison-Wesley Professional, 2018.
- Sourcemaking.com - [https://sourcemaking.com/refactoring/smells/](https://sourcemaking.com/refactoring/smells/)
- Refactoring.guru - [https://refactoring.guru/refactoring/smells/](https://refactoring.guru/refactoring/smells/)
