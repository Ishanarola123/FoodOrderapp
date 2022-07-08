package com.example.intership_project.recviewpopular.Dynamic;

public class model {
    double FoodPrice;
    String Foodname,ImageUrl;

    public model() {
    }

    public model(double foodPrice, String foodName, String imageUrl) {
        FoodPrice = foodPrice;
        Foodname = foodName;
        ImageUrl = imageUrl;
    }

    public double getFoodPrice() {
        return FoodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        FoodPrice = foodPrice;
    }

    public String getFoodname() {
        return Foodname;
    }

    public void setFoodname(String foodname) {
        Foodname = foodname;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
