package com.grapper;

import java.math.BigDecimal;

public class Item {
    private String productname;
    private String description;
    private String imageLink;
    private BigDecimal price;
    private double rating;
    private String merchantname;

    public String getproductname(){
        return productname;
    }

    public void setproductname(String Productname){
        this.productname = Productname;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String Description){
        this.description = Description;
    }

    public String getImageLink(){
        return imageLink;
    }

    public void setImageLink(String ImageLink){
        this.imageLink = ImageLink;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public void setPrice(BigDecimal Price){
        this.price = Price;
    }

    public double getRating(){
        return rating;
    }

    public void setRating(Double Rating){
        this.rating = Rating;
    }

    public String getMerchantName(){
        return merchantname;
    }

    public void setMerchantName(String MerchantName){
        this.merchantname = MerchantName;
    }

}
