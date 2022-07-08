package com.example.intership_project.orderlist;

public class Model {

String itemname;
String price;
int orderquantity;
String imageurl;
    public Model() {
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public Model(String itemname, String price, int orderquantity,String imageurl) {
        this.itemname = itemname;
        this.price = price;
        this.orderquantity = orderquantity;
        this.imageurl = imageurl;
    }



    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getOrderquantity() {
        return orderquantity;
    }

    public void setOrderquantity(int orderquantity) {
        this.orderquantity = orderquantity;
    }
}
