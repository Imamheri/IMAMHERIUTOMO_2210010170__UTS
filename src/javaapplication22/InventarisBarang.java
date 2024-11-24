/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication22;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
/**
 *
 * @author acer
 */
public class InventarisBarang extends javax.swing.JFrame {
 private Inventaris inventaris = new Inventaris();
    private JList<Barang> listBarang;
    private DefaultListModel<Barang> listModel;
    private JLabel lblNama, lblKategori, lblJumlah, lblHarga;
    


    /**
     * Creates new form InventarisBarang
     */
    public InventarisBarang() {
        
        initComponents();
        listModel = new DefaultListModel<>();
        listBarang = new JList<>(listModel);
        jScrollPane1.setViewportView(listBarang);
        jScrollPane1.setViewportView(listBarang);
        


   
        listBarang.addListSelectionListener(e -> tampilkanDetailBarang());
        btnTambah.addActionListener(e -> tambahBarang());
        btnUbah.addActionListener(e -> ubahBarang());
        btnHapus.addActionListener(e -> hapusBarang());
        btnSimpan.addActionListener(e -> simpanKeFile());

    }
 private void tampilkanDetailBarang() {
   Barang selectedBarang = listBarang.getSelectedValue();
    if (selectedBarang != null) {
        jLabel3.setText("<html><b>NAMA:</b> " + selectedBarang.getNama() + "</html>");
        jLabel4.setText("<html><b>KATEGORI:</b> " + selectedBarang.getKategori() + "</html>");
        jLabel5.setText("<html><b>JUMLAH:</b> " + selectedBarang.getJumlah() + "</html>");
        jLabel6.setText("<html><b>HARGA:</b> Rp " + String.format("%.2f", selectedBarang.getHarga()) + "</html>");
    } else {
        jLabel3.setText("<html><b>NAMA:</b> -</html>");
        jLabel4.setText("<html><b>KATEGORI:</b> -</html>");
        jLabel5.setText("<html><b>JUMLAH:</b> -</html>");
        jLabel6.setText("<html><b>HARGA:</b> -</html>");
    }
    }

    private void tambahBarang() {
        JTextField tfNama = new JTextField();
        JTextField tfKategori = new JTextField();
        JTextField tfJumlah = new JTextField();
        JTextField tfHarga = new JTextField();

        Object[] input = {
            "Nama:", tfNama,
            "Kategori:", tfKategori,
            "Jumlah:", tfJumlah,
            "Harga:", tfHarga
        };

        int option = JOptionPane.showConfirmDialog(this, input, "Tambah Barang", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                int id = listModel.getSize() + 1;
                String nama = tfNama.getText();
                String kategori = tfKategori.getText();
                int jumlah = Integer.parseInt(tfJumlah.getText());
                double harga = Double.parseDouble(tfHarga.getText());
                Barang barang = new Barang(id, nama, kategori, jumlah, harga);
                inventaris.tambahBarang(barang);
                listModel.addElement(barang);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void ubahBarang() {
        Barang selectedBarang = listBarang.getSelectedValue();
        if (selectedBarang == null) {
            JOptionPane.showMessageDialog(this, "Pilih barang untuk diubah!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField tfNama = new JTextField(selectedBarang.getNama());
        JTextField tfKategori = new JTextField(selectedBarang.getKategori());
        JTextField tfJumlah = new JTextField(String.valueOf(selectedBarang.getJumlah()));
        JTextField tfHarga = new JTextField(String.valueOf(selectedBarang.getHarga()));

        Object[] input = {
            "Nama:", tfNama,
            "Kategori:", tfKategori,
            "Jumlah:", tfJumlah,
            "Harga:", tfHarga
        };

        int option = JOptionPane.showConfirmDialog(this, input, "Ubah Barang", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                selectedBarang.setNama(tfNama.getText());
                selectedBarang.setKategori(tfKategori.getText());
                selectedBarang.setJumlah(Integer.parseInt(tfJumlah.getText()));
                selectedBarang.setHarga(Double.parseDouble(tfHarga.getText()));
                listBarang.repaint();
                tampilkanDetailBarang();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hapusBarang() {
        Barang selectedBarang = listBarang.getSelectedValue();
        if (selectedBarang == null) {
            JOptionPane.showMessageDialog(this, "Pilih barang untuk dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        inventaris.hapusBarang(selectedBarang.getId());
        listModel.removeElement(selectedBarang);
        tampilkanDetailBarang();
    }

  private void perbaruiListBarang() {
    listModel.clear();
    List<Barang> semuaBarang = inventaris.getDaftarBarang();
    for (int i = 0; i < semuaBarang.size(); i++) {
        Barang barang = semuaBarang.get(i);
        listModel.addElement((i + 1) + ". " + barang.getNama());
    }
}

    
    private void simpanKeFile() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                for (int i = 0; i < listModel.getSize(); i++) {
                    Barang barang = listModel.getElementAt(i);
                    writer.write(String.format("ID: %d, Nama: %s, Kategori: %s, Jumlah: %d, Harga: Rp %.2f%n",
                            barang.getId(), barang.getNama(), barang.getKategori(),
                            barang.getJumlah(), barang.getHarga()));
                }
                JOptionPane.showMessageDialog(this, "Data berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat menyimpan file.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnUbah = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 27, 176));
        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        jPanel1.setForeground(new java.awt.Color(0, 0, 255));

        jScrollPane1.setViewportView(jList1);

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 14)); // NOI18N
        jLabel2.setText("Daftar Barang :");

        jLabel3.setText("NAMA :");

        jLabel4.setText("KATEGORI :");

        jLabel5.setText("JUMLAH :");

        jLabel6.setText("HARGA :");

        jLabel7.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel7.setText("Detail Barang");

        btnTambah.setText("TAMBAH");

        btnUbah.setText("UBAH");
        btnUbah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahActionPerformed(evt);
            }
        });

        btnHapus.setText("HAPUS");

        btnSimpan.setText("SIMPAN");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel8.setText("INVENTARIS BARANG");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(315, 315, 315))
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnTambah)
                                    .addGap(58, 58, 58)
                                    .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(37, 37, 37)
                                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jLabel2))
                .addGap(19, 19, 19))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(244, 244, 244)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)
                        .addGap(88, 88, 88)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUbah, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnUbahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnUbahActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InventarisBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InventarisBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InventarisBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InventarisBarang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new InventarisBarang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnUbah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
