package com.lgj.sogyo;

public class Francise_item {
    public String ctg;
    public String store;
    public String cost;

    public Francise_item(String ctg, String store, String cost){
        this.ctg = ctg;
        this.store = store;
        this.cost = cost;
    }

    public void setCtg(String ctg) {
        this.ctg = ctg;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getCtg() {
        return ctg;
    }

    public String getStore() {
        return store;
    }

    public String getCost() {
        return cost;
    }
}
