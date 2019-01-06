package com.example.wako.contextmanagementactivity;

/*
*  store property of room
*   represent one room displaying on that moment*/
public class RoomContextState {

    private String room;
    private String status;
    private int LightLevel;

    public RoomContextState(String room, String status, int LightLevel) {
        super();
        this.room = room;
        this.status = status;
        this.LightLevel = LightLevel;
    }

    public String getRoom() {
        return this.room;
    }

    public String getLightStatus() {
        return this.status;
    }

    public int getLightLevel() {
        return this.LightLevel;
    }
}