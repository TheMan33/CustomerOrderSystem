/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ws.warkworth;

/**
 *
 * @author Ethan
 */
public class Item {
    private String description;
    private String barcode;
    private int quantity;
    
    public Item(String description, String barcode, int quantity){
        this.description = description;
        this.barcode = barcode;
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public String getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return barcode + " " + description + " x" + quantity;
    }
    
    
}
