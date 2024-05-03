package com.example.rehabilitationequipmentuserapp.Models;

public class InterestPoint {

    String Data;

    public InterestPoint(){
    }

    public String getData() {
        return  this.Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }

    @Override
    public String toString() {
        return this.Data;
    }
}
