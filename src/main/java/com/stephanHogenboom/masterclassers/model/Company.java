package com.stephanHogenboom.masterclassers.model;

public class Company {
    private int oid;
    private String name;

    public Company(){

    }

    public Company(int oid, String name) {
        this.oid = oid;
        this.name = name;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
