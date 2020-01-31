package com.ffc.emnet;

public class Upload2 {


    private String imgname;
    private String imgurl;
    private String videoname;
    private String videourl;
    private String phonenumber;

    public Upload2(){
    }


    public Upload2(String imgname, String imgurl, String videoname, String videourl, String phonenumber) {
        if(imgname.trim().equals(""))
        {
            imgname = "No Name";
        }
        if(videoname.trim().equals(""))
        {
            videoname="No Name";
        }
        this.imgname = imgname;
        this.imgurl = imgurl;
        this.videoname = videoname;
        this.videourl = videourl;
        this.phonenumber = phonenumber;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getVideoname() {
        return videoname;
    }

    public void setVideoname(String videoname) {
        this.videoname = videoname;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
