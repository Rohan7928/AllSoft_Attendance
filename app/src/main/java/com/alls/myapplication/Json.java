package com.alls.myapplication;

class Json {
   String[] uId;
     String[] uNames;
     String[] uLocation;
   String[] uMobile;

    public String[] getuId() {
        return uId;
    }

    public void setuId(String[] uId) {
        this.uId = uId;
    }

    public String[] getuNames() {
        return uNames;
    }

    public void setuNames(String[] uNames) {
        this.uNames = uNames;
    }

    public String[] getuLocation() {
        return uLocation;
    }

    public void setuLocation(String[] uLocation) {
        this.uLocation = uLocation;
    }

    public String[] getuMobile() {
        return uMobile;
    }

    public void setuMobile(String[] uMobile) {
        this.uMobile = uMobile;
    }

    public String[] getuTime() {
        return uTime;
    }

    public void setuTime(String[] uTime) {
        this.uTime = uTime;
    }

    public String[] getuType() {
        return uType;
    }

    public void setuType(String[] uType) {
        this.uType = uType;
    }

    public String[] getuAddress() {
        return uAddress;
    }

    public void setuAddress(String[] uAddress) {
        this.uAddress = uAddress;
    }

    public String[] getuImages() {
        return uImages;
    }

    public void setuImages(String[] uImages) {
        this.uImages = uImages;
    }

    String[] uTime;
    String[] uType;
     String[] uAddress;
   String[] uImages;
    public Json(String[] uIds, String[] uNames, String[] uLocation, String[] uMobile, String[] uTime, String[] uType, String[] uAddress, String[] uImages) {
        this.uId = uIds;
        this.uNames = uNames;
        this.uLocation=uLocation;
        this.uMobile=uMobile;
        this.uTime=uTime;
        this.uType=uType;
        this.uAddress=uAddress;
        this.uImages = uImages;
    }
        public Json()
        {

        }
}
