package com.lgj.sogyo;

public class Francise {
    String upperCategory;
    String fran_name;
    int total_money;

    Francise (String upperCategory, String fran_name, int total_money){
        this.fran_name = fran_name;
        this.total_money = total_money;
        this.upperCategory= upperCategory;
    }

    public String getUpperCategory() {
        return upperCategory;
    }

    public void setUpperCategory(String upperCategory) {
        this.upperCategory = upperCategory;
    }

    public String getFran_name() {
        return fran_name;
    }

    public void setFran_name(String fran_name) {
        this.fran_name = fran_name;
    }

    public int getTotal_money() {
        return total_money;
    }

    public void setTotal_money(int total_money) {
        this.total_money = total_money;
    }
}
