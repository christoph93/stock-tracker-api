package com.stoncks.document;

import org.springframework.data.annotation.Id;

import java.util.Date;

public class Comments {

    @Id
    private String id;

    private String name;
    private String email;
    private String movie_id;
    private String text;
    private Date date;

    public Comments(String name, String email, String movie_id, String text, Date date) {
        this.name = name;
        this.email = email;
        this.movie_id = movie_id;
        this.text = text;
        this.date = date;
    }

    public String getId() {
        return id;
    }

     public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(String movie_id) {
        this.movie_id = movie_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
