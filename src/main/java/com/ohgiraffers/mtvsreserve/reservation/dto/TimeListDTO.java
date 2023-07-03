package com.ohgiraffers.mtvsreserve.reservation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeListDTO {
    private int time;
    private String timeString;

    public TimeListDTO(int time, String timeString) {
        this.time = time;
        this.timeString = timeString;
    }
}
