package com.example.applicationloginusephp;

public class Movie {
    private String id ;
    private String title ;
    private String link ;
    private String info ;
    private String photolink ;
    private String type ;

    public Movie(String id, String title, String link, String info, String photolink, String type) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.info = info;
        this.photolink = photolink;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhotolink() {
        return photolink;
    }

    public void setPhotolink(String photolink) {
        this.photolink = photolink;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
