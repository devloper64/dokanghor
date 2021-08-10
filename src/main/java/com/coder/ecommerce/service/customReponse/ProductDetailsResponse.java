package com.coder.ecommerce.service.customReponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A DTO for the {@link com.coder.ecommerce.domain.ProductDetails} entity.
 */
public class ProductDetailsResponse implements Serializable {

    private Long id;

    private String brand;

    private ArrayList<String> colors;

    private String gender;

    private ArrayList<String> styles;

    private String size_mesaurments;

    private ArrayList<String> size_details;

    public ProductDetailsResponse(){

    }

    public ProductDetailsResponse(Long id, String brand, ArrayList<String> color, String gender, ArrayList<String> style, String size_mesaurments, ArrayList<String> size_details) {
        this.id = id;
        this.brand = brand;
        this.colors = color;
        this.gender = gender;
        this.styles = style;
        this.size_mesaurments = size_mesaurments;
        this.size_details = size_details;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ArrayList<String> getColors() {
        return colors;
    }

    public void setColors(ArrayList<String> colors) {
        this.colors = colors;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public ArrayList<String> getStyles() {
        return styles;
    }

    public void setStyles(ArrayList<String> styles) {
        this.styles = styles;
    }

    public String getSize_mesaurments() {
        return size_mesaurments;
    }

    public void setSize_mesaurments(String size_mesaurments) {
        this.size_mesaurments = size_mesaurments;
    }

    public ArrayList<String> getSize_details() {
        return size_details;
    }

    public void setSize_details(ArrayList<String> size_details) {
        this.size_details = size_details;
    }

    @Override
    public String toString() {
        return "ProductDetailsResponse{" +
            "id=" + id +
            ", brand='" + brand + '\'' +
            ", color=" + colors +
            ", gender='" + gender + '\'' +
            ", style=" + styles +
            ", size_mesaurments='" + size_mesaurments + '\'' +
            ", size_details=" + size_details +
            '}';
    }
}
