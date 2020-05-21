var quote = document.getElementById('error-quotes');

var arr = [
  "Sini Sasuke hantam kau!",
  "Naruto, ini kan kampus ByNUS bukan sekolah Hokage!",
  "Leh.. lagi cari situs judi? Carinya lewat IndoXX1 sono!",
  "Ini bukan sabung ayam!",
  "Eh.. lagi nyari materi Arduino? Kami nggak belajar Arduino :((",
  "Code Reengineering is a way to <strong>cleanse your brain</strong>, not making you married with code!",
  "Nyari downloadan materi? Kami gak ada file :((",
  "Nyari kode nuklir? Situs ini gak punya nomor berapapun :D",
  "CARI KE BINUSMAYA SONO!!!",
  "Aku bukan dosen, mas Naufal :D",
  "Gak ada konten gaming! Cari aja di YouTube..!",
  "Eh.. mampus..! :((",
  "Press <strong>F</strong> to pay respect..",
  "All you had to do was follow the correct link, CJ!"
];

quote.innerHTML = arr[Math.floor(Math.random() * arr.length)];
