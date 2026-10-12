import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Year;
import java.util.ArrayList;

public class PeopleMenu extends JFrame {
    // Component (terikat dengan PeopleMenu.form)
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField tahunLahirField;
    private JComboBox<String> kategoriComboBox;
    private JSlider skorSlider;
    private JLabel skorValueLabel;
    private JButton addUpdateButton;
    private JButton deleteButton;
    private JButton cancelButton;
    private JTable peopleTable;

    // Data
    private ArrayList<Person> listPerson;
    private int selectedIndex = -1; // -1 = tidak ada baris yang dipilih

    public static void main(String[] args) {
        // buat object window
        PeopleMenu menu = new PeopleMenu();

        // atur ukuran window
        menu.setSize(750, 650);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // tampilkan window
        menu.setTitle("PeopleData");
        menu.setVisible(true);
    }

    // constructor
    public PeopleMenu() {
        // inisialisasi listPerson
        listPerson = new ArrayList<>();

        // isi listPerson
        populateList();

        // isi tabel
        refreshTable();

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(java.awt.Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Mahasiswa", "Dosen", "Tenaga Kependidikan", "Alumni", "Tamu" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // atur slider skor kinerja (0 - 100)
        skorSlider.setMinimum(0);
        skorSlider.setMaximum(100);
        skorSlider.setValue(50);
        skorValueLabel.setText("50");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat slider digeser, update label nilainya
        skorSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                skorValueLabel.setText(String.valueOf(skorSlider.getValue()));
            }
        });

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan (dengan konfirmasi)
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    return;
                }
                String nama = listPerson.get(selectedIndex).getNama();
                int pilihan = JOptionPane.showConfirmDialog(
                        PeopleMenu.this,
                        "Apakah kamu yakin ingin menghapus data \"" + nama + "\"?",
                        "Konfirmasi Hapus",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
                if (pilihan == JOptionPane.YES_OPTION) {
                    deleteData();
                }
            }
        });

        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        // saat salah satu baris tabel ditekan
        peopleTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = peopleTable.getSelectedRow();
                if (row < 0 || row >= listPerson.size()) {
                    return;
                }

                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = row;
                Person p = listPerson.get(selectedIndex);

                // ubah isi textfield, combo box, dan slider
                idField.setText(p.getId());
                namaField.setText(p.getNama());
                tahunLahirField.setText(String.valueOf(p.getTahunLahir()));
                kategoriComboBox.setSelectedItem(p.getKategori());
                skorSlider.setValue(p.getSkorKinerja());

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);
            }
        });
    }

    // isi data awal (bebas)
    private void populateList() {
        listPerson.add(new Person("P001", "Budi Santoso", 2003, "Mahasiswa", 82));
        listPerson.add(new Person("P002", "Siti Aminah", 1985, "Dosen", 91));
        listPerson.add(new Person("P003", "Rian Pratama", 1990, "Tenaga Kependidikan", 74));
        listPerson.add(new Person("P004", "Dewi Lestari", 2004, "Mahasiswa", 88));
        listPerson.add(new Person("P005", "Ahmad Fauzi", 1978, "Dosen", 95));
        listPerson.add(new Person("P006", "Nadia Putri", 2001, "Alumni", 79));
        listPerson.add(new Person("P007", "Rizky Ramadhan", 1999, "Alumni", 68));
        listPerson.add(new Person("P008", "Maya Indah", 1995, "Tamu", 60));
        listPerson.add(new Person("P009", "Hendra Wijaya", 1988, "Tenaga Kependidikan", 85));
        listPerson.add(new Person("P010", "Clarissa Yun", 2005, "Mahasiswa", 77));
    }

    // buat model tabel dari listPerson
    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID", "Nama", "Tahun Lahir", "Kategori", "Skor Kinerja" };

        // buat objek tabel (sel tidak bisa diedit langsung)
        DefaultTableModel tmp = new DefaultTableModel(null, cols) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        // isi tabel dengan listPerson
        for (int i = 0; i < listPerson.size(); i++) {
            Person p = listPerson.get(i);
            Object[] row = {
                    i + 1,
                    p.getId(),
                    p.getNama(),
                    p.getTahunLahir(),
                    p.getKategori(),
                    p.getSkorKinerja()
            };
            tmp.addRow(row);
        }

        return tmp;
    }

    // pasang model baru ke tabel + rapikan lebar kolom "No"
    private void refreshTable() {
        peopleTable.setModel(setTable());
        peopleTable.getColumnModel().getColumn(0).setMaxWidth(50);
    }

    // baca & validasi isi form. Return null jika tidak valid.
    private Person readForm(boolean isUpdate) {
        String id = idField.getText().trim();
        String nama = namaField.getText().trim();
        String tahunText = tahunLahirField.getText().trim();

        if (id.isEmpty() || nama.isEmpty() || tahunText.isEmpty()) {
            showError("ID, Nama, dan Tahun Lahir tidak boleh kosong!");
            return null;
        }

        int tahunLahir;
        try {
            tahunLahir = Integer.parseInt(tahunText);
        } catch (NumberFormatException ex) {
            showError("Tahun lahir harus berupa angka!");
            return null;
        }

        int tahunSekarang = Year.now().getValue();
        if (tahunLahir < 1900 || tahunLahir > tahunSekarang) {
            showError("Tahun lahir harus antara 1900 dan " + tahunSekarang + "!");
            return null;
        }

        if (kategoriComboBox.getSelectedIndex() <= 0) {
            showError("Silakan pilih kategori!");
            return null;
        }
        String kategori = kategoriComboBox.getSelectedItem().toString();

        // cek ID duplikat (abaikan baris yang sedang diedit)
        for (int i = 0; i < listPerson.size(); i++) {
            if (isUpdate && i == selectedIndex) {
                continue;
            }
            if (listPerson.get(i).getId().equalsIgnoreCase(id)) {
                showError("ID \"" + id + "\" sudah dipakai!");
                return null;
            }
        }

        return new Person(id, nama, tahunLahir, kategori, skorSlider.getValue());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void insertData() {
        // ambil value dari form
        Person p = readForm(false);
        if (p == null) {
            return;
        }

        // tambahkan data ke dalam list
        listPerson.add(p);

        // update tabel
        refreshTable();

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Insert berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan");
    }

    public void updateData() {
        // ambil data dari form
        Person baru = readForm(true);
        if (baru == null) {
            return;
        }

        // ubah data person di list
        Person p = listPerson.get(selectedIndex);
        p.setId(baru.getId());
        p.setNama(baru.getNama());
        p.setTahunLahir(baru.getTahunLahir());
        p.setKategori(baru.getKategori());
        p.setSkorKinerja(baru.getSkorKinerja());

        // update tabel
        refreshTable();

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Update berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil diubah");
    }

    public void deleteData() {
        // hapus data dari list
        listPerson.remove(selectedIndex);

        // update tabel
        refreshTable();

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(this, "Data berhasil dihapus");
    }

    public void clearForm() {
        // kosongkan semua textfield, combo box, dan slider
        idField.setText("");
        namaField.setText("");
        tahunLahirField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        skorSlider.setValue(50);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // hilangkan seleksi baris tabel
        peopleTable.clearSelection();

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;
    }
}
