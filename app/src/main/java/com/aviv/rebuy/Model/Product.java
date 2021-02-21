package com.aviv.rebuy.Model;

public class Product {


    private int id;
    private String name;
    private String description;
    private Double price;
    private String condition;



    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getPrice() {
        return price;
    }

    public String getCondition() {
        return condition;
    }

    public int getId() {
        return id;
    }



}
