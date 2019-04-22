package com.example.bookworm;

public class messages
{
    public String date, message, time, type, from;

    public messages (){

    }

    public messages(String date, String message, String time, String type, String from) {
        this.date = date;
        this.message = message;
        this.time = time;
        this.type = type;
        this.from = from;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
