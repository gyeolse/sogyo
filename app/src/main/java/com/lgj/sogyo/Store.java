package com.lgj.sogyo;

public class Store {
    public int StoreNo;
    public String BizName;
    public String upperCategory;
    public String lowerCategory;
    public String address;
    public String floor;
    public double longitude;
    public double latitude;
    public String IsOpenStr;
    public String openYear;
    public String closeYear;
    public int Sales;
    public int IsFrancise;
    public int bizZone_localNo;


    //상점이름, 카테고리, 층수, 운영여부, 개점년도, 폐업년도,
    Store(String BizName, String upperCategory, String floor, String IsOpenStr, String openYear, String closeYear){
        this.BizName = BizName;
        this.upperCategory = upperCategory;
        this.floor = floor;
        this.IsOpenStr = IsOpenStr;
        this.openYear = openYear;
        this.closeYear = closeYear;
    }

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

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
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

    public String getIsOpenStr() {
        return IsOpenStr;
    }

    public void setIsOpenStr(String isOpenStr) {
        IsOpenStr = isOpenStr;
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
