package com.example.bookworm;

import java.security.acl.Group;

public class Note3 {

    private String clubname;
    private String username;
    private String clubdesc;
    private String Admin;
    private boolean group;



    public  Note3(){
        //empty constructor needed
    }
    public Note3(String clubname, String username, String clubdesc, String user, boolean group) {
        this. clubname = clubname;
        this. username = username;
        this. clubdesc = clubdesc;
        Admin = user;
        group = group;


    }

    public String getClubname() {return clubname;}

    public String getUsername() {
        return username;
    }

    public String getClubdesc() {return clubdesc;}

    public String getAdmin() {
        return Admin;
    }

    public boolean getGroup() {return group;
    }

}
