package com.example.mad_camp_week4;

public class GoodsItem {
    private String goodId;
    private String goodName;
    private String cafeName;
    private String price;
    private String caffeineContent;
    private int imgSrc;

    // Constructor
    public GoodsItem() {
    }

    public GoodsItem(String goodId, String goodName, String cafeName, String price, String caffeineContent, int imgSrc) {
        this.goodId = goodId;
        this.goodName = goodName;
        this.cafeName = cafeName;
        this.price = price;
        this.caffeineContent = caffeineContent;
        this.imgSrc = imgSrc;
    }

    // Getter
    public String getGoodId() {
        return goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getCafeName() {
        return cafeName;
    }

    public String getPrice() {
        return price;
    }

    public String getCaffeineContent() {
        return caffeineContent;
    }

    public int getImgSrc() {
        return imgSrc;
    }

    // Setter

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCaffeineContent(String caffeineContent) {
        this.caffeineContent = caffeineContent;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }
}
