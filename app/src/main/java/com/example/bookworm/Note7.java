package com.example.bookworm;

public class Note7 {

    private String clubname;
    private String username;
    private String clubdesc;


    public  Note7(){
        //empty constructor needed
    }
    public Note7(String clubname, String username, String clubdesc, String user, boolean group) {
        this. clubname = clubname;
        this. username = username;
        this. clubdesc = clubdesc;



    }

    public String getClubname() {return clubname;}

    public String getUsername() {
        return username;
    }

    public String getClubdesc() {return clubdesc;}


}
