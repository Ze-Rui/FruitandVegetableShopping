package com.example.shopping.home.bean;

import java.io.Serializable;

/**
 * 商品对象
 */
public class GoodsBean implements Serializable {
    // 价格
    private String cover_price;
    // 图片地址
    private String figure;
    // 名称
    private String name;
    // 名称
    private String num;
    // 产品id
    private String product_id;


    public GoodsBean() {
    }

    public GoodsBean(String cover_price, String figure, String name, String product_id, String num) {
        this.cover_price = cover_price;
        this.figure = figure;
        this.name = name;
        this.num = num;
        this.product_id = product_id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", product_id='" + product_id + '\'' +
                '}';
    }
}
