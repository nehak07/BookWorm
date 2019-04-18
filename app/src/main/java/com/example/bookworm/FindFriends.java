package com.example.bookworm;

public class FindFriends {
    public String fullname;
    public String gender;

    public FindFriends(){
        //empty constructor needed
    }

    public FindFriends(String fullname, String gender) {
        this.fullname = fullname;
        this.gender = gender;
    }

    public String getFullname() {
        return fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
