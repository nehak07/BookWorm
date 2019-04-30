package com.example.bookworm;

public class Note10 {

    private String memberID;
    private String memberName;


    public  Note10(){
        //empty constructor needed
    }


    public Note10(String memberID, String memberName) {
        this.memberID = memberID;
        this.memberName = memberName;
    }

    public String getMemberID() {
        return memberID;
    }

    public String getMemberName() {
        return memberName;
    }
}
