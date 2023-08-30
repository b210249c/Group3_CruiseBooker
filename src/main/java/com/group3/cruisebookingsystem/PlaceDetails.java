package com.group3.cruisebookingsystem;

public class PlaceDetails {
    private int index;
    private String selectedPlace;
    public PlaceDetails(int index, String selectedPlace){
        this.index = index;
        this.selectedPlace = selectedPlace;
    }

    public int getIndex() {
        return index;
    }

    public String getSelectedPlace() {
        return selectedPlace;
    }

    @Override
    public String toString() {
        return "Room: " + index + " Place: " + selectedPlace;
    }
}
