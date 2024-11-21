package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.text.SimpleDateFormat;

public class FinancialApp {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel tableModel;
    private ArrayList<Map<String, String>> transactions;
    private JLabel incomeLabel, expenseLabel, balanceLabel;

    public FinancialApp() {
        transactions = new ArrayList<>();
        frame = new JFrame("Pencatatan Keuangan UMKM");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        // Tabel untuk menampilkan transaksi
        tableModel = new DefaultTableModel(new String[]{"Pilih", "Tanggal", "Deskripsi", "Kategori", "Jumlah"}, 0);
        table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox())); // Menambahkan checkbox pada kolom pertama
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel atas (pencarian dan tambah transaksi)
        JPanel topPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("Cari");
        JButton addButton = new JButton("Tambah Transaksi");
        JButton deleteButton = new JButton("Hapus Transaksi");
        topPanel.add(new JLabel("Cari Transaksi:"));
        topPanel.add(searchField);
        topPanel.add(searchButton);
        topPanel.add(addButton);
        topPanel.add(deleteButton);
        frame.add(topPanel, BorderLayout.NORTH);

        // Panel bawah (ringkasan keuangan)
        JPanel summaryPanel = new JPanel();
        incomeLabel = new JLabel("Pemasukan: 0");
        expenseLabel = new JLabel("Pengeluaran: 0");
        balanceLabel = new JLabel("Saldo: 0");
        summaryPanel.add(incomeLabel);
        summaryPanel.add(expenseLabel);
        summaryPanel.add(balanceLabel);
        frame.add(summaryPanel, BorderLayout.SOUTH);

        // Load data dari file JSON
        loadTransactions();
        updateSummary();

        // Event untuk tombol cari
        searchButton.addActionListener(e -> filterTransactions(searchField.getText()));

        // Event untuk tombol tambah transaksi
        addButton.addActionListener(e -> openAddTransactionDialog());

        // Event untuk tombol hapus transaksi
        deleteButton.addActionListener(e -> deleteSelectedTransactions());

        // Tampilkan frame
        frame.setVisible(true);
    }

    // Fungsi untuk menampilkan dialog tambah transaksi
    private void openAddTransactionDialog() {
        JDialog dialog = new JDialog(frame, "Tambah Transaksi", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField dateField = new JTextField(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        JTextField descField = new JTextField();
        JComboBox<String> categoryBox = new JComboBox<>(new String[]{"Pemasukan", "Pengeluaran"});
        JTextField amountField = new JTextField();
        JButton saveButton = new JButton("Simpan");

        dialog.add(new JLabel("Tanggal (YYYY-MM-DD):"));
        dialog.add(dateField);
        dialog.add(new JLabel("Deskripsi:"));
        dialog.add(descField);
        dialog.add(new JLabel("Kategori:"));
        dialog.add(categoryBox);
        dialog.add(new JLabel("Jumlah:"));
        dialog.add(amountField);
        dialog.add(new JLabel());
        dialog.add(saveButton);

        saveButton.addActionListener(e -> {
            try {
                String date = dateField.getText();
                String desc = descField.getText();
                String category = categoryBox.getSelectedItem().toString();
                double amount = Double.parseDouble(amountField.getText());

                if (date.isEmpty() || desc.isEmpty() || amount <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Semua field harus diisi dengan benar!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Map<String, String> transaction = new HashMap<>();
                    transaction.put("date", date);
                    transaction.put("desc", desc);
                    transaction.put("category", category);
                    transaction.put("amount", String.valueOf(amount));
                    transactions.add(transaction);
                    saveTransactions();
                    refreshTable();
                    updateSummary();
                    dialog.dispose();
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Jumlah harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.setVisible(true);
    }

    // Fungsi untuk menyegarkan tabel
    private void refreshTable() {
        tableModel.setRowCount(0);  // Reset table
        for (Map<String, String> transaction : transactions) {
            tableModel.addRow(new Object[]{
                    false, // Checkbox untuk memilih transaksi
                    transaction.get("date"),
                    transaction.get("desc"),
                    transaction.get("category"),
                    "Rp" + formatCurrency(transaction.get("amount"))
            });
        }
    }

    // Fungsi untuk memperbarui ringkasan keuangan
    private void updateSummary() {
        double totalIncome = 0, totalExpense = 0;
        for (Map<String, String> transaction : transactions) {
            double amount = Double.parseDouble(transaction.get("amount"));
            if (transaction.get("category").equals("Pemasukan")) {
                totalIncome += amount;
            } else {
                totalExpense += amount;
            }
        }
        incomeLabel.setText("Pemasukan: " + "Rp" + formatCurrency(String.valueOf(totalIncome)));
        expenseLabel.setText("Pengeluaran: " + "Rp" + formatCurrency(String.valueOf(totalExpense)));
        balanceLabel.setText("Saldo: " + "Rp" + formatCurrency(String.valueOf(totalIncome - totalExpense)));
    }

    // Fungsi untuk menyaring transaksi berdasarkan kata kunci
    private void filterTransactions(String keyword) {
        tableModel.setRowCount(0);
        for (Map<String, String> transaction : transactions) {
            if (transaction.get("desc").toLowerCase().contains(keyword.toLowerCase())) {
                tableModel.addRow(new Object[]{
                        false,
                        transaction.get("date"),
                        transaction.get("desc"),
                        transaction.get("category"),
                        "Rp" + formatCurrency(transaction.get("amount"))
                });
            }
        }
    }

    // Fungsi untuk menyimpan data ke file JSON
    private void saveTransactions() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.json"))) {
            JSONArray jsonArray = new JSONArray();
            for (Map<String, String> transaction : transactions) {
                JSONObject jsonObject = new JSONObject(transaction);
                jsonArray.put(jsonObject);
            }
            writer.write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fungsi untuk memuat data dari file JSON
    private void loadTransactions() {
        File file = new File("transactions.json");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder json = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    json.append(line);
                }
                JSONArray jsonArray = new JSONArray(json.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Map<String, String> transaction = new HashMap<>();
                    transaction.put("date", jsonObject.getString("date"));
                    transaction.put("desc", jsonObject.getString("desc"));
                    transaction.put("category", jsonObject.getString("category"));
                    transaction.put("amount", jsonObject.getString("amount"));
                    transactions.add(transaction);
                }
                refreshTable();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Fungsi untuk menghapus transaksi yang dipilih
    private void deleteSelectedTransactions() {
        List<Integer> rowsToDelete = new ArrayList<>();

        // Looping untuk memeriksa setiap baris di tabel
        for (int row = 0; row < table.getRowCount(); row++) {
            // Mengecek apakah checkbox pada baris tersebut dicentang
            if (table.getValueAt(row, 0) instanceof Boolean && (Boolean) table.getValueAt(row, 0)) {
                rowsToDelete.add(row); // Menyimpan index baris yang dicentang
            }
        }

        // Menghapus transaksi yang dipilih dari daftar transaksi
        // Loop dari belakang agar penghapusan indeks tidak mengubah posisi baris yang tersisa
        for (int i = rowsToDelete.size() - 1; i >= 0; i--) {
            int row = rowsToDelete.get(i);
            transactions.remove(row); // Menghapus transaksi pada index yang sesuai
        }

        // Menyimpan daftar transaksi yang telah diperbarui ke file JSON
        saveTransactions();

        // Refresh tabel dan ringkasan keuangan
        refreshTable();
        updateSummary();
    }

    // Fungsi untuk memformat angka sebagai mata uang
    private String formatCurrency(String amount) {
        double number = Double.parseDouble(amount);
        return String.format("%,.0f", number);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(FinancialApp::new);
    }
}
