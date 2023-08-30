package com.group3.cruisebookingsystem;

public class RoomTypeDetails {
    private int index;
    private String roomType;
    private int price;

    public RoomTypeDetails(int index, String roomType, int price){
        this.index = index;
        this.roomType = roomType;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Room: " + index + " Room Type: " + roomType + " Price: " + price;
    }
}
