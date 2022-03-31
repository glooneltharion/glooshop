package com.neltharion.webshop.models;

public enum Type {
    CAS("Clothes and Shoes"), ELE("Electronics"), BAS("Beverages and Snacks");
    String value;

    Type(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return  value;
    }
}
