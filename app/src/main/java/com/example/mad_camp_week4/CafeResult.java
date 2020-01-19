package com.example.mad_camp_week4;

import java.io.Serializable;

public class CafeResult implements Serializable {
   private String rowId; // 행식별id
   private String tradDate; // 결제날짜
   private String tradTime; // 결제시간
   private String drinkTime; // 섭취시간
   private String goodId; // 상품id
   private String goodName; // 상품이름
   private String cafeName; // 카페이름
   private String multipleFlag; // 일괄결제 플래그

    // Constructor
    public CafeResult() {
    }

    // Getter
    public String getRowId() {
        return rowId;
    }

    public String getTradDate() {
        return tradDate;
    }

    public String getTradTime() {
        return tradTime;
    }

    public String getDrinkTime() {
        return drinkTime;
    }

    public String getGoodId() {
        return goodId;
    }

    public String getGoodName() {
        return goodName;
    }

    public String getCafeName() {
        return cafeName;
    }

    public String getMultipleFlag() {
        return multipleFlag;
    }
    // Setter

    public void setRowId(String rowId) {
        this.rowId = rowId;
    }

    public void setTradDate(String tradDate) {
        this.tradDate = tradDate;
    }

    public void setTradTime(String tradTime) {
        this.tradTime = tradTime;
    }

    public void setDrinkTime(String drinkTime) {
        this.drinkTime = drinkTime;
    }

    public void setGoodId(String goodId) {
        this.goodId = goodId;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public void setCafeName(String cafeName) {
        this.cafeName = cafeName;
    }

    public void setMultipleFlag(String multipleFlag) {
        this.multipleFlag = multipleFlag;
    }
}
