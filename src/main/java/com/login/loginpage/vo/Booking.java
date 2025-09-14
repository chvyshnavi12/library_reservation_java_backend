package com.login.loginpage.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    private String time;          // e.g. "2/5/2025, 10:00 AM"
    private int floorNumber;      // e.g. 3
    private int totalSeats;       // e.g. 2
    private List<String> seats;
    // e.g. ["A1", "A2"]

    public void setFloor(String floor) {
        this.floorNumber = (floor == null || floor.isEmpty()) ? 1 : Integer.parseInt(floor);
    }

}


