package com.example.bookworm;

public class Note8 {

    private String name;
    private String rate;
    private String url;
    private String User;
    private String author;
    private String write;


    public  Note8(){
        //empty constructor needed
    }

    public Note8(String name, String rate, String url, String user, String author, String write) {
        this.name = name;
        this.rate = rate;
        this.url = url;
        User = user;
        this.author = author;
        this.write = write;
    }

    public String getName() {
        return name;
    }

    public String getRate() {
        return rate;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return User;
    }

    public String getAuthor() {
        return author;
    }

    public String getWrite() {
        return write;
    }
}
