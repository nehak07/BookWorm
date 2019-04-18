package com.example.bookworm;

import java.net.URL;

public class Note6 {



    private String name;
    private String url;
    private String User;
    private String author;
    private String rate;
    private String write;



    public  Note6(){
        //empty constructor needed
    }

    public Note6(String author, String name, String rate, String write, String URL, String user) {
        this. author = author;
        this. name = name;
        this. rate = rate;
        this. write = write;
        this.url = URL;
        User = user;

    }

    public String getAuthor() {return author;}

    public String getName() {
        return name;
    }

    public String getURL() { return url; }

    public String getRate() {return rate;}

    public String getWrite () {return write;}

    public String getUser() {
        return User;
    }
}



