package com.example.etsupdate;

public class PendingModel {

    private String Booking_Type;
    private String From_Location;
    private String To_Location;
    private String Booking_Date_Time;

    public  PendingModel(){

    }

    public PendingModel(String booking_Type, String from_Location, String to_Location, String booking_Date_Time) {

        Booking_Type = booking_Type;
        From_Location = from_Location;
        To_Location = to_Location;
        Booking_Date_Time = booking_Date_Time;

    }

    public String getBooking_Type() {
        return Booking_Type;
    }

    public void setBooking_Type(String booking_Type) {
        Booking_Type = booking_Type;
    }

    public String getFrom_Location() {
        return From_Location;
    }

    public void setFrom_Location(String from_Location) {
        From_Location = from_Location;
    }

    public String getTo_Location() {
        return To_Location;
    }

    public void setTo_Location(String to_Location) {
        To_Location = to_Location;
    }

    public String getBooking_Date_Time() {
        return Booking_Date_Time;
    }

    public void setBooking_Date_Time(String booking_Date_Time) {
        Booking_Date_Time = booking_Date_Time;
    }
}
