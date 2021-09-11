package com.coder.ecommerce.service.helper;

public class InvoiceItem {

    private String item;
    private String description;
    private int quantity;
    private double unitCost;
    private double total;

    public InvoiceItem(String item, String description, int quantity, double unitCost, double total) {
        this.item = item;
        this.description = description;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.total = total;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
