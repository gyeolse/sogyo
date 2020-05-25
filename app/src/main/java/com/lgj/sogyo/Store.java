package com.lgj.sogyo;

public class Store {

    private int StoreNo;
    private String BizName;
    private String upperCategory;
    private String lowerCategory;
    private String address;
    private int floor;
    private double longitude;
    private double latitude;
    private int IsOpen;
    private String openYear;
    private String closeYear;
    private int Sales;
    private int IsFrancise;
    private int bizZone_localNo;

    public int getStoreNo() {
        return StoreNo;
    }

    public void setStoreNo(int storeNo) {
        StoreNo = storeNo;
    }

    public String getBizName() {
        return BizName;
    }

    public void setBizName(String bizName) {
        BizName = bizName;
    }

    public String getUpperCategory() {
        return upperCategory;
    }

    public void setUpperCategory(String upperCategory) {
        this.upperCategory = upperCategory;
    }

    public String getLowerCategory() {
        return lowerCategory;
    }

    public void setLowerCategory(String lowerCategory) {
        this.lowerCategory = lowerCategory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getIsOpen() {
        return IsOpen;
    }

    public void setIsOpen(int isOpen) {
        IsOpen = isOpen;
    }

    public String getOpenYear() {
        return openYear;
    }

    public void setOpenYear(String openYear) {
        this.openYear = openYear;
    }

    public String getCloseYear() {
        return closeYear;
    }

    public void setCloseYear(String closeYear) {
        this.closeYear = closeYear;
    }

    public int getSales() {
        return Sales;
    }

    public void setSales(int sales) {
        Sales = sales;
    }

    public int getIsFrancise() {
        return IsFrancise;
    }

    public void setIsFrancise(int isFrancise) {
        IsFrancise = isFrancise;
    }

    public int getBizZone_localNo() {
        return bizZone_localNo;
    }

    public void setBizZone_localNo(int bizZone_localNo) {
        this.bizZone_localNo = bizZone_localNo;
    }
}
