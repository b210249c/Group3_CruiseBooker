package com.group3.cruisebookingsystem;

public class DeckDetails {
    private int index;
    private int deckLevel;
    private String roomNumber;

    public DeckDetails(int index, int deckLevel, String roomNumber){
        this.index = index;
        this.deckLevel = deckLevel;
        this.roomNumber = roomNumber;
    }

    public int getIndex() {
        return index;
    }

    public int getDeckLevel() {
        return deckLevel;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return "Room: " + index + " Deck Level: " + deckLevel + " Room Number: " + roomNumber;
    }
}
