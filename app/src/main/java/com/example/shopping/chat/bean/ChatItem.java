package com.example.shopping.chat.bean;

import java.io.Serializable;

/**
 * 消息界面的一个聊天item
 */
public class ChatItem implements Serializable {
    private int seller_id;
    private String seller_name;
    private String product_img;
    private String productId;

    public ChatItem(){}

    public ChatItem(int seller_id, String seller_name, String product_img, String productId) {
        this.seller_id = seller_id;
        this.seller_name = seller_name;
        this.product_img = product_img;
        this.productId = productId;
    }

    public int getSeller_id() {
        return seller_id;
    }

    public void setSeller_id(int seller_id) {
        this.seller_id = seller_id;
    }

    public String getSeller_name() {
        return seller_name;
    }

    public void setSeller_name(String seller_name) {
        this.seller_name = seller_name;
    }

    public String getProduct_img() {
        return product_img;
    }

    public void setProduct_img(String product_img) {
        this.product_img = product_img;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "ChatItem{" +
                "seller_id=" + seller_id +
                ", seller_name='" + seller_name + '\'' +
                ", product_img='" + product_img + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}