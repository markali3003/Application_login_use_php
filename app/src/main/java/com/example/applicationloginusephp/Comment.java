package com.example.applicationloginusephp;

public class Comment {
    private String id ;
    private String name ;
    private String text;
    private String date_time ;
    private String movie_id ;

    public Comment(String id, String name, String text, String date_time, String movie_id) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.date_time = date_time;
        this.movie_id = movie_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }
}
