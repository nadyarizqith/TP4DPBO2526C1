JANJI:
Saya Nadya Rizqitha Salsabila dengan NIM 2500991 mengerjakan Tugas Praktikum 4
dalam mata kuliah Desain Pemrograman Berorientasi Objek untuk keberkahanNya
maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

#Desain / Fungsi Program

Aplikasi desktop Java Swing untuk mengelola data orang (Person) dengan operasi CRUD.

Struktur file (`PeopleData/src`):
| File | Fungsi |
|---|---|
| `Person.java` | Class model: `id`, `nama`, `tahunLahir`, `kategori`, `skorKinerja` + constructor, getter, setter |
| `PeopleMenu.java` | Logika aplikasi (listener, CRUD, validasi). Extends `JFrame` |
| `PeopleMenu.form` | Desain tampilan dari IntelliJ Swing UI Designer |

Atribut:
- ID (`JTextField`)
- Nama (`JTextField`)
- Tahun Lahir (`JTextField`, harus angka antara 1900 dan tahun sekarang)
- Kategori (`JComboBox`): Mahasiswa, Dosen, Tenaga Kependidikan, Alumni, Tamu
- Atribut baru: Skor Kinerja (`JSlider`, 0–100) .

Komponen UI: `JLabel` judul, 3 `JTextField`, 1 `JComboBox`, 1 `JSlider`, tombol Add/Update, Cancel, Delete, dan `JTable` di dalam `JScrollPane`.

#Penjelasan Alur

1. `main()` membuat window `PeopleMenu`, memasang `mainPanel`, lalu menampilkannya.
2. Constructor membuat `listPerson`, mengisinya via `populateList()` (10 data awal), mengisi tabel via `setTable()`, mengisi combo box, mengatur slider, dan menyembunyikan tombol Delete.
3. Create, saat `selectedIndex == -1` tombol bertuliskan *Add*. Klik → `insertData()` memvalidasi form (`readForm`), menambah `Person` ke list, menyegarkan tabel, mengosongkan form.
4. Read, semua data tampil di tabel. Klik sebuah baris → `selectedIndex` terisi, form terisi data baris tsb, tombol berubah menjadi Update dan tombol *Delete* muncul.
5. Update, klik Update -> `updateData()` memvalidasi form, mengubah objek di list lewat setter, menyegarkan tabel, mengosongkan form.
6. Delete — klik Delete -> muncul dialog konfirmasi (Yes/No). Jika Yes, `deleteData()` menghapus data dari list dan menyegarkan tabel. Jika No, tidak ada yang berubah.
7. Cancel — `clearForm()` mengosongkan form, mengembalikan tombol ke Add, menyembunyikan Delete, dan mereset `selectedIndex = -1`.

Validasi:field tidak boleh kosong, tahun lahir harus angka dan masuk akal, kategori harus dipilih (bukan `???`), dan ID tidak boleh duplikat.

#Cara Menjalankan
Run `PeopleMenu.java`.

#Dokumentasi Saat Program Dijalankan
https://youtu.be/UD4B6RpDLVc
Tambah data:
![alt text](<Dokumentasi/Screenshot 2026-10-12 211143.png>)
![alt text](<Dokumentasi/Screenshot 2026-10-12 211244.png>)
Update:
![alt text](<Dokumentasi/Screenshot 2026-10-12 211355.png>)
Sebelum di delete:
![alt text](<Dokumentasi/Screenshot 2026-10-12 211410.png>)
Delete:
![alt text](<Dokumentasi/Screenshot 2026-10-12 211421.png>)
![alt text](<Dokumentasi/Screenshot 2026-10-12 211434.png>)