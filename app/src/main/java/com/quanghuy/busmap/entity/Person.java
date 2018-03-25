package com.quanghuy.busmap.entity;

/**
 * Created by Huy on 3/25/2018.
 */
public class Person {
    private String hoTen;
    private String diaChi;
    private String tinhTrang;

    public Person() {
    }

    ;

    public Person(String hoTen, String diaChi, String tinhTrang) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.tinhTrang = tinhTrang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }


}
