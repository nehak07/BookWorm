package com.example.bookworm;

public class FindFriends {
    public String fullname;
    public String book;

    public FindFriends(){
        //empty constructor needed
    }

    public FindFriends(String fullname, String book) {
        this.fullname = fullname;
        this.book = book;

    }

    public String getFullname()
    {
        return fullname;
    }

    public String getBook()

    {
        return book;
    }

    public void setFullname (String fullname) {
        this.fullname = fullname;
    }

    public void setBook(String book) {this.book = book;}


}
