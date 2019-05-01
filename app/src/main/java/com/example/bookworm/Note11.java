package com.example.bookworm;

public class Note11 {


    private String genres;
    private String name;
    private String category;
    private int price;
    private String url;
    private String User;
    private String author;


    public  Note11(){
        //empty constructor needed
    }

    public Note11(String author, String genres, String name, String user, String URL, int price) {
        this. author = author;
        this. genres = genres;
        this. name = name;
        this. price = price;
        this.url = URL;
        User = user;

    }

    public String getAuthor() {return author;}

    public String getGenres() {
        return genres;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getURL() {
        return url;
    }

    public String getUser() {
        return User;
    }
}



