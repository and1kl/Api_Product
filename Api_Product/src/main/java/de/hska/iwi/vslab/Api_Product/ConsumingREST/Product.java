package de.hska.iwi.vslab.Api_Product.ConsumingREST;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Product {

    private int id = 999; // this value is determined by the core service
    private String name;
    private double price;
    private String details;
    private int categoryId;

    // the framework needs a default constructor for whatever reason..
    private Product() {
        name = "unknown";
        price = 0.0;
        details = "";
        categoryId = 0;
    }

    public Product(String name, double price, int categoryId, String details) {
        this.name = name;
        this.price = price;
        this.details = details;
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return String.format("Product[id='%d', name='%s', price=%e, categoryId=%d, details='%s']", id, name, price, categoryId,
        details);
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @return the details
     */
    public String getDetails() {
        return details;
    }
    /**
     * @return the categoryIdFromProduct
     */
    public int getCategoryId() {
        return categoryId;
    }

    /**
     * @param categoryId the categoryId to set
     */
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    /**
     * @param details the details to set
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

}