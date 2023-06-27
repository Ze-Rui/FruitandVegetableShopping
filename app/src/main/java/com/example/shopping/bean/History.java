package com.example.shopping.bean;

public class History {
    int shopid;
    String product_id;
    String time;
    String price;
    String name;
    String figure;
    int seller_id;
    int state;
    int buyer_id;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    @Override
    public String toString() {
        return "History{" +
                "shopid=" + shopid +
                ", product_id='" + product_id + '\'' +
                ", time='" + time + '\'' +
                ", price='" + price + '\'' +
                ", name='" + name + '\'' +
                ", seller_id=" + seller_id +
                ", buyer_id=" + buyer_id +
                '}';
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public int getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(int buyer_id) {
        this.buyer_id = buyer_id;
    }
}
