package com.ffc.emnet;

public class Mycontacts {
String name,number;

    public Mycontacts() {

    }

    public void clear(){
        this.name = null;
        this.number = null;
    }

    public Mycontacts(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
