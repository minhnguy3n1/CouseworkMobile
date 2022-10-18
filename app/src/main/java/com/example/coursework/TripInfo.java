package com.example.coursework;

import java.io.Serializable;

public class TripInfo implements Serializable {

    private int id;
    private String name;
    private String destination;
    private String date;
    private String assessment;

//    public TripInfo(int id, String name, String destination, String date, String assessment){
//        this.id = id;
//        this.name = name;
//        this.destination = destination;
//        this.date = date;
//        this.assessment = assessment;
//    }


    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getDestination(){
        return  destination;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getAssessment(){
        return assessment;
    }

    public void setAssessment(String assessment){
        this.assessment = assessment;
    }

}
