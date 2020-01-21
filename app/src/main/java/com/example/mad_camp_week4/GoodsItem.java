package com.example.mad_camp_week4;

import android.os.Parcel;
import android.os.Parcelable;

public class GoodsItem implements Parcelable {
    private String goodId;
    private String goodName;
    private String cafeName;
    private String price;
    private String caffeineContent;
    private int imgSrc;
    private boolean isFavorite;
    private boolean isChecked;

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
        this.isFavorite = false;
        this.isChecked = false;
    }

    protected GoodsItem(Parcel in) {
        goodId = in.readString();
        goodName = in.readString();
        cafeName = in.readString();
        price = in.readString();
        caffeineContent = in.readString();
        imgSrc = in.readInt();
    }

    public static final Creator<GoodsItem> CREATOR = new Creator<GoodsItem>() {
        @Override
        public GoodsItem createFromParcel(Parcel in) {
            return new GoodsItem(in);
        }

        @Override
        public GoodsItem[] newArray(int size) {
            return new GoodsItem[size];
        }
    };

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

    public boolean getIsFavorite(){ return isFavorite; }

    public boolean getIsChecked(){ return isChecked; }


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

    public void setIsFavorite(boolean isFavorite) { this.isFavorite = isFavorite; }

    public void setIsChecked(boolean isChecked) { this.isChecked = isChecked; }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(goodId);
        parcel.writeString(goodName);
        parcel.writeString(cafeName);
        parcel.writeString(price);
        parcel.writeString(caffeineContent);
        parcel.writeInt(imgSrc);
    }
}
