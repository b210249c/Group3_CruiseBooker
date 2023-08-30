package com.group3.cruisebookingsystem;

public class Room {
    private int roomNum;
    private int adultNum;
    private int kidNum;

    public Room(int roomNum, int adultNum, int kidNum){
        this.roomNum = roomNum;
        this.adultNum = adultNum;
        this.kidNum = kidNum;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getAdultNum() {
        return adultNum;
    }

    public int getKidNum() {
        return kidNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public void setAdultNum(int adultNum) {
        this.adultNum = adultNum;
    }

    public void setKidNum(int kidNum) {
        this.kidNum = kidNum;
    }

    @Override
    public String toString() {
        return "Room " + roomNum + " Adult: " + adultNum + " Kid: " + kidNum;
    }
}
