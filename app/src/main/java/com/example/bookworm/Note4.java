package com.example.bookworm;

        import java.security.acl.Group;

public class Note4 {

    private String clubname;
    private String username;
    private String Admin;
    private boolean group = true;



    public  Note4(){
        //empty constructor needed
    }
    public Note4(String clubname, String username, String user, boolean group) {
        this. clubname = clubname;
        this. username = username;
        Admin = user;
        group = group;


    }

    public String getClubname() {return clubname;}

    public String getUsername() {
        return username;
    }

    public String getAdmin() {
        return Admin;
    }

    public boolean getGroup() {return group;
    }

}
