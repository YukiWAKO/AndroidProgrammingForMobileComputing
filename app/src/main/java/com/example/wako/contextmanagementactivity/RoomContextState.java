package com.example.wako.contextmanagementactivity;

/*
*  store property of room
*   represent one room displaying on that moment*/
public class RoomContextState {

    private String room;
    private String status;
    private int LightLevel;

    public RoomContextState(String room, int LightLevel, String status) {
        super();
        this.room = room;
        this.LightLevel = LightLevel;
        this.status = status;
    }

    public String getRoom() {
        return this.room;
    }

    public int getLightLevel() {
        return this.LightLevel;
    }

    public String getStatus(){ return this.status; }
}