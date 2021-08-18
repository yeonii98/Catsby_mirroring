package org.techtown.catsby.QRcode.data.model;

public class Bowl {

    private String info;
    private String name;
    private String address;

    public Bowl(String info, String name, String address) {
        this.info = info;
        this.name = name;
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
