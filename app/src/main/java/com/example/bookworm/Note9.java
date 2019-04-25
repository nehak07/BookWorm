package com.example.bookworm;

import com.google.firebase.database.Exclude;

public class Note9 {

    private String clubdesc;
    private String date;
    private String time;
    private String documentId;


    public Note9(){
        //public empty constructor needed
    }

@Exclude
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Note9(String clubdesc, String date, String time) {
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
