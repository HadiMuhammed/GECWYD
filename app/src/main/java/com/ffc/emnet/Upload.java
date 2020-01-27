package com.ffc.emnet;

public class Upload {
    private String imgName;
    private String imgUri;
    private String phonenumber;

    public Upload() {
    }

    public Upload(String imgName, String imgUri,String phonenumber) {
        if(imgName.trim().equals(""))
        {
            imgName = "No Name";
        }
        this.imgName = imgName;
        this.imgUri = imgUri;
        this.phonenumber=phonenumber;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
