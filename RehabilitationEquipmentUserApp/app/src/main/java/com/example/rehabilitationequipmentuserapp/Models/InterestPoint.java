package com.example.rehabilitationequipmentuserapp.Models;

import java.util.ArrayList;

public class InterestPoint {

    String Name;
    String Data;


    public InterestPoint(){
    }

    public String getData() {
        return  this.Data;
    }

    public void setData(ArrayList<String> Data) {
        this.Data = Data.get(1);
        this.Name = Data.get(0);
    }

    public String getName() {return this.Name;}

    @Override
    public String toString() {
        return this.Data;
    }
}
