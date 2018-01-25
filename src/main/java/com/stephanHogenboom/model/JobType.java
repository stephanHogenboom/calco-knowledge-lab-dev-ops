package com.stephanHogenboom.model;

public class JobType {

    private int oid;
    private String name;



    public JobType(){

    }

    public JobType(int oid, String name) {
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
