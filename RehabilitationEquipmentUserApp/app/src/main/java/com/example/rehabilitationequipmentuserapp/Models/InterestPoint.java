package com.example.rehabilitationequipmentuserapp.Models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.HashMap;

public class InterestPoint {

    String Name;
    String Data;
    Bitmap Image;

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
    public Bitmap getImage() {return this.Image;}

    public void setImage(Bitmap image) {this.Image = image;}

    @Override
    public String toString() {
        return this.Data;
    }
}
