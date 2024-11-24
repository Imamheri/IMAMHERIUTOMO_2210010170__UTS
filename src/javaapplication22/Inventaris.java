package javaapplication22;

import java.util.ArrayList;
import java.util.List;

public class Inventaris {
    private ArrayList<Barang> daftarBarang = new ArrayList<>();

    public void tambahBarang(Barang barang) {
        daftarBarang.add(barang);
    }

    public void ubahBarang(int id, Barang barangBaru) {
        for (int i = 0; i < daftarBarang.size(); i++) {
            if (daftarBarang.get(i).getId() == id) {
                daftarBarang.set(i, barangBaru);
                return;
            }
        }
    }

    public void hapusBarang(int id) {
        daftarBarang.removeIf(barang -> barang.getId() == id);
    }

    public ArrayList<Barang> getBarang() {
        return daftarBarang;
    }

    List<Barang> getDaftarBarang() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
