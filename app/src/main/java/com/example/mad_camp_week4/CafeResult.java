package com.example.mad_camp_week4;

public class CafeResult {
    private String trad_date; // 결제 날짜
    private String trad_time; // 결제 시간
    private int user_id; // 사용자 id
    private String good_id; // 상품 id
    private String good_name; // 상품 이름
    private String cafe_name;  // 카페 이름

    // Constructor
    public CafeResult() {
    }

    // Getter
    public String getTrad_date() {
        return trad_date;
    }

    public String getTrad_time() {
        return trad_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public String getGood_id() {
        return good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public String getCafe_name() {
        return cafe_name;
    }

    // Setter
    public void setTrad_date(String trad_date) {
        this.trad_date = trad_date;
    }

    public void setTrad_time(String trad_time) {
        this.trad_time = trad_time;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setGood_id(String good_id) {
        this.good_id = good_id;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }

    public void setCafe_name(String cafe_name) {
        this.cafe_name = cafe_name;
    }
}
