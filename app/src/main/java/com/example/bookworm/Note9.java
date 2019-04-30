package com.example.bookworm;

import com.google.firebase.database.Exclude;

public class Note9 {

    private String clubdesc;
    private String date;
    private String time;


    public Note9(){
        //public empty constructor needed
    }

    public Note9(String clubdesc, String date, String time, String noData) {
        this.clubdesc = clubdesc;
        this.date = date;
        this.time = time;
    }

    public String getClubdesc() {
        return clubdesc;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

}
